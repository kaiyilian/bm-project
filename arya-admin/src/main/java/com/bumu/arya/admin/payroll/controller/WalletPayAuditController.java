package com.bumu.arya.admin.payroll.controller;

import com.bumu.arya.admin.corporation.constant.CorpConstants;
import com.bumu.arya.admin.corporation.service.CorporationService;
import com.bumu.arya.admin.payroll.manager.WalletPayAuditManager;
import com.bumu.arya.admin.payroll.result.WalletPayAuditQueryApplyResult;
import com.bumu.arya.admin.payroll.result.WalletPayAuditQueryDetailResult;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.model.entity.CorporationEntity;
import com.bumu.arya.response.HttpResponse;
import com.bumu.common.result.DownloadResult;
import com.bumu.common.result.Pager;
import com.bumu.exception.AryaServiceException;
import com.bumu.paysalary.command.WalletPaySalaryApplyCommand;
import com.bumu.paysalary.command.WalletPaySalaryApplyDetailCommand;
import com.bumu.paysalary.enums.BatchTradeStatusEnum;
import com.bumu.paysalary.enums.TradeStatusEnum;
import com.bumu.paysalary.model.entity.WalletPaySalaryApplyDetailEntity;
import com.bumu.paysalary.model.entity.WalletPaySalaryApplyEntity;
import com.bumu.paysalary.service.WalletPaySalaryApplyDetailService;
import com.bumu.paysalary.service.WalletPaySalaryApplyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * 钱包发薪申请审核
 *
 * @author xiexuefeng
 * @date 2018/03/21
 */
@Deprecated
@Api(value = "WalletPayAuditController", tags = {"钱包发薪申请审核WalletPayAuditManager"})
@RequestMapping(value = "/admin/wallet/pay/audit")
@Controller
public class WalletPayAuditController {
    Logger log = LoggerFactory.getLogger(WalletPayAuditController.class);

    @Autowired
    WalletPaySalaryApplyDetailService walletPaySalaryApplyDetailService;

    @Autowired
    WalletPaySalaryApplyService walletPaySalaryApplyService;

    @Autowired
    WalletPayAuditManager walletPayAuditManager;

    @Autowired
    private CorporationService corporationService;

    /**
     * 审核批次记录页面
     *
     * @return
     */
    @Deprecated
    @RequestMapping(value = "/apply/index", method = RequestMethod.GET)
    public String applyIndex(Model model) {
        List<CorporationEntity> list = corporationService.findNoParentCorpsByBusinessType(CorpConstants.CORP_BUSINESS_ECONTRACT);
        model.addAttribute("projectList",list);
        model.addAttribute("tradeList", BatchTradeStatusEnum.list);
        return "salary/wallet_pay_salary_apply";
    }

    /**查询需要审核的批次记录
     *
     * @param command
     * @return
     */
    @Deprecated
    @RequestMapping(value = "/apply/query", method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse queryApply(WalletPaySalaryApplyCommand command,
            @ApiParam @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @ApiParam @RequestParam(value = "page", required = false, defaultValue = "1") Integer page) {
        try {
            log.info("startTime:&&&&&&&&&&&&&&&"+command.getBeginTime());
            log.info("batchNo:&&&&&&&&&&&&&&&"+command.getBatchNo());
            command.setPage(page);
            command.setPageSize(pageSize);
            List<WalletPaySalaryApplyEntity> list = walletPaySalaryApplyService.find(command);
            List<WalletPayAuditQueryApplyResult> resultList=new ArrayList<WalletPayAuditQueryApplyResult>();
            if(list!=null){
                for(WalletPaySalaryApplyEntity apply:list){
                    CorporationEntity corporation = corporationService.findByBranId(apply.getProjectId());

                    WalletPayAuditQueryApplyResult r=new WalletPayAuditQueryApplyResult();
                    r.copy(apply);
                    r.setProjectName(corporation!=null?corporation.getName():"");
                    resultList.add(r);
                }
            }
            Pager<WalletPayAuditQueryApplyResult> resultPage = new Pager<WalletPayAuditQueryApplyResult>();
            resultPage.setResult(resultList);
            resultPage.setPage(page);
            resultPage.setRowCount(walletPaySalaryApplyService.count(command));
            resultPage.setPageSize(pageSize);

            return new HttpResponse(ErrorCode.CODE_OK, resultPage);
        } catch (AryaServiceException e) {
            return new HttpResponse<>(e.getErrorCode());
        }
    }


    /**
     * 审核批次记录详情页面
     *
     * @return
     */
    @Deprecated
    @RequestMapping(value = "/detail/index", method = RequestMethod.GET)
    public String detailIndex(@ApiParam @RequestParam(value = "applyId", required = false, defaultValue = "1") Long applyId,Model model) {
        WalletPaySalaryApplyEntity entity = walletPaySalaryApplyService.findById(applyId);
        model.addAttribute("apply",entity);
        model.addAttribute("tradeList", TradeStatusEnum.list);
        return "salary/wallet_pay_salary_detail";
    }

    /**查询需要审核的批次记录的详情
     *
     * @param command
     * @return
     */
    @Deprecated
    @RequestMapping(value = "/detail/query", method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse queryDetail(WalletPaySalaryApplyDetailCommand command,
            @ApiParam @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @ApiParam @RequestParam(value = "page", required = false, defaultValue = "1") Integer page) {
        try {
            command.setPage(page);
            command.setPageSize(pageSize);
            List<WalletPaySalaryApplyDetailEntity> list = walletPaySalaryApplyDetailService.find(command);

            List<WalletPayAuditQueryDetailResult> resultList=new ArrayList<WalletPayAuditQueryDetailResult>();
            if(list!=null){
                for(WalletPaySalaryApplyDetailEntity apply:list){
                    WalletPayAuditQueryDetailResult r=new WalletPayAuditQueryDetailResult();
                    r.copy(apply);
                    resultList.add(r);
                }
            }
            Pager<WalletPayAuditQueryDetailResult> resultPage = new Pager<WalletPayAuditQueryDetailResult>();
            resultPage.setResult(resultList);
            resultPage.setPage(page);
            resultPage.setRowCount(walletPaySalaryApplyDetailService.count(command));
            resultPage.setPageSize(pageSize);

            return new HttpResponse(ErrorCode.CODE_OK, resultPage);
        } catch (AryaServiceException e) {
            return new HttpResponse<>(e.getErrorCode());
        }
    }

    /**同意发薪
     *
     * @param id
     * @param session
     * @return
     */
    @Deprecated
    @RequestMapping(value = "/detail/approve", method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse approve(@RequestParam(name="id") String id, HttpSession session) {
        try {
            String[] ids=id.split(",");
            for(String detailId:ids){
                walletPayAuditManager.approve(Long.parseLong(detailId),(String)session.getAttribute("account"));
            }
            return new HttpResponse(ErrorCode.CODE_OK,"操作完成");
        } catch (AryaServiceException e) {
            return new HttpResponse<>(e.getErrorCode()!=null?e.getErrorCode():e.getMessage());
        }catch (Exception e) {
            return new HttpResponse<>(e.getMessage());
        }
    }

    /**拒绝发薪
     *
     * @param id
     * @param session
     * @return
     */
    @Deprecated
    @RequestMapping(value = "/detail/reject", method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse reject(@RequestParam(name="id") String id, HttpSession session) {
        try {
            String[] ids=id.split(",");
            for(String detailId:ids){
                walletPayAuditManager.reject(Long.parseLong(detailId),(String)session.getAttribute("account"));
            }
            return new HttpResponse(ErrorCode.CODE_OK,"操作完成");
        } catch (AryaServiceException e) {
            return new HttpResponse<>(e.getErrorCode());
        }
    }

    @ApiOperation(value = "获取导出文件地址")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "/detail/export", method = RequestMethod.GET)
    public HttpResponse<DownloadResult> export(WalletPaySalaryApplyDetailCommand command) {
        return new HttpResponse(ErrorCode.CODE_OK, walletPayAuditManager.export(command));
    }
}
