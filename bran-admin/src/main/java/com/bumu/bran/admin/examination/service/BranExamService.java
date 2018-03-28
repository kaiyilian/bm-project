package com.bumu.bran.admin.examination.service;

import com.bumu.bran.employee.command.ExamCommand;
import com.bumu.bran.employee.result.ExamResult;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface BranExamService {

	ExamResult querybyId(ExamCommand examCommand) throws Exception;
}
