package com.bumu.bran.admin.msg_center.service.impl;

import com.bumu.bran.admin.msg_center.service.MsgCenterService;
import com.bumu.bran.msgcenter.constant.MsgCenterEnum;
import com.bumu.bran.msgcenter.model.dao.BranMsgCenterDao;
import com.bumu.bran.msgcenter.model.entity.BranMsgCenterEntity;
import com.bumu.bran.msgcenter.result.MsgCenterResult;
import com.bumu.common.SessionInfo;
import com.bumu.common.command.BaseCommand;
import com.bumu.common.result.Pager;
import com.bumu.common.util.ListUtils;
import com.bumu.exception.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author majun
 * @date 2016/7/03
 */
@Service
public class MsgCenterServiceImpl implements MsgCenterService {

    @Autowired
    private BranMsgCenterDao branMsgCenterDao;

    @Override
    public Pager<MsgCenterResult> getPagerList(Integer page, Integer pageSize, SessionInfo sessionInfo) {
        Pager<MsgCenterResult> resultPager = new Pager<>();
        List<MsgCenterResult> resultList = new ArrayList<>();
        Pager<BranMsgCenterEntity> pager = branMsgCenterDao.findByPagerList(page, pageSize, sessionInfo);
        if (!ListUtils.checkNullOrEmpty(pager.getResult())) {
            for (BranMsgCenterEntity entity : pager.getResult()) {
                MsgCenterResult msgCenterResult = new MsgCenterResult();
                msgCenterResult.setId(entity.getId());
                msgCenterResult.setTxVersion(entity.getTxVersion());
                msgCenterResult.setBranCorpId(entity.getBranCorpId());
                msgCenterResult.setBusinessType(entity.getBusinessType().ordinal());
                msgCenterResult.setCreateTime(entity.getCreateTime());
                msgCenterResult.setMsg(entity.getMsg());
                msgCenterResult.setReadState(entity.getReadState().ordinal());
                msgCenterResult.setReturnId(entity.getReturnId());
                resultList.add(msgCenterResult);
            }
        }
        resultPager.setPageSize(pager.getPageSize());
        resultPager.setRowCount(pager.getRowCount());
        resultPager.setResult(resultList);
        return resultPager;
    }

    @Override
    public void setState(BaseCommand.IDCommand IDCommand) {
        BranMsgCenterEntity branMsgCenterEntity = branMsgCenterDao.findByIdNotDelete(IDCommand.getId());
        Assert.notNull(branMsgCenterEntity, "没有查询到消息: " + IDCommand.getId());
        branMsgCenterEntity.setReadState(MsgCenterEnum.ReadState.read);
        branMsgCenterEntity.setUpdateTime(System.currentTimeMillis());
        branMsgCenterDao.update(branMsgCenterEntity);
    }
}
