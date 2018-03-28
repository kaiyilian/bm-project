package com.bumu.arya.admin.soin.service.impl;


import com.bumu.SysUtils;
import com.bumu.arya.IdcardValidator;
import com.bumu.arya.admin.system.service.OpLogService;
import com.bumu.arya.admin.model.SysUserDao;
import com.bumu.arya.admin.model.entity.SysUserEntity;
import com.bumu.arya.admin.soin.controller.command.OrderPaymentCommand;
import com.bumu.arya.admin.soin.controller.command.SoinOrderSetSalesmanAndSupplierCommand;
import com.bumu.arya.admin.soin.model.dao.SoinSupplierDao;
import com.bumu.arya.admin.soin.model.entity.SoinSupplierEntity;
import com.bumu.arya.admin.soin.result.OrderResidualAmountResult;
import com.bumu.arya.admin.soin.result.OrderStatusChangeResult;
import com.bumu.arya.admin.soin.result.SoinOrderDetailResult;
import com.bumu.arya.admin.soin.result.SoinOrderListResult;
import com.bumu.arya.admin.soin.service.OrderPushService;
import com.bumu.arya.admin.soin.service.SoinOrderService;
import com.bumu.arya.common.Constants;
import com.bumu.arya.common.OperateConstants;
import com.bumu.arya.soin.constant.SoinOrderStatus;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.model.AryaUserDao;
import com.bumu.arya.model.SysLogDao;
import com.bumu.arya.model.entity.AryaUserEntity;
import com.bumu.arya.soin.constant.SoinOrderBillConstants;
import com.bumu.arya.soin.constant.SoinPersonHukouType;
import com.bumu.arya.soin.model.dao.*;
import com.bumu.arya.soin.model.entity.*;
import com.bumu.arya.soin.service.DistrictCommonService;
import com.bumu.arya.soin.service.SoinDistrictCommonService;
import com.bumu.arya.soin.util.SoinUtil;
import com.bumu.common.service.impl.BaseBumuService;
import com.bumu.common.util.BitOperateUtils;
import com.bumu.exception.AryaServiceException;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static com.bumu.SysUtils.*;
import static com.bumu.arya.admin.soin.result.SoinOrderListResult.SoinOrderResult;
import static com.bumu.arya.common.Constants.ORDER_ORIGIN_IMPORT;
import static com.bumu.arya.common.OperateConstants.SOIN_ORDER_SET_SALESMAN_SUPPLIER;
import static com.bumu.arya.common.OperateConstants.SOIN_ORDER_UPDATE_ORDER_SOIN_DETAIL;
import static com.bumu.arya.soin.constant.SoinOrderStatus.*;
import static com.bumu.arya.soin.constant.SoinOrderBillConstants.SOIN_PAY_SUCCESS;


/**
 * @author CuiMengxin
 * @date 2015/12/21
 */
@Service
public class SoinOrderServiceImpl extends BaseBumuService implements SoinOrderService {

    public static final int ORDER_NOT_ARREARAGE = 1;//订单不欠费
    public static final int ORDER_ARREARAGE = 2;//订单欠费

    public static final int FORWARD_MONTH = 0;

    Logger log = LoggerFactory.getLogger(SoinOrderServiceImpl.class);

    @Autowired
    SoinOrderDao soinOrderDao;

    @Autowired
    AryaUserDao userDao;

    @Autowired
    InsurancePersonDao personDao;

    @Autowired
    AryaSoinTypeDao soinTypeDao;

    @Autowired
    SoinTypeVersionDao versionDao;

    @Autowired
    SoinRuleDao ruleDao;

    @Autowired
    DistrictCommonService districtCommonService;

    @Autowired
    SoinDistrictCommonService soinDistrictCommonService;

    @Autowired
    AryaSoinDao soinDao;

    @Autowired
    OrderPushService orderPushService;

    @Autowired
    SysLogDao logDao;

    @Autowired
    SysUserDao sysUserDao;

    @Autowired
    SoinSupplierDao soinSupplierDao;

    @Autowired
    OpLogService opLogService;

    @Override
    public AryaSoinOrderEntity loadSoinOrder(String orderId) throws AryaServiceException {
        AryaSoinOrderEntity aryaSoinOrderEntity = soinOrderDao.find(orderId);
        if (aryaSoinOrderEntity == null) {
            throw new AryaServiceException(ErrorCode.CODE_SYS_ERR); // change it
        }
        return aryaSoinOrderEntity;
    }

    @Override
    public SoinOrderListResult getOrderManageList(String districtId, String soinTypeIds, String orderStatusCode, String keyword, int page, int pageSize) {
        SoinOrderListResult results = new SoinOrderListResult();
        if (StringUtils.isAnyBlank(orderStatusCode))//无订单状态返回该地区所有结果
            orderStatusCode = SoinOrderStatus.getAllManagerTypeBitOr().toString();

        if (StringUtils.isAnyBlank(districtId) && StringUtils.isAnyBlank(soinTypeIds))//无地区编号同时无社保类型返回全国结果
            districtId = Constants.CHN_ID;

        List<AryaSoinOrderEntity> orderEntities = null;
        int year = SysUtils.getCurrnentYear();//得到年
        int month = SysUtils.getCurrnentMonth() + FORWARD_MONTH;
        int currentTimeNumber = year * 12 + month;//当前月，数字表示形式
        //位与，取出订单状态
        ArrayList<Integer> orderStatus = BitOperateUtils.bitAnd(SoinOrderStatus.getOrderManageStatusArray(), Integer.parseInt(orderStatusCode));

        //如果不是全国所有社保类型
        if (!Constants.CHN_ID.equals(districtId) || !StringUtils.isAnyBlank(soinTypeIds)) {
            List<SoinTypeEntity> soinTypeEntities = null;
            if (!StringUtils.isAnyBlank(districtId)) {//如果是全选
                Collection<String> districtIds = soinDistrictCommonService.getAllChildSoinDistrictList(districtId);//从已开通社保地区中查出该地区下的所有子地区
                soinTypeEntities = soinTypeDao.findNotDeleteSoinTypeByDistricts(districtIds);//查出涉及的地区所有社保类型
            } else if (!StringUtils.isAnyBlank(soinTypeIds)) {//如果是选择部分社保类型
                String[] soinTypes = soinTypeIds.split(":");
                soinTypeEntities = soinTypeDao.findNotDeleteSoinTypeByIds(soinTypes);//查出所有未删除的社保类型
            } else return results;

            //取出类型ids
            ArrayList<String> soinTypeIdArray = new ArrayList<>();
            for (SoinTypeEntity typeEntity : soinTypeEntities) {
                soinTypeIdArray.add(typeEntity.getId());
            }
            //根据社保类型按状态分页查订单
            orderEntities = soinOrderDao.findSoinOrdersByPagination(soinTypeIdArray, orderStatus, keyword, currentTimeNumber, page, pageSize);
            results.setTotalCount(soinOrderDao.countOnlineSoinOrders(soinTypeIdArray, orderStatus, keyword, currentTimeNumber, page, pageSize));//总数
        } else {
            //查全国所有社保订单，按状态分页。
            orderEntities = soinOrderDao.findSoinOrdersByPagination(orderStatus, keyword, currentTimeNumber, page, pageSize);
            results.setTotalCount(Integer.parseInt(Long.toString(soinOrderDao.countOnlineSoinOrders(orderStatus, keyword, currentTimeNumber, page, pageSize))));//总数
        }

        if (orderEntities == null)
            return results;
        List<SoinOrderResult> orders = new ArrayList<>();
        results.setOrders(orders);
        for (AryaSoinOrderEntity orderEntity : orderEntities) {
            SoinOrderResult result = new SoinOrderResult();
            result.setOrderId(orderEntity.getId());
            result.setOrderNo(orderEntity.getOrderNo());
            result.setPersonName(orderEntity.getSoinPersonName());
            result.setDistrict(orderEntity.getDistrict());
            result.setTypeName(orderEntity.getSoinType());
            result.setStartYear(orderEntity.getYear());
            result.setStartMonth(orderEntity.getStartMonth());
            result.setCount(orderEntity.getCount());
            String desc = null;
            if (StringUtils.isAnyBlank(orderEntity.getPayedMonth())) {
                desc = "暂未缴纳";
            } else {
                desc = "已缴纳";
                String[] payedMonth = orderEntity.getPayedMonth().split(":");
                desc += StringUtils.join(payedMonth, ",") + "月";
            }
            result.setDesc(desc);
            result.setCreateTime(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(orderEntity.getCreateTime())));
            result.setPayment(turnBigDecimalMoneyToString(orderEntity.getPayment()));
            result.setStatusCode(orderEntity.getStatusCode());
            result.setArrearage(ORDER_NOT_ARREARAGE);
            orders.add(result);
        }
        return results;
    }

    @Override
    public SoinOrderListResult getOrderQueryList(String districtId, String soinTypeIds, String orderStatusCode, int page, int pageSize) {
        SoinOrderListResult results = new SoinOrderListResult();

        if (StringUtils.isAnyBlank(districtId) && StringUtils.isAnyBlank(soinTypeIds))//无地区编号同时无社保类型返回全国结果
            districtId = Constants.CHN_ID;

        List<AryaSoinOrderEntity> orderEntities = null;

        //如果不是全国所有社保类型
        if (!Constants.CHN_ID.equals(districtId) || !StringUtils.isAnyBlank(soinTypeIds)) {
            List<SoinTypeEntity> soinTypeEntities = null;
            if (!StringUtils.isAnyBlank(districtId)) {//如果是全选
                Collection<String> districtIds = soinDistrictCommonService.getAllChildSoinDistrictList(districtId);//从已开通社保地区中查出该地区下的所有子地区
                soinTypeEntities = soinTypeDao.findNotDeleteSoinTypeByDistricts(districtIds);//查出涉及的地区所有社保类型
            } else if (!StringUtils.isAnyBlank(soinTypeIds)) {//如果是选择部分社保类型
                String[] soinTypes = soinTypeIds.split(":");
                soinTypeEntities = soinTypeDao.findNotDeleteSoinTypeByIds(soinTypes);//查出所有未删除的社保类型
            } else return results;

            //取出类型ids
            ArrayList<String> soinTypeIdArray = new ArrayList<>();
            for (SoinTypeEntity typeEntity : soinTypeEntities) {
                soinTypeIdArray.add(typeEntity.getId());
            }

            if (StringUtils.isAnyBlank(orderStatusCode) || (Integer.parseInt(orderStatusCode) & SoinOrderStatus.ORDER_ALL) > 0)//无订单状态或者“所有状态”返回该地区所有结果
            {
                orderEntities = soinOrderDao.findAllPageOrdersBySoinTypes(soinTypeIdArray, page, pageSize);
                results.setFilterCount((int) soinOrderDao.countAllOrdersBySoinTypes(soinTypeIdArray));//过滤结果总数
            } else {
                //根据社保类型按状态分页查订单
                ArrayList<Integer> orderStatus = BitOperateUtils.bitAnd(SoinOrderStatus.getOrderStatusArray(), Integer.parseInt(orderStatusCode));
                orderEntities = soinOrderDao.findAllPageOrdersBySoinTypesAndStatus(soinTypeIdArray, orderStatus, page, pageSize);
                results.setFilterCount((int) soinOrderDao.countAllOrdersBySoinTypesAndStatus(soinTypeIdArray, orderStatus));//总数
            }
        } else {
            //查全国所有社保订单
            if (StringUtils.isAnyBlank(orderStatusCode) || (Integer.parseInt(orderStatusCode) & SoinOrderStatus.ORDER_ALL) > 0)//无订单状态或者“所有状态”返回该地区所有结果
            {
                orderEntities = soinOrderDao.findAllPageOrders(page, pageSize);
                results.setFilterCount((int) soinOrderDao.countAllOnlineOrders());//过滤结果总数
            } else {
                //根据订单状态分页查全国订单
                ArrayList<Integer> orderStatus = BitOperateUtils.bitAnd(SoinOrderStatus.getOrderStatusArray(), Integer.parseInt(orderStatusCode));
                orderEntities = soinOrderDao.findAllPageOrdersByStatus(orderStatus, page, pageSize);
                results.setFilterCount((int) soinOrderDao.countAllOrdersByStatus(orderStatus));//总数
            }
        }
        results.setTotalCount((int) soinOrderDao.countAllOnlineOrders());//订单总数
        if (orderEntities == null)
            return results;
        List<SoinOrderResult> orders = new ArrayList<>();
        results.setOrders(orders);
        for (AryaSoinOrderEntity orderEntity : orderEntities) {
            SoinOrderResult result = new SoinOrderResult();
            result.setOrderId(orderEntity.getId());
            result.setOrderNo(orderEntity.getOrderNo());
            result.setPersonName(orderEntity.getSoinPersonName());
            result.setDistrict(orderEntity.getDistrict());
            result.setTypeName(orderEntity.getSoinType());
            result.setStartYear(orderEntity.getYear());
            result.setStartMonth(orderEntity.getStartMonth());
            result.setCount(orderEntity.getCount());
            String desc = null;
            if (StringUtils.isAnyBlank(orderEntity.getPayedMonth())) {
                desc = "暂未缴纳";
            } else {
                desc = "已缴纳";
                String[] payedMonth = orderEntity.getPayedMonth().split(":");
                desc += StringUtils.join(payedMonth, ",") + "月";
            }
            result.setDesc(desc);
            result.setCreateTime(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(orderEntity.getCreateTime())));
            result.setPayment(turnBigDecimalMoneyToString(orderEntity.getPayment()));
            result.setStatusCode(orderEntity.getStatusCode());
            result.setArrearage(ORDER_NOT_ARREARAGE);
            orders.add(result);
        }
        return results;
    }

    @Override
    public SoinOrderListResult getOrderByOrderNo(String orderNo) {
        SoinOrderListResult results = new SoinOrderListResult();
        results.setTotalCount((int) soinOrderDao.countAllOnlineOrders());//订单总数
        AryaSoinOrderEntity orderEntity = soinOrderDao.findByOnlineOrderNo(orderNo.trim());
        if (orderEntity == null)
            return results;
        List<SoinOrderResult> orders = new ArrayList<>();
        results.setOrders(orders);
        SoinOrderResult result = new SoinOrderResult();
        result.setOrderId(orderEntity.getId());
        result.setOrderNo(orderEntity.getOrderNo());
        result.setPersonName(orderEntity.getSoinPersonName());
        result.setDistrict(orderEntity.getDistrict());
        result.setTypeName(orderEntity.getSoinType());
        result.setStartYear(orderEntity.getYear());
        result.setStartMonth(orderEntity.getStartMonth());
        result.setCount(orderEntity.getCount());
        String desc = null;
        if (StringUtils.isAnyBlank(orderEntity.getPayedMonth())) {
            desc = "暂未缴纳";
        } else {
            desc = "已缴纳";
            String[] payedMonth = orderEntity.getPayedMonth().split(":");
            desc += StringUtils.join(payedMonth, ",") + "月";
        }
        result.setDesc(desc);
        result.setCreateTime(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(orderEntity.getCreateTime())));
        result.setPayment(turnBigDecimalMoneyToString(orderEntity.getPayment()));
        result.setStatusCode(orderEntity.getStatusCode());
        result.setArrearage(ORDER_NOT_ARREARAGE);
        orders.add(result);
        results.setFilterCount(1);
        results.setTotalCount((int) soinOrderDao.countAllOnlineOrders());//订单总数
        return results;
    }

    @Override
    public SoinOrderListResult getOrderQueryListByUserIdcardOrPhoneOrName(String userIdcardOrPhoneOrName, int page, int pageSize) {
        SoinOrderListResult results = new SoinOrderListResult();
        results.setTotalCount((int) soinOrderDao.countAllOnlineOrders());//订单总数
        List<AryaSoinOrderEntity> orderEntities = null;

        String idcardNoOrPhoneNoOrRealName = new String();
        try {
            idcardNoOrPhoneNoOrRealName = URLDecoder.decode(userIdcardOrPhoneOrName.trim(), "UTF-8");
        } catch (Exception e) {
            elog.error(e.getMessage(), e);
            results.setFilterCount(0);
            return results;
        }

        if (SysUtils.checkPhoneNo(idcardNoOrPhoneNoOrRealName)) {
            AryaUserEntity userEntity = userDao.findUserByPhoneNo(idcardNoOrPhoneNoOrRealName);//手机号码查用户
            if (userEntity == null) {
                results.setFilterCount(0);
                return results;
            }
            orderEntities = soinOrderDao.findOnlineOrdersByUserId(userEntity.getId(), page, pageSize);//查订单
        } else if (IdcardValidator.isValidatedAllIdcard(idcardNoOrPhoneNoOrRealName)) {
            AryaUserEntity userEntity = userDao.findUserByIdcardNo(idcardNoOrPhoneNoOrRealName);//身份证号码查用户
            if (userEntity == null) {
                results.setFilterCount(0);
                return results;
            }
            orderEntities = soinOrderDao.findOnlineOrdersByUserId(userEntity.getId(), page, pageSize);//查订单
        } else {
            List<AryaUserEntity> userEntities = userDao.findUsersByRealName(idcardNoOrPhoneNoOrRealName);//姓名查用户
            if (userEntities == null || userEntities.size() == 0) {
                results.setFilterCount(0);
                return results;
            }
            ArrayList<String> userIds = new ArrayList<>();
            for (AryaUserEntity userEntity : userEntities) {
                userIds.add(userEntity.getId());
            }
            orderEntities = soinOrderDao.findOnlineOrdersByUserIds(userIds, page, pageSize);//查订单
        }

        if (orderEntities == null || orderEntities.size() == 0) {
            results.setFilterCount(0);
            return results;
        }
        List<SoinOrderResult> orders = new ArrayList<>();
        results.setOrders(orders);
        for (AryaSoinOrderEntity orderEntity : orderEntities) {
            SoinOrderResult result = new SoinOrderResult();
            result.setOrderId(orderEntity.getId());
            result.setOrderNo(orderEntity.getOrderNo());
            result.setPersonName(orderEntity.getSoinPersonName());
            result.setDistrict(orderEntity.getDistrict());
            result.setTypeName(orderEntity.getSoinType());
            result.setStartYear(orderEntity.getYear());
            result.setStartMonth(orderEntity.getStartMonth());
            result.setCount(orderEntity.getCount());
            String desc = null;
            if (StringUtils.isAnyBlank(orderEntity.getPayedMonth())) {
                desc = "暂未缴纳";
            } else {
                desc = "已缴纳";
                String[] payedMonth = orderEntity.getPayedMonth().split(":");
                desc += StringUtils.join(payedMonth, ",") + "月";
            }
            result.setDesc(desc);
            result.setCreateTime(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(orderEntity.getCreateTime())));
            result.setPayment(turnBigDecimalMoneyToString(orderEntity.getPayment()));
            result.setStatusCode(orderEntity.getStatusCode());
            result.setArrearage(ORDER_NOT_ARREARAGE);
            orders.add(result);
        }
        results.setFilterCount(orderEntities.size());
        return results;
    }

    @Override
    public SoinOrderListResult getOrderQueryListByPersonIdcardOrPhoneOrName(String personIdcardOrPhoneOrName, int page, int pageSize) {
        SoinOrderListResult results = new SoinOrderListResult();
        results.setTotalCount((int) soinOrderDao.countAllOnlineOrders());//订单总数
        List<AryaSoinPersonEntity> personEntities = null;
        String idcardNoOrPhoneNoOrRealName;
        try {
            idcardNoOrPhoneNoOrRealName = URLDecoder.decode(personIdcardOrPhoneOrName.trim(), "UTF-8");
        } catch (Exception e) {
            elog.error(e.getMessage(), e);
            results.setFilterCount(0);
            return results;
        }

        if (SysUtils.checkPhoneNo(idcardNoOrPhoneNoOrRealName)) {
            personEntities = personDao.findPersonsByPhoneNo(idcardNoOrPhoneNoOrRealName);//手机号码查参保人
        } else if (IdcardValidator.isValidatedAllIdcard(idcardNoOrPhoneNoOrRealName)) {
            personEntities = personDao.findPersonsByIdcardNo(idcardNoOrPhoneNoOrRealName);//身份证号码查参保人
        } else {
            personEntities = personDao.findPersonsByRealName(idcardNoOrPhoneNoOrRealName);//姓名查参保人
        }

        if (personEntities == null || personEntities.size() == 0) {
            results.setFilterCount(0);
            return results;
        }

        ArrayList<String> personIds = new ArrayList<>();
        for (AryaSoinPersonEntity personEntity : personEntities) {
            personIds.add(personEntity.getId());
        }

        List<AryaSoinOrderEntity> orderEntities = soinOrderDao.findOrdersByPersonIds(personIds, page, pageSize);

        if (orderEntities == null || orderEntities.size() == 0) {
            results.setFilterCount(0);
            return results;
        }
        List<SoinOrderResult> orders = new ArrayList<>();
        results.setOrders(orders);
        for (AryaSoinOrderEntity orderEntity : orderEntities) {
            SoinOrderResult result = new SoinOrderResult();
            result.setOrderId(orderEntity.getId());
            result.setOrderNo(orderEntity.getOrderNo());
            result.setPersonName(orderEntity.getSoinPersonName());
            result.setDistrict(orderEntity.getDistrict());
            result.setTypeName(orderEntity.getSoinType());
            result.setStartYear(orderEntity.getYear());
            result.setStartMonth(orderEntity.getStartMonth());
            result.setCount(orderEntity.getCount());
            String desc = null;
            if (StringUtils.isAnyBlank(orderEntity.getPayedMonth())) {
                desc = "暂未缴纳";
            } else {
                desc = "已缴纳";
                String[] payedMonth = orderEntity.getPayedMonth().split(":");
                desc += StringUtils.join(payedMonth, ",") + "月";
            }
            result.setDesc(desc);
            result.setCreateTime(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(orderEntity.getCreateTime())));
            result.setPayment(turnBigDecimalMoneyToString(orderEntity.getPayment()));
            result.setStatusCode(orderEntity.getStatusCode());
            result.setArrearage(ORDER_NOT_ARREARAGE);
            orders.add(result);
        }
        results.setFilterCount(orderEntities.size());
        return results;
    }

    @Override
    public SoinOrderDetailResult getOrderDetail(String orderId) throws AryaServiceException {

        SoinOrderDetailResult result = new SoinOrderDetailResult();
        AryaSoinOrderEntity orderEntity = soinOrderDao.findOrderById(orderId);//查订单
        AryaUserEntity userEntity;

        if (orderEntity.getOrigin() == null || orderEntity.getOrigin() != ORDER_ORIGIN_IMPORT) {
            userEntity = userDao.findUserByIdThrow(orderEntity.getAryaUserId());//查用户
            result.setCreatorPhoneNo(userEntity.getPhoneNo());
        } else {
            result.setCreatorPhoneNo("导入订单无用户账号");
        }
        AryaSoinPersonEntity personEntity = personDao.findSoinPersonByIdThrow(orderEntity.getSoinPersonId());//查参保人

        List<AryaSoinEntity> soinEntities = soinDao.findSoinsByOrderIdAscByTime(orderEntity.getId());//查缴纳记录
        SoinTypeEntity soinTypeEntity = soinTypeDao.findNotDeleteSoinTypeById(orderEntity.getSoinTypeId());//查社保类型
        SoinTypeVersionEntity versionEntity = versionDao.findEffectVersionByYearAndMonth(orderEntity.getYear(), orderEntity.getStartMonth(), soinTypeEntity.getId(), null);//查社保类型版本

        //订单信息部分
        result.setOrderId(orderEntity.getId());
        result.setOrderNo(orderEntity.getOrderNo());
        result.setVersion(orderEntity.getTxVersion());
        result.setStatusCode(orderEntity.getStatusCode());
        result.setArrearage(ORDER_NOT_ARREARAGE);
        result.setPayment(turnBigDecimalMoneyToString(orderEntity.getPayment()));

        //用户信息部分

        result.setPersonIdcardNo(personEntity.getIdcardNo());
        result.setPersonPhoneNo(personEntity.getPhoneNo());

        result.setPersonHukou(districtCommonService.districtCombinationTranslate(personEntity.getHukou()));//需要转成地区中文名
        result.setPersonHukouType(SoinPersonHukouType.getHuKouTypeName(personEntity.getHukouType()));
        result.setRefund(turnBigDecimalMoneyToString(orderEntity.getRefund()));
        result.setActualPayment(orderEntity.getActualPayment().toString());
        result.setSoinDistrictCode(orderEntity.getDistrictId());

        //明细部分
        result.setInjury(turnBigDecimalMoneyToString(orderEntity.getInjury()));
        result.setMedical(turnBigDecimalMoneyToString(orderEntity.getMedical()));
        result.setUnemployment(turnBigDecimalMoneyToString(orderEntity.getUnemployment()));
        result.setPregnancy(turnBigDecimalMoneyToString(orderEntity.getPregnancy()));
        result.setPension(turnBigDecimalMoneyToString(orderEntity.getPension()));
        result.setDisability(turnBigDecimalMoneyToString(orderEntity.getDisability()));
        if (turnBigDecimalMoneyToString(orderEntity.getSevereIllness()) == null) {
            result.setSevereIllness(turnBigDecimalMoneyToString(orderEntity.getSevereIllnessTotal()));
        } else {
            result.setSevereIllness(turnBigDecimalMoneyToString(orderEntity.getSevereIllness()));
        }
        result.setInjuryAddition(turnBigDecimalMoneyToString(orderEntity.getInjuryAddition()));
        result.setHouseFund(turnBigDecimalMoneyToString(orderEntity.getHouseFund()));
        result.setHouseFundAddition(turnBigDecimalMoneyToString(orderEntity.getHouseFundAddition()));
        result.setFees(bigDecimalMoneyDivideInt(orderEntity.getFees(), orderEntity.getCount()));
        result.setMonthPayment(turnBigDecimalMoneyToString((orderEntity.getPayment().divide(new BigDecimal(orderEntity.getCount()), BigDecimal.ROUND_UP))));

        //小计部分
        result.setInjuryTotal(bigDecimalMoneyMutiplyInt(orderEntity.getInjury(), orderEntity.getCount()));
        result.setMedicalTotal(bigDecimalMoneyMutiplyInt(orderEntity.getMedical(), orderEntity.getCount()));
        result.setUnemploymentTotal(bigDecimalMoneyMutiplyInt(orderEntity.getUnemployment(), orderEntity.getCount()));
        result.setPregnancyTotal(bigDecimalMoneyMutiplyInt(orderEntity.getPregnancy(), orderEntity.getCount()));
        result.setPensionTotal(bigDecimalMoneyMutiplyInt(orderEntity.getPension(), orderEntity.getCount()));
        result.setDisabilityTotal(bigDecimalMoneyMutiplyInt(orderEntity.getDisability(), orderEntity.getCount()));
        if (orderEntity.getSevereIllnessTotal() != null)
            result.setSevereIllnessTotal(turnBigDecimalMoneyToString(orderEntity.getSevereIllnessTotal()));
        result.setInjuryAdditionTotal(bigDecimalMoneyMutiplyInt(orderEntity.getInjuryAddition(), orderEntity.getCount()));
        result.setHouseFundTotal(bigDecimalMoneyMutiplyInt(orderEntity.getHouseFund(), orderEntity.getCount()));
        result.setHouseFundAdditionTotal(bigDecimalMoneyMutiplyInt(orderEntity.getHouseFundAddition(), orderEntity.getCount()));
        result.setFeesTotal(turnBigDecimalMoneyToString(orderEntity.getFees()));
        result.setMonthPaymentTotal(turnBigDecimalMoneyToString(orderEntity.getPayment()));

        //基数部分
        result.setInjuryBase(turnBigDecimalMoneyToString(orderEntity.getBaseInjury()));
        result.setMedicalBase(turnBigDecimalMoneyToString(orderEntity.getBaseMedical()));
        result.setUnemploymentBase(turnBigDecimalMoneyToString(orderEntity.getBaseUnemployment()));
        result.setPregnancyBase(turnBigDecimalMoneyToString(orderEntity.getBasePregnancy()));
        result.setPensionBase(turnBigDecimalMoneyToString(orderEntity.getBasePension()));
        result.setDisabilityBase(turnBigDecimalMoneyToString(orderEntity.getBaseDisability()));
        result.setSevereIllnessBase(turnBigDecimalMoneyToString(orderEntity.getBaseSevereIllness()));
        result.setInjuryAdditionBase(turnBigDecimalMoneyToString(orderEntity.getBaseInjuryAddition()));
        result.setHouseFundBase(turnBigDecimalMoneyToString(orderEntity.getBaseHouseFund()));
        result.setHouseFundAdditionBase(turnBigDecimalMoneyToString(orderEntity.getBaseHouseFundAddition()));

        //比例部分
        result.setMedicalPercentage(generateRulePercentageResult(versionEntity.getRuleMedical()));
        result.setPensionPercentage(generateRulePercentageResult(versionEntity.getRulePension()));
        result.setPregnancyPercentage(generateRulePercentageResult(versionEntity.getRulePregnancy()));
        result.setInjuryPercentage(generateRulePercentageResult(versionEntity.getRuleInjury()));
        result.setUnemploymentPercentage(generateRulePercentageResult(versionEntity.getRuleUnemployment()));
        result.setHouseFundPercentage(generateRulePercentageResult(versionEntity.getRuleHouseFund()));
        result.setDisabilityPercentage(generateRulePercentageResult(versionEntity.getRuleDisability()));
        result.setInjuryAdditionPercentage(generateRulePercentageResult(versionEntity.getRuleInjuryAddition()));
        result.setSevereIllnessPercentage(generateRulePercentageResult(versionEntity.getRuleSevereIllness()));
        result.setHouseFundAdditionPercentage(generateRulePercentageResult(versionEntity.getRuleHouseFundAddition()));

        //业务员和供应商部分
        result.setSalesman(orderEntity.getSalesmanName());
        if (StringUtils.isNotBlank(orderEntity.getSupplierId())) {
            SoinSupplierEntity supplierEntity = soinSupplierDao.findSoinSupplier(orderEntity.getSupplierId());
            if (supplierEntity != null) {
                result.setSupplier(supplierEntity.getSupplierName());
            }
            result.setServiceYearMonth(orderEntity.getServiceYearMonth() + "");
        }

        //缴纳进度部分
        int year = SysUtils.getCurrnentYear();//得到年
        int month = SysUtils.getCurrnentMonth() + FORWARD_MONTH;
        int currentTimeNumber = year * 12 + month;//当前月，数字表示形式
        BigDecimal otherPaymet = BigDecimal.ZERO;//其他费用
        ArrayList<SoinOrderDetailResult.PaymonthDetail> paymonthDetails = new ArrayList<>();
        for (int i = 0; i < soinEntities.size(); i++) {
            AryaSoinEntity soinEntity = soinEntities.get(i);
            SoinOrderDetailResult.PaymonthDetail paymonthDetail = new SoinOrderDetailResult.PaymonthDetail();
            paymonthDetail.setSoinId(soinEntity.getId());
            paymonthDetail.setIsPayed(soinEntity.getIsPayed());
            paymonthDetail.setPaymonth(soinEntity.getYear() + "/" + soinEntity.getMonth());
            //判断是否是当前需要缴纳的
            if ((soinEntity.getYear() * 12 + soinEntity.getMonth() - soinTypeEntity.getForwardMonth()) == currentTimeNumber) {
                paymonthDetail.setIsCurrentPaymonth(1);
            } else
                paymonthDetail.setIsCurrentPaymonth(0);
            paymonthDetails.add(paymonthDetail);
            otherPaymet = SoinUtil.bigDecimalAdd(otherPaymet, soinEntity.getOther());
        }
        result.setPaymonthDetails(paymonthDetails);
        result.setOtherPayment(otherPaymet.setScale(2));
        return result;
    }

    @Override
    public Long getOrderTotalCount() {
        try {
            return soinOrderDao.countAllOnlineOrders();
        } catch (Exception e) {
            elog.error(e.getMessage(), e);
            return 0L;
        }

    }

    @Override
    public OrderStatusChangeResult orderPaymentComplete(OrderPaymentCommand command) throws AryaServiceException {

        AryaSoinOrderEntity orderEntity = soinOrderDao.findOrderById(command.getOrderId());

        orderEntity.setTxVersion(command.getVersion());

        if (!SoinOrderStatus.checkOrderTransformable(orderEntity.getStatusCode(), ORDER_PAYED))
            throw new AryaServiceException(ErrorCode.CODE_SOIN_ORDER_STATUS_TRANSFORM_FAILED);//无法转化成目标状态

        Subject operator = SecurityUtils.getSubject();
        String loginName = String.valueOf(operator.getPrincipal());
        String operatorId = String.valueOf(operator.getSession().getAttribute("user_id"));
        StringBuffer logMsg = new StringBuffer("【社保订单支付】订单ID:" + orderEntity.getId() + ",编号:" + orderEntity.getOrderNo() + "支付。");
        try {
            orderEntity.setStatusCode(ORDER_PAYED);
            orderEntity.setActualPayment(new BigDecimal(command.getMoney()));
            soinOrderDao.update(orderEntity);

            opLogService.successLog(OperateConstants.OP_TYPE_FINISH_ORDER_PAYMENT, logMsg, log);
        } catch (Exception e) {
            elog.error(e.getMessage(), e);
            opLogService.failedLog(OperateConstants.OP_TYPE_FINISH_ORDER_PAYMENT, logMsg, log);
            throw new AryaServiceException(ErrorCode.CODE_SOIN_ORDER_UPDATE_FAILED);//更新订单失败
        }

        try {
			if (orderEntity.getOrigin() == null || orderEntity.getOrigin() != ORDER_ORIGIN_IMPORT) {
				orderPushService.pushOrderPayed(orderEntity.getId(), orderEntity.getOrderNo(), orderEntity.getAryaUserId());//推送支付成功
			}
		} catch (Exception e) {
        	elog.error(e.getMessage(), e);
		}


        return new OrderStatusChangeResult("订单" + orderEntity.getOrderNo() + "完成支付" + command.getMoney() + "元");
    }

    @Override
    public OrderStatusChangeResult orderRefundComplete(OrderPaymentCommand command) throws AryaServiceException {

        AryaSoinOrderEntity orderEntity = soinOrderDao.findOrderById(command.getOrderId());//查订单

        if (!SoinOrderStatus.checkOrderTransformable(orderEntity.getStatusCode(), ORDER_REFUNDED))
            throw new AryaServiceException(ErrorCode.CODE_SOIN_ORDER_STATUS_TRANSFORM_FAILED);//无法转化成目标状态

        Subject operator = SecurityUtils.getSubject();
        String loginName = String.valueOf(operator.getPrincipal());
        String operatorId = String.valueOf(operator.getSession().getAttribute("user_id"));
        orderEntity.setTxVersion(command.getVersion());//锁
        StringBuffer logMsg = new StringBuffer("【社保订单退款】订单" + orderEntity.getId() + "退款");
        try {
            orderEntity.setStatusCode(ORDER_REFUNDED);
            orderEntity.setRefund(new BigDecimal(command.getMoney()));
            soinOrderDao.update(orderEntity);
            if (orderEntity.getOrigin() == null || orderEntity.getOrigin() != ORDER_ORIGIN_IMPORT) {
                orderPushService.pushOrderRefund(orderEntity.getId(), orderEntity.getOrderNo(), orderEntity.getAryaUserId(), orderEntity.getSoinPersonName(), command.getMoney());
            }
            opLogService.successLog(OperateConstants.OP_TYPE_FINISH_ORDER_REFUND, logMsg, log);
        } catch (Exception e) {
            elog.error(e.getMessage(), e);
            opLogService.failedLog(OperateConstants.OP_TYPE_FINISH_ORDER_REFUND, logMsg, log);
            throw new AryaServiceException(ErrorCode.CODE_SOIN_ORDER_UPDATE_FAILED);//更新订单失败
        }
        return new OrderStatusChangeResult("订单" + orderEntity.getOrderNo() + "完成退款" + command.getMoney() + "元");
    }

    @Override
    public OrderStatusChangeResult orderRefunding(OrderPaymentCommand command) throws AryaServiceException {
        AryaSoinOrderEntity orderEntity = soinOrderDao.findOrderById(command.getOrderId());//查订单

        if (!SoinOrderStatus.checkOrderTransformable(orderEntity.getStatusCode(), ORDER_REFUNDING))
            throw new AryaServiceException(ErrorCode.CODE_SOIN_ORDER_STATUS_TRANSFORM_FAILED);//无法转化成目标状态

        Subject operator = SecurityUtils.getSubject();
        String loginName = String.valueOf(operator.getPrincipal());
        String operatorId = String.valueOf(operator.getSession().getAttribute("user_id"));
        orderEntity.setTxVersion(command.getVersion());//锁
        StringBuffer logMsg = new StringBuffer("【社保订单退款中】订单" + orderEntity.getId() + "转化成退款中");
        try {
            orderEntity.setStatusCode(ORDER_REFUNDING);
            soinOrderDao.update(orderEntity);
            if (orderEntity.getOrigin() == null || orderEntity.getOrigin() != ORDER_ORIGIN_IMPORT) {
                orderPushService.pushOrderRefunding(orderEntity.getId(), orderEntity.getOrderNo(), orderEntity.getAryaUserId(), orderEntity.getSoinPersonName());
            }
            opLogService.successLog(OperateConstants.OP_TYPE_FINISH_ORDER_REFUND, logMsg, log);
        } catch (Exception e) {
            elog.error(e.getMessage(), e);
            opLogService.failedLog(OperateConstants.OP_TYPE_FINISH_ORDER_REFUND, logMsg, log);
            throw new AryaServiceException(ErrorCode.CODE_SOIN_ORDER_UPDATE_FAILED);//更新订单失败
        }
        return new OrderStatusChangeResult("订单" + orderEntity.getOrderNo() + "正在退款中");
    }

    @Override
    public OrderResidualAmountResult orderResidualAmount(String orderId) throws AryaServiceException {
        OrderResidualAmountResult result = new OrderResidualAmountResult();
        AryaSoinOrderEntity orderEntity = soinOrderDao.findOrderById(orderId);
        BigDecimal totalMoney = new BigDecimal("0").setScale(2);
        try {
            totalMoney = soinDao.findSoinResidualAmount(orderEntity.getId());
        } catch (Exception e) {
            elog.error(e.getMessage(), e);
        }
        result.setAmount(totalMoney.toString());
        return result;
    }

    @Override
    public OrderStatusChangeResult orderPartialComplete(String soinId, long version) throws AryaServiceException {
        //查出缴纳记录
        AryaSoinEntity soinEntity = soinDao.findSoinById(soinId);

        //查出关联的订单
        AryaSoinOrderEntity orderEntity = soinOrderDao.findOrderById(soinEntity.getOrderId());

        //判断订单当前状态是否可以缴纳
        if (!SoinOrderStatus.checkOrderTransformable(orderEntity.getStatusCode(), ORDER_UNDER_WAY))
            throw new AryaServiceException(ErrorCode.CODE_SOIN_ORDER_STATUS_TRANSFORM_FAILED);//无法转化成目标状态

        //将不是“缴纳中”状态的改成“缴纳中”
        if (orderEntity.getStatusCode() != ORDER_UNDER_WAY) {
            orderEntity.setStatusCode(ORDER_UNDER_WAY);
        }
        //查出社保类型
        SoinTypeEntity soinTypeEntity = soinTypeDao.findNotDeleteSoinTypeById(orderEntity.getSoinTypeId());

        //判断是否是当前能够缴纳的
        int year = SysUtils.getCurrnentYear();//得到年
        int month = SysUtils.getCurrnentMonth() + FORWARD_MONTH;
        int currentTimeNumber = year * 12 + month;//当前时间，数字表示形式
        if ((soinEntity.getYear() * 12 + soinEntity.getMonth() - soinTypeEntity.getForwardMonth()) != currentTimeNumber)
            throw new AryaServiceException(ErrorCode.CODE_INSURANCE_PARTIAL_CANT_BE_COMPLETE);// 暂不能缴纳该月，请下月再试

        soinEntity.setIsPayed(SoinOrderBillConstants.SOIN_IS_PAYED);//该缴纳记录置为已缴纳
        soinEntity.setStatusCode(SOIN_PAY_SUCCESS);
        //订单增加缴纳一条月份
        if (StringUtils.isAnyBlank(orderEntity.getPayedMonth()))
            orderEntity.setPayedMonth(String.valueOf(soinEntity.getMonth()));
        else
            orderEntity.setPayedMonth(orderEntity.getPayedMonth() + ":" + soinEntity.getMonth());

        Subject operator = SecurityUtils.getSubject();
        String loginName = String.valueOf(operator.getPrincipal());
        String operatorId = String.valueOf(operator.getSession().getAttribute("user_id"));
        orderEntity.setTxVersion(version);//锁
        StringBuffer logMsg = new StringBuffer(String.format("【社保订单部分缴纳】订单%s的%d年%d月社保%s缴纳成功", orderEntity.getId(), soinEntity.getYear(), soinEntity.getMonth(), soinEntity.getId()));
        try {
            //更新DB
            soinDao.update(soinEntity);
            soinOrderDao.update(orderEntity);
            //发推送
            if (orderEntity.getOrigin() == null || orderEntity.getOrigin() != ORDER_ORIGIN_IMPORT) {
                orderPushService.pushSoinPayed(soinEntity.getId(), soinEntity.getMonth(), orderEntity.getAryaUserId(), orderEntity.getSoinPersonName());
            }
            opLogService.successLog(OperateConstants.OP_TYPE_SOIN_COMPLETE_MONTH, logMsg, log);
        } catch (Exception e) {
            opLogService.failedLog(OperateConstants.OP_TYPE_SOIN_COMPLETE_MONTH, logMsg, log);
            throw new AryaServiceException(ErrorCode.CODE_INSURANCE_PARTIAL_COMPLETE_FAILED);//缴纳失败
        }
        return new OrderStatusChangeResult("订单" + orderEntity.getOrderNo() + "完成缴纳" + soinEntity.getMonth() + "社保");
    }

    @Override
    public OrderStatusChangeResult orderComplete(String orderId, long version) throws AryaServiceException {

        AryaSoinOrderEntity orderEntity = soinOrderDao.findOrderById(orderId);//查出订单

        //判断订单当前状态是否可以转向已完成状态
        if (!SoinOrderStatus.checkOrderTransformable(orderEntity.getStatusCode(), ORDER_FINISHED))
            throw new AryaServiceException(ErrorCode.CODE_SOIN_ORDER_STATUS_TRANSFORM_FAILED);//无法转化成目标状态

        List<AryaSoinEntity> soinEntities = soinDao.findSoinsByOrderIdAscByTime(orderEntity.getId());//查缴纳记录
        for (AryaSoinEntity soinEntity : soinEntities) {
            if (soinEntity.getIsPayed() == 0) {
                throw new AryaServiceException(ErrorCode.CODE_SOIN_ORDER_CANT_NOT_FINISHED);//订单尚有未缴纳社保，不能完成！
            }
        }

        orderEntity.setStatusCode(ORDER_FINISHED);

        Subject operator = SecurityUtils.getSubject();
        String loginName = String.valueOf(operator.getPrincipal());
        String operatorId = String.valueOf(operator.getSession().getAttribute("user_id"));
        orderEntity.setTxVersion(version);//锁
        StringBuffer logMsg = new StringBuffer("【完成社保订单】订单" + orderEntity.getId() + "已经全部缴");
        try {
            //更新DB
            soinOrderDao.update(orderEntity);
            //发推送
            if (orderEntity.getOrigin() == null || orderEntity.getOrigin() != ORDER_ORIGIN_IMPORT) {
                orderPushService.pushOrderComplete(orderEntity.getId(), orderEntity.getOrderNo(), orderEntity.getAryaUserId(), orderEntity.getSoinPersonName());
            }
            opLogService.successLog(OperateConstants.OP_TYPE_ORDER_FINISHED, logMsg, log);
        } catch (Exception e) {
            opLogService.failedLog(OperateConstants.OP_TYPE_ORDER_FINISHED, logMsg, log);
            throw new AryaServiceException(ErrorCode.CODE_SYS_ERR);//失败
        }
        return new OrderStatusChangeResult("订单" + orderEntity.getOrderNo() + "已经完成");
    }

    @Override
    public OrderStatusChangeResult orderCancel(String orderId, long version) throws AryaServiceException {
        AryaSoinOrderEntity orderEntity = soinOrderDao.findOrderById(orderId);//查出订单

        //判断订单当前状态是否可以转向取消状态
        if (!SoinOrderStatus.checkOrderTransformable(orderEntity.getStatusCode(), ORDER_CANCELED))
            throw new AryaServiceException(ErrorCode.CODE_SOIN_ORDER_STATUS_TRANSFORM_FAILED);//无法转化成目标状态

        orderEntity.setStatusCode(ORDER_CANCELED);

        Subject operator = SecurityUtils.getSubject();
        String loginName = String.valueOf(operator.getPrincipal());
        String operatorId = String.valueOf(operator.getSession().getAttribute("user_id"));
        orderEntity.setTxVersion(version);//锁
        StringBuffer logMsg = new StringBuffer("【取消社保订单】订单" + orderEntity.getId() + "取消");
        try {
            //更新DB
            soinOrderDao.update(orderEntity);
            //发推送
            if (orderEntity.getOrigin() == null || orderEntity.getOrigin() != ORDER_ORIGIN_IMPORT) {
                orderPushService.pushOrderCancel(orderEntity.getId(), orderEntity.getOrderNo(), orderEntity.getAryaUserId(), orderEntity.getSoinPersonName());
            }
            opLogService.successLog(OperateConstants.OP_TYPE_ORDER_CANCELED, logMsg, log);
        } catch (Exception e) {
            opLogService.successLog(OperateConstants.OP_TYPE_ORDER_CANCELED, logMsg, log);
            throw new AryaServiceException(ErrorCode.CODE_SYS_ERR);//失败
        }
        return new OrderStatusChangeResult("订单" + orderEntity.getOrderNo() + "已经取消");
    }

    @Override
    public OrderStatusChangeResult orderStop(String orderId, long version) throws AryaServiceException {
        AryaSoinOrderEntity orderEntity = soinOrderDao.findOrderById(orderId);//查出订单

        //判断订单当前状态是否可以转向已完成状态
        if (!SoinOrderStatus.checkOrderTransformable(orderEntity.getStatusCode(), ORDER_STOPED))
            throw new AryaServiceException(ErrorCode.CODE_SOIN_ORDER_STATUS_TRANSFORM_FAILED);//无法转化成目标状态

        Subject operator = SecurityUtils.getSubject();
        String loginName = String.valueOf(operator.getPrincipal());
        String operatorId = String.valueOf(operator.getSession().getAttribute("user_id"));
        orderEntity.setTxVersion(version);//锁
        StringBuffer logMsg = new StringBuffer("【停缴社保订单】订单" + orderEntity.getId() + "停缴");
        try {
            //更新DB
            orderEntity.setStatusCode(ORDER_STOPED);
            soinOrderDao.update(orderEntity);
            //发推送
            if (orderEntity.getOrigin() == null || orderEntity.getOrigin() != ORDER_ORIGIN_IMPORT) {
                orderPushService.pushOrderStop(orderEntity.getId(), orderEntity.getOrderNo(), orderEntity.getAryaUserId(), orderEntity.getSoinPersonName());
            }
            opLogService.successLog(OperateConstants.OP_TYPE_ORDER_ARREARAGE, logMsg, log);
        } catch (Exception e) {
            opLogService.failedLog(OperateConstants.OP_TYPE_ORDER_ARREARAGE, logMsg, log);
            throw new AryaServiceException(ErrorCode.CODE_SYS_ERR);//失败
        }
        return new OrderStatusChangeResult("订单" + orderEntity.getOrderNo() + "已经停缴");
    }

    @Override
    public OrderStatusChangeResult orderException(String orderId, long version) throws AryaServiceException {
        AryaSoinOrderEntity orderEntity = soinOrderDao.findOrderById(orderId);//查出订单

        //判断订单当前状态是否可以转向已完成状态
        if (!SoinOrderStatus.checkOrderTransformable(orderEntity.getStatusCode(), ORDER_ABNORMAL))
            throw new AryaServiceException(ErrorCode.CODE_SOIN_ORDER_STATUS_TRANSFORM_FAILED);//无法转化成目标状态

        Subject operator = SecurityUtils.getSubject();
        String loginName = String.valueOf(operator.getPrincipal());
        String operatorId = String.valueOf(operator.getSession().getAttribute("user_id"));
        orderEntity.setTxVersion(version);//锁
        StringBuffer logMsg = new StringBuffer("【社保订单异常】订单" + orderEntity.getId() + "异常");
        try {
            //更新DB
            orderEntity.setStatusCode(ORDER_ABNORMAL);
            soinOrderDao.update(orderEntity);
            //发推送
            opLogService.successLog(OperateConstants.OP_TYPE_ORDER_ARREARAGE, logMsg, log);
        } catch (Exception e) {
            opLogService.failedLog(OperateConstants.OP_TYPE_ORDER_ARREARAGE, logMsg, log);
            throw new AryaServiceException(ErrorCode.CODE_SYS_ERR);//失败
        }
        return new OrderStatusChangeResult("订单" + orderEntity.getOrderNo() + "已经转进入异常状态");
    }

    @Override
    public OrderStatusChangeResult orderUnderway(String orderId, long version) throws AryaServiceException {
        AryaSoinOrderEntity orderEntity = soinOrderDao.findOrderById(orderId);//查出订单

        //判断订单当前状态是否可以转向已完成状态
        if (!SoinOrderStatus.checkOrderTransformable(orderEntity.getStatusCode(), ORDER_UNDER_WAY))
            throw new AryaServiceException(ErrorCode.CODE_SOIN_ORDER_STATUS_TRANSFORM_FAILED);//无法转化成目标状态

        Subject operator = SecurityUtils.getSubject();
        String loginName = String.valueOf(operator.getPrincipal());
        String operatorId = String.valueOf(operator.getSession().getAttribute("user_id"));
        orderEntity.setTxVersion(version);//锁
        StringBuffer logMsg = new StringBuffer("【社保订单缴纳中】订单" + orderEntity.getId() + "变更成缴纳中");
        try {
            //更新DB
            orderEntity.setStatusCode(ORDER_UNDER_WAY);
            soinOrderDao.update(orderEntity);
            //发推送
            if (orderEntity.getOrigin() == null || orderEntity.getOrigin() != ORDER_ORIGIN_IMPORT) {
                orderPushService.pushOrderUnderway(orderEntity.getId(), orderEntity.getOrderNo(), orderEntity.getAryaUserId(), orderEntity.getSoinPersonName());
            }
            opLogService.successLog(OperateConstants.OP_TYPE_ORDER_ARREARAGE, logMsg, log);
        } catch (Exception e) {
            opLogService.failedLog(OperateConstants.OP_TYPE_ORDER_ARREARAGE, logMsg, log);
            throw new AryaServiceException(ErrorCode.CODE_SYS_ERR);//失败
        }
        return new OrderStatusChangeResult("订单" + orderEntity.getOrderNo() + "正在缴纳中");
    }

    @Override
    public void setOrderSalesmanAndSupplier(SoinOrderSetSalesmanAndSupplierCommand command) throws AryaServiceException {
        AryaSoinOrderEntity orderEntity = soinOrderDao.findOrderById(command.getOrderId());
        if (orderEntity == null) {
            throw new AryaServiceException(ErrorCode.CODE_SOIN_ORDER_NOT_FOUND);
        }
        SysUserEntity salesman = sysUserDao.findSysUserById(command.getSalesmanId());
        if (salesman == null) {
            throw new AryaServiceException(ErrorCode.CODE_ORDER_BILL_SALESMAN_NOT_FOUND);
        }

        SoinSupplierEntity soinSupplierEntity = soinSupplierDao.findSoinSupplier(command.getSupplerId());
        if (soinSupplierEntity == null) {
            throw new AryaServiceException(ErrorCode.CODE_ORDER_BILL_SUPPLIER_NOT_FOUND);
        }

        StringBuffer logMsg = new StringBuffer("【订单管理】为订单:" + orderEntity.getId());
        if (StringUtils.isAnyBlank(orderEntity.getSalesmanId()) || !orderEntity.getSalesmanId().equals(salesman.getId())) {
            orderEntity.setSalesmanId(salesman.getId());
            orderEntity.setSalesmanName(salesman.getRealName());
            logMsg.append(",指派业务员:" + salesman.getRealName() + ",id:" + salesman.getId());
        }

        if (StringUtils.isAnyBlank(orderEntity.getSupplierId()) || !orderEntity.getSupplierId().equals(soinSupplierEntity.getId())) {
            orderEntity.setSupplierId(soinSupplierEntity.getId());
            orderEntity.setFeeOut(soinSupplierEntity.getSoinFee());
            orderEntity.setTotalOutPayment(orderEntity.getPayment().add(soinSupplierEntity.getSoinFee().multiply(new BigDecimal(orderEntity.getCount()))));
            changeOrderSoinOutFee(orderEntity.getId(), soinSupplierEntity.getSoinFee());
            logMsg.append(",指定供应商:" + soinSupplierEntity.getSupplierName() + ",id:" + soinSupplierEntity.getId());
        }
        if (orderEntity.getServiceYearMonth() == null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");
            orderEntity.setServiceYearMonth(Integer.parseInt(dateFormat.format(new Date(System.currentTimeMillis()))));
        }
        try {
            soinOrderDao.update(orderEntity);
            opLogService.successLog(SOIN_ORDER_SET_SALESMAN_SUPPLIER, logMsg, log);
        } catch (Exception e) {
            elog.error(e.getMessage(), e);
            opLogService.failedLog(SOIN_ORDER_SET_SALESMAN_SUPPLIER, logMsg, log);
            throw new AryaServiceException(ErrorCode.CODE_ORDER_BILL_SET_SALESMAN_SUPPLIER_FAILED);
        }
    }

    @Override
    public void changeOrderSoinOutFee(String orderId, BigDecimal feeOut) throws AryaServiceException {
        List<AryaSoinEntity> soinEntities = soinDao.findSoinEntitiesByOrderId(orderId);
        if (soinEntities.size() == 0) {
            throw new AryaServiceException(ErrorCode.CODE_INSURANCE_NOT_FOUND);
        }
        StringBuffer logMsg = new StringBuffer("【订单管理】更新社保缴纳详情的出账费用");
        for (AryaSoinEntity soinEntity : soinEntities) {
            logMsg.append(",id:" + soinEntity.getId() + "原出账管理费:" + soinEntity.getFeesOut() + "改为:" + feeOut);
            soinEntity.setFeesOut(feeOut);
            logMsg.append("原出账总金额:" + soinEntity.getTotalOutPayment());
            soinEntity.setTotalOutPayment(soinEntity.getCompanyTotal().add(soinEntity.getPersonalTotal()).add(feeOut));
            logMsg.append("改为:" + soinEntity.getTotalOutPayment());
        }

        try {
            soinDao.update(soinEntities);
            opLogService.successLog(SOIN_ORDER_UPDATE_ORDER_SOIN_DETAIL, logMsg, log);
        } catch (Exception e) {
            elog.error(e.getMessage(), e);
            opLogService.successLog(SOIN_ORDER_UPDATE_ORDER_SOIN_DETAIL, logMsg, log);
            throw new AryaServiceException(ErrorCode.CODE_ORDER_BILL_UPDATE_SOIN_FAILED);
        }
    }

    /**
     * 生成缴纳比例返回值
     *
     * @param ruleEntity
     * @return
     */
    private SoinOrderDetailResult.RulePercentage generateRulePercentageResult(SoinRuleEntity ruleEntity) {
        if (ruleEntity == null)
            return null;
        SoinOrderDetailResult.RulePercentage rulePercentage = new SoinOrderDetailResult.RulePercentage();
        rulePercentage.setPercentagePerson(ruleEntity.getPercentagePerson() == null ? "0" : ruleEntity.getPercentagePerson());
        rulePercentage.setPercentageCorp(ruleEntity.getPercentageCorp() == null ? "0" : ruleEntity.getPercentageCorp());
        rulePercentage.setExtraPerson(ruleEntity.getExtraPerson() == null ? null : ruleEntity.getExtraPerson().toString());
        rulePercentage.setExtraCorp(ruleEntity.getExtraCorp() == null ? null : ruleEntity.getExtraCorp().toString());

        BigDecimal extraTotal = new BigDecimal("0");
        if (ruleEntity.getExtraCorp() != null)
            extraTotal = extraTotal.add(ruleEntity.getExtraCorp());
        if (ruleEntity.getExtraPerson() != null)
            extraTotal = extraTotal.add(ruleEntity.getExtraPerson());
        rulePercentage.setExtraTotal(extraTotal.compareTo(new BigDecimal("0")) == 0 ? null : extraTotal.toString());

        BigDecimal percentageTotal = new BigDecimal("0");
        if (ruleEntity.getPercentageCorp() != null)
            percentageTotal = percentageTotal.add(new BigDecimal(ruleEntity.getPercentageCorp()));
        if (ruleEntity.getPercentagePerson() != null)
            percentageTotal = percentageTotal.add(new BigDecimal(ruleEntity.getPercentagePerson()));
        rulePercentage.setPercentageTotal(percentageTotal.compareTo(new BigDecimal("0")) == 0 ? null : percentageTotal.toString());
        return rulePercentage;
    }
}
