package com.bumu.arya.admin.misc.controller;

import com.bumu.arya.Utils;
import com.bumu.arya.common.Constants;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.response.HttpResponse;
import com.bumu.common.service.PushService;
import com.bumu.common.model.PushMessage;
import com.bumu.common.model.PushTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 推送测试
 */
@RestController
@RequestMapping(value = "/admin/push/test")
public class PushTestController {

    @Autowired
    private PushService pushService;

    @GetMapping(value = "send")
    public HttpResponse send() throws Exception {
        PushTo.PushToAll toAll = new PushTo.PushToAll(Utils.makeUUID());
        PushMessage pushMessage = new PushMessage();
        pushMessage.setTitle("测试");
        pushMessage.setContent("测试");
        pushMessage.setType(com.bumu.bran.common.Constants.E_CONTRACT_JUMP_QUERY_PAGE);
        HttpResponse httpResponse = new HttpResponse(ErrorCode.CODE_OK);
        httpResponse.setResult(pushService.push(pushMessage, toAll));
        return httpResponse;
    }

    @GetMapping(value = "send/{id}")
    public HttpResponse sendId(@PathVariable String id) throws Exception {
        PushMessage pushMessage = new PushMessage();
        pushMessage.setTitle("马俊测试推送");
        pushMessage.setContent("马俊测试推送");
        pushMessage.setType(com.bumu.bran.common.Constants.E_CONTRACT_JUMP_QUERY_PAGE);
        HttpResponse httpResponse = new HttpResponse(ErrorCode.CODE_OK);
        PushTo.PushToOne pushToOne = new PushTo.PushToOne(
                Utils.makeUUID(),
                id,
                Constants.ClientType.ANDROID
        );

        httpResponse.setResult(pushService.push(pushMessage, pushToOne));
        return httpResponse;
    }
}
