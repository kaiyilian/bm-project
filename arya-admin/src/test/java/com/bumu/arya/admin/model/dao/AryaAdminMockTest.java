package com.bumu.arya.admin.model.dao;

import com.bumu.arya.common.Constants;
import com.bumu.arya.soin.model.dao.SoinOrderDao;
import com.bumu.arya.soin.model.entity.AryaSoinOrderEntity;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


/**
 * @author majun
 * @date 2017/2/23
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("../web")
@ContextHierarchy({
		@ContextConfiguration(name = "parent", locations = {"classpath:test-spring-config.xml", "classpath:test-db.xml"})
//		@ContextConfiguration(name = "child", locations = "classpath:test-dispatcher-servlet.xml")
})
public class AryaAdminMockTest {

	private static Logger logger = LoggerFactory.getLogger(AryaAdminMockTest.class);

	@Autowired
	private WebApplicationContext wac;
	private MockMvc mockMvc;

	@Autowired
	SoinOrderDao orderDao;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

//	@Test
//	public void batchOrderUpdate() throws Exception {
//		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/admin/order/batch/update"))
//				.andDo(MockMvcResultHandlers.print())
//				.andReturn();
//	}

	/**
	 *	查询订单对应的详情,必须是正常缴纳的详情
	 * @throws Exception
	 */
	@Test
	public void orderTest() throws Exception {
		AryaSoinOrderEntity orderEntity = orderDao.billManageDetailList("123",
				Constants.SOIN_VERSION_TYPE_NORMAL);

		logger.info("id: " + orderEntity);

		Assert.assertNotNull(orderEntity);
	}


}
