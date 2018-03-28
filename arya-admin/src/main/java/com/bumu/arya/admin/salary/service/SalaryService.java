package com.bumu.arya.admin.salary.service;

import com.bumu.arya.admin.salary.result.SalaryImportResult;
import com.bumu.arya.model.entity.AryaSalaryEntity;
import com.bumu.arya.admin.salary.result.SalaryImportResult.SalaryOutputBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by bumu-zhz on 2015/11/6.
 */
@Transactional
@Deprecated
public interface SalaryService {

    @Deprecated
	List<AryaSalaryEntity> select();

    @Deprecated
	List<SalaryImportResult.SalaryErrorMsgBean> insert(String companyId, List<SalaryOutputBean> list);

}
