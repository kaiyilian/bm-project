package com.bumu.arya.admin.system.controller;

import com.bumu.arya.Utils;
import com.bumu.arya.admin.system.controller.command.SysConfigCreateCommand;
import com.bumu.arya.admin.system.controller.command.SysConfigUpdateCommand;
import com.bumu.arya.admin.system.result.SysConfigListResult;
import com.bumu.arya.admin.system.result.SysConfigListResult.SysConfig;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.model.ConfigDao;
import com.bumu.arya.model.ConfigEntity;
import com.bumu.arya.response.HttpResponse;
import com.bumu.common.constant.Constants;
import com.bumu.exception.AryaServiceException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isBlank;


/**
 * Created by CuiMengxin on 2017/2/14.
 */
@Api(tags = {"系统配置接口SysConfig"})
@Controller
public class SystemConfigController {

    @Autowired
    ConfigDao configDao;

    /**
     * 默认页面
     *
     * @return
     */
    @ApiOperation(value = "系统配置索引页")
    @RequestMapping(value = "/admin/sys/config/index", method = RequestMethod.GET)
    public String defaultPage() {
        return "system/sys_config";
    }

    /**
     * 获取配置列表
     *
     * @return
     */
    @ApiOperation(value = "获取配置列表")
    @RequestMapping(value = "/admin/sys/config/list", method = RequestMethod.GET)
    public
    @ResponseBody
    HttpResponse<SysConfigListResult> getConfigList() {
        SysConfigListResult result = new SysConfigListResult();
        result.setConfigs(new ArrayList<>());
        List<ConfigEntity> configEntities = configDao.getAll();

        for (ConfigEntity config : configEntities) {
            SysConfig conf = new SysConfig();
            conf.setId(config.getId());
            conf.setKey(config.getKey());
            conf.setValue(config.getValue());

            if (StringUtils.isNotBlank(config.getValue()) && config.getValue().length() > 64) {
                conf.setValueAbbreviate(StringUtils.abbreviate(config.getValue(), 64));
            }
            else {
                conf.setValueAbbreviate(config.getValue());
            }
            String memo = config.getMemo();
            if (isBlank(memo)) {
                memo = " ";
            }
            conf.setMemo(memo);
            conf.setIsDeprecated(config.getIsDeprecated() != null && config.getIsDeprecated() == Constants.TRUE ? "yes" : "no");
            result.getConfigs().add(conf);
        }
        return new HttpResponse<>(result);
    }

    @ApiOperation(value = "新增配置")
    @RequestMapping(value = "/admin/sys/config/create", method = RequestMethod.POST)
    public
    @ResponseBody
    HttpResponse<Void> createConfig(@RequestBody SysConfigCreateCommand command) {
        if (isBlank(command.getKey()) || isBlank(command.getValue()) || isBlank(command.getIsDeprecated())) {
            throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "key, value或是否废弃不能为空");
        }

        ConfigEntity config = configDao.getConfigByKey(command.getKey());
        if (config != null) {
            throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "配置项已经存在");
        }
        config = new ConfigEntity();
        config.setId(Utils.makeUUID());
        config.setKey(command.getKey());
        config.setValue(command.getValue());
        config.setMemo(command.getMemo());
        config.setIsDeprecated("yes".equals(command.getIsDeprecated()) ? Constants.TRUE : Constants.FALSE);
        configDao.create(config);
        return new HttpResponse<>();
    }

    /**
     * 修改配置
     *
     * @param command
     * @return
     */
    @ApiOperation(value = "修改配置")
    @RequestMapping(value = "/admin/sys/config/update", method = RequestMethod.POST)
    public
    @ResponseBody
    HttpResponse<Void> update(@RequestBody SysConfigUpdateCommand command) {
        if (isBlank(command.getId()) || isBlank(command.getValue()) || isBlank(command.getIsDeprecated())) {
            throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "id或value或是否废弃不能为空。");
        }
        ConfigEntity configEntity = configDao.find(command.getId());
        if (configEntity == null) {
            throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "找不到配置项");
        }
        configEntity.setValue(command.getValue());
        configEntity.setMemo(command.getMemo());
        configEntity.setIsDeprecated(command.getIsDeprecated().equals("yes") ? Constants.TRUE : Constants.FALSE);
        configDao.update(configEntity);
        return new HttpResponse<>();
    }

    public static void main(String[] args) {
        int i= 0;
        Integer j = null;
        System.out.println(i == j);
    }

}
