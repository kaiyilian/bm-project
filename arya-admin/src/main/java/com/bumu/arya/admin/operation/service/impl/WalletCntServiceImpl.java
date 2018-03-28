package com.bumu.arya.admin.operation.service.impl;

import com.bumu.arya.admin.common.service.AryaAdminConfigService;
import com.bumu.arya.admin.common.service.FileDownService;
import com.bumu.arya.admin.operation.model.dao.mybatis.WalletCntMybatisDao;
import com.bumu.arya.admin.operation.result.WalletUserCntResult;
import com.bumu.arya.admin.operation.result.WalletUserInfoResult;
import com.bumu.arya.admin.operation.service.WalletCntService;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.model.AryaUserDao;
import com.bumu.arya.model.entity.AryaUserEntity;
import com.bumu.arya.wallet.dao.WalletBankCardDao;
import com.bumu.arya.wallet.dao.WalletUserDao;
import com.bumu.arya.wallet.model.entity.WalletBankCardEntity;
import com.bumu.arya.wallet.model.entity.WalletUserEntity;
import com.bumu.arya.wallet.service.WalletService;
import com.bumu.common.result.FileUploadFileResult;
import com.bumu.common.result.Pager;
import com.bumu.exception.AryaServiceException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author majun
 * @date 2017/12/26
 * @email 351264830@qq.com
 */
@Service
public class WalletCntServiceImpl implements WalletCntService {

    private static Logger logger = LoggerFactory.getLogger(WalletCntServiceImpl.class);

    @Autowired
    private WalletCntMybatisDao walletCntMybatisDao;

    @Autowired
    private FileDownService fileDownService;

    @Autowired
    private WalletUserDao walletUserDao;

    @Autowired
    private WalletService walletService;

    @Autowired
    private AryaUserDao aryaUserDao;

    @Autowired
    private WalletBankCardDao walletBankCardDao;

    @Override
    public Pager<WalletUserCntResult> userCntPager(String param, Integer pageSize, Integer page) {
        List<WalletUserCntResult> walletUserCntResults = walletCntMybatisDao.walletUserCntList(StringUtils.isBlank(param) ? param : param.trim());
        // 取分页数据
        Pager<WalletUserCntResult> result = new Pager<>();
        logger.info("将计算结果分页---page:" + page + ",pageSize:" + pageSize);
        Integer total = walletUserCntResults.size();
        result.setResult(walletUserCntResults.subList(pageSize * (page - 1), ((pageSize * page) > total ? total : (pageSize * page))));
        result.setPage(page);
        result.setRowCount(walletUserCntResults.size());
        result.setPageSize(pageSize);
        return result;
    }

    @Override
    public FileUploadFileResult export() {
        FileUploadFileResult result = new FileUploadFileResult();
        result.setUrl(fileDownService.getExelDownUrl(walletCntMybatisDao.walletUserCntList(null), "钱包用户统计明细", AryaAdminConfigService.WALLET_USER_IMPORT));
        return result;
    }

    @Override
    public WalletUserInfoResult userInfo(String phone) throws Exception{
        if (StringUtils.isAnyBlank(phone)) {
            throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "请输入有效的用户App手机号");
        }
        //1.通过APP手机号查找APP用户
        AryaUserEntity aryaUserEntity = aryaUserDao.findUserByPhoneNo(phone.trim());
        if (null == aryaUserEntity) {
            throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "App用户尚未注册");
        }
        //2.查找钱包用户信息
        WalletUserEntity walletUserEntity = walletUserDao.findByUserId(aryaUserEntity.getId());
        if (null == walletUserEntity || StringUtils.isAnyBlank(walletUserEntity.getWalletUserId())) {
            throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "钱包用户不存在");
        }
        WalletUserInfoResult result = new WalletUserInfoResult();
        result.getUserInfo().setPhone(walletUserEntity.getPhone());
        result.getUserInfo().setValidUserName(walletUserEntity.getValidUserName());
        result.getUserInfo().setValidCardNo(walletUserEntity.getValidCardNo());
        result.getUserInfo().setWalletUserId(walletUserEntity.getWalletUserId());
        List<WalletBankCardEntity> walletBankCardEntityList = walletBankCardDao.findListByUserId(aryaUserEntity.getId());
        for (WalletBankCardEntity walletBankCardEntity : walletBankCardEntityList) {
            WalletUserInfoResult.BankCardInfo bankCardInfo = result.new BankCardInfo();
            bankCardInfo.setBankCardNo(walletBankCardEntity.getBankCardNo());
            bankCardInfo.setBankName(walletBankCardEntity.getBankName());
            bankCardInfo.setBankType(walletBankCardEntity.getCardTypeCode());
            bankCardInfo.setBindTime(walletBankCardEntity.getCreateTime());
            result.getBankCardInfoList().add(bankCardInfo);
        }
        /*WalletWSResult returnResult = walletService.queryBankcards(walletUserEntity.getWalletUserId());
        if (!returnResult.getSuccess()) {
            String msg =  WalletConstants.errMsgMap.get(returnResult.getCode());
            logger.info(StringUtils.isAnyBlank(msg) ? returnResult.getMsg() : msg);
            throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, StringUtils.isAnyBlank(msg) ? returnResult.getMsg() : msg);
        }
        Map<String, Object> returnResultMap = new Gson().fromJson(returnResult.getResultJson(), Map.class);
        if (null != returnResultMap.get("bankCardList")) {
            //查询个人绑定所有的银行卡（本地服务器）
            List<WalletBankCardEntity> walletBankCardEntityList = walletBankCardDao.findListByUserId(aryaUserEntity.getId());
            //将本地银行卡信息存放在一个map中 方便与汇付宝返回结果进行比较 key是授权码
            Map<String, WalletBankCardEntity> walletBankCardEntityMap = new HashMap<>();
            for (WalletBankCardEntity entity : walletBankCardEntityList) {
                walletBankCardEntityMap.put(entity.getAuthCode(), entity);
            }

            for (Map<String, String> map : (ArrayList<Map<String, String>>) returnResultMap.get("bankCardList")) {
                WalletUserInfoResult.BankCardInfo bankCardInfo = result.new BankCardInfo();
                bankCardInfo.setBankCardNo(map.get("bankcardNo"));
                bankCardInfo.setBankName(map.get("bankcardName"));
                bankCardInfo.setBankType(map.get("bankcardType"));
                if(null != walletBankCardEntityMap.get(map.get("authCode"))) {
                    bankCardInfo.setBindTime(String.valueOf(walletBankCardEntityMap.get(map.get("authCode")).getCreateTime()));
                }
                result.getBankCardInfoList().add(bankCardInfo);
            }
        }*/
        return result;
    }
}
