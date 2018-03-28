package com.bumu.bran.admin.notification.controller;

import com.bumu.arya.response.HttpResponse;
import com.bumu.bran.admin.corporation.service.BranCorpService;
import com.bumu.bran.admin.notification.controller.command.DeleteNotificationCommand;
import com.bumu.bran.admin.notification.controller.command.PostNewNotificationCommand;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author CuiMengxin
 * @date 2016/5/11
 */
@Controller
public class NotificationController {

	@Autowired
	BranCorpService branCorpService;

	/**
	 * 获取历史消息（推送公告）
	 *
	 * @return
	 */
	@RequestMapping(value = "/admin/corporation/notification/list", method = RequestMethod.GET)
	public
	@ResponseBody
	HttpResponse getCorpNotificationList(@RequestParam(value = "page", defaultValue = "1") int page,
										 @RequestParam(value = "page_size", defaultValue = "10") int pageSize)
			throws Exception {
		Session session = SecurityUtils.getSubject().getSession();
		String branCorpId = session.getAttribute("bran_corp_id").toString();
		return new HttpResponse(branCorpService.getCorpNotificationList(page - 1, pageSize, branCorpId));
	}

	/**
	 * 删除企业历史推送消息
	 *
	 * @param command
	 * @return
	 */
	@RequestMapping(value = "/admin/corporation/notification/delete", method = RequestMethod.POST)
	@ResponseBody
	public HttpResponse deleteCorpNotification(@RequestBody DeleteNotificationCommand command) throws Exception {
		Session session = SecurityUtils.getSubject().getSession();
		String currentUserId = session.getAttribute("user_id").toString();
		branCorpService.deleteCorpNotifications(command.getNotificationIds(), currentUserId);
		return new HttpResponse();
	}

	/**
	 * 推送新通知
	 *
	 * @param command
	 * @return
	 */
	@RequestMapping(value = "/admin/corporation/notification/post", method = RequestMethod.POST)
	@ResponseBody
	public HttpResponse postCorpNotification(@RequestBody PostNewNotificationCommand command) throws Exception {

		Session session = SecurityUtils.getSubject().getSession();
		String currentUserId = session.getAttribute("user_id").toString();
		String branCorpId = session.getAttribute("bran_corp_id").toString();
		command.setCurrentUserId(currentUserId);
		command.setBranCorpId(branCorpId);
		branCorpService.postNewNotification(command);
		return new HttpResponse();
	}

}
