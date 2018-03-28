package com.bumu.arya.admin.operation.model.dao.mybatis;

import com.bumu.arya.admin.operation.controller.command.RedEnvelopeCommand;
import com.bumu.arya.admin.operation.result.RedEnvelopeResult;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author majun
 * @date 2017/10/20
 * @email 351264830@qq.com
 */
@MapperScan
@Repository
public interface RedEnvelopeResultMybatisDao {

    List<RedEnvelopeResult> get(RedEnvelopeCommand redEnvelopeCommand);
}
