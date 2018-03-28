package com.bumu.bran.admin.approval.service.impl;

import com.bumu.approval.result.ApprovalTypeSettingResult;
import com.bumu.bran.admin.approval.service.ApprovalTypeHolidaySettingService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author majun
 * @date 2017/10/25
 * @email 351264830@qq.com
 */
@Service
public class ApprovalTypeHolidaySettingServiceImpl implements ApprovalTypeHolidaySettingService {

    /**
     * 请假明细 1:事假 2:年假 3:病假 4:产检假 6:婚假 7:产假 8:哺乳假 9:丧假
     *
     * @return
     */
    @Override
    public List<ApprovalTypeSettingResult> all() {
        List<ApprovalTypeSettingResult> list = new ArrayList<>();
        list.add(new ApprovalTypeSettingResult(0, 1));
        list.add(new ApprovalTypeSettingResult(0, 2));
        list.add(new ApprovalTypeSettingResult(0, 3));
        list.add(new ApprovalTypeSettingResult(0, 4));
        list.add(new ApprovalTypeSettingResult(0, 6));
        list.add(new ApprovalTypeSettingResult(0, 7));
        list.add(new ApprovalTypeSettingResult(0, 8));
        list.add(new ApprovalTypeSettingResult(0, 9));
        return list;
    }
}
