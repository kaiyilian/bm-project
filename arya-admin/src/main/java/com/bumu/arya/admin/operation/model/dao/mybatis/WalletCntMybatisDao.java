package com.bumu.arya.admin.operation.model.dao.mybatis;

import com.bumu.arya.admin.operation.result.WalletUserCntResult;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Guchaochao
 * @date 2018/3/2
 */
@MapperScan
@Repository
public interface WalletCntMybatisDao {

    List<WalletUserCntResult> walletUserCntList(String param);
}
