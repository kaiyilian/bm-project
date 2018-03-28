package com.bumu.arya.admin.system.controller;

import com.bumu.SysUtils;
import com.bumu.arya.admin.common.controller.BaseController;
import com.bumu.arya.admin.model.SysUserDao;
import com.bumu.arya.admin.model.entity.SysUserEntity;
import com.bumu.arya.admin.system.controller.command.*;
import com.bumu.arya.admin.system.controller.validate.CreateEntityValidateGroup;
import com.bumu.arya.admin.system.controller.validate.EditEntityValidateGroup;
import com.bumu.arya.admin.system.service.SysUserManageService;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.model.SysJournalRepository;
import com.bumu.arya.response.HttpResponse;
import com.bumu.common.result.JQDatatablesPaginationResult;
import com.bumu.exception.AryaServiceException;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.groups.Default;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 系统用户管理接口（不包含用户认证等接口）
 *
 * @author Allen
 */
@Controller
public class SystemUserController extends BaseController {

    @Autowired
    SysUserDao sysUserDao;

    @Autowired
    Validator validator;

    @Autowired
    SysJournalRepository sysJournalRepository;

    @Autowired
    SysUserManageService sysUserManageService;

    private Logger log = LoggerFactory.getLogger(SystemUserController.class);

    /**
     * 系统用户管理索引页
     *
     * @return
     */
    @RequestMapping(value = "/admin/sys/user", method = RequestMethod.GET)
    public String sysUserIndex(ModelMap model) {
        model.put("default_login_pwd", RandomStringUtils.randomAlphanumeric(10));
        return "system/sys_user_manager";
    }

    /**
     * 创建系统用户
     *
     * @param cmd
     * @return
     */
    @RequestMapping(value = "/admin/sys/user/create", method = RequestMethod.POST)
    public
    @ResponseBody
    HttpResponse<Void> createSysUser(
            @RequestBody @Validated({Default.class, CreateEntityValidateGroup.class}) SysUserCommand cmd,
            BindingResult valResult) {
        log.debug(cmd.toString());
        if (valResult.hasErrors()) return super.checkValidationResult(cmd, valResult);
        return sysUserManageService.createOrUpdateSysUser(cmd, adminContextService.getCurrentSysUser());
    }

    /**
     * 更新系统用户信息
     *
     * @param cmd
     * @param valResult
     * @return
     */
    @RequestMapping(value = "/admin/sys/user/update", method = RequestMethod.POST)
    public
    @ResponseBody
    HttpResponse<Void> updateSysUser(
            @RequestBody @Validated({Default.class, EditEntityValidateGroup.class}) SysUserCommand cmd,
            BindingResult valResult) {
        log.debug(cmd.toString());
        if (valResult.hasErrors()) return super.checkValidationResult(cmd, valResult);
        return sysUserManageService.createOrUpdateSysUser(cmd, adminContextService.getCurrentSysUser());
    }

    /**
     * 删除系统用户
     *
     * @param cmd
     * @return
     */
    @RequestMapping(value = "/admin/sys/user/delete", method = RequestMethod.POST)
    public
    @ResponseBody
    HttpResponse<Void> deleteSysUser(@RequestBody ChangeSysUserCommand cmd) {

        log.debug(cmd.getUid());

        SysUserEntity sysUserEntity = sysUserDao.find(cmd.getUid());
        if (sysUserEntity == null || "admin".equals(sysUserEntity.getLoginName())) {
            return new HttpResponse(ErrorCode.CODE_SYS_USER_DELETE_FAIL);
        }

        try {
            sysUserDao.delete(cmd.getUid());
        } catch (DataAccessException e) {
            log.error(e.getMessage(), e);
            return new HttpResponse(ErrorCode.CODE_SYS_USER_DELETE_FAIL);
        }

        return new HttpResponse();
    }

    /**
     * 冻结或者解冻系统用户
     *
     * @param cmd
     * @return
     */
    @RequestMapping(value = "/admin/sys/user/freeze", method = RequestMethod.POST)
    public
    @ResponseBody
    HttpResponse<Void> freezeSysUser(@RequestBody ChangeSysUserCommand cmd) {

        log.debug(cmd.getUid());

        SysUserEntity sysUser = sysUserDao.find(cmd.getUid());

        if (sysUser == null) {
            return new HttpResponse<>(ErrorCode.CODE_SYS_USER_NOT_EXIST);
        }

        if (SysUserEntity.STATUS_FROZEN == cmd.getStatus()) {
            sysUser.setStatus(SysUserEntity.STATUS_FROZEN);
            sysUserDao.update(sysUser);
        }
        else if (SysUserEntity.STATUS_NORMAL == cmd.getStatus()) {
            sysUser.setStatus(SysUserEntity.STATUS_NORMAL);
            sysUserDao.update(sysUser);
        }
        else {
            return new HttpResponse<>(ErrorCode.CODE_PARAMS_ERROR);
        }
        return new HttpResponse<>();
    }

    /**
     * 系统用户列表(JQueryDatatables)
     * TODO 未来改为通用处理
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/admin/sys/user/list")
    public
    @ResponseBody
    JQDatatablesPaginationResult sysUserList(@RequestParam Map<String, String> params) {

        Integer draw = Integer.valueOf(params.get("draw"));
        Integer start = Integer.valueOf(params.get("start"));
        Integer length = Integer.valueOf(params.get("length"));
        Integer status = 0;// 可选参数
        if (params.containsKey("status")) {
            status = Integer.valueOf(params.get("status"));
        }

        JQDatatablesPaginationResult result = null;

        try {
            result = new JQDatatablesPaginationResult();

            if (start == null || length == null) {
                result.setDraw(draw);
                result.setRecordsTotal(0);
                result.setRecordsFiltered(0);
                return result;
            }

//            List<SysUserEntity> all = null;
//            if (length <= 0) {
//                if (status == 0) {
//                    all = sysUserDao.findAll();
//                }
//                else {
//                    all = sysUserDao.findByStatus(status);
//                }
//
//            }
//            else {
//                if (status == 0) {
//                    // 需要改为分页排序的
//                    all = sysUserDao.findAllByPagination(length, start / length);
////                    all = sysUserDao.findAllByPaginationOrder(length, start / length);
//                }
//                else {
//                    all = sysUserDao.findByStatusPagination(status, length, start / length);
//                }
//            }
            HttpResponse<List<SysUserEntity>> listHttpResponse = sysUserListRaw(start, length, status);
            if (!listHttpResponse.getCode().equals(ErrorCode.CODE_OK)) {
                return result;
            }

            List<SysUserEntity> all = listHttpResponse.getResult();

            if (all == null) {
                result.setDraw(draw);
                result.setRecordsTotal(0);
                result.setRecordsFiltered(0);
                result.setData(new ArrayList());
                return result;
            }

            result.setDraw(draw);
            result.setRecordsTotal(status == 0 ? (int) sysUserDao.countAll() : sysUserDao.countByStatus(status)); // 从DB中获取总数
            result.setRecordsFiltered(result.getRecordsTotal());

            result.setData(all);
        } catch (DataAccessException e) {
            log.error(e.getMessage(), e);
        }
        return result;
    }

    @RequestMapping(value = "/admin/sys/user/list/raw")
    public
    @ResponseBody
    HttpResponse<List<SysUserEntity>> sysUserListRaw(
            @RequestParam int page,
            @RequestParam("page_size") int pageSize,
            @RequestParam int status) {
//        Integer start = Integer.valueOf(params.get("start"));
//        Integer length = Integer.valueOf(params.get("length"));
//        Integer status = 0;// 可选参数
//        if (params.containsKey("status")) {
//            status = Integer.valueOf(params.get("status"));
//        }

        List<SysUserEntity> all = null;
        if (pageSize <= 0) {
            if (status == 0) {
                all = sysUserDao.findAll();
            }
            else {
                all = sysUserDao.findByStatus(status);
            }

        }
        else {
            if (status == 0) {
                // 需要改为分页排序的
                all = sysUserDao.findAllByPagination(pageSize, page / pageSize);
//                    all = sysUserDao.findAllByPaginationOrder(length, start / length);
            }
            else {
                all = sysUserDao.findByStatusPagination(status, pageSize, page / pageSize);
            }
        }
        return new HttpResponse<>(all);
    }

    /**
     * 修改密码
     *
     * @param command
     * @param session
     * @return
     */
    @RequestMapping(value = "/admin/change_pwd", method = RequestMethod.POST)
    public
    @ResponseBody
    HttpResponse<Void> changePwd(@RequestBody ChangePwdCommand command, HttpSession session) {

        if (StringUtils.isAnyBlank(command.getNewPwd()) || command.getNewPwd().length() < 5) {
            return new HttpResponse<>(ErrorCode.CODE_SYS_USER_PASSWORD_TOO_SIMPLE);//密码太简单
        }

        try {
            SysUserEntity sysUserEntity = sysUserDao.findSysUserById(session.getAttribute("user_id").toString());
            if (sysUserEntity.getLoginPwd().equals(SysUtils.encryptPassword(command.getOldPwd()))) {
                sysUserEntity.setLoginPwd(SysUtils.encryptPassword(command.getNewPwd()));
                sysUserDao.update(sysUserEntity);
                return new HttpResponse<>();
            }
            else
                return new HttpResponse<>(ErrorCode.CODE_SYS_USER_PASSWORD_WRONG);//密码不正确
        } catch (AryaServiceException e) {
            log.error(e.getMessage(), e);
            return new HttpResponse<>(e.getErrorCode());
        }
    }
}
