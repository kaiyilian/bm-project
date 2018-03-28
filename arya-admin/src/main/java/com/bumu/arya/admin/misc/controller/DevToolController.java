package com.bumu.arya.admin.misc.controller;

import com.bumu.arya.Utils;
import com.bumu.arya.admin.misc.result.SimpleResult;
import com.bumu.arya.admin.misc.service.DevToolService;
import com.bumu.arya.admin.model.SysUserDao;
import com.bumu.arya.admin.model.SysUserModel;
import com.bumu.arya.common.Constants.ClientType;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.model.AryaUserDao;
import com.bumu.arya.model.entity.AryaUserEntity;
import com.bumu.arya.response.HttpResponse;
import com.bumu.arya.soin.model.dao.AryaSoinDao;
import com.bumu.arya.soin.model.dao.InsurancePersonDao;
import com.bumu.arya.soin.model.dao.SoinOrderDao;
import com.bumu.common.model.PushMessage;
import com.bumu.common.model.PushTo.BasePushToCustomize;
import com.bumu.common.model.PushTo.PushToAll;
import com.bumu.common.model.PushTo.PushToOne;
import com.bumu.common.service.PushService;
import com.bumu.common.util.EnvUtils;
import com.bumu.exception.AryaServiceException;
import com.github.swiftech.swifttime.Time;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * 仅用于开发调试
 * Created by allen on 16/1/21.
 */
@Controller
@Api(value = "DevToolController", tags = {"开发调试工具接口DevTool"})
public class DevToolController {

    Logger log = LoggerFactory.getLogger("api_ac_log");

	@Autowired
	PushService pushService;

    @Autowired
    private SoinOrderDao soinOrderDao;

    @Autowired
    private AryaSoinDao aryaSoinDao;

    @Autowired
    private InsurancePersonDao insurancePersonDao;

    @Autowired
    private SysUserDao sysUserDao;

    @Autowired
    DevToolService devToolService;

    @Autowired
    AryaUserDao aryaUserDao;

//	/**
//	 * 测试消息推送
//	 *
//	 * @param map
//	 * @param type   为 "all" 表示
//	 * @param device 为 “ios" 表示推iOS，否则就是Android
//	 * @return
//	 */
//	@RequestMapping(value = "/test_push", method = RequestMethod.GET)
//	public String testPush(ModelMap map, String type, String device, int jump) {
//		try {
//			PushMessage pushMessage = new PushMessage();
//			pushMessage.setTitle("招才进宝提醒您");
//			pushMessage.setContent("您的社保已经缴纳");
//			pushMessage.setIcon(PushMessage.ICON_SOIN_ORDER);
//			pushMessage.setUrl("http://www.taobao.com");
//			pushMessage.setVersionType(jump);
//
//			ClientType clientType = "ios".equals(device) ? ClientType.IOS : ClientType.ANDROID;
//
//			String bizId = RandomStringUtils.randomAlphabetic(32);
//
//			String pushResult;
//			if ("all".equals(type)) {
//				PushToAll pushToAll = new PushToAll(bizId);
//				pushResult = pushService.push(pushMessage, pushToAll);
//			} else {
//				PushToOne pushTo = null;
//				if (clientType == ClientType.IOS) {
//					pushTo = new PushToOne(bizId, "98646301ba164385b1e4a6e71a1db0ad", ClientType.IOS);
//				} else if (clientType == ClientType.ANDROID) {
//					String allenId = "d38122f6be0a469ebb689bb67865b62b";
//					String hongzhanId = "4c87d4e7a9a944909ef2cf8d85a9d4c1";
//					pushTo = new PushToOne(bizId, allenId, ClientType.ANDROID);
//				} else {
//					throw new RuntimeException();
//				}
//				pushResult = pushService.push(pushMessage, pushTo);
//			}
//			map.put("result", pushResult);
//		} catch (AryaServiceException e) {
//			e.printStackTrace();
//		}
//		return "dev_tool";
//	}

    @PostConstruct
    public void init() {
        log.info("API访问日志测试");
        EnvUtils.printSystemProperties();
    }

    @ApiOperation(httpMethod = "POST", notes = "push", value = "JQDatatables 返回数据")
    @RequestMapping(value = "/jqdatatables/foobar", method = RequestMethod.GET)
    public
    @ResponseBody
    HttpResponse<List> jqdatatable(@RequestParam Map<String, String> params) {

        List ret = new ArrayList();

        for (int i = 0; i < 5; i++) {
            String r = RandomStringUtils.randomNumeric(2);
            ret.add(new SysUserModel(Utils.makeUUID()
                    , "admin" + r
                    , "Admin" + r
                    , "Jojo" + r));
        }

        return new HttpResponse<>(ret);
    }

	@ApiOperation(httpMethod = "GET", notes = "test_push", value = "测试推送页面")
	@RequestMapping(value = "/admin/test_push", method = RequestMethod.GET)
	public String adminTestPush(ModelMap map) {

		List<AryaUserEntity> allUser = aryaUserDao.findAll();

		map.put("all_users", allUser);

		return "dev_tool";
	}

	@ApiOperation(httpMethod = "POST", notes = "push", value = "推送消息")
	@RequestMapping(value = "/admin/push", method = RequestMethod.GET)
	public
	@ResponseBody
	HttpResponse<SimpleResult> adminTestPush2() {
		String userId = "1260cf7c82a64e1799e624e809fb2768";
		PushMessage pushMessage = new PushMessage();
		pushMessage.setTitle("测试标题");
		pushMessage.setContent("测试内容");
		pushMessage.setIcon(PushMessage.ICON_SOIN_ORDER);
		pushMessage.setUrl("http://www.taobao.com");
		pushMessage.setType(1);
		pushMessage.setUserId(userId);

		ClientType clientType1 = ClientType.getClientType(2);


		String bizId = RandomStringUtils.randomAlphabetic(32);

		String pushResult;
		BasePushToCustomize pushTo = null;
		if (clientType1 == ClientType.IOS) {
			pushTo = new PushToOne(bizId, userId, ClientType.IOS);
		}
		else if (clientType1 == ClientType.ANDROID) {
			pushTo = new PushToOne(bizId, userId, ClientType.ANDROID);
		}
		else {
			throw new RuntimeException();
		}
		pushResult = pushService.push(pushMessage, pushTo);

		SimpleResult simpleResult = new SimpleResult();
		simpleResult.setName(pushResult);
		return new HttpResponse(simpleResult);
	}

    @ApiOperation(value = "发送推送")
	@RequestMapping(value = "/admin/do_push", method = RequestMethod.POST)
	public String doTestPush(ModelMap map, @RequestBody LinkedMultiValueMap params) {

		int jump = Integer.parseInt(params.getFirst("jump_type").toString());
		int clientType = Integer.parseInt(params.getFirst("client_type").toString());
		String userId = (String) params.getFirst("user_id");
		String msgTitle = (String) params.getFirst("msg_title");
		String msgBody = (String) params.getFirst("msg_body");

		System.out.println(params);

		try {
			PushMessage pushMessage = new PushMessage();
			pushMessage.setTitle(msgTitle);
			pushMessage.setContent(msgBody);
			pushMessage.setIcon(PushMessage.ICON_SOIN_ORDER);
			pushMessage.setUrl("http://www.taobao.com");
			pushMessage.setType(jump);
			pushMessage.setUserId(userId);

			ClientType clientType1 = ClientType.getClientType(clientType);

//			ClientType clientType = "ios".equals(device) ? ClientType.IOS : ClientType.ANDROID;

			String bizId = RandomStringUtils.randomAlphabetic(32);

			String pushResult;
			if (StringUtils.isBlank(userId)) {
				PushToAll pushToAll = new PushToAll(bizId);
				pushResult = pushService.push(pushMessage, pushToAll);
			}
			else {
				BasePushToCustomize pushTo = null;
				if (clientType1 == ClientType.IOS) {
					pushTo = new PushToOne(bizId, userId, ClientType.IOS);
				}
				else if (clientType1 == ClientType.ANDROID) {
					pushTo = new PushToOne(bizId, userId, ClientType.ANDROID);
				}
				else {
					throw new RuntimeException();
				}
				pushResult = pushService.push(pushMessage, pushTo);
			}
			map.put("result", pushResult);
		} catch (AryaServiceException e) {
			e.printStackTrace();
		}
		return "dev_tool_result";
	}


	/**
	 * 查询所有省一级下的无用社保类型及其版本
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/admin/search_soin_types", method = RequestMethod.GET)
	public String searchSoinRules(ModelMap model) throws Exception {
		System.out.println("查询所有省一级下的无用社保类型及其版本");

		DevToolService.DelSoinTypeResult allUnusedSoinTypes = devToolService.findAllUnusedSoinTypes();

		model.put("soin_types", allUnusedSoinTypes);
		model.put("count", allUnusedSoinTypes.size());

		return "ops/del_soin_types";
	}

	/**
	 * 删除所有省一级下的无用社保类型及其版本
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/admin/del_soin_types", method = RequestMethod.POST)
	public HttpResponse delSoinBills(ModelMap model) throws Exception {
		if (!"admin".equals(SecurityUtils.getSubject().getPrincipal())) {
			return new HttpResponse<>(ErrorCode.CODE_NO_PERMISSION);
		}

		System.out.println("删除所有省一级下的无用社保类型及其版本");

		devToolService.deleteAllUnusedSoinTypes();

		return new HttpResponse<>();
	}

	/**
	 * 列出待删除订单
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/admin/search_soin_orders", method = RequestMethod.GET)
	public String searchSoinBills(ModelMap model, @RequestParam int days) throws Exception {

		System.out.println("查询天数: " + days);

		long cutoffTime = new Time().truncateAtDate().getTimeInMillis() - days * (24 * 60 * 60 * 1000L);

		long start = System.currentTimeMillis();

		long orderCount = soinOrderDao.countAll();

		System.out.println("一共有" + orderCount + "条订单");

//		if (orderCount == 0) {
//			return new HttpResponse(ErrorCode.CODE_ORDER_BILL_BATCH_NOT_FOUND);
//		}

		DevToolService.DelSoinOrderResult allImportedOrders = devToolService.findAllImportedOrders(cutoffTime);

		model.put("del_soin_orders", allImportedOrders);
		model.put("orders_count", allImportedOrders.size());
		model.put("time_cutoff", DateFormatUtils.format(new Time(cutoffTime), "yyyy-MM-dd HH:mm:ss"));
		model.put("time_consume", (System.currentTimeMillis() - start) / 1000);
		model.put("days", days);

		return "ops/del_soin_orders";
	}

	/**
	 * 删除无用的订单，包括订单、缴纳记录、批次（不包括增减员记录）
	 * @param model
	 * @param cmd
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/admin/del_soin_orders", method = RequestMethod.POST)
	public HttpResponse<DevToolService.DelResult> delSoinBills(ModelMap model, @RequestBody DelSoinOrderCommand cmd) throws Exception {
		if (!"admin".equals(SecurityUtils.getSubject().getPrincipal())) {
			return new HttpResponse<>(ErrorCode.CODE_NO_PERMISSION);
		}

		System.out.println("删除天数: " + cmd.getDays());
		long cutoffTime = new Time().truncateAtDate().getTimeInMillis() - cmd.getDays() * (24 * 60 * 60 * 1000L);

		DevToolService.DelResult delResult;
		try {
			delResult = devToolService.deleteAllImportedOrders(cutoffTime);
			String msg = String.format("删除成功，共删除%d条订单、%d条缴纳记录、%d条批次信息",
					delResult.getOrderCount(), delResult.getSoinCount(), delResult.getBatchCount());
			System.out.println(msg);
			model.put("err_msg", msg);
		} catch (Exception e) {
			e.printStackTrace();
			return new HttpResponse<>(ErrorCode.CODE_SYS_ERR);
		}
		return new HttpResponse<>(delResult);
	}

	/**
	 *
	 */
	public static class DelSoinOrderCommand {
		int days;

		public int getDays() {
			return days;
		}

		public void setDays(int days) {
			this.days = days;
		}
	}


}
