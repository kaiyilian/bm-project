package com.bumu.arya.salary.dao;

import com.bumu.arya.salary.model.entity.ProjectApplyEntity;
import com.bumu.common.result.Pager;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by liangjun on 2017/6/7
 */
@Transactional
public interface ProjectApplyDao extends SalaryBaseDao<ProjectApplyEntity> {

    Pager<ProjectApplyEntity> getPage(String condition, Integer page, Integer pageSize) throws Exception;

}
