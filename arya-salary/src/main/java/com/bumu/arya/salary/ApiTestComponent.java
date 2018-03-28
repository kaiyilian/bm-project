package com.bumu.arya.salary;

import com.bumu.common.ApiLogger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author Allen 2017-11-17
 **/
@Service
public class ApiTestComponent {

    @PostConstruct
    public void init() {
        System.out.println("测试API日志");
        ApiLogger.logApiAccess("http://192.168.13.248/arya-salary/foo/bar",
                "/arya-salary", "/foo/bar", "a1.1", "353");
    }
}
