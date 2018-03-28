package com.bumu.bran.admin;


import com.bumu.SysUtils;
import com.bumu.common.result.WorkAttendanceHttpRecordsResult;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.collections.map.HashedMap;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WorkAttendanceRecordTest {

    @Test
    public void getRecordTest() throws Exception {
        String json = "[{\"device_id\":\"D266B4A2D7522621\",\"attend_num\":\"800000568\",\"attend_date\":1519800536000,\"attend_style\":\"1\"},{\"device_id\":\"DC663C725755592A\",\"attend_num\":\"800000568\",\"attend_date\":1519799662000,\"attend_style\":\"[\\\"FP\\\"]\"}]";
        List<WorkAttendanceHttpRecordsResult> records = SysUtils.jsonTo(json, new TypeReference<List<WorkAttendanceHttpRecordsResult>>() {
        });

        Map<String, List<WorkAttendanceHttpRecordsResult>> tempGroupList = new HashedMap();
        Map<String, List<WorkAttendanceHttpRecordsResult>> resultGroupList = new HashedMap();


        tempGroupList = records
                .stream()
                .sorted(new WorkAttendanceHttpRecordsResult()::compare)
                .collect(Collectors.groupingBy(one -> one.getAttendNo()));


        for (String attendNo : tempGroupList.keySet()) {
            List<WorkAttendanceHttpRecordsResult> list = tempGroupList.get(attendNo);
            if (com.bumu.common.util.ListUtils.checkNullOrEmpty(list)) {
                break;
            }
            attendNo = list.get(0).getDeviceId() + "," + attendNo;
            resultGroupList.put(attendNo, list);
        }
    }

}
