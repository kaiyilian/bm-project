package com.bumu.arya.salary.dao;

import com.bumu.arya.salary.model.entity.SalaryErrLogEntity;
import com.bumu.common.result.Pager;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Created by liangjun on 2017/6/7
 */
@Transactional
public interface SalaryErrLogDao extends SalaryBaseDao<SalaryErrLogEntity> {

    Pager<SalaryErrLogEntity> getPager(String condition, Integer pageSize, Integer page) throws Exception;

    List<SalaryErrLogEntity> getList(List<String> idList);

    List<SalaryErrLogEntity> getList(String condition);
}
