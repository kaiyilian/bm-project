package com.bumu.arya.admin.operation.service;

import com.bumu.arya.admin.operation.result.WalletUserCntResult;
import com.bumu.arya.admin.operation.result.WalletUserInfoResult;
import com.bumu.common.result.FileUploadFileResult;
import com.bumu.common.result.Pager;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 */
@Transactional
public interface WalletCntService {

    Pager<WalletUserCntResult> userCntPager(String param, Integer pageSize, Integer page);

    FileUploadFileResult export();

    WalletUserInfoResult userInfo(String phone) throws Exception;

}
