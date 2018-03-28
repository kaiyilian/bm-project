package com.bumu.arya.salary.dao.mybatis;

import com.bumu.arya.salary.command.CustomerAccountTotalQueryCommand;
import com.bumu.arya.salary.result.CustomerAccountTotalQueryResult;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

/**
 * @author majun
 * @date 2017/4/1
 */
@MapperScan
public interface CustomerAccountTotalMybatisDao {

    /**
     * 客户台账汇总list
     * @param customerAccountTotalQueryCommand
     * @return
     */
    List<CustomerAccountTotalQueryResult> findList(CustomerAccountTotalQueryCommand customerAccountTotalQueryCommand);

}
