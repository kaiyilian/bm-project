package com.bumu.bran.admin.approval.service.impl;

import com.bumu.approval.result.ApprovalTypeSettingResult;
import com.bumu.bran.admin.approval.service.ApprovalTypeOverTimeSettingService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author majun
 * @date 2017/10/25
 * @email 351264830@qq.com
 */
@Service
public class ApprovalTypeOverTimeSettingServiceImpl implements ApprovalTypeOverTimeSettingService {
    /**
     * 加班明细 10:平日加班 11:休息期加班 12:节假日加班
     *
     * @return
     */
    @Override
    public List<ApprovalTypeSettingResult> all() {
        List<ApprovalTypeSettingResult> list = new ArrayList<>();
        list.add(new ApprovalTypeSettingResult(1, 10));
        list.add(new ApprovalTypeSettingResult(1, 11));
        list.add(new ApprovalTypeSettingResult(1, 12));
        return list;
    }
}
