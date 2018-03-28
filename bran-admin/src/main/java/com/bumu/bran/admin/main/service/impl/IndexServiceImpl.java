package com.bumu.bran.admin.main.service.impl;

import com.bumu.SysUtils;
import com.bumu.arya.Utils;
import com.bumu.arya.command.PagerCommand;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.model.AryaUserDao;
import com.bumu.arya.model.entity.AryaUserEntity;
import com.bumu.bran.admin.config.service.BranAdminConfigService;
import com.bumu.bran.admin.main.controller.command.EmpIdCardNoExpireTimeSetCommand;
import com.bumu.bran.admin.main.service.IndexService;
import com.bumu.bran.employee.command.ModelCommand;
import com.bumu.bran.employee.model.dao.EmployeeDao;
import com.bumu.bran.employee.model.dao.ProspectiveEmployeeDao;
import com.bumu.bran.employee.model.dao.mybatis.EmployeeMybatisDao;
import com.bumu.bran.helper.ExcelExportHelper;
import com.bumu.bran.home.result.EmpBirthdayWarningResult;
import com.bumu.bran.home.result.EmpProspectiveWarningResult;
import com.bumu.bran.model.dao.BranCorporationDao;
import com.bumu.bran.model.entity.BranCorporationEntity;
import com.bumu.common.SessionInfo;
import com.bumu.common.command.BatchCommand;
import com.bumu.common.model.PushMessage;
import com.bumu.common.model.PushTo;
import com.bumu.common.result.FileUploadFileResult;
import com.bumu.common.result.Pager;
import com.bumu.common.result.SmsSendResult;
import com.bumu.common.service.ConfigService;
import com.bumu.common.service.FileUploadService;
import com.bumu.common.service.PushService;
import com.bumu.common.service.SmsSendService;
import com.bumu.common.util.ListUtils;
import com.bumu.employee.model.entity.EmployeeEntity;
import com.bumu.exception.AryaServiceException;
import com.bumu.exception.Assert;
import com.bumu.prospective.model.entity.ProspectiveEmployeeEntity;
import com.bumu.utils.ShortUrlUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.bumu.common.constant.Constants.TRUE;

/**
 * majun
 */
@Service
public class IndexServiceImpl implements IndexService {

    private static Logger logger = LoggerFactory.getLogger(IndexServiceImpl.class);

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private EmployeeMybatisDao employeeMybatisDao;

    @Autowired
    private ProspectiveEmployeeDao prospectiveEmployeeDao;

    @Autowired
    private BranAdminConfigService branAdminConfigService;

    @Autowired
    private BranCorporationDao branCorporationDao;

    @Autowired
    private ExcelExportHelper excelExportHelper;

    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private AryaUserDao aryaUserDao;

    @Autowired
    private PushService pushService;

    @Autowired
    private SmsSendService<SmsSendResult> smsSendService;

    @Autowired
    private ConfigService configService;


    @Override
    public Map<String, Object> getScheduleViews(ModelCommand modelCommand) {
        // 为什么返回Null, 首页查询排班视图列表的现在不显示了,废弃
        // see svn \arya\项目开发文档\026东吴黄金-员工管理完善
        return null;
    }

    @Override
    public Pager<EmpBirthdayWarningResult> getEmpBirthdayWarning(PagerCommand pagerCommand, SessionInfo sessionInfo) {
        logger.debug("计算生日日期是否需要更新, 是否是当前年");
        int count = employeeDao.updateCurYearBirthday(sessionInfo.getCorpId());
        logger.debug("成功更新了: " + count);

        Page<EmpBirthdayWarningResult> page = PageHelper.startPage(pagerCommand.getPage(), pagerCommand.getPage_size()).doSelectPage(() ->
                employeeMybatisDao.findEmpBirthdayWarning(sessionInfo.getCorpId())
        );
        return new Pager(page.getPageSize(), (int) page.getTotal(), page.getResult());
    }

    @Override
    public void disposeEmpBirthdayWarning(Set<com.bumu.common.command.ModelCommand> ids, SessionInfo sessionInfo) {
        if (ListUtils.checkNullOrEmpty(ids)) {
            throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR, "没有要处理的员工");
        }
        ids.forEach(one -> {
            EmployeeEntity employeeEntity = employeeDao.findByIdNotDelete(one.getId());
            Assert.notNull(employeeEntity, "没有查询到员工");
            // 已处理
            employeeEntity.setIsDisposed(TRUE);
            employeeDao.update(employeeEntity);
        });
    }

    @Override
    public Pager<EmpBirthdayWarningResult> getEmpIdCardNoWarning(PagerCommand pagerCommand, SessionInfo sessionInfo) {

        Page<EmpBirthdayWarningResult> page = PageHelper.startPage(pagerCommand.getPage(), pagerCommand.getPage_size()).doSelectPage(() ->
                employeeMybatisDao.findEmpIdCardNoWarning(sessionInfo.getCorpId())
        );
        return new Pager(page.getPageSize(), (int) page.getTotal(), page.getResult());
    }

    @Override
    public void setEmpIdCardNoExpireTime(EmpIdCardNoExpireTimeSetCommand empIdCardNoExpireTimeSetCommand, SessionInfo sessionInfo) {
        Assert.notBlank(empIdCardNoExpireTimeSetCommand.getId(), "员工id必填");
        Assert.notNull(empIdCardNoExpireTimeSetCommand.getStart(), "开始时间必填");
        Assert.notNull(empIdCardNoExpireTimeSetCommand.getEnd(), "结束时间必填");
        if (empIdCardNoExpireTimeSetCommand.getEnd() < empIdCardNoExpireTimeSetCommand.getStart()) {
            throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR, "开始时间不可以大于结束时间");
        }

        EmployeeEntity employeeEntity = employeeDao.findByIdNotDelete(empIdCardNoExpireTimeSetCommand.getId());
        Assert.notNull(employeeEntity, "没有查询到员工");
        employeeEntity.setExpireTime(
                SysUtils.getDateStringFormTimestamp(empIdCardNoExpireTimeSetCommand.getStart(), "yyyy.MM.dd") +
                        "-" +
                        SysUtils.getDateStringFormTimestamp(empIdCardNoExpireTimeSetCommand.getEnd(), "yyyy.MM.dd")
        );
        employeeDao.update(employeeEntity);
    }

    @Override
    public Pager<EmpProspectiveWarningResult> getEmpProspectiveWarning(PagerCommand pagerCommand, SessionInfo sessionInfo) {
        Page<EmpProspectiveWarningResult> page = PageHelper.startPage(pagerCommand.getPage(), pagerCommand.getPage_size()).doSelectPage(() ->
                employeeMybatisDao.getEmpProspectiveWarning(sessionInfo.getCorpId())
        );
        return new Pager(page.getPageSize(), (int) page.getTotal(), page.getResult());
    }

    @Override
    public void disposeEmpProspectiveWarning(BatchCommand batch, SessionInfo sessionInfo) {

        BranCorporationEntity branCorporationEntity = branCorporationDao.findByIdNotDelete(sessionInfo.getCorpId());
        Assert.notNull(branCorporationEntity, "公司不存在");

        batch.getBatch().forEach(one -> {
            ProspectiveEmployeeEntity entity = prospectiveEmployeeDao.findProspectiveEmployeeById(one.getId());
            if (entity != null && entity.getNotifyState() != 2) {
                // 已通知未确认
                entity.setNotifyState(1);
                prospectiveEmployeeDao.update(entity);

                String url = configService.getConfigByKey("bran.core.prospective.warning.url") + entity.getId();
                url = ShortUrlUtil.generalShortUrl(url);

                logger.info("发送推送");
                PushMessage pushMessage = new PushMessage();
                if (StringUtils.isBlank(entity.getPhoneNo())) {
                    logger.warn("待入职员工未填写手机号: " + entity.getId());
                    // 只是结束本次循环
                    return;
                }
                AryaUserEntity aryaUserEntity = aryaUserDao.findUserByPhoneNo(entity.getPhoneNo());
                if (aryaUserEntity == null) {
                    logger.warn("待入职员工还没有注册app: " + entity.getId());
                    // 只是结束本次循环
                    return;
                }
                pushMessage.setUserId(aryaUserEntity.getId());
                pushMessage.setTitle("入职通知");
                pushMessage.setContent("您好,你已收到" + branCorporationEntity.getCorpName() + "的入职通知，请尽快确认");
                pushMessage.setDisplayType(3);
                pushMessage.setType(10);
                pushMessage.setUrl(url);
                pushService.push(pushMessage, new PushTo.PushToOne(
                        Utils.makeUUID(),
                        aryaUserEntity.getId(),
                        com.bumu.arya.common.Constants.ClientType.getClientType(aryaUserEntity.getLastClientType())
                ));
                logger.info("推送发送完毕");

                logger.info("发送短信");
                try {

                    SmsSendResult smsSendResult = smsSendService.sendSmsMessage(entity.getPhoneNo(),
                            String.format("您收到一份通知,点击 %s  确认。【不木科技】", url));

                    logger.info(smsSendResult.toString());
                } catch (Exception e) {
                    logger.error(String.format("%s 短信发送失败", entity.getPhoneNo()));
                    logger.error(String.format("%s 短信发送失败的原因", e.getMessage()));
                }
                logger.info("发送短信完毕");
            }
        });
    }

    @Override
    public FileUploadFileResult exportEmpProspectiveWarning(SessionInfo sessionInfo) throws Exception {
        String templateName = "入职提醒导出模板";
        File in = new File(branAdminConfigService.getFilePath("bran.dir.excel.template", 0) + File.separator +
                templateName + ".xls");

        if (!in.exists()) {
            logger.error("in... :" + in.getPath());
            throw new AryaServiceException(ErrorCode.CODE_ORDER_TEMPLATE_IS_NOT_EXIST);
        }

        List<EmpProspectiveWarningResult> list = employeeMybatisDao.getEmpProspectiveWarning(sessionInfo.getCorpId());

        Map<String, Object> params = new HashedMap();
        params.put("list", list);

        BranCorporationEntity branCorporationEntity = branCorporationDao.findByIdNotDelete(sessionInfo.getCorpId());
        Assert.notNull(branCorporationEntity, "没有查询到企业");

        // 入职提醒导出模板
        String fileName = branCorporationEntity.getCorpName() + "入职提醒导出";
        File temp = new File(branAdminConfigService.getFilePath("temp", 0) + fileName + "." + "xls");
        logger.info("temp: " + temp.getPath());
        OutputStream outputStream = new FileOutputStream(temp);

        excelExportHelper.export(
                in.getPath(),
                in.getName(),
                params,
                outputStream
        );

        // 生成下载模板的url路径
        String url = fileUploadService.generateDownLoadFileUrl(
                branAdminConfigService.getConfigByKey("bran.admin.resource.server"),
                com.bumu.bran.common.Constants.HPPT_TYPE_EXCEL,
                fileName,
                0,
                "xls",
                "bran.dir.excel.template"
        );

        logger.info("url: " + url);

        return new FileUploadFileResult(null, url);
    }

}
