package com.bumu.arya.salary.dao;

import com.bumu.arya.salary.model.entity.CustomerFollowEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Created by liangjun on 2017/6/7
 */
@Transactional
public interface CustomerFollowDao extends SalaryBaseDao<CustomerFollowEntity> {

    List<CustomerFollowEntity> getListByProjectId(String projectApplyId);

    List<CustomerFollowEntity> getListByCustomerId(String customerId);
}
