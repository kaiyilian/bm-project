package com.bumu.arya.admin.payroll.service.impl;

import com.bumu.SysUtils;
import com.bumu.arya.admin.common.service.AryaAdminConfigService;
import com.bumu.arya.admin.payroll.controller.command.ESalaryCommand;
import com.bumu.arya.admin.payroll.result.PayrollDetailResult;
import com.bumu.arya.admin.payroll.result.PayrollManagerResult;
import com.bumu.arya.admin.payroll.result.PayrollUserResult;
import com.bumu.arya.admin.payroll.service.PayrollService;
import com.bumu.arya.command.OrderCommand;
import com.bumu.arya.command.PagerCommand;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.bran.model.dao.BranCorporationDao;
import com.bumu.bran.model.entity.BranCorporationEntity;
import com.bumu.common.service.ReadFileResponseService;
import com.bumu.payroll.model.dao.BranSalaryUserRelationDao;
import com.bumu.esalary.model.dao.mybatis.ESalaryMybatisDao;
import com.bumu.payroll.model.entity.PayrollInfoEntity;
import com.bumu.payroll.model.entity.PayrollUserRelationEntity;
import com.bumu.payroll.result.AryaAdminInfoResult;
import com.bumu.exception.AryaServiceException;
import com.bumu.payroll.model.dao.PayrollInfoDao;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.bumu.SysUtils.DATE_FORMAT_EXPORT;

/**
 * Created by liangjun on 17-7-26.
 */
@Service
public class PayrollServiceImpl implements PayrollService {
    public static final String salaryTemplateURL = "admin/esalary/download";
    public static final int salaryTemplateReadLength = 200 * 1024;//每次读取200k
    private static Logger logger = LoggerFactory.getLogger(PayrollServiceImpl.class);
//    @Autowired
//    BranESalaryCropUserDao branESalaryCropUserDao;

    @Autowired
    PayrollInfoDao payrollInfoDao;

    @Autowired
    BranSalaryUserRelationDao branSalaryUserRelationDao;

    @Autowired
    ReadFileResponseService readFileResponseService;

    @Autowired
    AryaAdminConfigService configService;

    @Autowired
    private BranCorporationDao branCorporationDao;

    @Autowired
    private ESalaryMybatisDao eSalaryMybatisDao;

    @Override
    public PayrollManagerResult getCorpUser(ESalaryCommand eSalaryCommand, PagerCommand pagerCommand, OrderCommand orderCommand) throws Exception {
        PayrollManagerResult payrollManagerResult = new PayrollManagerResult();
        List<PayrollUserResult> eSalaryCorpUsers = new ArrayList<>();
        String notInIds = configService.getPayrollExclusionCorpIds();
        List<String> notIdsList = new ArrayList<>();
        if (StringUtils.isNotBlank(notInIds)) {
            notIdsList = Arrays.asList(notInIds.split(","));
        }


        logger.info("notInIds: " + notInIds);
        logger.info("order: " + orderCommand.toString());
        payrollManagerResult.seteSalaryCorpUsers(eSalaryCorpUsers);

        List<String> queryIds = notIdsList;

        Page<AryaAdminInfoResult> allByKeyword = PageHelper.startPage(pagerCommand.getPage(), pagerCommand.getPage_size()).doSelectPage(() ->
                eSalaryMybatisDao.getCorpInfo(eSalaryCommand.getKeyword(), queryIds, orderCommand.getOrder(), orderCommand.getOrderParam())
        );

        List<AryaAdminInfoResult> result = allByKeyword.getResult();
        PayrollUserResult payrollUserResult = null;
        for (AryaAdminInfoResult aryaAdminInfoResult : result) {
            payrollUserResult = new PayrollUserResult();
            payrollUserResult.setId(aryaAdminInfoResult.getId());
            payrollUserResult.setLoginName(aryaAdminInfoResult.getLoginName());
            payrollUserResult.setCorpName(aryaAdminInfoResult.getCorpName());
            payrollUserResult.setUserName(aryaAdminInfoResult.getHrName());
            payrollUserResult.setUseTimes(aryaAdminInfoResult.getUseTimes());
            payrollUserResult.setPayrollNumber(aryaAdminInfoResult.getPayrollNumber());
            SysUtils.getDateStringFormTimestamp(aryaAdminInfoResult.getCreateTime(), DATE_FORMAT_EXPORT);
            eSalaryCorpUsers.add(payrollUserResult);
        }
        payrollManagerResult.setPages(allByKeyword.getPages());
        payrollManagerResult.setRowCount((int) allByKeyword.getTotal());
        return payrollManagerResult;
    }

    @Override
    public List<PayrollDetailResult> getSalaryInfo(String corpId) {
        List<PayrollDetailResult> payrollDetailResults = new ArrayList<>();
        PayrollDetailResult payrollDetailResult = null;
        List<PayrollInfoEntity> byCorpId = payrollInfoDao.findByCorpIdNotHrData(corpId);
        if (byCorpId == null) {
            throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "该公司没有发送过薪资条");
        }
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy/MM/dd");

        for (PayrollInfoEntity payrollInfoEntity : byCorpId) {
            payrollDetailResult = new PayrollDetailResult();
            List<PayrollUserRelationEntity> bySalaryId
                    = branSalaryUserRelationDao.findBySalaryId(payrollInfoEntity.getId());
            if (bySalaryId == null) {
                continue;
            }
            //导入时间
            String importTime = String.format("%s/%s", payrollInfoEntity.getInfoYear(), payrollInfoEntity.getInfoMonth());
            payrollDetailResult.setImportTime(importTime);
            //发送时间
            String sendTime = format2.format(payrollInfoEntity.getSendTime());
            payrollDetailResult.setSendTime(sendTime);
            //发送人数
            payrollDetailResult.setSendNum(bySalaryId.size() + "");

            payrollDetailResult.setSalaryId(payrollInfoEntity.getId());
            //拼接url
            payrollDetailResult.setUrl(salaryTemplateURL +
                    "?salaryId=" + payrollInfoEntity.getId());

            payrollDetailResults.add(payrollDetailResult);
        }

        return payrollDetailResults;
    }

    @Override
    public String downloadFile(String salaryId) {
        PayrollInfoEntity byId = payrollInfoDao.findByIdSendSuccess(salaryId);
        if (byId == null) {
            throw new AryaServiceException(ErrorCode.CODE_NOT_FIND_SALARY, "薪资条没有发现");
        }
        String sendTime = String.format("%s-%s", byId.getInfoYear(), byId.getInfoMonth());
        String fileName = byId.getFileName();
        if (StringUtils.isEmpty(fileName)) {
            throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "上传的excel文档丢失,疑是老数据");
        }
        String[] split = fileName.split("\\.");

        if (!(split.length > 1)) {
            throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "上传的excel文档丢失,疑是老数据");
        }
        String fileType = split[1];

        BranCorporationEntity branCorporationEntity = branCorporationDao.findByIdNotDelete(byId.getBranCorpId());

        if (branCorporationEntity == null) {
            throw new AryaServiceException(ErrorCode.CODE_NOT_FIND_SALARY, "工资条对应的公司没有查询到");
        }
        String filePath = configService.getFilePath("temp", 0) + fileName;
        logger.info("电子工资单原始文件下载地址" + filePath);
        File file = new File(filePath);
        if (!file.exists()) {
            throw new AryaServiceException(ErrorCode.CODE_NOT_FIND_SALARY, "下载的文件丢失");
        }
        String corpName = branCorporationEntity.getCorpName();

        String realFileName = corpName + " " + sendTime + "." + fileType;

        return "admin/esalary/download/file?realFileName=" + realFileName + "&fileName=" + fileName;
    }


    @Override
    public void downloadFile(HttpServletResponse response, String realFileName, String fileName) throws AryaServiceException {
        try {
            response.setContentType("application/vnd.ms-excel");
            response.addHeader("Content-Disposition", "attachment; filename=\"" +
                    SysUtils.parseEncoding(realFileName, "UTF-8") + "\"");
            String filePath = configService.getFilePath("temp", 0) + fileName;
            logger.info("电子工资单原始文件下载地址" + filePath);
            readFileResponseService.readFileToResponse(filePath, salaryTemplateReadLength, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
