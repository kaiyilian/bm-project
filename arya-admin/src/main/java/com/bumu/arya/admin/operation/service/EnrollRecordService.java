package com.bumu.arya.admin.operation.service;

import com.bumu.arya.activity.command.EnrollRecordCommand;
import com.bumu.arya.activity.result.EnrollRecordListResult;
import com.bumu.common.result.DownloadResult;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;


@Transactional
public interface EnrollRecordService {

    EnrollRecordListResult queryRecordList(EnrollRecordCommand command);

    DownloadResult exportOrders(EnrollRecordCommand command, HttpServletResponse response)throws Exception;
}
