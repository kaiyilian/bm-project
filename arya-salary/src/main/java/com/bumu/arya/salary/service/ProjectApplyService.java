package com.bumu.arya.salary.service;

import com.bumu.arya.salary.command.*;
import com.bumu.arya.salary.result.CustomerFollowResult;
import com.bumu.arya.salary.result.ProjectApplyResult;
import com.bumu.common.result.Pager;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: Gujianchao
 * @Description:
 * @Date： 2017/7/6
 */
@Transactional
public interface ProjectApplyService {

    Pager<ProjectApplyResult> getPage(String condition, Integer page, Integer pageSize) throws Exception ;

    ProjectApplyResult view(String id);

    List<CustomerFollowResult> followList(String projectId);

    void addFollow(ProjectFollowCommand projectFollowCommand);

    void create(ProjectApplyCommand projectApplyCommand);

    void update(ProjectApplyUpdateCommand projectApplyUpdateCommand);

    void toCustomer(ProjectApplyCustomerCommand projectApplyCustomerCommand);

}
