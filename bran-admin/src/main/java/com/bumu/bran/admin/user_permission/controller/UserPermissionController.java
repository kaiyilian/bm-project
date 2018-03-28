package com.bumu.bran.admin.user_permission.controller;

import com.bumu.arya.response.HttpResponse;
import com.bumu.bran.admin.system.command.CorpModel;
import com.bumu.bran.admin.user_permission.result.CorpModelResult;
import com.bumu.bran.admin.user_permission.service.UserPermissionService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 用户管理
 */
@Controller
@RequestMapping(value = "/admin/permission/account/")
public class UserPermissionController {

	private static Logger logger = LoggerFactory.getLogger(UserPermissionController.class);

	@Autowired
	private UserPermissionService userPermissionService;

	/**
	 * 添加用户
	 *
	 * @param command
	 * @return
	 */
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public HttpResponse addPermissionUser(@RequestBody CorpModel command) throws Exception {
		Session session = SecurityUtils.getSubject().getSession();
		String branCorpId = session.getAttribute("bran_corp_id").toString();
		String currentUserId = session.getAttribute("user_id").toString();
		command.setBranCorpId(branCorpId);
		command.setOperateUserId(currentUserId);
		return new HttpResponse(userPermissionService.addPermissionUser(command));
	}

	/**
	 * 修改密码
	 *
	 * @param command
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public HttpResponse updatePermissionUser(@RequestBody CorpModel command) throws Exception {
		Session session = SecurityUtils.getSubject().getSession();
		String branCorpId = session.getAttribute("bran_corp_id").toString();
		String currentUserId = session.getAttribute("user_id").toString();
		command.setBranCorpId(branCorpId);
		command.setOperateUserId(currentUserId);
		return new HttpResponse(userPermissionService.updatePermissionUser(command));
	}

	/**
	 * 删除用户
	 *
	 * @param command
	 * @return
	 */
	@RequestMapping(value = "delete/ids", method = RequestMethod.POST)
	@ResponseBody
	public HttpResponse deletePermissionUser(@RequestBody CorpModel command) throws Exception{
		Session session = SecurityUtils.getSubject().getSession();
		String branCorpId = session.getAttribute("bran_corp_id").toString();
		String currentUserId = session.getAttribute("user_id").toString();
		command.setBranCorpId(branCorpId);
		command.setOperateUserId(currentUserId);
		userPermissionService.deletePermissionUser(command);
		return new HttpResponse();
	}

	/**
	 * 获取用户
	 *
	 * @param page
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "get", method = RequestMethod.GET)
	@ResponseBody
	public HttpResponse<CorpModelResult> getPermissionUser(@RequestParam(value = "page", defaultValue = "1") int page,
														   @RequestParam(value = "page_size", defaultValue = "10")
														   int pageSize) throws Exception {
		Session session = SecurityUtils.getSubject().getSession();
		String branCorpId = session.getAttribute("bran_corp_id").toString();
		String currentUserId = session.getAttribute("user_id").toString();
		CorpModel command = new CorpModel();
		command.setPage(page - 1);
		command.setPage_size(pageSize);
		command.setBranCorpId(branCorpId);
		command.setOperateUserId(currentUserId);
		return new HttpResponse(userPermissionService.getPermissionUser(command));
	}

}
