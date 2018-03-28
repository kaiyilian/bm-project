package com.bumu.arya.admin.corporation.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

/**
 * Created by CuiMengxin on 16/7/20.
 */
public class CorpAdminListResult {

    List<CorpAdminResult> admins;

    public List<CorpAdminResult> getAdmins() {
        return admins;
    }

    public void setAdmins(List<CorpAdminResult> admins) {
        this.admins = admins;
    }

    public CorpAdminListResult() {

    }

    public static class CorpAdminResult {

        String id;

        String account;

        String email;

        @JsonProperty("nick_name")
        String nickName;

        @JsonProperty("last_login_time")
        Long lastLoginTime;

        @JsonProperty("last_login_ip")
        String lastLoginIp;

        @JsonProperty("create_time")
        Long createTime;

        /**
         * 今日尝试登录次数
         */
        @JsonProperty("try_login_times_today")
        int tryLoginTimesToday;

        public CorpAdminResult() {
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public Long getLastLoginTime() {
            return lastLoginTime;
        }

        public void setLastLoginTime(Long lastLoginTime) {
            this.lastLoginTime = lastLoginTime;
        }

        public String getLastLoginIp() {
            return lastLoginIp;
        }

        public void setLastLoginIp(String lastLoginIp) {
            this.lastLoginIp = lastLoginIp;
        }

        public Long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Long createTime) {
            this.createTime = createTime;
        }

        public int getTryLoginTimesToday() {
            return tryLoginTimesToday;
        }

        public void setTryLoginTimesToday(int tryLoginTimesToday) {
            this.tryLoginTimesToday = tryLoginTimesToday;
        }
    }
}
