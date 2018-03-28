package com.bumu.arya.admin.operation.service;

import com.bumu.arya.admin.operation.controller.command.RedEnvelopeCommand;
import com.bumu.arya.admin.operation.result.RedEnvelopeResult;
import com.bumu.arya.command.PagerCommand;
import com.bumu.common.result.DownloadResult;
import com.bumu.common.result.Pager;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author majun
 * @date 2017/12/26
 * @email 351264830@qq.com
 */
@Transactional
public interface RedEnvelopeService {

    Pager<RedEnvelopeResult> get(RedEnvelopeCommand redEnvelopeCommand, PagerCommand mybatisPagerCommand);

    DownloadResult export(RedEnvelopeCommand redEnvelopeCommand) throws Exception;
}
