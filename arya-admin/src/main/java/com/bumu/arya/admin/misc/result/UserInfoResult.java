package com.bumu.arya.admin.misc.result;

import com.bumu.arya.model.entity.AryaUserEntity;
import com.bumu.function.VoConverter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 用户信息
 *
 * @author majun
 * @date 2018/1/19
 * @email 351264830@qq.com
 */
@ApiModel
public class UserInfoResult implements VoConverter<AryaUserEntity>{
    @ApiModelProperty(value = "当前手机号码")
    private String phoneNo;
    @ApiModelProperty(value = "历史手机号码")
    private String usedPhoneNo;
    @ApiModelProperty(value = "姓名")
    private String realName;
    @ApiModelProperty(value = "身份证")
    private String idcardNo;
    @ApiModelProperty(value = "最后登录时间")
    private Long lastLoginTime;
    @ApiModelProperty(value = "最后登录设备类型 0:暂无 1:安卓 2:IOS")
    private Integer lastClientType = 1;
    @ApiModelProperty(value = "app版本")
    private String appVersion;
    @ApiModelProperty(value = "最后访问时间")
    private Long lastAccessTime;

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getUsedPhoneNo() {
        return usedPhoneNo;
    }

    public void setUsedPhoneNo(String usedPhoneNo) {
        this.usedPhoneNo = usedPhoneNo;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdcardNo() {
        return idcardNo;
    }

    public void setIdcardNo(String idcardNo) {
        this.idcardNo = idcardNo;
    }

    public Long getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Integer getLastClientType() {
        return lastClientType;
    }

    public void setLastClientType(Integer lastClientType) {
        this.lastClientType = lastClientType;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public Long getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(Long lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }
}
