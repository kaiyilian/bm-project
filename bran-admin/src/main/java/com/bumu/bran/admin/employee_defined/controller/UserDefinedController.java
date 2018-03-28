package com.bumu.bran.admin.employee_defined.controller;

import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.response.HttpResponse;
import com.bumu.bran.admin.employee_defined.service.UserDefinedService;
import com.bumu.bran.admin.system.aop.BranRecordLogsAop;
import com.bumu.bran.common.annotation.RecordLogs;
import com.bumu.bran.common.annotation.SetParams;
import com.bumu.bran.employee.command.UserDefinedCommand;
import com.bumu.bran.model.entity.BranOpLogEntity;
import com.bumu.bran.validated.user.defined.UserDefinedAddValidatedGroup;
import com.bumu.bran.validated.user.defined.UserDefinedDeleteValidatedGroup;
import com.bumu.bran.validated.user.defined.UserDefinedUpdateValidatedGroup;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author majun
 * @date 2016/11/28
 *
 */
@Api(tags = {"员工管理-花名册自定义"})
@Controller
@Transactional // 使用aop记录日志,所以在controller层也需要事务回滚
@RequestMapping()
public class UserDefinedController {

	private Logger logger = LoggerFactory.getLogger(UserDefinedController.class);

	@Autowired
	private UserDefinedService userDefinedService;

	/**
	 * 功能:获取花名册自定义
	 * 步骤:从session中获取当前登录的公司ID, 通过公司ID查询所有的花名册自定义项
	 *
	 * @param userDefinedCommand 公司ID
	 * @param bindingResult      这里放bindingResult的意义在于统一验证, spring aop 在方法开始之前验证
	 *                           参数是否正确
	 * @return HttpResponse 返回所有的自定义类
	 * @throws Exception 抛出给统一异常处理类
	 * @see BranRecordLogsAop aop
	 * @see com.bumu.bran.common.aop.ControllerExceptionProcessor
	 */
	@RequestMapping(value = "/admin/no_permission/employee/setting/roster_custom/all", method = RequestMethod.GET)
	@ResponseBody
	@SetParams
	public HttpResponse all(UserDefinedCommand userDefinedCommand, BindingResult bindingResult) throws Exception {
		return new HttpResponse<>(ErrorCode.CODE_OK, userDefinedService.all(userDefinedCommand));
	}

	/**
	 * 功能:新增花名册自定义项
	 * 步骤:获取花名册名字,新增entity,查询数据库
	 *
	 * @param userDefinedCommand colName(列名)
	 * @param bindingResult      这里放bindingResult的意义在于统一验证, spring aop 在方法开始之前验证
	 *                           参数是否正确
	 * @return 返回成功信息
	 * @throws Exception 抛出给统一异常处理类
	 * @see @Validated 验证参数 colName必填
	 * @see @RequestBody 把json字符串转换成对象
	 * @see BranRecordLogsAop aop
	 * @see com.bumu.bran.common.aop.ControllerExceptionProcessor
	 */
	@RequestMapping(value = "/admin/employee/setting/roster_custom/add", method = RequestMethod.POST)
	@ResponseBody
	@SetParams
	@RecordLogs(model = BranOpLogEntity.OP_MODULE_USER_DEFINED, type = BranOpLogEntity.OP_TYPE_ADD, msg = "新建花名册自定义项: ")
	public HttpResponse add(@RequestBody @Validated(value = UserDefinedAddValidatedGroup.class) UserDefinedCommand userDefinedCommand,
							BindingResult bindingResult) throws Exception {
		userDefinedService.add(userDefinedCommand);
		return new HttpResponse(ErrorCode.CODE_OK);
	}

	/**
	 * 功能:更新花名册自定义项
	 * 步骤:根据ID,查询花名册,根据规则更新
	 *
	 * @param userDefinedCommand id,colName
	 * @param bindingResult      这里放bindingResult的意义在于统一验证, spring aop 在方法开始之前验证
	 *                           参数是否正确
	 * @return 返回成功信息
	 * @throws Exception 抛出给统一异常处理类
	 * @see @Validated 验证参数 id,colName必填
	 * @see @RequestBody 把json字符串转换成对象
	 * @see BranRecordLogsAop aop
	 * @see com.bumu.bran.common.aop.ControllerExceptionProcessor
	 */
	@RequestMapping(value = "/admin/employee/setting/roster_custom/update", method = RequestMethod.POST)
	@ResponseBody
	@SetParams
	@RecordLogs(model = BranOpLogEntity.OP_MODULE_USER_DEFINED, type = BranOpLogEntity.OP_TYPE_UPDATE, msg = "修改花名册自定义项: ")
	public HttpResponse update(@RequestBody @Validated(value = UserDefinedUpdateValidatedGroup.class)
							   UserDefinedCommand userDefinedCommand, BindingResult bindingResult) throws Exception {
		return new HttpResponse(ErrorCode.CODE_OK, userDefinedService.update(userDefinedCommand));
	}

	/**
	 * 功能:删除花名册自定义项
	 * 步骤:根据ID,查询花名册,根据规则逻辑删除(isDelete = 1)
	 *
	 * @param userDefinedCommand id
	 * @param bindingResult      这里放bindingResult的意义在于统一验证, spring aop 在方法开始之前验证
	 *                           参数是否正确
	 * @throws Exception 抛出给统一异常处理类
	 * @see @Validated 验证参数 id必填
	 * @see @RequestBody 把json字符串转换成对象
	 * @see BranRecordLogsAop aop
	 * @see com.bumu.bran.common.aop.ControllerExceptionProcessor
	 */
	@RequestMapping(value = "/admin/employee/setting/roster_custom/delete", method = RequestMethod.POST)
	@ResponseBody
	@SetParams
	@RecordLogs(model = BranOpLogEntity.OP_MODULE_USER_DEFINED, type = BranOpLogEntity.OP_TYPE_DELETE, msg = "删除花名册自定义项: ")
	public HttpResponse delete(@RequestBody @Validated(value = UserDefinedDeleteValidatedGroup.class)
							   UserDefinedCommand userDefinedCommand, BindingResult bindingResult) throws Exception {
		userDefinedService.delete(userDefinedCommand);
		return new HttpResponse(ErrorCode.CODE_OK);
	}
}
