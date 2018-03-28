package com.bumu.arya.admin.system.result;

import com.bumu.common.result.PaginationResult;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by CuiMengxin on 2016/10/11.
 */
public class SysLogListResult extends PaginationResult {

    List<SysLogResult> logs;

    public List<SysLogResult> getLogs() {
        return logs;
    }

    public void setLogs(List<SysLogResult> logs) {
        this.logs = logs;
    }

    public static class SysLogResult {
        String id;

        String operator;

        @JsonProperty("operate_type")
        int operateType;

        @JsonProperty("operate_type_name")
        String operateTypeName;

        String content;

        @JsonProperty("operate_time")
        long operateTime;

        String status;

        @JsonProperty("login_name")
        String loginName;

        public String getLoginName() {
            return loginName;
        }

        public void setLoginName(String loginName) {
            this.loginName = loginName;
        }

        public String getOperator() {
            return operator;
        }

        public void setOperator(String operator) {
            this.operator = operator;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOperateTypeName() {
            return operateTypeName;
        }

        public void setOperateTypeName(String operateTypeName) {
            this.operateTypeName = operateTypeName;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public long getOperateTime() {
            return operateTime;
        }

        public void setOperateTime(long operateTime) {
            this.operateTime = operateTime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public int getOperateType() {
            return operateType;
        }

        public void setOperateType(int operateType) {
            this.operateType = operateType;
        }
    }
}
