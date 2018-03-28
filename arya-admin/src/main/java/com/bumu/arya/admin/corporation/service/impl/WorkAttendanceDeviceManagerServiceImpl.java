package com.bumu.arya.admin.corporation.service.impl;


import com.bumu.arya.admin.corporation.service.WorkAttendanceDeviceManagerService;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.model.CorporationDao;
import com.bumu.arya.model.entity.CorporationEntity;
import com.bumu.attendance.model.dao.WorkAttendanceDeviceManagerDao;
import com.bumu.attendance.model.entity.WorkAttendanceDeviceManagerEntity;
import com.bumu.attendance.result.WorkAttendanceDeviceManagerResult;
import com.bumu.bran.model.dao.BranCorporationDao;
import com.bumu.bran.model.entity.BranCorporationEntity;
import com.bumu.common.SessionInfo;
import com.bumu.common.command.BaseCommand;
import com.bumu.common.command.WorkAttendanceDeviceManagerCommand;
import com.bumu.common.result.BaseResult;
import com.bumu.common.util.ListUtils;
import com.bumu.exception.AryaServiceException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author majun
 * @date 2017/3/21
 */
@Service
public class WorkAttendanceDeviceManagerServiceImpl implements WorkAttendanceDeviceManagerService {

    private static Logger logger = LoggerFactory.getLogger(WorkAttendanceDeviceManagerServiceImpl.class);

    @Autowired
    private WorkAttendanceDeviceManagerDao workAttendanceDeviceManagerDao;

    @Autowired
    private CorporationDao corporationDao;

    @Autowired
    private BranCorporationDao branCorporationDao;

    @Override
    public BaseResult.IDResult add(WorkAttendanceDeviceManagerCommand.Add command, SessionInfo sessionInfo) {
        if (StringUtils.isAnyBlank(command.getDeviceNo()) || command.getDeviceNo().length() > 16) {
            throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "考勤机编号最多9位且不能为空");
        }

        logger.info("查询branCorpId...");
        CorporationEntity aryaCorpEntity = corporationDao.findCorporationByIdThrow(sessionInfo.getCorpId());
        if (StringUtils.isBlank(aryaCorpEntity.getBranCorpId())) {
            throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "没有branCorpId");
        }

        logger.info("设置branCorpId...");
        sessionInfo.setCorpId(aryaCorpEntity.getBranCorpId());

        WorkAttendanceDeviceManagerEntity workAttendanceDeviceManagerEntity = new WorkAttendanceDeviceManagerEntity();
        command.convert(
                workAttendanceDeviceManagerEntity,
                entity -> {
                    logger.info("判断本公司是否存在,相同的设备");
                    logger.info("设备号: " + command.getDeviceNo());
                    WorkAttendanceDeviceManagerEntity other =
                            workAttendanceDeviceManagerDao.findByBranCorpIdAndDeviceNo(command.getDeviceNo(), sessionInfo.getCorpId());
                    if (other != null) {
                        throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "当前公司已经存在该设备,不要重复添加");
                    }

                    logger.info("判断其他公司是否存在,相同的设备");
                    List<WorkAttendanceDeviceManagerEntity> others = workAttendanceDeviceManagerDao.findByOtherBranCorpIdAndDeviceNo(
                            command.getDeviceNo(), sessionInfo.getCorpId());
//
                    // 公司已经删除的设备要过滤掉
                    Iterator<WorkAttendanceDeviceManagerEntity> iterator = others.iterator();
                    while (iterator.hasNext()) {
                        WorkAttendanceDeviceManagerEntity device = iterator.next();
                        BranCorporationEntity branCorporationEntity = branCorporationDao.findByIdNotDelete(device.getBranCorpId());
                        if (branCorporationEntity == null) {
                            iterator.remove();
                        }
                    }

                    if (!ListUtils.checkNullOrEmpty(others)) {
                        logger.info("others: " + others.toString());
                        throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "其他公司已经存在该设备,不要重复添加");
                    }
                    command.begin(entity, sessionInfo);
                },
                workAttendanceDeviceManagerDao::persist
        );
        BaseResult.IDResult idResult = new BaseResult.IDResult();
        idResult.setId(workAttendanceDeviceManagerEntity.getId());

        return idResult;
    }

    @Override
    public BaseResult.IDResult update(WorkAttendanceDeviceManagerCommand.Update command, SessionInfo sessionInfo) {
        if (StringUtils.isAnyBlank(command.getDeviceNo()) || command.getDeviceNo().length() > 16) {
            throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "考勤机编号最多9位且不能为空");
        }
        logger.info("查询branCorpId...");
        CorporationEntity aryaCorpEntity = corporationDao.findCorporationByIdThrow(sessionInfo.getCorpId());
        if (StringUtils.isBlank(aryaCorpEntity.getBranCorpId())) {
            throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "没有branCorpId");
        }

        logger.info("设置branCorpId...");
        sessionInfo.setCorpId(aryaCorpEntity.getBranCorpId());

        command.convert(
                workAttendanceDeviceManagerDao.findByIdNotDelete(command.getId()),
                entity -> {
                    logger.info("判断本公司是否存在,相同的设备");
                    logger.info("设备号: " + command.getDeviceNo());
                    WorkAttendanceDeviceManagerEntity other =
                            workAttendanceDeviceManagerDao.findByBranCorpIdAndDeviceNo(command.getDeviceNo(), sessionInfo.getCorpId());
                    if (other != null && !other.getId().equals(command.getId())) {
                        throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "当前公司已经存在该设备,不要重复添加");
                    }

                    logger.info("判断其他公司是否存在,相同的设备");
                    List<WorkAttendanceDeviceManagerEntity> others = workAttendanceDeviceManagerDao.findByOtherBranCorpIdAndDeviceNo(
                            command.getDeviceNo(), sessionInfo.getCorpId());


                    // 公司已经删除的设备要过滤掉
                    Iterator<WorkAttendanceDeviceManagerEntity> iterator = others.iterator();
                    while (iterator.hasNext()) {
                        WorkAttendanceDeviceManagerEntity device = iterator.next();
                        BranCorporationEntity branCorporationEntity = branCorporationDao.findByIdNotDelete(device.getBranCorpId());
                        if (branCorporationEntity == null) {
                            iterator.remove();
                        }
                    }

                    if (!ListUtils.checkNullOrEmpty(others)) {
                        throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "其他公司已经存在该设备,不要重复添加");
                    }
                    command.begin(entity, sessionInfo);
                },
                workAttendanceDeviceManagerDao::update
        );
        BaseResult.IDResult idResult = new BaseResult.IDResult();
        idResult.setId(command.getId());

        return idResult;
    }

    @Override
    public void batchDelete(BaseCommand.BatchIds command, SessionInfo sessionInfo) {
        logger.info("ids: " + command.getBatch());
        command.getBatch().forEach(param -> workAttendanceDeviceManagerDao.mockDelete(param.getId(), sessionInfo));
    }

    @Override
    public List<WorkAttendanceDeviceManagerResult> getList(SessionInfo sessionInfo) {

        logger.info("查询branCorpId...");
        CorporationEntity aryaCorpEntity = corporationDao.findCorporationByIdThrow(sessionInfo.getCorpId());
        if (StringUtils.isBlank(aryaCorpEntity.getBranCorpId())) {
            throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "没有branCorpId");
        }

        logger.info("设置branCorpId...");
        sessionInfo.setCorpId(aryaCorpEntity.getBranCorpId());

        return workAttendanceDeviceManagerDao.findByBranCorpId(sessionInfo.getCorpId())
                .stream()
                .map(one -> {
                    WorkAttendanceDeviceManagerResult r = new WorkAttendanceDeviceManagerResult();
                    r.convert(one);
                    return r;
                })
                .collect(Collectors.toList());
    }
}
