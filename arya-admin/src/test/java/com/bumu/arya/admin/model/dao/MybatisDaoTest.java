package com.bumu.arya.admin.model.dao;

import com.bumu.arya.admin.soin.model.dao.SoinOrderMybatisTTDao;
import com.bumu.arya.soin.model.entity.AryaSoinOrderEntity;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * @author majun
 * @date 2017/2/23
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-spring-config.xml")
public class MybatisDaoTest {

	private static Logger logger = LoggerFactory.getLogger(MybatisDaoTest.class);

	@Autowired
	private SoinOrderMybatisTTDao soinOrderMybatisTTDao;

	@Before
	public void setUp() {
	}

	@Test
	public void orderTest() throws Exception {
		AryaSoinOrderEntity aryaSoinOrderEntity = soinOrderMybatisTTDao.findById("045a1e09c416493599fc4148abce1a99");
		logger.info("getId: " + aryaSoinOrderEntity.getId());
		Assert.assertEquals(aryaSoinOrderEntity.getId(),"123");
	}
}
