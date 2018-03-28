package com.bumu.arya.salary.service;

import com.bumu.common.service.ConfigService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


/**
 * @Author: Gujianchao
 * @Description:
 * @Date： 2017/8/21
 */
@Service
public class SalaryConfigService extends ConfigService{

    @Value("${arya.admin.sso.signout.url}")
    private String ssoSignoutUrl;

    public String getSsoSignoutUrl() {
        return ssoSignoutUrl;
    }

    public void setSsoSignoutUrl(String ssoSignoutUrl) {
        this.ssoSignoutUrl = ssoSignoutUrl;
    }

}
