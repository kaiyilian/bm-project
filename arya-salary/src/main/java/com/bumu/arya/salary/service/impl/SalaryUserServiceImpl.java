package com.bumu.arya.salary.service.impl;

import com.bumu.arya.salary.dao.SalaryUserDao;
import com.bumu.arya.salary.model.entity.SalaryUserEntity;
import com.bumu.arya.salary.service.SalaryUserService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.bumu.function.VoConverter.logger;

/**
 * @Author: Gujianchao
 * @Description:
 * @Date： 2017/7/14
 */
@Service
public class SalaryUserServiceImpl implements SalaryUserService{

    @Autowired
    private SalaryUserDao salaryUserDao;

    //如果数组太长 使用In查询担心报错 所以500个一查
    private static Integer LIST_SELECT_SIZE = 500;

    @Override
    public List<SalaryUserEntity> getUserList(List<String> userIds, String customerId) {
        if (CollectionUtils.isEmpty(userIds)) {
            return new ArrayList<>();
        }
        List<SalaryUserEntity> result = salaryUserDao.getListByIdCard(
                userIds.size() > LIST_SELECT_SIZE ? userIds.subList(0, LIST_SELECT_SIZE) : userIds, customerId);
        if (userIds.size() > LIST_SELECT_SIZE) {
            result.addAll(getUserList(userIds.subList(LIST_SELECT_SIZE, userIds.size()), customerId));
        }
        return result;
    }

    @Override
    public Map<String, SalaryUserEntity> userListToMap(List<SalaryUserEntity> list) {
        Map<String, SalaryUserEntity> map = new HashMap<>();
        for (SalaryUserEntity salaryUserEntity : list) {
            map.put(salaryUserEntity.getIdCardNo(), salaryUserEntity);
        }
        return map;
    }

    @Override
    public void saveUser(SalaryUserEntity salaryUserEntity) {
        //判断用户身份证是否存在
        SalaryUserEntity user = salaryUserDao.findByUniqueParam("idCardNo", salaryUserEntity.getIdCardNo());
        if (null == user) {
            salaryUserDao.create(salaryUserEntity);
        }else{
            logger.info("身份证号码：" + user.getIdCardNo() + "已经存在");
        }
    }

    @Override
    public Map<String, SalaryUserEntity> customerUserMap(String customerId) {
        List<SalaryUserEntity> salaryUserEntities = salaryUserDao.findByParam("customerId", customerId);
        Map<String, SalaryUserEntity> salaryUserEntityMap = new HashMap<>();
        for (SalaryUserEntity salaryUserEntity : salaryUserEntities) {
            salaryUserEntityMap.put(salaryUserEntity.getId(), salaryUserEntity);
        }
        return salaryUserEntityMap;
    }
}
