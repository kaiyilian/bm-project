package com.bumu.arya.admin.payroll.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumu.SysUtils;
import com.bumu.arya.IdcardValidator;
import com.bumu.arya.admin.common.service.AryaAdminConfigService;
import com.bumu.arya.admin.corporation.constant.CorpConstants;
import com.bumu.arya.admin.corporation.service.CorporationService;
import com.bumu.arya.admin.misc.service.AryaUserService;
import com.bumu.arya.admin.payroll.result.WalletPaysCorpResult;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.model.entity.CorporationEntity;
import com.bumu.arya.response.HttpResponse;
import com.bumu.arya.wallet.model.entity.WalletUserEntity;
import com.bumu.arya.wallet.service.WalletCommonService;
import com.bumu.common.constant.RedisConstants;
import com.bumu.common.service.RedisService;
import com.bumu.common.util.StringUtil;
import com.bumu.employee.model.entity.EmployeeEntity;
import com.bumu.paysalary.result.WalletPaySalaryPreviewResult;
import com.bumu.paysalary.service.WalletPaySalaryApplyService;
import com.bumu.utils.PayNoUtils;
import io.swagger.annotations.Api;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * @author yousihang
 * @date 2018/3/21
 */
@Controller
@Api(tags = {"钱包发薪"})
@RequestMapping(value = "/admin/wallet/pays")
public class WalletPaysController {

    private static Logger LOGGER = LoggerFactory.getLogger(PayrollController.class);


    @Autowired
    private AryaAdminConfigService aryaAdminConfigService;

    @Autowired
    private WalletPaySalaryApplyService walletPaySalaryApplyService;

    @Autowired
    private WalletCommonService walletCommonService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private AryaUserService aryaUserService;

    @Autowired
    private CorporationService corporationService;

    private static final String[] HEADERS = {"NO.", "姓名", "身份证号码", "钱包账号", "薪资", "备注"};


    /**
     * 首页
     *
     * @param response
     * @return
     */
    @RequestMapping(value = "/preview/index", method = RequestMethod.GET)
    public String previewIndex(HttpServletResponse response) throws IOException {
      return "salary/wallet_pay_salary_preview";
    }

    /**
     * 查询开通发薪企业
     *
     * @param response
     * @return
     */
    @RequestMapping(value = "/corp/list", method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse corporationCorpList(HttpServletResponse response) throws IOException {
        List<CorporationEntity> li = corporationService.findNoParentCorpsByBusinessType(CorpConstants.CORP_BUSINESS_WALLETPAYS);

        List<WalletPaysCorpResult> results = new ArrayList<>(2);
        for (CorporationEntity e : li) {
            WalletPaysCorpResult walletPaysCorpResult = new WalletPaysCorpResult(
                    e.getId(), e.getBranCorpId(), e.getName(), e.getShortName()
            );

            results.add(walletPaysCorpResult);
        }

        return new HttpResponse<>(results);
    }

    /**
     * 下载导入模板
     *
     * @param response
     * @return
     */
    @RequestMapping(value = "/export/template", method = RequestMethod.GET)
    @ResponseBody
    public void exportTemplateDownload(HttpServletResponse response) throws IOException {
        String templatePath = "";
        response.setContentType("application/vnd.ms-excel");
        String headStr = "attachment;filename=\"" + SysUtils.parseGBK("薪资导入模板.xlsx") + "\"";
        response.setHeader("Content-Disposition", headStr);
        templatePath = aryaAdminConfigService.getExportTemplatePath() + AryaAdminConfigService.WALLET_PAYS_IMPORT;

        FileInputStream excelFileInputStream = new FileInputStream(templatePath);
        //读取模板
        XSSFWorkbook workbook = new XSSFWorkbook(excelFileInputStream);
        excelFileInputStream.close();

        workbook.write(response.getOutputStream());
        response.getOutputStream().close();
    }


    /**
     * 上传文件并计算
     * <p>
     * <p>
     * • 姓名必须是0-10位汉字
     * • 身份证号码格式不对
     * • 钱包账号格式不对
     * • 转账金额必须为数字，最多包含两位小数
     * • 姓名不能为空
     * • 身份证不能为空
     * • 钱包账号不能为空
     * • 转账金额不能为空
     * • 姓名、身份证、钱包账号三者不匹配
     * • 尚未绑定当前选择的项目
     * date YYYY-DD
     * @param file
     * @return
     */
    @RequestMapping(value = "/import/template", method = RequestMethod.POST)
    public
    @ResponseBody
    HttpResponse uploadAndCalculateOrder(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws Exception {
        String corpId= request.getParameter("corpId");
        if(StringUtil.isEmptyStr(corpId)){
            return new HttpResponse<>(ErrorCode.CODE_PARAMS_ERROR);
        }

        //读文件
        FileInputStream excelFileInputStream = (FileInputStream) file.getInputStream();
        //得到Excel工作簿对象
        XSSFWorkbook wb = new XSSFWorkbook(excelFileInputStream);
        //遍历Excel工作表对象
        Sheet sheet = wb.getSheetAt(0);
        //遍历Excel工作表的行
        String batchNo = PayNoUtils.generatePayOrderNo("XZ");
        StringBuffer prostr = new StringBuffer();
        List<WalletPaySalaryPreviewResult> li = new ArrayList<>(2);

        Double rightMoney = 0d;
        Double badMoney = 0d;
        Integer rightCount = 0;
        Integer badCount = 0;

        Map<String, Object> result = new HashMap<>(2);

        for (int j = 0; j < sheet.getPhysicalNumberOfRows(); j++) {
            prostr.setLength(0);
            Row row = sheet.getRow(j);
            //判断该sheet是否为导入模板，不是则进行下一个sheet
            if (j == 1) {
                int i = 0;
                if (row.getPhysicalNumberOfCells() != HEADERS.length) {
                    return new HttpResponse<>(ErrorCode.CODE_IMPORT_TITLE_MISS_ERR);
                }
                for (String head : HEADERS) {
                    String name = row.getCell(i).getStringCellValue();
                    if (!name.equals(head)) {
                        return new HttpResponse<>(ErrorCode.CODE_IMPORT_FIELD_TYPE_ERR);
                    }
                    i++;
                }
            }
            if (j >=2) {
                WalletPaySalaryPreviewResult previewResult = new WalletPaySalaryPreviewResult();
                Boolean isRight = true;
                //判断第一个cell内容是否为编号
                Cell cell1 =row.getCell(0);
                if( cell1==null){
                    break;
                }

                String no = cell1.getStringCellValue();
                if (StringUtil.isEmptyStr(no)) {
                    prostr.append("序号为空,");
                    isRight = false;
                }

                Cell cell2 = row.getCell(1);
                String name = cell2.getStringCellValue();
                if (StringUtil.isEmptyStr(name)) {
                    prostr.append("姓名不正确,");
                    isRight = false;
                }
                previewResult.setUserName(name);

                Cell cell3 = row.getCell(2);
                String ID = cell3.getStringCellValue();
                if (StringUtil.isEmptyStr(ID) || !IdcardValidator.isIdcard(ID)) {
                    prostr.append("身份证不正确,");
                    isRight = false;
                }
                previewResult.setCardNo(ID);

                if(isRight){
                   List<EmployeeEntity> entities= aryaUserService.getEmployeeUserById(corpId,ID);
                   if(entities.isEmpty()){
                       prostr.append("该员工不属于此项目,");
                       isRight = false;
                   }
                }

                Cell cell4 = row.getCell(3);
                String walletId = cell4.getStringCellValue();
                if (StringUtil.isEmptyStr(walletId)) {
                    prostr.append("钱包账号不正确,");
                    isRight = false;
                }
                if(!StringUtil.isAllNotEmpty(walletId,ID,name)){
                    continue;
                }

                if(isRight){
                  List<WalletUserEntity> userEntities= walletCommonService.findWalletUser(name,ID,walletId);
                  if(userEntities.isEmpty()){
                      prostr.append("姓名、身份证、钱包账号三者不匹配,");
                      isRight = false;
                  }
                }

                previewResult.setWalletUserId(walletId);

                Cell cell5 = row.getCell(4);
                String money = cell5.getStringCellValue();
                if (StringUtil.isEmptyStr(money) || !StringUtil.checkNum(money)){
                    prostr.append("金额输入不正确,");
                    money = "0";
                    isRight = false;
                }
                if(money.contains(".")){
                   if(money.split("\\.").length!=2||money.split("\\.")[1].length()!=2){
                       prostr.append("金额输入不正确,");
                       money = "0";
                       isRight = false;
                   }
                }

                Double Money = Double.valueOf(money);
                previewResult.setMoney(money);
                if (!isRight) {
                    badMoney += Money;
                    badCount++;
                } else {
                    rightMoney += Money;
                    rightCount++;
                }
                Cell cell6 = row.getCell(5);
                String memo = cell6.getStringCellValue();
                previewResult.setMemo(memo);
                previewResult.setProblem(prostr.length()!=0?prostr.toString().split(",")[0]:"");
                previewResult.setRight(isRight);
                previewResult.setNo(no);
                redisService.hset(RedisConstants.ARYA_WALLET_PAYS_USER_MAP + batchNo, no, JSON.toJSONString(previewResult));
                li.add(previewResult);
            }
        }

        result.put("list", li);
        result.put("badMoney", badMoney);
        result.put("badCount", badCount);
        result.put("rightMoney", rightMoney);
        result.put("rightCount", rightCount);
        result.put("totalMoney", badMoney + rightMoney);
        result.put("totalCount", badCount + rightCount);
        result.put("batchNo", batchNo);
        return new HttpResponse<>(result);
    }



    /**
     * 删除
     *
     * @param response
     * @return
     */
    @RequestMapping(value = "/preview/data/delete", method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse previewDataDelete(HttpServletResponse response,@RequestParam  String batchNo,@RequestParam String no) throws IOException {
        if(!StringUtil.isAllNotEmpty(no,batchNo)){
            return new HttpResponse<>(ErrorCode.CODE_PARAMS_ERROR);
        }
        redisService.hdel(RedisConstants.ARYA_WALLET_PAYS_USER_MAP + batchNo, no);
        return new HttpResponse<>(ErrorCode.CODE_OK);
    }

    /**
     *保存
     * @param response
     * @return
     */
    @RequestMapping(value = "/preview/data/save", method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse previewDataSave(HttpServletResponse response,HttpServletRequest request) throws IOException {
        request.getParameterMap();
        String corpId= request.getParameter("corpId");
        String date= request.getParameter("date");
        String batchNo= request.getParameter("batchNo");
        Double amount=0d;
        if(!StringUtil.isAllNotEmpty(corpId,date)){
            return new HttpResponse<>(ErrorCode.CODE_PARAMS_ERROR);
        }
        List<WalletPaySalaryPreviewResult> li =new ArrayList<>();
        Set<String> set= redisService.hkeys(RedisConstants.ARYA_WALLET_PAYS_USER_MAP + batchNo);
        if(set!=null){
            for (String str:set){
                String json= redisService.hget(RedisConstants.ARYA_WALLET_PAYS_USER_MAP+batchNo,str);
                WalletPaySalaryPreviewResult previewResult= JSONObject.parseObject(json,WalletPaySalaryPreviewResult.class);
                amount+=Double.valueOf(previewResult.getMoney());
                li.add(previewResult);
            }
            walletPaySalaryApplyService.savePreviewData(li,corpId,date,batchNo,amount);
        }

        redisService.del(RedisConstants.ARYA_WALLET_PAYS_USER_MAP+batchNo);
        return new HttpResponse<>(ErrorCode.CODE_OK);
    }



}
