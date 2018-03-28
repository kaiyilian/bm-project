package com.bumu.arya.admin.payroll.manager.impl;

import com.bumu.arya.admin.common.service.AryaAdminConfigService;
import com.bumu.arya.admin.common.service.FileDownService;
import com.bumu.arya.admin.payroll.manager.WalletPayAuditManager;
import com.bumu.arya.common.Constants;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.model.entity.AryaUserEntity;
import com.bumu.arya.wallet.constants.WalletConstants;
import com.bumu.arya.wallet.model.entity.WalletUserEntity;
import com.bumu.arya.wallet.model.result.WalletOrderCreateResult;
import com.bumu.arya.wallet.model.result.WalletQueryBalanceResult;
import com.bumu.arya.wallet.model.result.WalletWSResult;
import com.bumu.arya.wallet.service.WalletCommonService;
import com.bumu.arya.wallet.service.WalletOrderCommonService;
import com.bumu.arya.wallet.service.WalletService;
import com.bumu.common.result.DownloadResult;
import com.bumu.exception.AryaServiceException;
import com.bumu.paysalary.command.WalletPaySalaryApplyDetailCommand;
import com.bumu.paysalary.enums.AuditStatusEnum;
import com.bumu.paysalary.enums.TradeStatusEnum;
import com.bumu.paysalary.model.entity.WalletPaySalaryApplyDetailEntity;
import com.bumu.paysalary.service.WalletPaySalaryApplyDetailService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class WalletPayAuditManagerImpl implements WalletPayAuditManager {
    private static Logger logger = LoggerFactory.getLogger(WalletPayAuditManagerImpl.class);
    @Autowired
    private WalletPaySalaryApplyDetailService walletPaySalaryApplyDetailService;
    @Autowired
    private WalletService walletService;
    @Autowired
    private WalletOrderCommonService walletOrderCommonService;
    @Autowired
    private WalletCommonService walletCommonService;
    @Autowired
    private FileDownService fileDownService;
    /**同意发薪
     *
     * @param id
     * @return
     */
    @Transactional
    public void approve(Long id, String account) throws Exception{
        WalletPaySalaryApplyDetailEntity detail = walletPaySalaryApplyDetailService.findById(id);
        if(!TradeStatusEnum.WAIT.getCode().equals(detail.getTradeStatus())){
            throw new AryaServiceException(ErrorCode.CODE_TXVERSION_ERROR);
        }

        WalletUserEntity walletUserEntity = walletCommonService.findWalletUser(null, null, detail.getWalletUserId()).get(0);
        AryaUserEntity aryaUserEntity = new AryaUserEntity();
        aryaUserEntity.setId(walletUserEntity.getUserId());
        WalletQueryBalanceResult balanceResult = walletCommonService.queryBalance(aryaUserEntity);
        //2.生成订单
        String orderNo = walletOrderCommonService.generatorOrderNo(detail.getWalletUserId(), null);

        logger.info("生成流水号->" + orderNo);
        Map<String, String> orderCreateParams = new HashMap<>();
        orderCreateParams.put("userId", walletUserEntity.getUserId());
        orderCreateParams.put("walletUserId", walletUserEntity.getWalletUserId());
        orderCreateParams.put("orderAmount", detail.getAmount());
        orderCreateParams.put("orderNo", orderNo);
        WalletOrderCreateResult orderCreateResult = walletOrderCommonService.createOrder(orderCreateParams, WalletConstants.OrderType.salary);

        orderCreateResult.getWalletOrderEntity().setIsDelete(Constants.TRUE);
        walletOrderCommonService.createOrder(orderCreateResult.getWalletOrderEntity(), orderCreateResult.getWalletOrderFlowEntityList());
        logger.info("生成订单流水");
        String walletUserInfo = detail.getWalletUserId().trim()
                + "^" + detail.getUserName().trim() +
                "^" + detail.getCardNo().trim();
        //WalletWSResult walletWSResult =walletService.transferm2u(walletUserInfo,detail.getAmount(), WalletKeysConstants.notifyUrl,orderNo);//TODO
        WalletWSResult walletWSResult =new WalletWSResult();
        walletWSResult.setSuccess(true);

        logger.info("调用第三方商户-用户转账接口");
        Map<String, String> returnResultMap = new Gson().fromJson(walletWSResult.getResultJson(), Map.class);
        if (walletWSResult.getSuccess()) {
            logger.info("转账请求成功，生成订单");
            orderCreateResult.getWalletOrderEntity().setIsSuccess(Constants.TRUE);
            orderCreateResult.getWalletOrderEntity().setIsFinish(Constants.TRUE);
            orderCreateResult.getWalletOrderEntity().setIsFinalSuccess(Constants.TRUE);
            orderCreateResult.getWalletOrderEntity().setIsDelete(Constants.FALSE);
            orderCreateResult.getWalletOrderEntity().setTransNo(returnResultMap.get("transNo"));
            orderCreateResult.getWalletOrderEntity().setBalance(new BigDecimal(balanceResult.getBalance()).add(new BigDecimal(detail.getAmount())).toString());
            logger.info("记录充值订单与银行卡信息");

            detail.setTradeStatus(TradeStatusEnum.SUCCESS.getCode());
        }else{
            logger.info("转账请求失败："+walletWSResult.getMsg() + "(" + walletWSResult.getCode() + ")");
            detail.setTradeStatus(TradeStatusEnum.FAIL.getCode());
            detail.setTradeMsg(walletWSResult.getMsg());
        }

        //将订单和流程实例化
        walletOrderCommonService.updateOrder(orderCreateResult.getWalletOrderEntity());

        detail.setAuditStatus(AuditStatusEnum.AUDIT.getCode());
        detail.setAuditTime(new Date());
        detail.setAuditUser(account);
        detail.setVersion(detail.getVersion()+1);
        walletPaySalaryApplyDetailService.save(detail);
    }

    /**拒绝发薪
     *
     * @param id
     * @return
     */
    @Transactional
    public void reject(Long id,String account) {
        WalletPaySalaryApplyDetailEntity detail = walletPaySalaryApplyDetailService.findById(id);
        if(!TradeStatusEnum.WAIT.getCode().equals(detail.getTradeStatus())){
            throw new AryaServiceException(ErrorCode.CODE_TXVERSION_ERROR);
        }

        detail.setAuditStatus(AuditStatusEnum.REJECT.getCode());
        detail.setAuditTime(new Date());
        detail.setAuditUser(account);
        detail.setTradeStatus(TradeStatusEnum.REJECT.getCode());
        walletPaySalaryApplyDetailService.save(detail);
    }

    /**导出钱包发薪明细记录
     *
     * @param command
     * @return
     */
    @Override
    public DownloadResult export(WalletPaySalaryApplyDetailCommand command) {
        String url = "";
        try {
            command.setPage(null);
            command.setPageSize(null);
            List<WalletPaySalaryApplyDetailEntity> list = walletPaySalaryApplyDetailService.find(command);

            url = fileDownService.getExelDownUrl(list,"发薪明细记录", AryaAdminConfigService.WALLET_PAY_SALARY_IMPORT);

            logger.info("url: " + url);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new DownloadResult(url);
    }
}
