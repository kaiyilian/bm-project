package com.bumu.bran.admin.prospective.service.impl;

import com.bumu.arya.Utils;
import com.bumu.bran.admin.prospective.service.ProspectiveConfigService;
import com.bumu.bran.employee.command.ProspectiveConfigCommand;
import com.bumu.bran.employee.model.dao.ProspectiveConfigDao;
import com.bumu.bran.employee.model.entity.ProspectiveConfigEntity;
import com.bumu.bran.employee.result.ProspectiveConfigResult;
import com.bumu.common.SessionInfo;
import com.bumu.exception.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author majun
 * @date 2017/8/15
 * @email 351264830@qq.com
 */
@Service
public class ProspectiveConfigServiceImpl implements ProspectiveConfigService {

    @Autowired
    private ProspectiveConfigDao prospectiveConfigDao;

    @Override
    public void add(ProspectiveConfigCommand prospectiveConfigCommand, SessionInfo sessionInfo) {

        ProspectiveConfigEntity prospectiveConfigEntity = new ProspectiveConfigEntity();
        prospectiveConfigEntity.setId(Utils.makeUUID());
        prospectiveConfigEntity.setBranCorpId(sessionInfo.getCorpId());
        prospectiveConfigEntity.setCareerInfoRequired(prospectiveConfigCommand.getCareerInfoRequired());
        prospectiveConfigEntity.setFaceInfoRequired(prospectiveConfigCommand.getFaceInfoRequired());
        prospectiveConfigEntity.setEducationInfoRequired(prospectiveConfigCommand.getEducationInfoRequired());
        prospectiveConfigEntity.setBankCardNoInfoRequired(prospectiveConfigCommand.getBankCardNoInfoRequired());
        prospectiveConfigEntity.setBaseInfoRequired(prospectiveConfigCommand.getBaseInfoRequired());
        prospectiveConfigDao.persist(prospectiveConfigEntity);
    }

    @Override
    public void update(ProspectiveConfigCommand prospectiveConfigCommand, SessionInfo sessionInfo) {
        ProspectiveConfigEntity prospectiveConfigEntity = prospectiveConfigDao.findByIdNotDelete(prospectiveConfigCommand.getId());
        Assert.notNull(prospectiveConfigEntity, "没有查询到配置id: " + prospectiveConfigCommand.getId());
        prospectiveConfigEntity.setBranCorpId(sessionInfo.getCorpId());
        prospectiveConfigEntity.setCareerInfoRequired(prospectiveConfigCommand.getCareerInfoRequired());
        prospectiveConfigEntity.setFaceInfoRequired(prospectiveConfigCommand.getFaceInfoRequired());
        prospectiveConfigEntity.setEducationInfoRequired(prospectiveConfigCommand.getEducationInfoRequired());
        prospectiveConfigEntity.setBankCardNoInfoRequired(prospectiveConfigCommand.getBankCardNoInfoRequired());
        prospectiveConfigEntity.setBaseInfoRequired(prospectiveConfigCommand.getBaseInfoRequired());
        prospectiveConfigDao.update(prospectiveConfigEntity);
    }

    @Override
    public ProspectiveConfigResult get(SessionInfo sessionInfo) {
        ProspectiveConfigResult prospectiveConfigResult = new ProspectiveConfigResult();
        ProspectiveConfigEntity prospectiveConfigEntity = prospectiveConfigDao.findByBranCorpId(sessionInfo.getCorpId());
        if (prospectiveConfigEntity != null) {
            prospectiveConfigResult.setId(prospectiveConfigEntity.getId());
            prospectiveConfigResult.setIdCardNoInfoRequired(prospectiveConfigEntity.getIdCardNoInfoRequired());
            prospectiveConfigResult.setFaceInfoRequired(prospectiveConfigEntity.getFaceInfoRequired());
            prospectiveConfigResult.setBaseInfoRequired(prospectiveConfigEntity.getBaseInfoRequired());
            prospectiveConfigResult.setBankCardNoInfoRequired(prospectiveConfigEntity.getBankCardNoInfoRequired());
            prospectiveConfigResult.setEducationInfoRequired(prospectiveConfigEntity.getEducationInfoRequired());
            prospectiveConfigResult.setCareerInfoRequired(prospectiveConfigEntity.getCareerInfoRequired());
        }
        return prospectiveConfigResult;
    }
}
