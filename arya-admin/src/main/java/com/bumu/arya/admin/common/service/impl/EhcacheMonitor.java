package com.bumu.arya.admin.common.service.impl;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.management.ManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.management.MBeanServer;
import java.lang.management.ManagementFactory;

/**
 * @author Allen 2018-01-04
 **/
@Service
public class EhcacheMonitor {

    @Autowired
    CacheManager cacheManager;

    @PostConstruct
    public void init() {
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        ManagementService.registerMBeans(cacheManager, mBeanServer, true, true, true, true);
    }
}
