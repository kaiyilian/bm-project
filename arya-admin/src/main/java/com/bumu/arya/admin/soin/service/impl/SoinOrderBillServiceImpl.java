package com.bumu.arya.admin.soin.service.impl;

import com.bumu.SysUtils;
import com.bumu.arya.IdcardValidator;
import com.bumu.arya.soin.model.dao.*;
import com.bumu.arya.soin.model.entity.*;
import com.bumu.arya.soin.util.SoinUtil;
import com.bumu.arya.Utils;
import com.bumu.arya.admin.common.service.AryaAdminConfigService;
import com.bumu.arya.admin.system.service.OpLogService;
import com.bumu.arya.admin.corporation.constant.CorpConstants;
import com.bumu.arya.admin.misc.result.SimpleResult;
import com.bumu.arya.admin.model.SysUserDao;
import com.bumu.arya.admin.model.SysUserModel;
import com.bumu.arya.admin.salary.service.PhoneService;
import com.bumu.arya.admin.soin.constant.SoinConstants;
import com.bumu.arya.admin.soin.controller.command.*;
import com.bumu.arya.admin.soin.model.SoinTypeMinMaxBaseModel;
import com.bumu.arya.admin.soin.model.dao.SoinDistrictSupplierDao;
import com.bumu.arya.admin.soin.model.dao.SoinImportBatchDao;
import com.bumu.arya.admin.soin.model.dao.SoinSupplierDao;
import com.bumu.arya.admin.soin.model.entity.SoinImportBatchEntity;
import com.bumu.arya.admin.soin.model.entity.SoinSupplierEntity;
import com.bumu.arya.admin.model.entity.SysUserEntity;
import com.bumu.arya.admin.soin.result.EmployeeExportOutDetail;
import com.bumu.arya.admin.soin.result.EmployeeExportOutResult;
import com.bumu.arya.admin.soin.result.OrderExportResult;
import com.bumu.arya.admin.service.*;
import com.bumu.arya.admin.soin.model.entity.*;
import com.bumu.arya.admin.soin.result.*;
import com.bumu.arya.admin.soin.service.SoinDistrictTreeService;
import com.bumu.arya.admin.soin.service.SoinFileService;
import com.bumu.arya.admin.soin.service.SoinOrderBillService;
import com.bumu.arya.admin.system.service.SystemRoleService;
import com.bumu.arya.command.CreateOrderCommand;
import com.bumu.arya.command.OrderBillListQueryCommand;
import com.bumu.arya.common.Constants;
import com.bumu.arya.soin.constant.SoinOrderStatus;
import com.bumu.arya.soin.constant.SoinPersonStatus;
import com.bumu.arya.soin.constant.SoinOrderBillConstants;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.model.*;
import com.bumu.arya.model.entity.*;
import com.bumu.arya.response.HttpResponse;
import com.bumu.arya.soin.model.SoinCalculateParams;
import com.bumu.arya.soin.service.SoinRuleService;
import com.bumu.arya.soin.service.UserSoinService;
import com.bumu.bran.helper.ExcelExportHelper;
import com.bumu.exception.AryaServiceException;
import com.bumu.arya.soin.model.SoinTypeCalculateModel;
import com.bumu.common.result.Pager;
import com.bumu.arya.common.result.ZtreeDistrictListResult;
import com.bumu.arya.common.service.CommonDistrictService;
import com.bumu.common.service.impl.BaseBumuService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.bumu.arya.soin.util.SoinUtil.turnBeenBigDecimalHalfRoundUp;
import static com.bumu.arya.Utils.formatMoney;
import static com.bumu.arya.admin.corporation.constant.CorpConstants.CORP_BUSINESS_SOIN;
import static com.bumu.arya.admin.soin.constant.SoinOrderBillImportConstants.*;
import static com.bumu.arya.admin.soin.result.OrderBillUploadAndCalculateListResult.OrderBillUploadAndCalculateResult.ColumnResult;
import static com.bumu.arya.admin.common.util.POIExcelUtil.*;
import static com.bumu.arya.admin.soin.util.SoinUtil.calculateMonths;
import static com.bumu.arya.common.Constants.*;
import static com.bumu.arya.common.OperateConstants.*;
import static com.bumu.arya.soin.constant.SoinPersonHukouType.getHuKouTypeIndex;
import static com.bumu.arya.soin.constant.SoinOrderBillConstants.*;
import static org.apache.commons.lang3.StringUtils.*;

/**
 * Created by CuiMengxin on 16/8/2.
 * 订单对账单管理
 */
@Service
public class SoinOrderBillServiceImpl extends BaseBumuService implements SoinOrderBillService {

	Logger log = LoggerFactory.getLogger(SoinOrderBillServiceImpl.class);

	@Autowired
    OpLogService opLogService;

	@Autowired
	CommonDistrictService commonDistrictService;

	@Autowired
    SoinFileService fileService;

	@Autowired
	DistrictDao districtDao;

	@Autowired
    AryaSoinTypeDao soinTypeDao;

	@Autowired
	SoinTypeVersionDao soinTypeVersionDao;

	@Autowired
	InsurancePersonDao soinPersonDao;

	@Autowired
	CorporationDao corporationDao;

	@Autowired
    SoinSupplierDao soinSupplierDao;

	@Autowired
    SoinDistrictSupplierDao soinDistrictSupplierDao;

	@Autowired
	SoinRuleService soinRuleService;

	@Autowired
    SysUserDao sysUserDao;

	@Autowired
	SysUserService sysUserService;

	@Autowired
	private SoinOrderDao soinOrderDao;

	@Autowired
	UserSoinService userSoinService;

	@Autowired
    AryaSoinDao soinDao;

	@Autowired
    SoinImportBatchDao batchDao;

	@Autowired
	private ExcelExportHelper excelExportHelper;

	@Autowired
	private AryaAdminConfigService aryaAdminConfigService;

	@Autowired
	private AryaSoinDistrictDao aryaSoinDistrictDao;

	@Autowired
	private AryaSoinTypeDao aryaSoinTypeDao;

	@Autowired
    SystemRoleService systemRoleService;

	@Autowired
    PhoneService phoneService;

	@Autowired
    SoinDistrictTreeService soinDistrictTreeService;

	@Autowired
	AryaSoinDistrictDao soinDistrictDao;

	@Autowired
    SoinInOrDecreaseDao soinInOrDecreaseDao;

	@Override
	public ZtreeDistrictListResult getAllSoinDistricts() throws AryaServiceException {
		return commonDistrictService.getAllSoinDistricts();
	}

	@Override
	public OrderBillUploadAndCalculateListResult uploadAndCalculateOrderBill(String fileName) throws AryaServiceException {
		OrderBillUploadAndCalculateListResult results = new OrderBillUploadAndCalculateListResult();
		SoinOrderBillCalculateStructure structure = readFileAndCalculateOrderBill(fileName);
		results.setMsg(structure.getBillCalculateMsg());//赋值错误消息
		if (structure.getMessage().getErrorMsgs().isEmpty()) {
			String msg = "/n需要增员数：" + structure.getSoinInEntityMap().size() + " 需要减员数：" + structure.getSoinDecreaseEntityMap().size() + " 不增不减数：" + structure.getSoinExtendEntityMap().size();
			msg = msg + "/n需要保存的订单数：" + structure.getMessage().getSaveRows() + " 减员需要删除的订单数：" + structure.getNeedDeleteOrders().size();
			results.setMsg(results.getMsg() + msg);
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmmss");
		results.setBatch(dateFormat.format(new Date(System.currentTimeMillis())));
		results.setBatchId(Utils.makeUUID());
		if (structure.getCalculateModels().size() == 0) {
			results.setDuplicateRowsCount(0);
			results.setWrongRowsCount(0);
			results.setTotalRowsCount(0);
			return results;
		}
		List<OrderBillUploadAndCalculateListResult.OrderBillUploadAndCalculateResult> resultlist = new ArrayList<>();
		try {
			for (SoinOrderBillCalculateModel calculateModel : structure.getCalculateModels()) {
				OrderBillUploadAndCalculateListResult.OrderBillUploadAndCalculateResult result = new OrderBillUploadAndCalculateListResult.OrderBillUploadAndCalculateResult();
				result.setId(calculateModel.getTempId());
				result.setStatus(calculateModel.getStatus());
				SoinOrderBillCalculateModel.SoinPerson soinPerson = calculateModel.getSoinPerson();
				//参保人
				{
					ColumnResult name = new ColumnResult();
					if (isBlank(soinPerson.getName().getContent())) {
						name.setContent("-");
					} else {
						name.setContent(soinPerson.getName().getContent());
					}
					name.setStatus(soinPerson.getName().getStatus());
					result.setName(name);

					ColumnResult idcard = new ColumnResult();
					if (isBlank(soinPerson.getIdcardNo().getContent())) {
						idcard.setContent("-");
					} else {
						idcard.setContent(soinPerson.getIdcardNo().getContent());
					}
					idcard.setStatus(soinPerson.getIdcardNo().getStatus());
					result.setIdcard(idcard);

					ColumnResult hukouType = new ColumnResult();
					if (isBlank(soinPerson.getHukouTypeReadName())) {
						hukouType.setContent("-");
					} else {
						hukouType.setContent(soinPerson.getHukouTypeReadName());
					}
					hukouType.setStatus(soinPerson.getHukouType().getStatus());
					result.setHukouType(hukouType);

					ColumnResult hukouDistrict = new ColumnResult();
					if (isBlank(soinPerson.getHukouDistrict().getContent())) {
						hukouDistrict.setContent("-");
					} else {
						hukouDistrict.setContent(soinPerson.getHukouDistrict().getContent());
					}
					hukouDistrict.setStatus(soinPerson.getHukouDistrict().getStatus());
					result.setHukouDistrict(hukouDistrict);
				}

				//社保,公积金编号
				{

					ColumnResult soinCode = new ColumnResult();
					if (isNotBlank(calculateModel.getSoinCode())) {
						soinCode.setContent(calculateModel.getSoinCode());
					} else {
						soinCode.setContent("");
					}
					result.setSoinCode(soinCode);


					ColumnResult houseFundCode = new ColumnResult();
					if (isNotBlank(calculateModel.getHouseFundCode())) {
						houseFundCode.setContent(calculateModel.getHouseFundCode());
					} else {
						houseFundCode.setContent("");
					}
					result.setHouseFundCode(houseFundCode);
				}

				//主体
				{
					ColumnResult subject = new ColumnResult();
					if (calculateModel.getSubjectType() == PERSON_CUSTOMER) {
						subject.setContent(PERSON_CUSTOMER_STR);
					} else if (calculateModel.getSubjectType() == APP_CUSTOMER) {
						subject.setContent(APP_CUSTOMER_STR);
					} else {
						subject.setContent(calculateModel.getCorp().getName());
						subject.setStatus(calculateModel.getCorp().getStatus());
					}
					result.setSubject(subject);
				}

				//社保类型
				{
					ColumnResult soinDistrict = new ColumnResult();
					if (isAnyBlank(calculateModel.getSoinDistrict().getNames())) {
						soinDistrict.setContent("-");
					} else {
						soinDistrict.setContent(calculateModel.getSoinDistrict().getNames());
					}
					soinDistrict.setStatus(calculateModel.getSoinDistrict().getStatus());
					result.setSoinDistrict(soinDistrict);

					ColumnResult soinType = new ColumnResult();
					if (isBlank(calculateModel.getSoinType().getName())) {
						soinType.setContent("-");
					} else {
						soinType.setContent(calculateModel.getSoinType().getName());
					}
					soinType.setStatus(calculateModel.getSoinType().getStatus());
					result.setSoinType(soinType);

					//服务年月
					ColumnResult serviceYearMonth = new ColumnResult();
					serviceYearMonth.setContent(calculateModel.getServiceYearMonth().getContent());
					serviceYearMonth.setStatus(calculateModel.getServiceYearMonth().getStatus());
					result.setServiceMonth(serviceYearMonth);

					//缴纳年月
					ColumnResult payYearMonth = new ColumnResult();
					if (isNotBlank(calculateModel.getPayYearMonth().getContent())) {
						payYearMonth.setContent(calculateModel.getPayYearMonth().getContent());
					} else {
						payYearMonth.setContent("-");
					}
					payYearMonth.setStatus(calculateModel.getPayYearMonth().getStatus());
					result.setPayMonth(payYearMonth);

					//补缴年月
					ColumnResult backYearMonth = new ColumnResult();
					if (calculateModel.getBackYearMonth() != null) {
						if (isNotBlank(calculateModel.getBackYearMonth().getContent())) {
							backYearMonth.setContent(calculateModel.getBackYearMonth().getContent());
						} else {
							backYearMonth.setContent("-");
						}
						backYearMonth.setStatus(calculateModel.getBackYearMonth().getStatus());
					}
					result.setBackMonth(backYearMonth);


					ColumnResult soinBase = new ColumnResult();
					if (calculateModel.getSoinType().getSoinBase().getStatus() == SoinConstants.RESULT_WRONG) {
						soinBase.setContent("错误");
					} else {
						soinBase.setContent(formatMoney(calculateModel.getSoinType().getSoinBase().getContent(), 2));
					}
					soinBase.setStatus(calculateModel.getSoinType().getSoinBase().getStatus());
					result.setSoinBase(soinBase);

					//公积金基数
					ColumnResult housefundBase = new ColumnResult();
					if (calculateModel.getSoinType().getHousefundBase().getStatus() == SoinConstants.RESULT_WRONG) {
						housefundBase.setContent("错误");
					} else {
						if (calculateModel.getSoinType().getHousefundBase().getContent() == null) {
							housefundBase.setContent("不缴纳公积金");
						} else {
							housefundBase.setContent(formatMoney(calculateModel.getSoinType().getHousefundBase().getContent(), 2));
						}
					}
					housefundBase.setStatus(calculateModel.getSoinType().getHousefundBase().getStatus());
					result.setHousefundBase(housefundBase);

					//公积金比例
					ColumnResult housefundPercent = new ColumnResult();
					if (calculateModel.getSoinType().getHousefundPercent().getStatus() == SoinConstants.RESULT_WRONG) {
						housefundPercent.setContent("错误");
					} else {
						housefundPercent.setContent(calculateModel.getSoinType().getPercentStr() + (isNotBlank(calculateModel.getSoinType().getBackPercentStr()) ? calculateModel.getSoinType().getBackPercentStr() : ""));
					}
					housefundPercent.setStatus(calculateModel.getSoinType().getHousefundPercent().getStatus());
					result.setHousefundPercent(housefundPercent);

					ColumnResult feeIn = new ColumnResult();
					if (calculateModel.getSoinType().getCollectionFee().getStatus() == SoinConstants.RESULT_WRONG) {
						feeIn.setContent("错误");
					} else {
						feeIn.setContent(SoinUtil.turnBigDecimalHalfRoundUpToString(calculateModel.getSoinType().getCollectionFee().getContent(), 2));
					}
					feeIn.setStatus(calculateModel.getSoinType().getCollectionFee().getStatus());
					result.setCollectionServiceFee(feeIn);

					ColumnResult feeOut = new ColumnResult();
					if (calculateModel.getSupplier().getFee().getStatus() == SoinConstants.RESULT_WRONG) {
						feeOut.setContent("错误");
					} else {
						feeOut.setContent(SoinUtil.turnBigDecimalHalfRoundUpToString(calculateModel.getSupplier().getFee().getContent(), 2));
					}
					feeOut.setStatus(calculateModel.getSupplier().getFee().getStatus());
					result.setChargeServiceFee(feeOut);

					ColumnResult totalIn = new ColumnResult();
					totalIn.setContent(formatMoney(calculateModel.getTotalIn(), 3));
					result.setCollectionSubtotal(totalIn);

					ColumnResult totalOut = new ColumnResult();
					totalOut.setContent(formatMoney(calculateModel.getTotalOut(), 3));
					result.setChargeSubtotal(totalOut);
				}

				//供应商
				{
					ColumnResult supplier = new ColumnResult();
					if (isAnyBlank(calculateModel.getSupplier().getName())) {
						supplier.setContent("-");
					} else {
						supplier.setContent(calculateModel.getSupplier().getName());
					}
					supplier.setStatus(calculateModel.getSupplier().getStatus());
					result.setSupplier(supplier);
				}

				//业务员
				{
					ColumnResult salesman = new ColumnResult();
					if (isAnyBlank(calculateModel.getSalesman().getName())) {
						salesman.setContent("-");
					} else {
						salesman.setContent(calculateModel.getSalesman().getName());
					}
					salesman.setStatus(calculateModel.getSalesman().getStatus());
					result.setSalesman(salesman);
				}

				//顺延月
				if (calculateModel.getTemplateType().equals("personal")) {
					{
						ColumnResult postponeMonth = new ColumnResult();
						if (isAnyBlank(calculateModel.getPostponeMonth().getContent())) {
							postponeMonth.setContent("-");
						}
						if (calculateModel.getPostponeMonth().getStatus() == SoinConstants.RESULT_WRONG) {
							postponeMonth.setContent("错误");
						} else {
							postponeMonth.setContent(calculateModel.getPostponeMonth().getContent());
						}
						postponeMonth.setStatus(calculateModel.getPostponeMonth().getStatus());
						result.setPostponeMonth(postponeMonth);
					}
				}

				//增减员
				{
					ColumnResult inOrDecrease = new ColumnResult();
					inOrDecrease.setContent(calculateModel.getModify().getContent());
					result.setInOrDecreaseStatus(inOrDecrease);
				}
				resultlist.add(result);
			}
		} catch (Exception e) {
			elog.error("【对账单计算错误】", e);
			throw new AryaServiceException(ErrorCode.CODE_ORDER_BILL_GENERATE_RESULT_WRONG);
		}
		results.setOrders(resultlist);
		results.setFileName(fileName);
		results.setTemplateType(structure.getCalculateModels().get(0).getTemplateType());
		SoinOrderBillCountModel countModel = soinOrderBillCount(structure);
		results.setDuplicateRowsCount(countModel.getDumplicateCount());
		results.setWrongRowsCount(countModel.getWrongCount());
		results.setTotalRowsCount(countModel.getTotalCount());
		return results;
	}

	@Override
	public SoinOrderBillCalculateStructure readFileAndCalculateOrderBill(String fileName) throws AryaServiceException {

		SoinOrderBillCalculateStructure structure = new SoinOrderBillCalculateStructure();

		//读取文件转成对象
		SoinOrderBillExcelFileReadResult soinOrderBillExcelFileReadResult = fileService.readSoinOrderBillCalculateExcelFile(fileName);
		if (!soinOrderBillExcelFileReadResult.getMessages().getErrorMsgs().isEmpty()) {
			//读文件出错直接返回
			structure.setMessage(soinOrderBillExcelFileReadResult.getMessages());
			return structure;
		}
		structure.getMessage().setTotalRows(soinOrderBillExcelFileReadResult.getModels().size());

		//转换计算模型并校验记录中简单字段的格式是否正确,
		// 身份证号码,手机号码,服务年月,缴纳年月,缴纳年月,补缴开始年月，
		// 补缴结束年月，增减员，户口性质,社保编号,公积金编号。
		generateAndCheckSoinOrderBillCalculateModelsFromReadModels(soinOrderBillExcelFileReadResult.getModels(), structure);

		//校验户籍地址。
		checkOrderBillHukouDistrict(structure);

		//校验 参保地区,社保类型,社保基数,公积金基数,公积金比例
		checkOrderBillSoinDistrictAndSoinType(structure);

		//校验合法的参保人在DB是是否存在
		checkOrderBillSoinPerson(structure);

		//校验缴纳主体公司是否存在
		checkOrderBillCorpCustomer(structure);

		//校验供应商是否存在
		checkOrderBillSupplier(structure);

		//校验业务员
		checkOrderBillSalesman(structure);

		//模型中订单查重
		checkDuplicateSoinOrderBillInFile(structure);

		//校验是否增减员
		checkSoinPersonIncrease(structure);

		//合法的订单在DB中查重
		checkSoinOrderBillExistInDB(structure);

		//计算订单
		soinOrderBillCalculate(structure);
		return structure;
	}

	@Override
	public List<SoinOrderBillCalculateModel> generateAndCheckSoinOrderBillCalculateModelsFromReadModels(List<SoinOrderBillExcelReadModel> readModels, SoinOrderBillCalculateStructure structure) throws AryaServiceException {
		List<SoinOrderBillCalculateModel> calculateModels = new ArrayList<>();
		structure.setCalculateModels(calculateModels);
		for (SoinOrderBillExcelReadModel readModel : readModels) {
			SoinOrderBillCalculateModel calculateModel = new SoinOrderBillCalculateModel();
			try {
				calculateModel.setTempId(Utils.makeUUID());
				calculateModel.setOrderId(Utils.makeUUID());
				calculateModel.setNo(readModel.getNo());
				calculateModel.setTemplateType(readModel.getTemplateType());

				//判断缴纳计算类型

				// 缴纳年月不为空, 补缴不为空 -> 纯正常缴纳计算
				if (isNotBlank(readModel.getPayYearMonth()) && isBlank(readModel.getBackStartYearMonth())) {
					calculateModel.setType(SOIN_NORMAL_CALCULATE);
				// 缴纳月份为空, 补缴不为空 -> 纯补缴计算
				} else if (isBlank(readModel.getPayYearMonth()) && isNotBlank(readModel.getBackStartYearMonth())) {
					calculateModel.setType(SOIN_BACK_CALCULATE);
				} else {
					// 正常与补缴混合计算
					calculateModel.setType(SOIN_MIX_CALCULATE);
				}

				//判断和赋值主体
				if (readModel.getTemplateType().equals("personal")){
					//主体是个人客户
					calculateModel.setSubjectType(PERSON_CUSTOMER);
				} else if (readModel.getSubject().toUpperCase().equals(APP_CUSTOMER_STR)) {
					structure.getMessage().appendErrorMsg(readModel.getNo(), "订单为App订单,拒绝导入。");
					calculateModel.setStatus(SoinConstants.RESULT_WRONG);
					calculateModel.setSubjectType(APP_CUSTOMER);
				} else {
					calculateModel.setSubjectType(COPR_CUSTOMER);
					SoinOrderBillCalculateModel.Corp corp = new SoinOrderBillCalculateModel.Corp();
					corp.setName(readModel.getSubject());
					calculateModel.setCorp(corp);
				}

				//赋值参保人
				String soinPersonName = "";
				{
					SoinOrderBillCalculateModel.SoinPerson soinPerson = new SoinOrderBillCalculateModel.SoinPerson();
					{
						//赋值姓名
						SoinOrderBillCalculateModel.StringSubject nameSubject = new SoinOrderBillCalculateModel.StringSubject();
						nameSubject.setContent(readModel.getName());
						soinPerson.setName(nameSubject);
						soinPersonName = readModel.getName();
						if (isAnyBlank(readModel.getName())) {
							nameSubject.setStatus(SoinConstants.RESULT_WRONG);
							soinPerson.setStatus(SoinConstants.RESULT_WRONG);
							calculateModel.setStatus(SoinConstants.RESULT_WRONG);
						} else if (readModel.getName().length() > 16) {
							structure.getMessage().appendErrorMsg(readModel.getNo(), "姓名过长,不能超过16字。");
							nameSubject.setStatus(SoinConstants.RESULT_WRONG);
							soinPerson.setStatus(SoinConstants.RESULT_WRONG);
							calculateModel.setStatus(SoinConstants.RESULT_WRONG);
						}
					}

					{
						//校验和赋值身份证号码
						SoinOrderBillCalculateModel.StringSubject idcardSubject = new SoinOrderBillCalculateModel.StringSubject();
						idcardSubject.setContent(readModel.getIdcardNo());
						if (isBlank(readModel.getIdcardNo()) || !IdcardValidator.isValidatedAllIdcard(readModel.getIdcardNo())) {
							idcardSubject.setStatus(SoinConstants.RESULT_WRONG);//标记为错误
							soinPerson.setStatus(SoinConstants.RESULT_WRONG);
							calculateModel.setStatus(SoinConstants.RESULT_WRONG);
							structure.getMessage().appendErrorMsg(readModel.getNo(), isBlank(readModel.getIdcardNo()) ? "身份证号码为空。" : ("身份证号码:" + readModel.getIdcardNo() + "格式校验不正确。"));
						}
						soinPerson.setIdcardNo(idcardSubject);
					}

					{
						//校验和赋值联系电话
						SoinOrderBillCalculateModel.StringSubject phoneSubject = new SoinOrderBillCalculateModel.StringSubject();
						phoneSubject.setContent(readModel.getPhoneNo());
						if (!isBlank(readModel.getPhoneNo()) && !SysUtils.checkPhoneNo(readModel.getPhoneNo())) {
							phoneSubject.setStatus(SoinConstants.RESULT_WRONG);//标记为错误
							soinPerson.setStatus(SoinConstants.RESULT_WRONG);
							calculateModel.setStatus(SoinConstants.RESULT_WRONG);
							structure.getMessage().appendErrorMsg(readModel.getNo(), "手机号码:" + readModel.getPhoneNo() + "，格式错误。");
						}
						soinPerson.setPhoneNo(phoneSubject);
					}

					{
						//赋值户口性质
						SoinOrderBillCalculateModel.IntSubject hukouTypeSubject = new SoinOrderBillCalculateModel.IntSubject();
						soinPerson.setHukouTypeReadName(readModel.getHukouType());
						hukouTypeSubject.setContent(getHuKouTypeIndex(readModel.getHukouType()));
						hukouTypeSubject.setStrContent(readModel.getHukouType());
						soinPerson.setHukouType(hukouTypeSubject);
					}

					{
						//赋值户籍地址
						SoinOrderBillCalculateModel.StringSubject hukouDistrict = new SoinOrderBillCalculateModel.StringSubject();
						hukouDistrict.setContent(readModel.getHukouDistrict());
						soinPerson.setHukouDistrict(hukouDistrict);
						if (isNotBlank(readModel.getHukouDistrict()) && readModel.getHukouDistrict().length() > 128) {
							soinPerson.setStatus(SoinConstants.RESULT_WRONG);
							calculateModel.setStatus(SoinConstants.RESULT_WRONG);
							structure.getMessage().appendErrorMsg(readModel.getNo(), "户籍地址过长,最多128字。");
						}
					}

					calculateModel.setSoinPerson(soinPerson);

					//根据张媛需求 需要读取社保,公积金编号
					calculateModel.setSoinCode(readModel.getSoinCode());
					if (isNotBlank(readModel.getSoinCode()) && readModel.getSoinCode().length() > 32) {
						soinPerson.setStatus(SoinConstants.RESULT_WRONG);
						calculateModel.setStatus(SoinConstants.RESULT_WRONG);
						structure.getMessage().appendErrorMsg(readModel.getNo(), "社保编号过长,最多32字。");
					}
					calculateModel.setHouseFundCode(readModel.getHouseFundCode());
					if (isNotBlank(readModel.getHouseFundCode()) && readModel.getHouseFundCode().length() > 32) {
						soinPerson.setStatus(SoinConstants.RESULT_WRONG);
						calculateModel.setStatus(SoinConstants.RESULT_WRONG);
						structure.getMessage().appendErrorMsg(readModel.getNo(), "公积金编号过长,最多32字。");
					}
				}


				//校验和赋值服务年月
				{
					SoinOrderBillCalculateModel.StringSubject serviceYearMonth = new SoinOrderBillCalculateModel.StringSubject();
					serviceYearMonth.setContent(readModel.getServiceYearMonth());
					if (checkYearMonth(readModel.getServiceYearMonth())) {
						YearMonth yearMonth = generateYearMonthStrToObj(readModel.getServiceYearMonth());
						calculateModel.setServiceYear(yearMonth.getYear());
						calculateModel.setServiceMonth(yearMonth.getMonth());
					} else {
						serviceYearMonth.setStatus(SoinConstants.RESULT_WRONG);
						calculateModel.setStatus(SoinConstants.RESULT_WRONG);
						structure.getMessage().appendErrorMsg(readModel.getNo(), "服务年月:" + readModel.getServiceYearMonth() + ",格式错误。");
					}
					calculateModel.setServiceYearMonth(serviceYearMonth);
				}

				//校验和赋值缴纳年月
				{
					SoinOrderBillCalculateModel.StringSubject chargeYearMonth = new SoinOrderBillCalculateModel.StringSubject();
					chargeYearMonth.setContent(readModel.getPayYearMonth());
					calculateModel.setPayYearMonth(chargeYearMonth);
					if (calculateModel.getType() != SOIN_BACK_CALCULATE) {
						//如果不为纯补缴计算，则缴纳年月必填
						if (checkYearMonth(readModel.getPayYearMonth())) {
							YearMonth yearMonth = generateYearMonthStrToObj(readModel.getPayYearMonth());
							calculateModel.setPayYear(yearMonth.getYear());
							calculateModel.setPayMonth(yearMonth.getMonth());
						} else {
							chargeYearMonth.setStatus(SoinConstants.RESULT_WRONG);
							calculateModel.setStatus(SoinConstants.RESULT_WRONG);
							structure.getMessage().appendErrorMsg(readModel.getNo(), "缴纳年月:" + readModel.getPayYearMonth() + ",格式错误。");
						}
					}
				}

				//校验补缴年月
				{
					if (isNotBlank(readModel.getBackStartYearMonth()) || isNotBlank(readModel.getBackEndYearMonth())) {
						SoinOrderBillCalculateModel.StringSubject backYearMonth = new SoinOrderBillCalculateModel.StringSubject();
						backYearMonth.setContent("");
						calculateModel.setBackYearMonth(backYearMonth);
						if (calculateModel.getType() != SOIN_NORMAL_CALCULATE) {
							//如果计算类型不为纯正常缴纳类型，则补缴年月必填
							if (isBlank(readModel.getBackStartYearMonth())) {
								structure.getMessage().appendErrorMsg(calculateModel.getNo(), "补缴开始年月不能为空。");
								backYearMonth.setContent("错误");
								backYearMonth.setStatus(SoinConstants.RESULT_WRONG);
								calculateModel.setStatus(SoinConstants.RESULT_WRONG);
							} else if (isBlank(readModel.getBackEndYearMonth())) {
								structure.getMessage().appendErrorMsg(calculateModel.getNo(), "补缴结束年月不能为空。");
								backYearMonth.setContent("错误");
								backYearMonth.setStatus(SoinConstants.RESULT_WRONG);
								calculateModel.setStatus(SoinConstants.RESULT_WRONG);
							} else {
								YearMonth yearMonth = generateYearMonthStrToObj(readModel.getBackStartYearMonth());
								calculateModel.setBackYear(yearMonth.getYear());
								calculateModel.setBackMonth(yearMonth.getMonth());
								YearMonth endYearMonth = generateYearMonthStrToObj(readModel.getBackEndYearMonth());
								calculateModel.setBackCount(calculateMonths(yearMonth.getYear(), yearMonth.getMonth(), endYearMonth.getYear(), endYearMonth.getMonth()));
								if (calculateModel.getBackCount() == -1) {
									backYearMonth.setContent("补缴区间不正确");
									backYearMonth.setStatus(SoinConstants.RESULT_WRONG);
									calculateModel.setStatus(SoinConstants.RESULT_WRONG);
									structure.getMessage().appendErrorMsg(calculateModel.getNo(), "补缴区间不正确");
								} else {
									backYearMonth.setContent(yearMonth.getYear() + "年" + yearMonth.getMonth() + "月起" + calculateModel.getBackCount() + "个月");
								}
							}
						}
					}
				}

				//赋值参保地区
				{
					SoinOrderBillCalculateModel.SoinDistrict soinDistrict = new SoinOrderBillCalculateModel.SoinDistrict();
					soinDistrict.setNames(readModel.getSoinDistrict());
					calculateModel.setSoinDistrict(soinDistrict);
				}
				SoinOrderBillCalculateModel.SoinType soinType = new SoinOrderBillCalculateModel.SoinType();
				calculateModel.setSoinType(soinType);
				//赋值社保类型
				{
					calculateModel.getSoinType().setName(readModel.getSoinType());
				}

				//赋值社保基数
				{
					SoinOrderBillCalculateModel.BigDecimalSubject soinBase = new SoinOrderBillCalculateModel.BigDecimalSubject();
					if (readModel.getSoinBase().compareTo(SoinConstants.NUMBER_INVALID_FORMAT) == 0) {
						soinBase.setStatus(SoinConstants.RESULT_WRONG);
					}
					soinBase.setContent(readModel.getSoinBase());
					calculateModel.getSoinType().setSoinBase(soinBase);
				}

				//赋值公积金基数
				{
					SoinOrderBillCalculateModel.BigDecimalSubject housefundBase = new SoinOrderBillCalculateModel.BigDecimalSubject();
					if (readModel.getHousefundBase() != null && readModel.getHousefundBase().compareTo(SoinConstants.NUMBER_INVALID_FORMAT) == 0) {
						//如果公积金=null不认为是错误的。null是不缴纳公积金
						housefundBase.setStatus(SoinConstants.RESULT_WRONG);
					}
					housefundBase.setContent(readModel.getHousefundBase());
					calculateModel.getSoinType().setHousefundBase(housefundBase);
				}

				//赋值公积金比例
				{
					SoinOrderBillCalculateModel.BigDecimalSubject housefundPercent = new SoinOrderBillCalculateModel.BigDecimalSubject();
					if (readModel.getHousefundPercent().compareTo(SoinConstants.NUMBER_INVALID_FORMAT) == 0) {
						housefundPercent.setStatus(SoinConstants.RESULT_WRONG);
					}
					housefundPercent.setContent(readModel.getHousefundPercent());
					calculateModel.getSoinType().setHousefundPercent(housefundPercent);
				}

				//赋值增减员
				{
					SoinOrderBillCalculateModel.StringSubject modify = new SoinOrderBillCalculateModel.StringSubject();
					if (isBlank(readModel.getModify())) {
						modify.setContent(SOIN_PERSON_EXTEND);
					} else {
						modify.setContent(readModel.getModify());
					}
					calculateModel.setModify(modify);
				}

				//赋值收账服务费
				{
					SoinOrderBillCalculateModel.BigDecimalSubject collectionFee = new SoinOrderBillCalculateModel.BigDecimalSubject();
					if (readModel.getCollectionServiceFee().compareTo(new BigDecimal("0")) < 0 || readModel.getCollectionServiceFee().compareTo(SoinConstants.NUMBER_INVALID_FORMAT) == 0) {
						collectionFee.setStatus(SoinConstants.RESULT_WRONG);
						calculateModel.setStatus(SoinConstants.RESULT_WRONG);
						structure.getMessage().appendErrorMsg(readModel.getNo(), "收账服务费不正确!");
					}
					collectionFee.setContent(readModel.getCollectionServiceFee());
					calculateModel.getSoinType().setCollectionFee(collectionFee);
				}

				//赋值出账服务费(供应商)
				{
					SoinOrderBillCalculateModel.Supplier supplier = new SoinOrderBillCalculateModel.Supplier();
					SoinOrderBillCalculateModel.BigDecimalSubject fee = new SoinOrderBillCalculateModel.BigDecimalSubject();
					fee.setContent(readModel.getChargeServiceFee());
					if (readModel.getChargeServiceFee().compareTo(new BigDecimal("0")) < 0 || readModel.getCollectionServiceFee().compareTo(SoinConstants.NUMBER_INVALID_FORMAT) == 0) {
						fee.setStatus(SoinConstants.RESULT_WRONG);
						calculateModel.setStatus(SoinConstants.RESULT_WRONG);
						structure.getMessage().appendErrorMsg(readModel.getNo(), "出账服务费不正确!");
					}
					supplier.setFee(fee);
					calculateModel.setSupplier(supplier);
				}

				//赋值业务员
				{
					SoinOrderBillCalculateModel.Salesman salesman = new SoinOrderBillCalculateModel.Salesman();
					salesman.setName(readModel.getSalesman());
					calculateModel.setSalesman(salesman);
				}

				//顺延月
				{
					if (readModel.getTemplateType().equals("personal")) {
						SoinOrderBillCalculateModel.StringSubject postponeMonth = new SoinOrderBillCalculateModel.StringSubject();
						postponeMonth.setContent(readModel.getPostponeMonth());
						if (readModel.getPostponeMonth() == null && !calculateModel.getModify().getContent().equals("减员")) {
							postponeMonth.setStatus(SoinConstants.RESULT_WRONG);
							calculateModel.setStatus(SoinConstants.RESULT_WRONG);
							structure.getMessage().appendErrorMsg(readModel.getNo(), "顺延月份限制不能为空。");
						} else if (readModel.getPostponeMonth() != null && Integer.parseInt(readModel.getPostponeMonth()) < 0) {
							postponeMonth.setStatus(SoinConstants.RESULT_WRONG);
							calculateModel.setStatus(SoinConstants.RESULT_WRONG);
							structure.getMessage().appendErrorMsg(readModel.getNo(), "顺延月份不能小于0。");
						}
						calculateModel.setPostponeMonth(postponeMonth);
					}
				}

				calculateModels.add(calculateModel);
			} catch (Exception e) {
				structure.getMessage().appendErrorMsg(calculateModel.getNo(), "转换计算模型出错！");
			}
		}


		return calculateModels;
	}

	@Override
	public void checkOrderBillHukouDistrict(SoinOrderBillCalculateStructure structure) throws AryaServiceException {
		try {
			for (SoinOrderBillCalculateModel calculateModel : structure.getCalculateModels()) {
				String soinPersonName = calculateModel.getSoinPerson().getName().getContent();
				SoinOrderBillCalculateModel.StringSubject hukouDistrictSubject = calculateModel.getSoinPerson().getHukouDistrict();
				if (hukouDistrictSubject == null) {
					continue;
				}
				String hukouDistrict = hukouDistrictSubject.getContent();
				//可以为空
//                if (StringUtils.isAnyBlank(hukouDistrict)) {
//                    hukouDistrictSubject.setStatus(RESULT_WRONG);
//                    calculateModel.getSoinPerson().setStatus(RESULT_WRONG);
//                    calculateModel.setStatus(RESULT_WRONG);
//                    structure.getErrorMsgs().add(soinPersonName + "的户籍地址为空。");
//                    continue;
//                }
				//根据张媛需求 户籍地址存用户任意填写的内容 不再校验
//                String districtIds = commonDistrictService.checkDistrictNameStrIsExist(hukouDistrict);
//                if (StringUtils.isAnyBlank(districtIds)) {
//                    hukouDistrictSubject.setStatus(RESULT_WRONG);//没查询到地区则标记为错误。
//                    calculateModel.getSoinPerson().setStatus(RESULT_WRONG);
//                    calculateModel.setStatus(RESULT_WRONG);
//                    structure.getErrorMsgs().add("未能匹配到" + soinPersonName + "的户籍地址:" + hukouDistrict + "。");
//                    continue;
//                }
//                calculateModel.getSoinPerson().setHukouDistrictIds(districtIds);
			}
		} catch (Exception e) {
			throw new AryaServiceException(ErrorCode.CODE_ORDER_BILL_CHECK_HUKOU_DISTRICT_FAILED);
		}
	}

	@Override
	public void checkOrderBillSoinDistrictAndSoinType(SoinOrderBillCalculateStructure structure) throws AryaServiceException {
		List<SoinOrderBillCalculateModel> calculateModels = structure.getCalculateModels();
		BigDecimal zero = new BigDecimal("0");
		for (SoinOrderBillCalculateModel calculateModel : calculateModels) {
			try {
				SoinOrderBillCalculateModel.SoinDistrict soinDistrictSubject = calculateModel.getSoinDistrict();
				if (soinDistrictSubject == null) {
					continue;
				}
				String soinDistrict = soinDistrictSubject.getNames();
				if (isBlank(soinDistrict)) {
					soinDistrictSubject.setStatus(SoinConstants.RESULT_WRONG);
					calculateModel.getSoinType().setStatus(SoinConstants.RESULT_WRONG);
					structure.getMessage().appendErrorMsg(calculateModel.getNo(), "参保地区为空。");
					calculateModel.setStatus(SoinConstants.RESULT_WRONG);
					continue;
				}
				String soinDistrictId = commonDistrictService.checkImportSoinDistrictNameStrIsExist(soinDistrict);
				if (isBlank(soinDistrictId)) {
					soinDistrictSubject.setStatus(SoinConstants.RESULT_WRONG);//没查询到地区则标记为错误。
					structure.getMessage().appendErrorMsg(calculateModel.getNo(), "未能匹配到参保地区:" + soinDistrict + "。");
					calculateModel.setStatus(SoinConstants.RESULT_WRONG);
					continue;
				}
				soinDistrictSubject.setIds(soinDistrictId);
				String[] soinDistrictIds = soinDistrictId.split(":");
				soinDistrictSubject.setId(soinDistrictIds[soinDistrictIds.length - 1]);
				String soinTypeMapKey = soinDistrictSubject.getId() + calculateModel.getSoinType().getName();


				//参保地校验完成,开始校验社保类型
				SoinOrderBillCalculateModel.SoinType soinType = calculateModel.getSoinType();
				if (soinType == null) {
					continue;
				}

				String soinTypeName = soinType.getName();
				if (isBlank(soinTypeName)) {
					soinType.setStatus(SoinConstants.RESULT_WRONG);
					calculateModel.setStatus(SoinConstants.RESULT_WRONG);
					structure.getMessage().appendErrorMsg(calculateModel.getNo(), "社保类型为空。");
					continue;
				}

				//先从map缓存里找
				SoinTypeEntity soinTypeEntity = structure.getSoinTypeEntityMap().get(soinTypeMapKey);
				if (soinTypeEntity == null) {
					soinTypeEntity = soinTypeDao.findNotDeleteSoinTypeByNameAndDistrictId(soinTypeName, soinDistrictSubject.getId());
					if (soinTypeEntity == null || !soinTypeEntity.getTypeName().equals(soinTypeName)) {
						soinType.setStatus(SoinConstants.RESULT_WRONG);//没有找到社保类型
						calculateModel.setStatus(SoinConstants.RESULT_WRONG);
						structure.getMessage().appendErrorMsg(calculateModel.getNo(), "未能匹配到社保类型:" + soinTypeName + "。");
						continue;
					}
					structure.getSoinTypeEntityMap().put(soinTypeMapKey, soinTypeEntity);
				} else {
					if (!soinTypeEntity.getTypeName().equals(soinTypeName)) {
						soinType.setStatus(SoinConstants.RESULT_WRONG);//没有找到社保类型
						calculateModel.setStatus(SoinConstants.RESULT_WRONG);
						structure.getMessage().appendErrorMsg(calculateModel.getNo(), "未能匹配到社保类型:" + soinTypeName + "。");
						continue;
					}
				}
				SoinTypeVersionEntity typeVersionEntity = null;
				SoinTypeVersionEntity normalTypeVersionEntity = null;
				SoinTypeVersionEntity backTypeVersionEntity = null;
				calculateModel.getSoinType().setId(soinTypeEntity.getId());
				if (soinTypeEntity.getFees() == null) {
					soinTypeEntity.setFees(BigDecimal.ZERO);
				}
				if (calculateModel.getType() != SOIN_BACK_CALCULATE) {
					normalTypeVersionEntity = structure.getSoinTypeVersionEntityMap().get(soinTypeEntity.getId());
					if (normalTypeVersionEntity == null) {
						normalTypeVersionEntity = soinTypeVersionDao.findEffectVersionBySoinTypeIdAndYearMonth(soinTypeEntity.getId(), calculateModel.getPayYear(), calculateModel.getPayMonth(), SOIN_VERSION_TYPE_NORMAL, null);
						if (normalTypeVersionEntity == null) {
							calculateModel.getPayYearMonth().setStatus(SoinConstants.RESULT_WRONG);//没有找到有效版本
							calculateModel.setStatus(SoinConstants.RESULT_WRONG);
							structure.getMessage().appendErrorMsg(calculateModel.getNo(), "未能匹配到社保类型的有效版本:" + soinTypeName + "。");
							continue;
						}
						structure.getSoinTypeVersionEntityMap().put(soinTypeEntity.getId(), normalTypeVersionEntity);
					}
				}
				if (calculateModel.getType() != SOIN_NORMAL_CALCULATE) {
					//如果有补缴业务
					backTypeVersionEntity = structure.getSoinBackTypeVersionEntityMap().get(soinTypeEntity.getId());
					if (backTypeVersionEntity == null) {
						backTypeVersionEntity = soinTypeVersionDao.findEffectVersionBySoinTypeIdAndYearMonth(soinTypeEntity.getId(), calculateModel.getBackYear(), calculateModel.getBackMonth(), SOIN_VERSION_TYPE_BACK, null);
						if (backTypeVersionEntity == null) {
							calculateModel.getBackYearMonth().setStatus(SoinConstants.RESULT_WRONG);//没有找到有效补缴版本
							calculateModel.setStatus(SoinConstants.RESULT_WRONG);
							structure.getMessage().appendErrorMsg(calculateModel.getNo(), "未能匹配到社保类型的有效补缴版本:" + soinTypeName + "。");
							continue;
						}
						structure.getSoinBackTypeVersionEntityMap().put(soinTypeEntity.getId(), backTypeVersionEntity);
					}
				}

				typeVersionEntity = normalTypeVersionEntity;
				if (normalTypeVersionEntity == null) {
					typeVersionEntity = backTypeVersionEntity;
				}

				//校验社保基数,公积金基数和公积金比例,服务费
				if (soinType.getCollectionFee().getContent().compareTo(BigDecimal.ZERO) == 0) {
					soinType.getCollectionFee().setContent(soinTypeEntity.getFees());//使用系统的管理费
				} else if (soinType.getCollectionFee().getContent().compareTo(new BigDecimal("0")) < 0) {
					soinType.getCollectionFee().setStatus(SoinConstants.RESULT_WRONG);
					calculateModel.setStatus(SoinConstants.RESULT_WRONG);
					structure.getMessage().appendErrorMsg(calculateModel.getNo(), "收账服务费小于0。");
				} else if (soinType.getCollectionFee().getContent().compareTo(soinTypeEntity.getFees()) != 0) {
					soinType.getCollectionFee().setStatus(SoinConstants.RESULT_USER);
				}

				//找出社保的最低基数
				SoinTypeMinMaxBaseModel minMaxBaseModel = getMinMaxBase(typeVersionEntity);
				if (soinType.getSoinBase().getContent().compareTo(zero) == 0) {
					soinType.getSoinBase().setContent(minMaxBaseModel.getMinBase());//使用系统的最低社保基数
				}

				if (soinType.getSoinBase().getContent().compareTo(minMaxBaseModel.getMinBase()) < 0 || soinType.getSoinBase().getContent().compareTo(minMaxBaseModel.getMaxBase()) > 0) {
					soinType.getSoinBase().setStatus(SoinConstants.RESULT_WRONG);
					calculateModel.setStatus(SoinConstants.RESULT_WRONG);
					structure.getMessage().appendErrorMsg(calculateModel.getNo(), "社保基数不在" + minMaxBaseModel.getMinBase() + "和" + minMaxBaseModel.getMaxBase() + "之间。");
				} else if (soinType.getSoinBase().getContent().compareTo(minMaxBaseModel.getMinBase()) != 0) {
					soinType.getSoinBase().setStatus(SoinConstants.RESULT_USER);//标记为使用用户给出的社保基数
				}

				//判断是否必须缴纳公积金
				if (soinTypeEntity.getHouseFundMust() == false) {
					if (soinType.getHousefundBase().getContent() == null) {
						soinType.setPercentStr("不缴纳公积金");
						continue;
					}
				}
				if (soinType.getHousefundBase().getContent() == null) {
					soinType.getHousefundBase().setContent(SoinUtil.ZERO);
				}

				SoinRuleEntity housefundRule = typeVersionEntity.getRuleHouseFund();
				if (housefundRule == null) {
					soinType.setPercentStr("不缴纳公积金");
					continue;
				}

				//公积金基数
				if (soinType.getHousefundBase().getContent().compareTo(BigDecimal.ZERO) == 0) {
					soinType.getHousefundBase().setContent(housefundRule.getMinBase());//使用系统默认的最低基数
				} else if (soinType.getHousefundBase().getContent().compareTo(housefundRule.getMinBase()) < 0 || soinType.getHousefundBase().getContent().compareTo(housefundRule.getMaxBase()) > 0) {
					soinType.getHousefundBase().setStatus(SoinConstants.RESULT_WRONG);
					calculateModel.setStatus(SoinConstants.RESULT_WRONG);
					structure.getMessage().appendErrorMsg(calculateModel.getNo(), "公积金基数不在" + housefundRule.getMinBase() + "和" + housefundRule.getMaxBase() + "之间。");
				} else if (soinType.getHousefundBase().getContent().compareTo(housefundRule.getMinBase()) != 0) {
					soinType.getHousefundBase().setStatus(SoinConstants.RESULT_USER);//标记为使用用户给出的公积金基数
				}

				//公积金比例
				if (soinType.getHousefundPercent().getContent().compareTo(zero) == 0) {
					//走到这里说明用户没有设定公积金比例，按照系统默认比例进行下一步计算
					soinType.getHousefundPercent().setContent(null);
					soinType.setPersonPercent(SoinUtil.turnBigDecimalHalfRoundUp(new BigDecimal(housefundRule.getPercentagePerson()), 2));
					soinType.setCorpPercent(SoinUtil.turnBigDecimalHalfRoundUp(new BigDecimal(housefundRule.getPercentageCorp()), 2));
					if (backTypeVersionEntity != null) {
						soinType.setBackPersonPercent(SoinUtil.turnBigDecimalHalfRoundUp(new BigDecimal(backTypeVersionEntity.getRuleHouseFund().getPercentagePerson()), 2));
						soinType.setBackCorpPercent(SoinUtil.turnBigDecimalHalfRoundUp(new BigDecimal(backTypeVersionEntity.getRuleHouseFund().getPercentageCorp()), 2));
					}
				} else if (soinType.getHousefundPercent().getContent().compareTo(new BigDecimal(housefundRule.getPercentageCorp())) < 0 || soinType.getHousefundPercent().getContent().compareTo(new BigDecimal("100")) > 0) {
					soinType.getHousefundPercent().setStatus(SoinConstants.RESULT_WRONG);
					calculateModel.setStatus(SoinConstants.RESULT_WRONG);
					structure.getMessage().appendErrorMsg(calculateModel.getNo(), "公积金比例不在" + housefundRule.getPercentageCorp() + "-100之间。");
					soinType.setPersonPercent(soinType.getHousefundPercent().getContent());
					soinType.setCorpPercent(soinType.getHousefundPercent().getContent());
				} else {
					soinType.getHousefundPercent().setStatus(SoinConstants.RESULT_USER);
					soinType.setPersonPercent(soinType.getHousefundPercent().getContent());
					soinType.setCorpPercent(soinType.getHousefundPercent().getContent());
					if (backTypeVersionEntity != null) {
						soinType.setBackPersonPercent(soinType.getHousefundPercent().getContent());
						soinType.setBackCorpPercent(soinType.getHousefundPercent().getContent());
					}
				}
				soinType.setPercentStr("公司" + soinType.getCorpPercent() + "%个人" + soinType.getPersonPercent() + "%");
				if (backTypeVersionEntity != null) {
					soinType.setBackPercentStr("补缴公司" + soinType.getBackCorpPercent() + "%个人" + soinType.getBackPersonPercent() + "%");
				}
			} catch (Exception e) {
				elog.error("【对账单计算】", e);
				structure.getMessage().appendErrorMsg(calculateModel.getNo(), "社保类型的基础数据配置有误，请检查。");
			}
		}

	}

	@Override
	public void checkOrderBillSoinPerson(SoinOrderBillCalculateStructure structure) throws AryaServiceException {
		try {
			List<SoinOrderBillCalculateModel> calculateModels = structure.getCalculateModels();
			for (SoinOrderBillCalculateModel calculateModel : calculateModels) {
				if (calculateModel.getSoinPerson().getStatus() == SoinConstants.RESULT_WRONG) {
					continue;
				}
				SoinOrderBillCalculateModel.SoinPerson soinPerson = calculateModel.getSoinPerson();
				String idCardNo = soinPerson.getIdcardNo().getContent();
				SoinOrderBillCalculateStructure.SoinPerson soinPersonStructure = structure.getSoinPersonStructureMap().get(idCardNo.toUpperCase());

				if (soinPersonStructure != null) {
					calculateModel.getSoinPerson().setStatus(soinPersonStructure.getStatus());
					calculateModel.getSoinPerson().setId(soinPersonStructure.getPersonEntity().getId());
				} else {
					AryaSoinPersonEntity soinPersonEntity = soinPersonDao.findImportedPersonByIdcardNo(idCardNo);
					soinPersonStructure = new SoinOrderBillCalculateStructure.SoinPerson();
					if (soinPersonEntity == null) {
						soinPersonStructure.setStatus(SoinConstants.RESULT_NEW);
						AryaSoinPersonEntity newPerson = new AryaSoinPersonEntity();
						newPerson.setId(Utils.makeUUID());
						newPerson.setDelete(false);
						newPerson.setHukou(soinPerson.getHukouDistrictIds());
						newPerson.setHukouDistrict(soinPerson.getHukouDistrict().getContent());
						if (soinPerson.getHukouType().getContent() > 0) {
							newPerson.setHukouType(soinPerson.getHukouType().getContent());
						} else {
							newPerson.setHukouTypeName(soinPerson.getHukouType().getStrContent());
						}
						//不需要检查身份证是否被使用
						newPerson.setIdcardNo(idCardNo);
						newPerson.setInsurancePersonName(soinPerson.getName().getContent());
						newPerson.setIsImported(CorpConstants.TRUE);
						newPerson.setVerifyStatus(SoinPersonStatus.PERSON_PASSED);
						//判断手机号码是否为空 为空则系统生成
						if (isBlank(soinPerson.getPhoneNo().getContent())) {
							soinPerson.getPhoneNo().setContent(phoneService.getNewSystemPhoneNumber());
//							structure.getErrorMsgs().add(newPerson.getInsurancePersonName() + "的手机号码为空,系统自动生成:" + soinPerson.getPhoneNo().getContent() + "。");
						}
						newPerson.setPhoneNo(soinPerson.getPhoneNo().getContent());
						newPerson.setCreateTime(System.currentTimeMillis());
						newPerson.setLastPayedYear(0);
						newPerson.setLastPayedMonth(0);
						soinPersonStructure.setPersonEntity(newPerson);
						calculateModel.getSoinPerson().setStatus(soinPersonStructure.getStatus());
						calculateModel.getSoinPerson().setId(newPerson.getId());
					} else {
						soinPersonEntity.setHukouDistrict(soinPerson.getHukouDistrict().getContent());
						if (soinPerson.getHukouType().getContent() > 0) {
							soinPersonEntity.setHukouType(soinPerson.getHukouType().getContent());
						} else {
							soinPersonEntity.setHukouType(0);
							soinPersonEntity.setHukouTypeName(soinPerson.getHukouType().getStrContent());
						}
						//判断手机号码是否为空 为空则沿用之前手机号
						if (isBlank(soinPerson.getPhoneNo().getContent())) {
							soinPerson.getPhoneNo().setContent(soinPersonEntity.getPhoneNo());
						} else if (!soinPerson.getPhoneNo().getContent().equals(soinPersonEntity.getPhoneNo())) {
							//如果手机号码不相同 则替换为新的
							soinPersonEntity.setPhoneNo(soinPerson.getPhoneNo().getContent());
						}
						soinPersonStructure.setPersonEntity(soinPersonEntity);
						calculateModel.getSoinPerson().setId(soinPersonEntity.getId());
					}
					structure.getSoinPersonStructureMap().put(idCardNo.toUpperCase(), soinPersonStructure);
				}
			}
		} catch (Exception e) {
			throw new AryaServiceException(ErrorCode.CODE_ORDER_BILL_CHECK_SOIN_PERSON_FAILED);
		}
	}

	@Override
	public void checkSoinPersonIncrease(SoinOrderBillCalculateStructure structure) throws AryaServiceException {
		List<SoinOrderBillCalculateModel> calculateModels = structure.getCalculateModels();
		for (SoinOrderBillCalculateModel calculateModel : calculateModels) {
			//如果身份证号码不存在错误则判断一下该身份证号码的参保人是否需要增员操作
			if (calculateModel.getSoinPerson().getIdcardNo().getStatus() == SoinConstants.RESULT_WRONG) {
				continue;
			}
			String currentModifyType = calculateModel.getModify().getContent();
			String orderId = calculateModel.getOrderId();
			SoinOrderBillCalculateModel.SoinPerson person = calculateModel.getSoinPerson();
			if (currentModifyType.equals(SOIN_PERSON_DECREASE)) {
				//如果当前订单是做减员，则查出最新订单的服务年月
				SoinInOrDecreaseEntity latest = soinInOrDecreaseDao.findLastMonthByIdcardNo(calculateModel.getSoinPerson().getIdcardNo().getContent());
				if (latest != null) {
					calculateModel.setServiceYear(latest.getYear());
					calculateModel.setServiceMonth(latest.getMonth());
					calculateModel.getServiceYearMonth().setContent(latest.getYear() + String.format("%02d", latest.getMonth()));
				}
			}

			//查出本月的增减员记录
			SoinInOrDecreaseEntity thisMonthInOrDecreaseEntity = soinInOrDecreaseDao.findMonthByIdcardNo(person.getIdcardNo().getContent(), calculateModel.getServiceYear(), calculateModel.getServiceMonth());
			if (thisMonthInOrDecreaseEntity != null) {
				if (currentModifyType.equals(SOIN_PERSON_INCREASE)) { //本次导入为增员
					if (!thisMonthInOrDecreaseEntity.getOperateType().equals(getCode(SOIN_PERSON_DECREASE))) {
						structure.getMessage().appendWarnMsg(calculateModel.getNo(), "当月重复增员操作，系统自动忽略本次增员操作！");
						calculateModel.setOrderOperate(ORDER_KEEP);
						continue;
					} else {
						//允许增员
						//structure.getMessage().appendWarnMsg(calculateModel.getNo(), "参保人当月做过减员，允许当月增员，系统将修改减员订单为增员订单或增加增员订单。");
//						structure.getSoinDeleteEntityMap().put(calculateModel.getSoinPerson().getIdcardNo() + "" + calculateModel.getPayYear() + calculateModel.getPayMonth(), thisMonthInOrDecreaseEntity);
						//标记在数据修改订单
//						calculateModel.setOrderOperate(ORDER_INCREASE_UPDATE);//标记对DB订单做增员修改
//						orderId = thisMonthInOrDecreaseEntity.getOrderId();
//						calculateModel.setNeedUpdateOrderId(orderId);
//						structure.getNeedIncreaseUpdateOrderIds().add(orderId);//保存订单id

						structure.getMessage().appendWarnMsg(calculateModel.getNo(), "参保人当月做过减员，允许当月增员，系统将删除减员订单并增加订单为顺延。");						//删除减员订单
						String oldOrderId = thisMonthInOrDecreaseEntity.getOrderId();
						soinOrderDao.deleteSoinOrderById(oldOrderId);
						structure.getNeedDeleteOrderIds().add(oldOrderId);
						structure.getNeedDeleteModifyIds().add(oldOrderId);
						//标记在数据库新增订单
						currentModifyType = SOIN_PERSON_EXTEND;
						calculateModel.setOrderOperate(ORDER_INCREATE);
					}
				} else if (currentModifyType.equals(SOIN_PERSON_DECREASE)) { //本次导入为减员
					if (!thisMonthInOrDecreaseEntity.getOperateType().equals(getCode(SOIN_PERSON_DECREASE))) {
						//允许减员
						if (thisMonthInOrDecreaseEntity.getOperateType().equals(getCode(SOIN_PERSON_INCREASE))) {
							structure.getMessage().appendWarnMsg(calculateModel.getNo(), "参保人当月做过增员，允许当月减员，系统将删除当月订单。");
							calculateModel.setOrderOperate(ORDER_DELETE);//标记删除
							orderId = thisMonthInOrDecreaseEntity.getOrderId();
							structure.getNeedDeleteOrderIds().add(orderId);
							structure.getNeedDeleteModifyIds().add(orderId);
							continue;
						} else {
							structure.getMessage().appendWarnMsg(calculateModel.getNo(), "参保人当月做过顺延，允许当月减员，系统将当月订单改成减员订单。");
							calculateModel.setOrderOperate(ORDER_DECREASE_UPDATE);//标记对DB订单修改成减员
							orderId = thisMonthInOrDecreaseEntity.getOrderId();
							calculateModel.setNeedUpdateOrderId(orderId);
							structure.getNeedDecreaseUpdateOrderIds().add(orderId);//保存订单id
						}
					} else {
						structure.getMessage().appendWarnMsg(calculateModel.getNo(), "当月重复减员，系统自动忽略本次操作！");
						calculateModel.setOrderOperate(ORDER_KEEP);
						continue;
					}
				} else {  //本次导入为顺延
					if (thisMonthInOrDecreaseEntity.getOperateType().equals(getCode(SOIN_PERSON_DECREASE))) {
//						structure.getMessage().appendWarnMsg(calculateModel.getNo(), "当月上一次做的是减员，系统自动将本次的顺延改为增员，并将订单改为增员！");
//						currentModifyType = SOIN_PERSON_INCREASE;
//						calculateModel.setOrderOperate(ORDER_INCREASE_UPDATE);
//						orderId = thisMonthInOrDecreaseEntity.getOrderId();
//						calculateModel.setNeedUpdateOrderId(orderId);
//						structure.getNeedIncreaseUpdateOrderIds().add(orderId);//保存订单id

						structure.getMessage().appendWarnMsg(calculateModel.getNo(), "当月上一次做的是减员，系统自动将本次的顺延改为增员，并将减员订单删除，新增顺延订单！");
						//删除减员订单
						String oldOrderId = thisMonthInOrDecreaseEntity.getOrderId();
						soinOrderDao.deleteSoinOrderById(oldOrderId);
						structure.getNeedDeleteOrderIds().add(oldOrderId);
						structure.getNeedDeleteModifyIds().add(oldOrderId);
						//标记在数据库新增订单
						currentModifyType = SOIN_PERSON_EXTEND;
						calculateModel.setOrderOperate(ORDER_INCREATE);
					} else if (thisMonthInOrDecreaseEntity.getOperateType().equals(getCode(SOIN_PERSON_INCREASE))) {
						structure.getMessage().appendWarnMsg(calculateModel.getNo(), "当月上一次做的是增员，系统忽略本次顺延操作！");
						calculateModel.setOrderOperate(ORDER_KEEP);
						continue;
					} else {
						structure.getMessage().appendWarnMsg(calculateModel.getNo(), "当月上一次做的是顺延，系统忽略本次顺延操作！");
						calculateModel.setOrderOperate(ORDER_KEEP);
						continue;
					}
				}
			} else {
				//查出上个月的增减员记录
				SoinInOrDecreaseEntity inOrDecreaseEntity = soinInOrDecreaseDao.findLastMonthByIdcardNo(person.getIdcardNo().getContent(), calculateModel.getServiceYear(), calculateModel.getServiceMonth());
				if (currentModifyType.equals(SOIN_PERSON_EXTEND) || currentModifyType.equals(SOIN_PERSON_INCREASE)) {
					//如果是顺延或者增员情况下
					if (inOrDecreaseEntity == null || inOrDecreaseEntity.getOperateType() == SoinOrderBillConstants.getCode(SOIN_PERSON_DECREASE)) {
						//必须增员
						//从来没增员过 或者 如果上一次是减员 则这一次需要增员
						//标记增员
						if (!currentModifyType.equals(SOIN_PERSON_INCREASE)) {
							StringBuilder warnMsg = new StringBuilder();
							if (inOrDecreaseEntity == null) {
								warnMsg.append("参保人上个月未做过增员或顺延");
							} else if (inOrDecreaseEntity.getOperateType() == SoinOrderBillConstants.getCode(SOIN_PERSON_DECREASE)) {
								warnMsg.append("参保人上个月做过减员");
							}
							structure.getMessage().appendWarnMsg(calculateModel.getNo(), warnMsg + "，系统自动将" + currentModifyType + "更换为" + SOIN_PERSON_INCREASE + "。");
							currentModifyType = SOIN_PERSON_INCREASE;
						}
						//标记往数据新增订单
						calculateModel.setOrderOperate(ORDER_INCREATE);
					} else {
						//顺延订单
						if (currentModifyType.equals(SOIN_PERSON_INCREASE)) {
							//如果用户在导入模板填写的是增员，则自动更改为顺延
							structure.getMessage().appendWarnMsg(calculateModel.getNo(), "参保人上个月做过增员，系统自动将" + SOIN_PERSON_INCREASE + "更换为" + SOIN_PERSON_EXTEND + "。");
							currentModifyType = SOIN_PERSON_EXTEND;
						}
						calculateModel.setOrderOperate(ORDER_INCREATE);//标记新增订单
						structure.getMessage().setNotModifyRows(structure.getMessage().getNotModifyRows() + 1);//记录下未做增减员操作的条数
					}
				} else {
					//如果是减员情况下
					StringBuilder warnMsg = new StringBuilder();
					if (inOrDecreaseEntity == null) {
						//如果从未增员过
						structure.getMessage().appendWarnMsg(calculateModel.getNo(), "参保人上个月未做过增员或顺延，系统自动取消减员操作。");
						calculateModel.setOrderOperate(ORDER_KEEP);
					} else if (inOrDecreaseEntity.getOperateType() == SoinOrderBillConstants.getCode(SOIN_PERSON_DECREASE)) {
						//如果最近一次操作也是减员
						structure.getMessage().appendWarnMsg(calculateModel.getNo(), "参保人上个月做过减员，系统自动取消减员操作。");
						calculateModel.setOrderOperate(ORDER_KEEP);
					} else {
						//取消新增订单，并且如果当月DB中也有订单则修改订单
						calculateModel.setOrderOperate(ORDER_DECREASE_UPDATE);
					}
				}
			}

			if (calculateModel.getOrderOperate().equals(ORDER_KEEP)) {
				//如果不增减员则continue
				structure.getMessage().setNotModifyRows(structure.getMessage().getNotModifyRows() + 1);
				continue;
			}

			SoinInOrDecreaseEntity inOrDecreaseNew = new SoinInOrDecreaseEntity();
			inOrDecreaseNew.setId(Utils.makeUUID());
			inOrDecreaseNew.setOrderId(orderId);
			inOrDecreaseNew.setIdcardNo(person.getIdcardNo().getContent());
			inOrDecreaseNew.setYear(calculateModel.getServiceYear());
			inOrDecreaseNew.setMonth(calculateModel.getServiceMonth());
			inOrDecreaseNew.setDistrictIds(calculateModel.getSoinDistrict().getIds());
			inOrDecreaseNew.setSupplierId(calculateModel.getSupplier().getId());
			inOrDecreaseNew.setCreateTime(System.currentTimeMillis());
			if (currentModifyType.equals(SOIN_PERSON_INCREASE)) {
				//增员
				inOrDecreaseNew.setOperateType(SoinOrderBillConstants.getCode(SOIN_PERSON_INCREASE));
				if (!structure.getSoinInEntityMap().containsKey(inOrDecreaseNew.getIdcardNo())) {
					//判断是否存在同一个参保人的增员记录
					structure.getSoinInEntityMap().put(inOrDecreaseNew.getIdcardNo(), inOrDecreaseNew);
					calculateModel.setModifyType(getCode(SOIN_PERSON_INCREASE));
				} else {
					structure.getMessage().appendErrorMsg(calculateModel.getNo(), "存在重复的增员订单！");
				}
			} else if (currentModifyType.equals(SOIN_PERSON_DECREASE)) {
				//减员
				inOrDecreaseNew.setOperateType(SoinOrderBillConstants.getCode(SOIN_PERSON_DECREASE));
				if (!structure.getSoinDecreaseEntityMap().containsKey(inOrDecreaseNew.getIdcardNo())) {
					structure.getSoinDecreaseEntityMap().put(inOrDecreaseNew.getIdcardNo(), inOrDecreaseNew);
					calculateModel.setModifyType(getCode(SOIN_PERSON_DECREASE));
				} else {
					structure.getMessage().appendErrorMsg(calculateModel.getNo(), "存在重复的减员订单！");
				}
			} else {
				//顺延
				inOrDecreaseNew.setOperateType(SoinOrderBillConstants.getCode(SOIN_PERSON_EXTEND));
				if (!structure.getSoinExtendEntityMap().containsKey(inOrDecreaseNew.getIdcardNo())) {
					structure.getSoinExtendEntityMap().put(inOrDecreaseNew.getIdcardNo(), inOrDecreaseNew);
					calculateModel.setModifyType(getCode(SOIN_PERSON_EXTEND));
				} else {
					structure.getMessage().appendErrorMsg(calculateModel.getNo(), "存在重复的顺延订单！");
				}
			}
		}
	}

	@Override
	public void checkOrderBillCorpCustomer(SoinOrderBillCalculateStructure structure) throws AryaServiceException {
		try {
			for (SoinOrderBillCalculateModel calculateModel : structure.getCalculateModels()) {
				if (calculateModel.getSubjectType() != COPR_CUSTOMER) {
					continue;
				}
				SoinOrderBillCalculateModel.Corp corp = calculateModel.getCorp();

				SoinOrderBillCalculateStructure.Corp corpStructure = structure.getCorporationStructureMap().get(corp.getName());
				if (corpStructure != null) {
					corp.setStatus(corpStructure.getStatus());
				} else {
					CorporationEntity corporationEntity = corporationDao.findCorpByName(corp.getName());
					corpStructure = new SoinOrderBillCalculateStructure.Corp();
					if (corporationEntity == null) {
						corpStructure.setStatus(SoinConstants.RESULT_NEW);
						corp.setStatus(SoinConstants.RESULT_NEW);
						CorporationEntity newCorpEntity = new CorporationEntity();
						newCorpEntity.setId(Utils.makeUUID());
						newCorpEntity.setCreateTime(System.currentTimeMillis());
						newCorpEntity.setBusinessType(CORP_BUSINESS_SOIN);
						newCorpEntity.setIsGroup(CorpConstants.FALSE);
						newCorpEntity.setIsDelete(false);
						newCorpEntity.setName(corp.getName());
						corpStructure.setCorporationEntity(newCorpEntity);
					} else {
						corpStructure.setCorporationEntity(corporationEntity);
					}
					structure.getCorporationStructureMap().put(corp.getName(), corpStructure);
				}
			}
		} catch (Exception e) {
			throw new AryaServiceException(ErrorCode.CODE_ORDER_BILL_CHECK_SOIN_CORP_CUSTOMER_FAILED);
		}
	}

	@Override
	public void checkOrderBillSupplier(SoinOrderBillCalculateStructure structure) throws AryaServiceException {
		try {
			for (SoinOrderBillCalculateModel calculateModel : structure.getCalculateModels()) {
				if (calculateModel.getSoinDistrict().getStatus() == SoinConstants.RESULT_WRONG) {
					//如果参保地区错误,则跳过,肯定找不到供应商
					continue;
				}

				String soinPersonName = calculateModel.getSoinPerson().getName().getContent();

				SoinOrderBillCalculateModel.Supplier supplier = calculateModel.getSupplier();
				SoinOrderBillCalculateModel.SoinDistrict soinDistrict = calculateModel.getSoinDistrict();
				//先从缓存中拿
				SoinSupplierEntity soinSupplierEntity = structure.getSoinSupplierEntityMap().get(soinDistrict.getId());
				if (soinSupplierEntity == null) {
					SoinDistrictSupplierEntity soinDistrictSupplierEntity = soinDistrictSupplierDao.findDistrictPrimarySupplier(soinDistrict.getId());
					if (soinDistrictSupplierEntity == null) {
						supplier.setStatus(SoinConstants.RESULT_WRONG);
						calculateModel.setStatus(SoinConstants.RESULT_WRONG);
						structure.getMessage().appendErrorMsg(calculateModel.getNo(), "参保地区:" + soinDistrict.getNames() + ",没有供应商。");
						continue;
					}
					soinSupplierEntity = soinSupplierDao.findSoinSupplier(soinDistrictSupplierEntity.getSupplierId());
					if (soinSupplierEntity == null) {
						supplier.setStatus(SoinConstants.RESULT_WRONG);
						calculateModel.setStatus(SoinConstants.RESULT_WRONG);
						structure.getMessage().appendErrorMsg(calculateModel.getNo(), "未能找到供应商。");
						continue;
					}
					//放入缓存
					structure.getSoinSupplierEntityMap().put(soinDistrict.getId(), soinSupplierEntity);
				}
				supplier.setId(soinSupplierEntity.getId());
				supplier.setName(soinSupplierEntity.getSupplierName());
				if (supplier.getFee().getContent().compareTo(BigDecimal.ZERO) == 0) {
					supplier.getFee().setContent(soinSupplierEntity.getSoinFee());//使用系统默认的出账服务费
				} else if (supplier.getFee().getContent().compareTo(new BigDecimal("0")) < 0) {
					supplier.getFee().setStatus(SoinConstants.RESULT_WRONG);
					calculateModel.setStatus(SoinConstants.RESULT_WRONG);
					structure.getMessage().appendErrorMsg(calculateModel.getNo(), "收账服务费" + supplier.getFee().getContent() + "小于0。");
				} else if (supplier.getFee().getStatus() != SoinConstants.RESULT_WRONG && supplier.getFee().getContent().compareTo(soinSupplierEntity.getSoinFee()) != 0) {
					//校验出账服务费是否是使用文件记录给出的
					supplier.getFee().setStatus(SoinConstants.RESULT_USER);
				}
			}
		} catch (Exception e) {
			throw new AryaServiceException(ErrorCode.CODE_ORDER_BILL_CHECK_SOIN_SUPPLIER_FAILED);
		}
	}

	@Override
	public void checkOrderBillSalesman(SoinOrderBillCalculateStructure structure) throws AryaServiceException {
		try {
			for (SoinOrderBillCalculateModel calculateModel : structure.getCalculateModels()) {
				SoinOrderBillCalculateModel.Salesman salesman = calculateModel.getSalesman();
				SysUserEntity sysUserEntity = structure.getSysUserEntityMap().get(salesman.getName());
				if (sysUserEntity == null) {
					sysUserEntity = sysUserDao.findSysUserByName(salesman.getName());
					if (sysUserEntity == null) {
						salesman.setStatus(SoinConstants.RESULT_WRONG);
						calculateModel.setStatus(SoinConstants.RESULT_WRONG);
						structure.getMessage().appendErrorMsg(calculateModel.getNo(), "没有匹配到业务员:" + salesman.getName());
						continue;
					}
					structure.getSysUserEntityMap().put(salesman.getName(), sysUserEntity);
				}
				salesman.setId(sysUserEntity.getId());
			}
		} catch (Exception e) {
			throw new AryaServiceException(ErrorCode.CODE_ORDER_BILL_CHECK_SOIN_SALESMAN_FAILED);
		}
	}

	@Override
	public void soinOrderBillCalculate(SoinOrderBillCalculateStructure structure) throws AryaServiceException {
		for (SoinOrderBillCalculateModel calculateModel : structure.getCalculateModels()) {
			try {
				if (calculateModel.getStatus() == SoinConstants.RESULT_WRONG) {
					continue;
				}
				SoinOrderBillCalculateModel.SoinDistrict soinDistrict = calculateModel.getSoinDistrict();
				if (soinDistrict.getStatus() == SoinConstants.RESULT_WRONG) {
					calculateModel.setStatus(SoinConstants.RESULT_WRONG);
					continue;
				}

				SoinOrderBillCalculateModel.SoinType soinType = calculateModel.getSoinType();
				if (soinType.getStatus() == SoinConstants.RESULT_WRONG) {
					calculateModel.setStatus(SoinConstants.RESULT_WRONG);
					continue;
				}

				if (soinType.getSoinBase().getStatus() == SoinConstants.RESULT_WRONG || soinType.getHousefundBase().getStatus() == SoinConstants.RESULT_WRONG || soinType.getHousefundPercent().getStatus() == SoinConstants.RESULT_WRONG) {
					calculateModel.setStatus(SoinConstants.RESULT_WRONG);
					continue;
				}

				SoinOrderBillCalculateModel.Supplier supplier = calculateModel.getSupplier();
				if (supplier.getStatus() == SoinConstants.RESULT_WRONG) {
					calculateModel.setStatus(SoinConstants.RESULT_WRONG);
					structure.getMessage().appendErrorMsg(calculateModel.getNo(), "没有匹配到有效的供应商。");
					//没供应商也计算
				}


				SoinTypeEntity soinTypeEntity = structure.getSoinTypeEntityMap().get(soinDistrict.getId() + calculateModel.getSoinType().getName());
				if (soinTypeEntity == null) {
					calculateModel.setStatus(SoinConstants.RESULT_WRONG);
					structure.getMessage().appendErrorMsg(calculateModel.getNo(), "没有匹配到有效的社保类型。");
					continue;
				}


				SoinSupplierEntity supplierEntity = structure.getSoinSupplierEntityMap().get(soinDistrict.getId());
				if (supplierEntity == null) {
					calculateModel.setStatus(SoinConstants.RESULT_WRONG);
					supplier.setStatus(SoinConstants.RESULT_WRONG);
					//没供应商也计算
				}

				AryaSoinPersonEntity soinPersonEntity = structure.getSoinPersonStructureMap().get(calculateModel.getSoinPerson().getIdcardNo().getContent().toUpperCase()).getPersonEntity();
				//开始计算
				if (calculateModel.getType() != SOIN_BACK_CALCULATE) {
					//计算正常缴纳的订单
					SoinTypeVersionEntity soinTypeVersionEntity = structure.getSoinTypeVersionEntityMap().get(soinTypeEntity.getId());
					if (soinTypeVersionEntity == null) {
						calculateModel.setStatus(SoinConstants.RESULT_WRONG);
						structure.getMessage().appendErrorMsg(calculateModel.getNo(), "没有匹配到有效的社保类型版本。");
						continue;
					}
					SoinTypeCalculateModel soinTypeCalculateModel = soinRuleService.soinCalculate(new SoinCalculateParams(
							soinTypeEntity.getHouseFundMust(), soinTypeVersionEntity,
							calculateModel.getSoinType().getCollectionFee().getContent(),
							calculateModel.getSupplier().getFee().getContent(), SoinUtil.ZERO,
							calculateModel.getSoinType().getSoinBase().getContent(),
							calculateModel.getSoinType().getHousefundBase().getContent(),
							calculateModel.getSoinType().getPersonPercent(),
							calculateModel.getSoinType().getCorpPercent(),
							soinPersonEntity.getLastPayedYear(),
							soinPersonEntity.getLastPayedMonth(),
							calculateModel.getPayYear(),
							calculateModel.getPayMonth(),
							new BigDecimal("1"),
							calculateModel.getBackYear(),
							calculateModel.getBackMonth(),
							new BigDecimal(calculateModel.getBackCount()),
							calculateModel.getType()));
					calculateModel.setNormalCalculateModel(soinTypeCalculateModel);
					calculateModel.setTotalIn(soinTypeCalculateModel.getTotalPaymentWithFeeIn());
					calculateModel.setTotalOut(soinTypeCalculateModel.getTotalPaymentWithFeeOut());
				}
				if (calculateModel.getType() != SOIN_NORMAL_CALCULATE) {
					//计算补缴的订单
					SoinTypeVersionEntity backSoinTypeVersionEntity = structure.getSoinBackTypeVersionEntityMap().get(soinTypeEntity.getId());
					if (backSoinTypeVersionEntity == null) {
						calculateModel.setStatus(SoinConstants.RESULT_WRONG);
						structure.getMessage().appendErrorMsg(calculateModel.getNo(), "没有匹配到有效的补缴版本");
						continue;
					}
					SoinTypeCalculateModel soinTypeCalculateModel = soinRuleService.soinCalculate(new SoinCalculateParams(soinTypeEntity.getHouseFundMust(), backSoinTypeVersionEntity, calculateModel.getSoinType().getCollectionFee().getContent(), calculateModel.getSupplier().getFee().getContent(), SoinUtil.ZERO, calculateModel.getSoinType().getSoinBase().getContent(), calculateModel.getSoinType().getHousefundBase().getContent(), calculateModel.getSoinType().getBackPersonPercent(), calculateModel.getSoinType().getBackCorpPercent(), soinPersonEntity.getLastPayedYear(), soinPersonEntity.getLastPayedMonth(), calculateModel.getPayYear(), calculateModel.getPayMonth(), new BigDecimal("1"), calculateModel.getBackYear(), calculateModel.getBackMonth(), new BigDecimal(calculateModel.getBackCount()), calculateModel.getType()));
					calculateModel.setBackCalculateModel(soinTypeCalculateModel);
					calculateModel.setTotalIn(calculateModel.getTotalIn().add(soinTypeCalculateModel.getTotalPaymentWithFeeIn()));
					calculateModel.setTotalOut(calculateModel.getTotalOut().add(soinTypeCalculateModel.getTotalPaymentWithFeeOut()));
				}
			} catch (Exception e) {
				elog.error(e.getMessage(), e);
				structure.getMessage().appendErrorMsg(calculateModel.getNo(), "订单计算出错，检查基础数据配置和导入数据格式。");
				calculateModel.setStatus(SoinConstants.RESULT_WRONG);
			}
		}

	}

	@Override
	public void checkDuplicateSoinOrderBillInFile(SoinOrderBillCalculateStructure structure) throws AryaServiceException {

		try {
			for (SoinOrderBillCalculateModel calculateModel : structure.getCalculateModels()) {
				if (calculateModel.getStatus() == SoinConstants.RESULT_WRONG) {
					continue;
				}
				if (calculateModel.getStatus() == SoinConstants.RESULT_DUPLICATE) {
					continue;
				}
				for (SoinOrderBillCalculateModel compareModel : structure.getCalculateModels()) {
					if (compareModel.getStatus() == SoinConstants.RESULT_WRONG) {
						continue;
					}
					if (compareModel.getStatus() == SoinConstants.RESULT_DUPLICATE) {
						continue;
					}
					if (calculateModel.getSoinPerson().getId().equals(compareModel.getSoinPerson().getId()) && !calculateModel.getTempId().equals(compareModel.getTempId())) {
						if (calculateModel.getPayYear() == compareModel.getPayYear() && calculateModel.getPayMonth() == compareModel.getPayMonth()) {
							calculateModel.setStatus(SoinConstants.RESULT_DUPLICATE);
							compareModel.setStatus(SoinConstants.RESULT_DUPLICATE);
							structure.getMessage().appendErrorMsg(calculateModel.getNo(), "在文件中存在重复订单,缴纳年月为:" + calculateModel.getPayYearMonth().getContent() + "。");
						}
					}
				}
			}
		} catch (Exception e) {
			elog.error(e.getMessage(), e);
			throw new AryaServiceException(ErrorCode.CODE_ORDER_BILL_DUMPLICATE_CHECK_IN_FILE_FAILED);
		}
	}

	@Override
	public void checkSoinOrderBillExistInDB(SoinOrderBillCalculateStructure structure) throws AryaServiceException {
		try {
			for (SoinOrderBillCalculateModel calculateModel : structure.getCalculateModels()) {
				if (calculateModel.getStatus() == SoinConstants.RESULT_WRONG) {
					continue;
				}
				if (calculateModel.getStatus() == SoinConstants.RESULT_DUPLICATE) {
					continue;
				}
				if (calculateModel.getSoinPerson().getStatus() == SoinConstants.RESULT_NEW) {
					continue;
				}
				if (calculateModel.getOrderOperate() == null) {
					calculateModel.setOrderOperate(ORDER_KEEP);//如果为null则置为keep
				}
				if (!calculateModel.getOrderOperate().equals(ORDER_INCREATE)) {
					//如果对订单不是保存操作的都略过。（减员业务导致的）
					if (calculateModel.getOrderOperate().equals(ORDER_DELETE)) {
						//在DB中查是否存在相同年月的订单，有的话删掉
						AryaSoinOrderEntity orderEntity = soinOrderDao.findByPersonIdAndPayYearMonth(calculateModel.getSoinPerson().getId(), calculateModel.getPayYear(), calculateModel.getPayMonth());
						if (orderEntity != null) {
							orderEntity.setDelete(true);
							structure.getNeedDeleteOrders().add(orderEntity);
							structure.getNeedDeleteOrderIds().add(orderEntity.getId());
						}
					}
					continue;
				}
				//在DB中查
				AryaSoinOrderEntity orderEntity = soinOrderDao.findByPersonIdAndServiceYearMonth(calculateModel.getSoinPerson().getId(), Integer.parseInt(calculateModel.getServiceYearMonth().getContent()));
				if (orderEntity == null) {
					structure.getMessage().setSaveRows(structure.getMessage().getSaveRows() + 1);
					continue;
				}
				calculateModel.setStatus(SoinConstants.RESULT_DUPLICATE);
				structure.getMessage().appendWarnMsg(calculateModel.getNo(), "参保人已经在数据库中有" + calculateModel.getServiceYearMonth().getContent() + "服务年月的社保订单了,系统自动忽略该条。");
			}
		} catch (Exception e) {
			elog.error(e.getMessage(), e);
			throw new AryaServiceException(ErrorCode.CODE_ORDER_BILL_DUMPLICATE_CHECK_IN_DB_FAILED);
		}
	}

	@Override
	public SoinOrderBillCountModel soinOrderBillCount(SoinOrderBillCalculateStructure structure) throws AryaServiceException {
		SoinOrderBillCountModel countModel = new SoinOrderBillCountModel();
		countModel.setTotalCount(structure.getCalculateModels().size());
		for (SoinOrderBillCalculateModel calculateModel : structure.getCalculateModels()) {
			if (calculateModel.getStatus() == SoinConstants.RESULT_WRONG) {
				countModel.setWrongCount(countModel.getWrongCount() + 1);
			}
			if (calculateModel.getStatus() == SoinConstants.RESULT_DUPLICATE) {
				countModel.setDumplicateCount(countModel.getDumplicateCount() + 1);
			}
		}
		return countModel;
	}

	@Override
	public String soinOrderBillImportConfirm(SoinOrderBillImportConfirmCommand command) throws AryaServiceException {
		SysUserModel sysUserModel = sysUserService.getCurrentSysUser();
		SoinImportBatchEntity batchEntity = batchDao.findSoinImportBatch(sysUserModel.getId(), command.getBatch());
		if (batchEntity != null) {
			throw new AryaServiceException(ErrorCode.CODE_ORDER_BILL_BATCH_EXIST);
		}
		SoinOrderBillCalculateStructure structure = readFileAndCalculateOrderBill(command.getFileName());
		SoinOrderBillCountModel countModel = soinOrderBillCount(structure);

		if (countModel.getWrongCount() > 0) {
			throw new AryaServiceException(ErrorCode.CODE_ORDER_BILL_ERROR_NOT_FIXED_YET);
		}

		if (structure.getCalculateModels().size() > 9999) {
			throw new AryaServiceException(ErrorCode.CODE_ORDER_BILL_IMPORT_TOO_MANY);
		}

		batchEntity = new SoinImportBatchEntity();
		batchEntity.setId(command.getBatchId());
		batchEntity.setBatchNo(command.getBatch());
		batchEntity.setImportFileName(command.getFileName());
		batchEntity.setOperatorId(sysUserModel.getId());
		batchEntity.setCreateTime(System.currentTimeMillis());

		List<AryaSoinOrderEntity> soinOrderEntities = new ArrayList<>();
		List<AryaSoinEntity> soinEntities = new ArrayList<>();
		int decreaseSaveOrderCount = 0;
		int no = 0;
		for (SoinOrderBillCalculateModel calculateModel : structure.getCalculateModels()) {
			if (calculateModel.getStatus() == SoinConstants.RESULT_DUPLICATE) {
				//如果是跟DB中订单重复，则continue
				continue;
			}
			if (!calculateModel.getOrderOperate().equals(ORDER_INCREATE) && !calculateModel.getOrderOperate().equals(ORDER_INCREASE_UPDATE) && !calculateModel.getOrderOperate().equals(ORDER_DECREASE_UPDATE)) {
				//如果对订单操作不是“向数据库新增订单”或者“修改订单”则跳过
				continue;
			}

			if (calculateModel.getOrderOperate().equals(ORDER_INCREASE_UPDATE)) {
				//如果是增员修改，将订单增减员状态改成增员，并保存增员记录。
			} else if (calculateModel.getOrderOperate().equals(ORDER_DECREASE_UPDATE)) {
				//如果是减员修改
				if (isNotBlank(calculateModel.getNeedUpdateOrderId())) {
					//如果存在当月减员的订单则修改
				}
				decreaseSaveOrderCount++;
			}

			AryaSoinPersonEntity soinPersonEntity = structure.getSoinPersonStructureMap().get(calculateModel.getSoinPerson().getIdcardNo().getContent().toUpperCase()).getPersonEntity();
			if (calculateModel.getOrderOperate().equals(ORDER_INCREATE)) {
				soinPersonEntity.setOrderCount(soinPersonEntity.getOrderCount() + 1);//参保人订单数累加


				AryaSoinOrderEntity soinOrderEntity = new AryaSoinOrderEntity();
				soinOrderEntity.setId(calculateModel.getOrderId());
				soinOrderEntity.setCreateTime(System.currentTimeMillis());
				soinOrderEntity.setModifyType(calculateModel.getModifyType());//增减员类型
				soinOrderEntity.setBatchId(command.getBatch());
				soinOrderEntity.setCalculateType(calculateModel.getType());//计算类型
				soinOrderEntity.setDelete(false);
				soinOrderEntity.setSubject(calculateModel.getSubjectType());
				soinOrderEntity.setBatchId(batchEntity.getId());
				soinOrderEntity.setOperatorId(sysUserModel.getId());
				String[] soinDistrictNames = calculateModel.getSoinDistrict().getNames().split("-");
				soinOrderEntity.setDistrict(soinDistrictNames[soinDistrictNames.length - 1]);
				soinOrderEntity.setDistrictId(calculateModel.getSoinDistrict().getId());
				soinOrderEntity.setOrderNo(generateOrderNo(no++));
				soinOrderEntity.setOrigin(ORDER_ORIGIN_IMPORT);
				//默认已支付
				soinOrderEntity.setStatusCode(SoinOrderStatus.ORDER_PAYED);
				String serviceYearMonth = String.format("%d%s", calculateModel.getServiceYear(), calculateModel.getServiceMonth() > 9 ? calculateModel.getServiceMonth() : ("0" + calculateModel.getServiceMonth()));
				soinOrderEntity.setServiceYearMonth(Integer.parseInt(serviceYearMonth));

				if (calculateModel.getTemplateType().equals("personal")) {
					String postponeYearMonth = "";
					int postponeMonthAdd = Integer.parseInt(calculateModel.getPostponeMonth().getContent()) + calculateModel.getServiceMonth();
					if (postponeMonthAdd > 12) {
						postponeYearMonth = String.format("%d%s", calculateModel.getServiceYear() + 1, postponeMonthAdd - 12 > 9 ? postponeMonthAdd - 12 : ("0" + (postponeMonthAdd - 12)));
					} else {
						postponeYearMonth = String.format("%d%s", calculateModel.getServiceYear(), postponeMonthAdd > 9 ? postponeMonthAdd : ("0" + postponeMonthAdd));
					}
					soinOrderEntity.setPostponeMonth(Integer.parseInt(postponeYearMonth));
				}

				if (calculateModel.getType() != SOIN_BACK_CALCULATE) {
					soinOrderEntity.setYear(calculateModel.getPayYear());
					soinOrderEntity.setStartMonth(calculateModel.getPayMonth());
					soinOrderEntity.setCount(1);
				}
				if (calculateModel.getType() != SOIN_NORMAL_CALCULATE) {
					//补缴
					soinOrderEntity.setBackYear(calculateModel.getBackYear());
					soinOrderEntity.setBackStartMonth(calculateModel.getBackMonth());
					soinOrderEntity.setBackCount(calculateModel.getBackCount());
				}
				//社保类型
				{
					soinOrderEntity.setSoinTypeId(calculateModel.getSoinType().getId());
					soinOrderEntity.setSoinType(calculateModel.getSoinType().getName());
				}
				//参保人
				{
					soinOrderEntity.setSoinPersonId(calculateModel.getSoinPerson().getId());
					soinOrderEntity.setSoinPersonName(calculateModel.getSoinPerson().getName().getContent());
				}
				//供应商
				{
					SoinSupplierEntity supplierEntity = structure.getSoinSupplierEntityMap().get(calculateModel.getSoinDistrict().getId());
					soinOrderEntity.setSupplierId(supplierEntity.getId());
				}

				//业务员
				{
					soinOrderEntity.setSalesmanId(calculateModel.getSalesman().getId());
					soinOrderEntity.setSalesmanName(calculateModel.getSalesman().getName());
				}//生成订单
				userSoinService.assignSoinOrderMoneyFiled(soinOrderEntity, calculateModel.getNormalCalculateModel(), calculateModel.getBackCalculateModel());

				// 设置服务费
				soinOrderEntity.setFees(calculateModel.getNormalCalculateModel().getFeeInTotal());
				soinOrderEntity.setFeeOut(calculateModel.getNormalCalculateModel().getFeeOutTotal());
				//拆分社保每月缴纳明细
				try {
					SoinTypeEntity soinTypeEntity = structure.getSoinTypeEntityMap().get(calculateModel.getSoinDistrict().getId() + calculateModel.getSoinType().getName());
					SoinTypeVersionEntity soinTypeVersionEntity = structure.getSoinTypeVersionEntityMap().get(soinTypeEntity.getId());
					SoinTypeVersionEntity backSoinTypeVersionEntity = structure.getSoinBackTypeVersionEntityMap().get(soinTypeEntity.getId());
					CreateOrderCommand createOrderCommand = new CreateOrderCommand();
					createOrderCommand.setYear(calculateModel.getPayYear());
					createOrderCommand.setStartMonth(calculateModel.getPayMonth());
					createOrderCommand.setCount(1);
					createOrderCommand.setBase(SoinUtil.turnBigDecimalRoundUpToString(calculateModel.getSoinType().getSoinBase().getContent(), 2));
					createOrderCommand.setHouseFundBase(calculateModel.getSoinType().getHousefundBase().getContent() == null ? null : SoinUtil.turnBigDecimalRoundUpToString(calculateModel.getSoinType().getHousefundBase().getContent(), 2));
					createOrderCommand.setDistrictId(calculateModel.getSoinDistrict().getIds());
					//补缴
					createOrderCommand.setBackYear(calculateModel.getBackYear());
					createOrderCommand.setBackStartMonth(calculateModel.getBackMonth());
					createOrderCommand.setBackCount(calculateModel.getBackCount());
					BigDecimal houseFundPersonPercent = null;
					BigDecimal houseFundCorpPercent = null;
					BigDecimal backHouseFundPersonPercent = null;
					BigDecimal backHouseFundCorpPercent = null;
					if (calculateModel.getType() != SOIN_BACK_CALCULATE && calculateModel.getNormalCalculateModel().getHousefund() != null) {
						houseFundPersonPercent = calculateModel.getNormalCalculateModel().getHousefund().getPersonPercentage();
						houseFundCorpPercent = calculateModel.getNormalCalculateModel().getHousefund().getCorpPercentage();
					}
					if (calculateModel.getType() != SOIN_NORMAL_CALCULATE && calculateModel.getBackCalculateModel().getHousefund() != null) {
						backHouseFundPersonPercent = calculateModel.getBackCalculateModel().getHousefund().getPersonPercentage();
						backHouseFundCorpPercent = calculateModel.getBackCalculateModel().getHousefund().getCorpPercentage();
					}
					soinEntities.addAll(userSoinService.generateSoinDetail(soinTypeEntity, soinPersonEntity, createOrderCommand, soinOrderEntity, soinTypeVersionEntity, backSoinTypeVersionEntity, houseFundPersonPercent, houseFundCorpPercent, backHouseFundPersonPercent, backHouseFundCorpPercent, calculateModel.getType()));
					for (AryaSoinEntity soinEntity : soinEntities) {
						//设置公积金编号
						soinEntity.setSoinCode(calculateModel.getSoinCode());
						soinEntity.setHouseFundCode(calculateModel.getHouseFundCode());
						soinEntity.setModifyType(calculateModel.getModifyType());
						//默认已支付 默认缴纳成功
						soinEntity.setStatusCode(SOIN_PAY_SUCCESS);
					}
				} catch (AryaServiceException e) {
					elog.error(e.getMessage(), e);
					throw new AryaServiceException(ErrorCode.CODE_SOIN_SPLIT_DETAIL_FAILED);// 拆分社保缴纳明细失败
				}
				if (calculateModel.getSubjectType() == COPR_CUSTOMER) {
					CorporationEntity corporationEntity = structure.getCorporationStructureMap().get(calculateModel.getCorp().getName()).getCorporationEntity();
					soinOrderEntity.setCorpId(corporationEntity.getId());
				}

				soinOrderEntities.add(soinOrderEntity);
			}
		}


		List<AryaSoinPersonEntity> newSoinPersonEntities = new ArrayList<>();
		List<CorporationEntity> newCorporationEntities = new ArrayList<>();
		for (String personIdcardNo : structure.getSoinPersonStructureMap().keySet()) {
			SoinOrderBillCalculateStructure.SoinPerson soinPersonStructure = structure.getSoinPersonStructureMap().get(personIdcardNo);
			if (soinPersonStructure.getStatus() == SoinConstants.RESULT_NEW) {
				newSoinPersonEntities.add(soinPersonStructure.getPersonEntity());
			}
		}
		for (String corpName : structure.getCorporationStructureMap().keySet()) {
			SoinOrderBillCalculateStructure.Corp corp = structure.getCorporationStructureMap().get(corpName);
			if (corp.getStatus() == SoinConstants.RESULT_NEW) {
				newCorporationEntities.add(corp.getCorporationEntity());
			}
		}

		StringBuffer logMsg = new StringBuffer("【对账单导入】");

		try {
			//存DB
//			if (soinOrderEntities.size() == 0) {
//
//			}
			//新增参保人
			if (newSoinPersonEntities.size() > 0) {
				soinPersonDao.createOrUpdate(newSoinPersonEntities);
				String msg = "新增" + newSoinPersonEntities.size() + "个参保人。/n";
				logMsg.append(msg);
				structure.getMessage().appendNormalMsg(-1, msg);
			}
			//新增企业
			if (newCorporationEntities.size() > 0) {
				corporationDao.createOrUpdate(newCorporationEntities);
				String msg = "新增" + newCorporationEntities.size() + "个企业。/n";
				logMsg.append(msg);
				structure.getMessage().appendNormalMsg(-1, msg);
			}
			//社保订单
			if (soinOrderEntities.size() > 0) {
				//新增批次
				batchDao.create(batchEntity);
				// 订单
				soinOrderDao.create(soinOrderEntities);
				String msg = "/n保存" + soinOrderEntities.size() + "个社保订单。";
				logMsg.append(msg);
				structure.getMessage().appendNormalMsg(-1, msg);
				int normalCount = 0;
				int backCount = 0;
				int mixCount = 0;
				for (AryaSoinOrderEntity orderEntity : soinOrderEntities) {
					switch (orderEntity.getCalculateType()) {
						case SOIN_NORMAL_CALCULATE: {
							normalCount++;
							break;
						}
						case SOIN_BACK_CALCULATE: {
							backCount++;
							break;
						}
						case SOIN_MIX_CALCULATE: {
							mixCount++;
							break;
						}
					}
				}
				String msg2 = "其中包含" + normalCount + "个纯正常缴纳订单，" + backCount + "个纯补缴订单，" + mixCount + "个混合订单。/n";
				logMsg.append(msg2);
				structure.getMessage().appendNormalMsg(-1, msg2);
			}
			//减员操作需要删除的订单
			if (!structure.getNeedDeleteOrderIds().isEmpty()) {
				List<AryaSoinOrderEntity> orderEntities = soinOrderDao.findOrderByIds(structure.getNeedDeleteOrderIds());
				soinOrderDao.update(orderEntities);
				soinDao.deleteSoinsByOrderIds(structure.getNeedDeleteOrderIds());
				String msg = "删除" + structure.getNeedDeleteOrderIds().size() + "个社保订单。/n";
				logMsg.append(msg);
				structure.getMessage().appendNormalMsg(-1, msg);
			}

			if (!structure.getNeedDeleteModifyIds().isEmpty()) {
				soinInOrDecreaseDao.deleteByIds(structure.getNeedDeleteModifyIds());
			}

			//修改订单为增员
			if (!structure.getNeedIncreaseUpdateOrderIds().isEmpty()) {
				List<AryaSoinOrderEntity> needUpdateOrders = soinOrderDao.findOrderByIds(structure.getNeedIncreaseUpdateOrderIds());
				for (AryaSoinOrderEntity orderEntity : needUpdateOrders) {
					orderEntity.setModifyType(getCode(SOIN_PERSON_INCREASE));
				}
				for (AryaSoinEntity soinEntity : soinDao.findSoinsByOrderIds(structure.getNeedIncreaseUpdateOrderIds())) {
					soinEntity.setModifyType(getCode(SOIN_PERSON_INCREASE));
				}
				soinOrderDao.update(needUpdateOrders);
				String msg = "修改" + structure.getNeedIncreaseUpdateOrderIds().size() + "个订单为增员。/n";
				logMsg.append(msg);
				structure.getMessage().appendNormalMsg(-1, msg);
			}

			//修改订单为减员
			if (!structure.getNeedDecreaseUpdateOrderIds().isEmpty()) {
				List<AryaSoinOrderEntity> needUpdateOrders = soinOrderDao.findOrderByIds(structure.getNeedDecreaseUpdateOrderIds());
				for (AryaSoinOrderEntity orderEntity : needUpdateOrders) {
					orderEntity.setModifyType(getCode(SOIN_PERSON_DECREASE));
				}
				for (AryaSoinEntity soinEntity : soinDao.findSoinsByOrderIds(structure.getNeedDecreaseUpdateOrderIds())) {
					soinEntity.setModifyType(getCode(SOIN_PERSON_DECREASE));
				}
				soinOrderDao.update(needUpdateOrders);
				String msg = "修改" + structure.getNeedDecreaseUpdateOrderIds().size() + "个订单为减员。/n";
				logMsg.append(msg);
				structure.getMessage().appendNormalMsg(-1, msg);
			}

			//社保订单缴纳明细
			if (soinEntities.size() > 0) {
				soinDao.create(soinEntities);
				String msg = "/n新增" + soinEntities.size() + "个社保订单详情。";
				logMsg.append(msg);
				structure.getMessage().appendNormalMsg(-1, msg);
				int normalCount = 0;
				int backCount = 0;
				for (AryaSoinEntity soinEntity : soinEntities) {
					switch (soinEntity.getCalculateType()) {
						case SOIN_NORMAL_CALCULATE: {
							normalCount++;
							break;
						}
						case SOIN_BACK_CALCULATE: {
							backCount++;
							break;
						}
					}
				}
				String msg2 = "其中包含" + normalCount + "个正常缴纳详情，" + backCount + "个补缴详情。/n";
				logMsg.append(msg2);
				structure.getMessage().appendNormalMsg(-1, msg2);
			}
			//增员记录
			if (!structure.getSoinInEntityMap().isEmpty()) {
				soinInOrDecreaseDao.create(structure.getSoinInEntityMap().values());
				String msg = "增员" + structure.getSoinInEntityMap().size() + "个参保人。/n";
				logMsg.append(msg);
				structure.getMessage().appendNormalMsg(-1, msg);
			}

			//减员记录
			if (!structure.getSoinDecreaseEntityMap().isEmpty()) {
				soinInOrDecreaseDao.create(structure.getSoinDecreaseEntityMap().values());
				String msg = "减员" + structure.getSoinDecreaseEntityMap().size() + "个参保人。/n";
				logMsg.append(msg);
				structure.getMessage().appendNormalMsg(-1, msg);
			}

			//顺延记录
			if (!structure.getSoinExtendEntityMap().isEmpty()) {
				soinInOrDecreaseDao.create(structure.getSoinExtendEntityMap().values());
				String msg = "顺延" + structure.getSoinExtendEntityMap().size() + "个订单。/n";
				logMsg.append(msg);
				structure.getMessage().appendNormalMsg(-1, msg);
			}
			opLogService.successLog(SOIN_ORDER_IMPORT, logMsg, log);
			return structure.getBillImportMsg() + " 保存订单条数：" + soinOrderEntities.size() + "其中有" + decreaseSaveOrderCount + "条是减员订单" + " 删除订单条数：" + structure.getNeedDeleteOrders().size() + " 增员数：" + structure.getSoinInEntityMap().size() + " 减员数：" + structure.getSoinDecreaseEntityMap().size() + " 不增不减数：" + structure.getSoinExtendEntityMap().size();
		} catch (Exception e) {
			elog.error(e.getMessage(), e);
			opLogService.successLog(SOIN_ORDER_IMPORT, logMsg, log);
			throw new AryaServiceException(ErrorCode.CODE_ORDER_BILL_IMPORT_FAILED);
		}
	}


	@Override
	public SoinOrderCustomerListResult getAllSoinOrderCustomers() throws AryaServiceException {
		SoinOrderCustomerListResult listResult = new SoinOrderCustomerListResult();
		List<SoinOrderCustomerListResult.SoinOrderCustomerResult> results = new ArrayList<>();
		listResult.setCustomer(results);
//		//App
//		SoinOrderCustomerListResult.SoinOrderCustomerResult appCustomerResult = new SoinOrderCustomerListResult.SoinOrderCustomerResult();
//		appCustomerResult.setId(Integer.toString(APP_CUSTOMER));
//		appCustomerResult.setName(APP_CUSTOMER_STR);
//		results.add(appCustomerResult);
		//个人
		SoinOrderCustomerListResult.SoinOrderCustomerResult personCustomerResult = new SoinOrderCustomerListResult.SoinOrderCustomerResult();
		personCustomerResult.setId(Integer.toString(PERSON_CUSTOMER));
		personCustomerResult.setName(PERSON_CUSTOMER_STR);
		results.add(personCustomerResult);
		//查出所有开通社保业务的公司
		List<CorporationEntity> soinCorporationEntities = corporationDao.findCorpsByBusinessType(CORP_BUSINESS_SOIN);
		for (CorporationEntity corporationEntity : soinCorporationEntities) {
			SoinOrderCustomerListResult.SoinOrderCustomerResult corpCustomerResult = new SoinOrderCustomerListResult.SoinOrderCustomerResult();
			corpCustomerResult.setId(corporationEntity.getId());
			corpCustomerResult.setName(corporationEntity.getName());
			results.add(corpCustomerResult);
		}

		return listResult;
	}

	@Override
	public void deleteSoinOrders(IdListCommand deleteListCommand) throws AryaServiceException {
		//id不是订单的id,是订单每月缴纳详情的id
		if (deleteListCommand.getIds() == null || deleteListCommand.getIds().size() == 0) {
			return;
		}
		SysUserModel currentUser = sysUserService.getCurrentSysUser();
		List<String> deleteOrderIds = new ArrayList<>();
		List<AryaSoinOrderEntity> deleteOrderEntities = new ArrayList<>();
		List<AryaSoinOrderEntity> updateOrderEntities = new ArrayList<>();
		for (IdListCommand.IdCommand deleteCommand : deleteListCommand.getIds()) {
			AryaSoinEntity soinEntity = soinDao.findSoinById(deleteCommand.getId());
			if (soinEntity == null) {
				continue;
			}
			AryaSoinOrderEntity orderEntity = soinOrderDao.findOrderById(soinEntity.getOrderId());
			if (orderEntity == null) {
				continue;
			}
			//如果是业务员角色,则特殊处理。判断该批次数据是否是该业务员的,如果是则可以操作
			if (systemRoleService.isSalesmanRole(currentUser.getId())) {
				if (!currentUser.getId().equals(orderEntity.getSalesmanId()))
					throw new AryaServiceException(ErrorCode.CODE_ORDER_BILL_DELETE_NO_AUTH);
			}
			if (orderEntity.getOrigin() != null && orderEntity.getOrigin() == ORDER_ORIGIN_IMPORT) {
				//如果是线下订单,则删除
				deleteOrderIds.add(orderEntity.getId());
				orderEntity.setDelete(true);
				deleteOrderEntities.add(orderEntity);
			} else {
				//线上订单则将状态置为订单异常
				if (orderEntity.getActualPayment().compareTo(BigDecimal.ZERO) > 0) {
					//支付后的订单置为异常
					orderEntity.setStatusCode(SoinOrderStatus.ORDER_ABNORMAL);
				} else {
					//还未支付的置为取消
					orderEntity.setStatusCode(SoinOrderStatus.ORDER_CANCELED);
				}
				updateOrderEntities.add(orderEntity);
			}
		}

		try {
			if (deleteOrderIds.size() > 0) {
				soinOrderDao.update(deleteOrderEntities);
				soinDao.deleteSoinsByOrderIds(deleteOrderIds);
			}
			if (updateOrderEntities.size() > 0) {
				soinOrderDao.update(updateOrderEntities);
			}

		} catch (Exception e) {
			elog.error(e.getMessage(), e);
			throw new AryaServiceException(ErrorCode.CODE_ORDER_BILL_DELETE_FAILED);
		}

	}

	@Override
	public void paySuccessSoinOrders(IdListCommand command) throws AryaServiceException {
		//id不是订单的id,是订单每月缴纳详情的id
		if (command.getIds() == null || command.getIds().size() == 0) {
			return;
		}
		List<String> soinIds = new ArrayList<>();
		for (IdListCommand.IdCommand deleteCommand : command.getIds()) {
			soinIds.add(deleteCommand.getId());
		}
		if (soinIds.size() == 0) {
			return;
		}

		try {
			soinDao.paySucceedSoinsBySoinIds(soinIds);
		} catch (Exception e) {
			elog.error(e.getMessage(), e);
			throw new AryaServiceException(ErrorCode.CODE_ORDER_BILL_PAY_SUCCESS_FAILED);
		}
	}

	@Override
	public void paySuccessAllSoinOrders(OrderBillListQueryCommand command) throws AryaServiceException {
		List<AryaSoinOrderEntity> orderEntities = queryAllOrders(command);
		List<AryaSoinEntity> needUpdateSoinEntities = new ArrayList<>();
		for (AryaSoinOrderEntity orderEntity : orderEntities) {
			for (AryaSoinEntity soinEntity : orderEntity.getDetails()) {
				if (!checkSoinIsInCommand(soinEntity, command)) {
					continue;
				}
				soinEntity.setStatusCode(SOIN_PAY_SUCCESS);
				needUpdateSoinEntities.add(soinEntity);
			}
		}
		try {
			soinDao.update(needUpdateSoinEntities);
		} catch (Exception e) {
			elog.error(e.getMessage(), e);
			throw new AryaServiceException(ErrorCode.CODE_ORDER_BILL_ALL_PAY_SUCCESS_FAILED);
		}
	}

	@Override
	public void payFailedSoinOrders(SoinOrderBillPayFailedCommand command) throws AryaServiceException {
		//id不是订单的id,是订单每月缴纳详情的id
		if (command.getIds() == null || command.getIds().size() == 0) {
			return;
		}

		if (isNotBlank(command.getReason()) && command.getReason().length() > 1024) {
			throw new AryaServiceException(ErrorCode.CODE_ORDER_BILL_PAY_FAILED_REASON_TOO_LONG);
		}
		List<String> soinIds = new ArrayList<>();
		for (IdListCommand.IdCommand deleteCommand : command.getIds()) {
			soinIds.add(deleteCommand.getId());
		}
		if (soinIds.size() == 0) {
			return;
		}

		try {
			soinDao.payFailedSoinsBySoinIds(soinIds, command.getReason());
		} catch (Exception e) {
			elog.error(e.getMessage(), e);
			throw new AryaServiceException(ErrorCode.CODE_ORDER_BILL_PAY_FAILED_FAILED);
		}
	}

	@Override
	public void payFailedAllSoinOrders(OrderBillListQueryCommand command) throws AryaServiceException {
		List<AryaSoinOrderEntity> orderEntities = queryAllOrders(command);
		List<AryaSoinEntity> needUpdateSoinEntities = new ArrayList<>();
		for (AryaSoinOrderEntity orderEntity : orderEntities) {
			for (AryaSoinEntity soinEntity : orderEntity.getDetails()) {
				if (!checkSoinIsInCommand(soinEntity, command)) {
					continue;
				}
				soinEntity.setStatusCode(SOIN_PAY_FAILED);
				soinEntity.setMemo(command.getReason());
				needUpdateSoinEntities.add(soinEntity);
			}
		}
		try {
			soinDao.update(needUpdateSoinEntities);
		} catch (Exception e) {
			elog.error(e.getMessage(), e);
			throw new AryaServiceException(ErrorCode.CODE_ORDER_BILL_ALL_PAY_FAILED_FAILED);
		}
	}

	@Override
	public SoinOrderBillQueryListResult queryOrderBill(QueryOrderBillCommand command) throws AryaServiceException {
		return null;
	}

	@Override
	public HttpResponse<SoinSupplierListResult> suppliersList(String district_id) {
		HttpResponse httpResponse;
		SoinSupplierListResult result = new SoinSupplierListResult();
		List<SoinSupplierListResult.SoinSupplierResult> supplier = new ArrayList<>();
		try {
			List<SoinSupplierEntity> suppliers = new ArrayList<>();
			// 如果地区id为空，查询所有的供应商
			if (isAnyBlank(district_id) || district_id.equals(CHN_ID)) {
				suppliers.addAll(soinSupplierDao.findAllSoinSupplier());
			} else {
				// 如果有ID，先查询中间表
				List<SoinDistrictSupplierEntity> disSuppliers = soinDistrictSupplierDao.findDistrictAllSuppliers(district_id);
				List<String> ids = new ArrayList<>();
				for (SoinDistrictSupplierEntity disSupplier : disSuppliers) {
					if (disSupplier != null && isNotBlank(disSupplier.getDistrictId())) {
						ids.add(disSupplier.getSupplierId());
					}
				}
				//如果没有供应商直接返回空
				if (ids.size() == 0) {
					return new HttpResponse<>(result);
				}
				// 查询ids
				suppliers.addAll(soinSupplierDao.findSuppliersByIds(ids));
			}

			// 组装数据
			for (SoinSupplierEntity s : suppliers) {
				SoinSupplierListResult.SoinSupplierResult subSup = new SoinSupplierListResult.SoinSupplierResult();
				subSup.setId(s.getId());
				subSup.setName(s.getSupplierName());
				subSup.setFee(s.getSoinFee());
				supplier.add(subSup);
			}

			result.setSuppliers(supplier);
			httpResponse = new HttpResponse(result, ErrorCode.CODE_OK);

		} catch (AryaServiceException arya) {
			arya.printStackTrace();
			httpResponse = new HttpResponse(arya.getErrorCode());

		} catch (Exception e) {
			elog.error(e.getMessage(), e);
			httpResponse = new HttpResponse(ErrorCode.CODE_SYS_ERR);
		}
		return httpResponse;
	}

	@Override
	public SoinBillListResult billManageQuery(OrderBillListQueryCommand cmd) {
		SoinBillListResult result = new SoinBillListResult();
		List<SoinBillQueryResult> orderResults = new ArrayList<>();
		result.setOrders(orderResults);
		parseOrderBillQueryCommand(cmd);
		SysUserModel currentUser = sysUserService.getCurrentSysUser();
		log.info("【对账单查询】判断角色是否是销售。用户id:" + currentUser.getId() + "。");
		if (systemRoleService.isSalesmanRole(currentUser.getId())) {
			cmd.setSalesmanId(currentUser.getId());
			log.info("【对账单查询】用户角色是销售。");
		} else {
			log.info("【对账单查询】用户角色不是销售。");
		}

		// 解析地区
		parseDistrictIds(cmd.getDistrict_id(), cmd);

		// 查询订单
		Pager<AryaSoinOrderEntity> pager = soinOrderDao.billManageQueryPagination(cmd);
		result.setPages(Utils.calculatePages(pager.getRowCount(), pager.getPageSize()));
		log.info("【对账单查询】查询出" + pager.getResult().size() + "条订单。");
		List<AryaSoinOrderEntity> resultList = pager.getResult();
		Collection<String> cookedOrderIds = new ArrayList<>();
		Map<String, AryaSoinPersonEntity> personEntityMap = new HashMap<>();
		Map<String, SoinSupplierEntity> supplierEntityMap = new HashMap<>();
		Map<String, CorporationEntity> corporationEntityMap = new HashMap<>();
		for (AryaSoinOrderEntity orderEntity : resultList) {
			if (cookedOrderIds.contains(orderEntity.getId())) {
				continue;
			} else {
				cookedOrderIds.add(orderEntity.getId());
			}
			SoinBillQueryResult orderResult = new SoinBillQueryResult();
			orderResults.add(orderResult);
			orderResult.setId(orderEntity.getId());
			if (orderEntity.getModifyType() == null) {
				orderResult.setModify("-");
			} else {
				orderResult.setModify(NAME_LIST.get(orderEntity.getModifyType()));
			}
			orderResult.setDistrict(orderEntity.getDistrict());
			orderResult.setName(orderEntity.getSoinPersonName());
			orderResult.setSoinType(orderEntity.getSoinType());
			orderResult.setServiceYearMonth(orderEntity.getServiceYearMonth().toString());
			orderResult.setTotalIn(SoinUtil.turnBigDecimalHalfRoundUpToString(orderEntity.getPayment(), 3));
			orderResult.setTotalOut(SoinUtil.turnBigDecimalHalfRoundUpToString(orderEntity.getTotalOutPayment(), 3));
			orderResult.setSalesman(orderEntity.getSalesmanName());
			StringStatusResult payYearMonth = new StringStatusResult();
			if (orderEntity.getYear() == 0) {
				payYearMonth.setContent("-");
			} else {
				payYearMonth.setContent(orderEntity.getYear() + String.format("%02d", orderEntity.getStartMonth()));
			}
			orderResult.setPayYearMonth(payYearMonth);

			List<AryaSoinEntity> details = new ArrayList<>(orderEntity.getDetails());
			List<StringStatusResult> backMonthStatus = new ArrayList<>();
			orderResult.setBackMonth(backMonthStatus);

			//对账单详情排序
			Collections.sort(details, new Comparator<AryaSoinEntity>() {
				@Override
				public int compare(AryaSoinEntity o1, AryaSoinEntity o2) {
					return Integer.parseInt(o1.getYear() + String.format("%02d", o1.getMonth())) - Integer.parseInt(o2.getYear() + String.format("%02d", o2.getMonth()));
				}
			});

			//标记缴纳月份是否失败
			for (AryaSoinEntity soinEntity : details) {
				StringStatusResult stringStatusResult = new StringStatusResult();
				if (soinEntity.getCalculateType() != null && soinEntity.getCalculateType() == SOIN_BACK_CALCULATE) {
					//补缴的
					stringStatusResult.setContent(soinEntity.getMonth() + "月");
					backMonthStatus.add(stringStatusResult);
				} else {
					stringStatusResult = orderResult.getPayYearMonth();
				}

				if (soinEntity.getStatusCode() != null && soinEntity.getStatusCode() == SOIN_PAY_FAILED) {
					stringStatusResult.setStatus(SoinConstants.RESULT_WRONG);
				}
			}
			//缴纳主体
			if (orderEntity.getSubject() == PERSON_CUSTOMER) {
				orderResult.setSubject("个人");
			} else if (orderEntity.getSubject() == APP_CUSTOMER) {
				orderResult.setSubject("APP");
			} else {
				CorporationEntity corporationEntity = corporationEntityMap.get(orderEntity.getCorpId());
				if (corporationEntity != null) {
					orderResult.setSubject(corporationEntity.getName());
				} else {
					corporationEntity = corporationDao.find(orderEntity.getCorpId());
					if (corporationEntity != null) {
						corporationEntityMap.put(orderEntity.getCorpId(), corporationEntity);
						orderResult.setSubject(corporationEntity.getName());
					}
				}

			}

			// 参保人查询
			AryaSoinPersonEntity personEntity = personEntityMap.get(orderEntity.getSoinPersonId());
			if (personEntity == null) {
				personEntity = soinPersonDao.findSoinPersonById(orderEntity.getSoinPersonId());
				if (personEntity != null) {
					personEntityMap.put(personEntity.getId(), personEntity);
					orderResult.setIdcard(personEntity.getIdcardNo());
				}
			} else {
				orderResult.setIdcard(personEntity.getIdcardNo());
			}

			//供应商查询
			SoinSupplierEntity supplierEntity = supplierEntityMap.get(orderEntity.getSupplierId());
			if (supplierEntity == null) {
				supplierEntity = soinSupplierDao.find(orderEntity.getSupplierId());
				if (supplierEntity != null) {
					orderResult.setSupplier(supplierEntity.getSupplierName());
					supplierEntityMap.put(supplierEntity.getId(), supplierEntity);
				}
			} else {
				orderResult.setSupplier(supplierEntity.getSupplierName());
			}
		}
		return result;
	}

	@Override
	public SoinBillListResult billManageNewQuery(OrderBillListQueryCommand order) {
		return null;
	}

	@Override
	public SoinBillDetailList billManageDetailList(String id) {

		SoinBillDetailList listResult = new SoinBillDetailList();

		AryaSoinOrderEntity soinOrderEntity = soinOrderDao.billManageDetailList(id);

		if (soinOrderEntity == null) {
			return listResult;
		}

		List<AryaSoinEntity> soinEntities = new ArrayList<>(soinOrderEntity.getDetails());

		for (AryaSoinEntity soinEntity : soinEntities) {
			SimpleResult detailResult = new SimpleResult();
			detailResult.setId(soinEntity.getId());
			if (soinEntity.getCalculateType() == null) {
				detailResult.setName(soinEntity.getYear() + String.format("%02d", soinEntity.getMonth()));
			} else {
				detailResult.setName((soinEntity.getCalculateType() == SOIN_BACK_CALCULATE ? "补" : "") + soinEntity.getYear() + String.format("%02d", soinEntity.getMonth()));
			}
			listResult.getDetails().add(detailResult);
		}
		return listResult;
	}

	@Override
	public SoinBillDetailResult billManageDetail(String id) {
		SoinBillDetailResult detailResult = new SoinBillDetailResult();
		AryaSoinEntity soinEntity = soinDao.find(id);
		detailResult.setId(soinEntity.getId());
		detailResult.setOrderId(soinEntity.getOrderId());
		detailResult.setFees(SoinUtil.turnBigDecimalHalfRoundUpToString(soinEntity.getFees(), 3));
		detailResult.setReason(soinEntity.getMemo());
		detailResult.setTotalIn(SoinUtil.turnBigDecimalHalfRoundUpToString(soinEntity.getTotalPayment(), 3));
		detailResult.setYearMonth(soinEntity.getYear() + "" + soinEntity.getMonth());
		detailResult.setStatus(soinEntity.getStatusCode());
		//金额
		SoinBillDetailResult.RuleDetail money = new SoinBillDetailResult.RuleDetail();//金额
		money.setName("明细(元)");
		SoinBillDetailResult.RuleDetail base = new SoinBillDetailResult.RuleDetail();//基数
		base.setName("基数");
		SoinBillDetailResult.RuleDetail person = new SoinBillDetailResult.RuleDetail();//个人比例
		person.setName("个人比例(%)");
		SoinBillDetailResult.RuleDetail corp = new SoinBillDetailResult.RuleDetail();//企业比例
		corp.setName("企业比例(%)");
		SoinBillDetailResult.RuleDetail total = new SoinBillDetailResult.RuleDetail();//总比例
		total.setName("比例之和(%)");
		detailResult.getRuleDetails().add(money);
		detailResult.getRuleDetails().add(base);
		detailResult.getRuleDetails().add(person);
		detailResult.getRuleDetails().add(corp);
		detailResult.getRuleDetails().add(total);
		//金额
		{
			money.setInjury(SoinUtil.turnBigDecimalHalfRoundUpToString(soinEntity.getInjuryTotal(), 3));
			money.setMedical(SoinUtil.turnBigDecimalHalfRoundUpToString(soinEntity.getMedicalTotal(), 3));
			money.setPregnancy(SoinUtil.turnBigDecimalHalfRoundUpToString(soinEntity.getPregnancyTotal(), 3));
			money.setPension(SoinUtil.turnBigDecimalHalfRoundUpToString(soinEntity.getPensionTotal(), 3));
			money.setUnemployment(SoinUtil.turnBigDecimalHalfRoundUpToString(soinEntity.getUnemploymentTotal(), 3));
			money.setHouseFund(SoinUtil.turnBigDecimalHalfRoundUpToString(soinEntity.getHouseFundTotal(), 3));
			money.setHouseFundAddition(SoinUtil.turnBigDecimalHalfRoundUpToString(soinEntity.getHouseFundAdditionTotal(), 3));
			money.setDisable(SoinUtil.turnBigDecimalHalfRoundUpToString(soinEntity.getDisabilityTotal(), 3));
			money.setSevereIllness(SoinUtil.turnBigDecimalHalfRoundUpToString(soinEntity.getServerIllnessTotal(), 3));
			money.setInjuryAddition(SoinUtil.turnBigDecimalHalfRoundUpToString(soinEntity.getInjuryAdditionTotal(), 3));
			money.setHeating(SoinUtil.turnBigDecimalHalfRoundUpToString(soinEntity.getHeatingTotal(), 3));
		}

		//基数
		{
			base.setInjury(SoinUtil.turnBigDecimalHalfRoundUpToString(soinEntity.getCompanyInjuryBase(), 2));
			base.setMedical(SoinUtil.turnBigDecimalHalfRoundUpToString(soinEntity.getCompanyMedicalBase(), 2));
			base.setPregnancy(SoinUtil.turnBigDecimalHalfRoundUpToString(soinEntity.getCompanyPregnancyBase(), 2));
			base.setPension(SoinUtil.turnBigDecimalHalfRoundUpToString(soinEntity.getCompanyPensionBase(), 2));
			base.setUnemployment(SoinUtil.turnBigDecimalHalfRoundUpToString(soinEntity.getCompanyUnemploymentBase(), 2));
			base.setHouseFund(SoinUtil.turnBigDecimalHalfRoundUpToString(soinEntity.getCompanyHouseFundBase(), 2));
			base.setHouseFundAddition(SoinUtil.turnBigDecimalHalfRoundUpToString(soinEntity.getCompanyHouseFundAdditionBase(), 2));
			base.setDisable(SoinUtil.turnBigDecimalHalfRoundUpToString(soinEntity.getCompanyDisabilityBase(), 2));
			base.setSevereIllness(SoinUtil.turnBigDecimalHalfRoundUpToString(soinEntity.getCompanyServerIllnessBase(), 2));
			base.setInjuryAddition(SoinUtil.turnBigDecimalHalfRoundUpToString(soinEntity.getCompanyInjuryAdditionBase(), 2));
			base.setHeating(SoinUtil.turnBigDecimalHalfRoundUpToString(soinEntity.getCompanyHeatingBase(), 2));
		}

		//个人比例
		{
			person.setInjury(SoinUtil.turnBigDecimalHalfRoundUpToString(soinEntity.getPersonalInjuryPercentage(), 2));
			person.setMedical(SoinUtil.turnBigDecimalHalfRoundUpToString(soinEntity.getPersonalMedicalPercentage(), 2));
			person.setPregnancy(SoinUtil.turnBigDecimalHalfRoundUpToString(soinEntity.getPersonalPregnancyPercentage(), 2));
			person.setPension(SoinUtil.turnBigDecimalHalfRoundUpToString(soinEntity.getPersonalPensionPercentage(), 2));
			person.setUnemployment(SoinUtil.turnBigDecimalHalfRoundUpToString(soinEntity.getPersonalUnemploymentPercentage(), 2));
			person.setHouseFund(SoinUtil.turnBigDecimalHalfRoundUpToString(soinEntity.getPersonalHouseFundPercentage(), 2));
			person.setHouseFundAddition(SoinUtil.turnBigDecimalHalfRoundUpToString(soinEntity.getPersonalHouseFundAdditionPercentage(), 2));
			person.setDisable(SoinUtil.turnBigDecimalHalfRoundUpToString(soinEntity.getPersonalDisabilityPercentage(), 2));
			person.setSevereIllness(SoinUtil.turnBigDecimalHalfRoundUpToString(soinEntity.getPersonalServerIllnessPercentage(), 2));
			person.setInjuryAddition(SoinUtil.turnBigDecimalHalfRoundUpToString(soinEntity.getPersonalInjuryAdditionPercentage(), 2));
			person.setHeating(SoinUtil.turnBigDecimalHalfRoundUpToString(soinEntity.getPersonalHeatingPercentage(), 2));
		}
		//公司比例
		{
			corp.setInjury(SoinUtil.turnBigDecimalHalfRoundUpToString(soinEntity.getCompanyInjuryPercentage(), 2));
			corp.setMedical(SoinUtil.turnBigDecimalHalfRoundUpToString(soinEntity.getCompanyMedicalPercentage(), 2));
			corp.setPregnancy(SoinUtil.turnBigDecimalHalfRoundUpToString(soinEntity.getCompanyPregnancyPercentage(), 2));
			corp.setPension(SoinUtil.turnBigDecimalHalfRoundUpToString(soinEntity.getCompanyPensionPercentage(), 2));
			corp.setUnemployment(SoinUtil.turnBigDecimalHalfRoundUpToString(soinEntity.getCompanyUnemploymentPercentage(), 2));
			corp.setHouseFund(SoinUtil.turnBigDecimalHalfRoundUpToString(soinEntity.getCompanyHouseFundPercentage(), 2));
			corp.setHouseFundAddition(SoinUtil.turnBigDecimalHalfRoundUpToString(soinEntity.getCompanyHouseFundAdditionPercentage(), 2));
			corp.setDisable(SoinUtil.turnBigDecimalHalfRoundUpToString(soinEntity.getCompanyDisabilityPercentage(), 2));
			corp.setSevereIllness(SoinUtil.turnBigDecimalHalfRoundUpToString(soinEntity.getCompanyServerIllnessPercentage(), 2));
			corp.setInjuryAddition(SoinUtil.turnBigDecimalHalfRoundUpToString(soinEntity.getCompanyInjuryAdditionPercentage(), 2));
			corp.setHeating(SoinUtil.turnBigDecimalHalfRoundUpToString(soinEntity.getCompanyHeatingPercentage(), 2));
		}
		//比例之和
		{
			total.setInjury(SoinUtil.turnBigDecimalHalfRoundUpToString(SoinUtil.bigDecimalAdd(soinEntity.getPersonalInjuryPercentage(), soinEntity.getCompanyInjuryPercentage()), 2));
			total.setMedical(SoinUtil.turnBigDecimalHalfRoundUpToString(SoinUtil.bigDecimalAdd(soinEntity.getPersonalMedicalPercentage(), soinEntity.getCompanyMedicalPercentage()), 2));
			total.setPregnancy(SoinUtil.turnBigDecimalHalfRoundUpToString(SoinUtil.bigDecimalAdd(soinEntity.getPersonalPregnancyPercentage(), soinEntity.getCompanyPregnancyPercentage()), 2));
			total.setPension(SoinUtil.turnBigDecimalHalfRoundUpToString(SoinUtil.bigDecimalAdd(soinEntity.getPersonalPensionPercentage(), soinEntity.getCompanyPensionPercentage()), 2));
			total.setUnemployment(SoinUtil.turnBigDecimalHalfRoundUpToString(SoinUtil.bigDecimalAdd(soinEntity.getPersonalUnemploymentPercentage(), soinEntity.getCompanyUnemploymentPercentage()), 2));
			total.setHouseFund(SoinUtil.turnBigDecimalHalfRoundUpToString(SoinUtil.bigDecimalAdd(soinEntity.getPersonalHouseFundPercentage(), soinEntity.getCompanyHouseFundPercentage()), 2));
			total.setHouseFundAddition(SoinUtil.turnBigDecimalHalfRoundUpToString(SoinUtil.bigDecimalAdd(soinEntity.getPersonalHouseFundAdditionPercentage(), soinEntity.getCompanyHouseFundAdditionPercentage()), 2));
			total.setDisable(SoinUtil.turnBigDecimalHalfRoundUpToString(SoinUtil.bigDecimalAdd(soinEntity.getPersonalDisabilityPercentage(), soinEntity.getCompanyDisabilityPercentage()), 2));
			total.setSevereIllness(SoinUtil.turnBigDecimalHalfRoundUpToString(SoinUtil.bigDecimalAdd(soinEntity.getPersonalServerIllnessPercentage(), soinEntity.getCompanyServerIllnessPercentage()), 2));
			total.setInjuryAddition(SoinUtil.turnBigDecimalHalfRoundUpToString(SoinUtil.bigDecimalAdd(soinEntity.getPersonalInjuryAdditionPercentage(), soinEntity.getCompanyInjuryAdditionPercentage()), 2));
			total.setHeating(SoinUtil.turnBigDecimalHalfRoundUpToString(SoinUtil.bigDecimalAdd(soinEntity.getPersonalHeatingPercentage(), soinEntity.getCompanyHeatingPercentage()), 2));
		}

		return detailResult;
	}

	@Override
	public HttpResponse export(OrderBillListQueryCommand order, HttpServletResponse response) {
		HttpResponse httpResponse = null;
		try {
			order.setIsExport(1);
			parseOrderBillQueryCommand(order);
			SysUserModel currentUser = sysUserService.getCurrentSysUser();
			boolean isSalesman = false;
			if (systemRoleService.isSalesmanRole(currentUser.getId())) {
				order.setSalesmanId(currentUser.getId());
				isSalesman = true;
			}

			// 解析地区
			parseDistrictIds(order.getDistrict_id(), order);

			// 查询订单
			List<AryaSoinOrderEntity> orders = soinOrderDao.billManageQueryPagination(order).getResult();
			Collection<String> cookedOrderIds = new ArrayList<>();
			List<OrderExportResult> resultList = new ArrayList<>();
			for (AryaSoinOrderEntity orderEntity : orders) {
				if (cookedOrderIds.contains(orderEntity.getId())) {
					continue;
				} else {
					cookedOrderIds.add(orderEntity.getId());
				}
				Set<AryaSoinEntity> details = orderEntity.getDetails();
				for (AryaSoinEntity detail : details) {
					if (!checkSoinIsInCommand(detail, order)) {
						continue;
					}
					OrderExportResult orderExportResult = new OrderExportResult();
					BeanUtils.copyProperties(orderExportResult, detail);
					BeanUtils.copyProperties(orderExportResult, orderEntity);
					turnBeenBigDecimalHalfRoundUp(orderExportResult, 2);//将所有金额四舍五入保留两位
					processOrderBillResult(orderExportResult);//统计的金额求和保留两位
					if (detail.getCalculateType() == null) {
						orderExportResult.setPayMonth(detail.getYear() + String.format("%02d", detail.getMonth()));
					} else {
						orderExportResult.setPayMonth((detail.getCalculateType() == SOIN_BACK_CALCULATE ? "补" : "") + detail.getYear() + String.format("%02d", detail.getMonth()));
					}
					orderExportResult.setYear(detail.getYear());
					orderExportResult.setSubject(orderEntity.getSubject());
					orderExportResult.entityToResult(detail);
					orderExportResult.setOtherPayment(detail.getOther() == null ? BigDecimal.ZERO.setScale(2) : detail.getOther());
					// 如果公司不为空, 那么缴纳主体就是公司名称
					if (isNotBlank(orderEntity.getCorpId())) {
						CorporationEntity corp = corporationDao.findCorporationById(orderEntity.getCorpId());
						if (corp != null) {
							orderExportResult.setSubject(corp.getName());
						}
					}
					orderExportResult.setMemo(detail.getMemo());
					if (detail.getStatusCode() != null) {
						orderExportResult.setStatusCodeName(soinPayStatus.get(detail.getStatusCode()));
					}

					SoinSupplierEntity supplier = soinSupplierDao.findSoinSupplier(orderEntity.getSupplierId());
					// 供应商名称
					if (supplier != null) {
						orderExportResult.setSupplierName(supplier.getSupplierName());
					}

					//业务员导出 去掉出账管理费 出账总计和供应商
					if (isSalesman) {
						orderExportResult.setFeesOut(null);
						orderExportResult.setTotalOutPayment(null);
						orderExportResult.setSupplierName(null);
					}

					resultList.add(orderExportResult);
				}
			}

			excelExportHelper.export(
					aryaAdminConfigService.getExportTemplatePath() + AryaAdminConfigService.SOIN_ORDER_BILL_EXPORT,
					"对账单",
					new HashedMap() {{
						put("list", resultList);
					}},
					response
			);
			httpResponse = new HttpResponse(ErrorCode.CODE_OK);

		} catch (AryaServiceException arya) {
			arya.printStackTrace();
			httpResponse = new HttpResponse(arya.getErrorCode());

		} catch (Exception e) {
			elog.error(e.getMessage(), e);
			httpResponse = new HttpResponse(ErrorCode.CODE_SYS_ERR);
		}
		return httpResponse;
	}

	@Override
	public HttpResponse employeeModifyExport(OrderBillListQueryCommand order, HttpServletResponse response) {
		HttpResponse httpResponse = null;
		try {
			//是否是导出
			order.setIsExport(1);
			//判断查询主体
			parseOrderBillQueryCommand(order);
			SysUserModel currentUser = sysUserService.getCurrentSysUser();

			//判断当前用户是否是
			if (systemRoleService.isSalesmanRole(currentUser.getId())) {
				order.setSalesmanId(currentUser.getId());
			}
			// 解析地区
			parseDistrictIds(order.getDistrict_id(), order);
			if(order.getModify()==null){
				order.setModify(3);
			}
			List<String> orderIds = soinInOrDecreaseDao.findOrderIdsByInOrDecrease(order.getModify());

			// 查询订单
			List<AryaSoinOrderEntity> orders = soinOrderDao.employeeTypeQueryPagination(order,orderIds)
					.getResult();
			Collection<String> cookedOrderIds = new ArrayList<>();
			Map<String, AryaSoinPersonEntity> personEntityMap = new HashMap<>();
			Map<String, CorporationEntity> corporationEntityMap = new HashMap<>();
			Map<String, String> districts = new HashMap<>();
			EmployeeExportOutResult employeeExportOutResult = new EmployeeExportOutResult();
			employeeExportOutResult.setData("苏州汇嘉个人/企业社保代缴客户明细表-"+new SimpleDateFormat("YYYYMMdd").format(new Date(System.currentTimeMillis())));

			List<EmployeeExportOutDetail> resultList = new ArrayList<>();

			for (AryaSoinOrderEntity orderEntity : orders) {
				if (cookedOrderIds.contains(orderEntity.getId())) {
					continue;
				} else {
					cookedOrderIds.add(orderEntity.getId());
				}
				//开始拼接相应的数据
				EmployeeExportOutDetail exportOutResult = new EmployeeExportOutDetail();
				//增减员
				if (orderEntity.getModifyType() == null) {
					exportOutResult.setModifyName("-");
				} else {
					exportOutResult.setModifyName(NAME_LIST.get(orderEntity.getModifyType()));
				}
				//地区
				exportOutResult.setDistrict(orderEntity.getDistrict());
				//社保人姓名
				exportOutResult.setInsurancePersonName(orderEntity.getSoinPersonName());
				//社保类型
				exportOutResult.setSoinType(orderEntity.getSoinType());
				//服务月份
				exportOutResult.setServiceYearMonth(orderEntity.getServiceYearMonth());
				//服务费收账出账
				BigDecimal fees_in = orderEntity.getFees();
				BigDecimal fees_out = orderEntity.getFeeOut();

				if (fees_in != null) {
					//服务费收账
					exportOutResult.setFees(String.format("%.2f", fees_in));
				}
				if (fees_out != null) {
					//服务费出账
					exportOutResult.setFeeOut(String.format("%.2f", fees_out));
				}

				StringBuffer bufferHouseFund = new StringBuffer();

				List<String> year_month = new ArrayList<>();
				Set<AryaSoinEntity> details = orderEntity.getDetails();
				for (AryaSoinEntity detail : details) {
					//拼接公积金比例
					if (detail != null) {
						exportOutResult.entityToResult(detail);
						if (isBlank(bufferHouseFund)) {
							//公积金比例
							BigDecimal personalHouseFundPercentage = detail.getPersonalHouseFundPercentage();
							BigDecimal companyHouseFundPercentage = detail.getCompanyHouseFundPercentage();
							if (personalHouseFundPercentage != null && companyHouseFundPercentage != null) {
								bufferHouseFund.append("个人");
								bufferHouseFund.append(personalHouseFundPercentage.setScale(2).toString());
								bufferHouseFund.append("%");
								bufferHouseFund.append(",公司");
								bufferHouseFund.append(companyHouseFundPercentage.setScale(2).toString());
								bufferHouseFund.append("%");
								exportOutResult.setPersonalHouseFundPercentage(bufferHouseFund.toString());
							}
						}

						if (detail.getCalculateType() != null && Constants.SOIN_VERSION_TYPE_BACK == detail.getCalculateType()) {//判断是补缴
							//计算补缴开始月份，和结束月份
							StringBuffer tempYearMonth = new StringBuffer();
							tempYearMonth.append(detail.getYear());
							int month = detail.getMonth();
							if (month < 10) {//处理月份小于10月，字符串凭拼接缺少一个0的问题
								tempYearMonth.append("0"+month);
							} else {
								tempYearMonth.append(month);
							}

							if (tempYearMonth.length() > 0 && !isBlank(tempYearMonth)) {
								year_month.add(tempYearMonth.toString());
							}
						} else {
							//缴纳月份
							int month = detail.getMonth();
							if (month < 10) {//处理月份小于10月，字符串凭拼接缺少一个0的问题
								exportOutResult.setPayMonth(detail.getYear() + "0" + month);
							} else {
								exportOutResult.setPayMonth(detail.getYear() + "" + month);
							}
						}
					}
				}

				Collections.sort(year_month);
				if (year_month.size() > 0) {
					exportOutResult.setBackStartYearMonth(year_month.get(0));
					exportOutResult.setBackEndYearMonth(year_month.get(year_month.size() - 1));
				} else {
					exportOutResult.setBackStartYearMonth(null);
					exportOutResult.setBackEndYearMonth(null);
				}
				// 参保人查询，处理参保人相关页面
				AryaSoinPersonEntity personEntity = personEntityMap.get(orderEntity.getSoinPersonId());
				if (personEntity == null) {
					personEntity = soinPersonDao.findSoinPersonById(orderEntity.getSoinPersonId());
					if (personEntity != null) {
						personEntityMap.put(personEntity.getId(), personEntity);
					}
				}
				//身份证
				exportOutResult.setIdcardNo(personEntity.getIdcardNo());
				//电话号码
				exportOutResult.setPhoneNo(personEntity.getPhoneNo());
				//户口性质
				exportOutResult.setHukouDistrict(personEntity.getHukouDistrict());
				//户籍地址
				exportOutResult.setHukouTypeName(personEntity.getHukouTypeName());

				//缴纳主体
				if (orderEntity.getSubject() == PERSON_CUSTOMER) {
					exportOutResult.setSubject("个人");
				} else {
					CorporationEntity corporationEntity = corporationEntityMap.get(orderEntity.getCorpId());
					if (corporationEntity != null) {
						exportOutResult.setSubject(corporationEntity.getName());
					} else {
						corporationEntity = corporationDao.find(orderEntity.getCorpId());
						if (corporationEntity != null) {
							corporationEntityMap.put(orderEntity.getCorpId(), corporationEntity);
							exportOutResult.setSubject(corporationEntity.getName());
						}
					}
				}
				//参保地区
				exportOutResult.setDistrict(orderEntity.getDistrict());
				//参保省份
				String provinceName = districts.get(orderEntity.getDistrictId());
				if (isBlank(provinceName)) {
					//查询数据库回去省份
					AryaSoinDistrictEntity provinceSoinDistrictById = aryaSoinDistrictDao
							.findProvinceSoinDistrictById(orderEntity.getDistrictId());
					if (provinceSoinDistrictById != null) {
						districts.put(orderEntity.getDistrictId(), provinceSoinDistrictById.getDistrictName());
						exportOutResult.setProvince(provinceSoinDistrictById.getDistrictName());
					}
				} else {
					exportOutResult.setProvince(districts.get(orderEntity.getDistrictId()));
				}

				//备注
				exportOutResult.setMemo(orderEntity.getMemo());
				//业务员
				exportOutResult.setSalesmanName(orderEntity.getSalesmanName());
				resultList.add(exportOutResult);
			}
			employeeExportOutResult.setResult(resultList);
			excelExportHelper.export(
					aryaAdminConfigService.getExportTemplatePath() + AryaAdminConfigService.EMPLOYEE_IN_OR_DECREASE_EXPORT,
					"增减员",
					new HashedMap() {{
						put("list", employeeExportOutResult);
					}},
					response
			);
			httpResponse = new HttpResponse(ErrorCode.CODE_OK);

		} catch (AryaServiceException arya) {
			arya.printStackTrace();
			httpResponse = new HttpResponse(arya.getErrorCode());

		} catch (Exception e) {
			elog.error(e.getMessage(), e);
			httpResponse = new HttpResponse(ErrorCode.CODE_SYS_ERR);
		}


		return httpResponse;
	}

	@Override
	public EmployTypeListResult employeeTypeDetailQuery(OrderBillListQueryCommand order, HttpServletResponse response) {
		try {
			EmployTypeListResult listResult = new EmployTypeListResult();
			List<EmployTypeQueryResult> queryResults = new ArrayList<>();
			listResult.setOrders(queryResults);

			//处理缴纳主体   还是个人 或者是公司
			parseOrderBillQueryCommand(order);
			//查询当前登录用户
			SysUserModel currentUser = sysUserService.getCurrentSysUser();
			log.info("【对账单查询】判断角色是否是销售。用户id:" + currentUser.getId() + "。");
			if (systemRoleService.isSalesmanRole(currentUser.getId())) {
				//标记业务员，但是作用是什么？？？
				order.setSalesmanId(currentUser.getId());
				log.info("【对账单查询】用户角色是销售。");
			} else {
				log.info("【对账单查询】用户角色不是销售。");
			}
			// 解析地区
			parseDistrictIds(order.getDistrict_id(), order);
			if (order.getModify() == null) {
				order.setModify(3);
			}
			List<String> orderIds = soinInOrDecreaseDao.findOrderIdsByInOrDecrease(order.getModify());
			// 查询订单 采用分页查询，
			Pager<AryaSoinOrderEntity> pager = soinOrderDao.employeeTypeQueryPagination(order, orderIds);
			//设置页数
			listResult.setPages(Utils.calculatePages(pager.getRowCount(), pager.getPageSize()));
			Collection<String> cookedOrderIds = new ArrayList<>();
			List<AryaSoinOrderEntity> resultListTemp = pager.getResult();


			Map<String, AryaSoinPersonEntity> personEntityMap = new HashMap<>();
			Map<String, CorporationEntity> corporationEntityMap = new HashMap<>();


			//接下来处理需要返回的数据
			for (AryaSoinOrderEntity orderEntity : resultListTemp) {
				if (cookedOrderIds.contains(orderEntity.getId())) {
					continue;
				} else {
					cookedOrderIds.add(orderEntity.getId());
				}
				//开始拼接相应的数据
				EmployTypeQueryResult employTypeQueryResult = new EmployTypeQueryResult();
				queryResults.add(employTypeQueryResult);
				//id
				employTypeQueryResult.setId(orderEntity.getId());
				if (orderEntity.getModifyType() == null) {
					employTypeQueryResult.setModifyName("-");
				} else {
					employTypeQueryResult.setModifyName(NAME_LIST.get(orderEntity.getModifyType()));
				}
				//地区
				employTypeQueryResult.setDistrict(orderEntity.getDistrict());
				//社保人姓名
				employTypeQueryResult.setName(orderEntity.getSoinPersonName());
				//社保类型
				employTypeQueryResult.setSoinType(orderEntity.getSoinType());
				//服务月份
				employTypeQueryResult.setServiceYearMonth(orderEntity.getServiceYearMonth());
				//服务费收账出账
				BigDecimal fees_in = orderEntity.getFees();

				if (fees_in != null) {
					//服务费收账
					employTypeQueryResult.setFees(String.format("%.2f", fees_in));
				}

				Set<AryaSoinEntity> details = orderEntity.getDetails();
				StringBuffer buffer = new StringBuffer();
				List<String> year_month = new ArrayList<>();
				for (AryaSoinEntity detail : details) {
					//拼接公积金比例
					if (detail != null) {
						if (isBlank(buffer)) {
							BigDecimal personalHouseFundPercentage = detail.getPersonalHouseFundPercentage();
							BigDecimal companyHouseFundPercentage = detail.getCompanyHouseFundPercentage();
							if (personalHouseFundPercentage != null && companyHouseFundPercentage != null) {
								buffer.append("个人");
								buffer.append(personalHouseFundPercentage.setScale(2).toString());
								buffer.append("%");
								buffer.append(",公司");
								buffer.append(companyHouseFundPercentage.setScale(2).toString());
								buffer.append("%");
								employTypeQueryResult.setHousefundProportion(buffer.toString());
							}

						}
						//计算服务收账
						//计算补缴开始月份，和结束月份
						if (detail.getCalculateType() != null && Constants.SOIN_VERSION_TYPE_BACK == detail.getCalculateType()) {//判断是补缴
							//计算补缴开始月份，和结束月份
							StringBuffer tempYearMonth = new StringBuffer();
							tempYearMonth.append(detail.getYear());
							int month = detail.getMonth();
							if (month < 10) {//处理月份小于10月，字符串凭拼接缺少一个0的问题
								tempYearMonth.append("0" + month);
							} else {
								tempYearMonth.append(month);
							}

							if (tempYearMonth.length() > 0 && !isBlank(tempYearMonth)) {
								year_month.add(tempYearMonth.toString());
							}
						} else {
							//缴纳月份
							int month = detail.getMonth();
							if (month < 10) {
								employTypeQueryResult.setPayMonth(detail.getYear() + "0" + month);
							} else {
								employTypeQueryResult.setPayMonth(detail.getYear() + "" + month);
							}
						}
					}
				}
				Collections.sort(year_month);
				if (year_month.size() > 0) {
					employTypeQueryResult.setBackStartMonth(year_month.get(0));
					employTypeQueryResult.setBackEndMonth(year_month.get(year_month.size() - 1));
				} else {
					employTypeQueryResult.setBackStartMonth(null);
					employTypeQueryResult.setBackEndMonth(null);

				}
				// 参保人查询
				AryaSoinPersonEntity personEntity = personEntityMap.get(orderEntity.getSoinPersonId());
				if (personEntity == null) {
					personEntity = soinPersonDao.findSoinPersonById(orderEntity.getSoinPersonId());
					if (personEntity != null) {
						personEntityMap.put(personEntity.getId(), personEntity);
						employTypeQueryResult.setIdcard(personEntity.getIdcardNo());
					}
				} else {
					employTypeQueryResult.setIdcard(personEntity.getIdcardNo());
				}
				//缴纳主体
				if (orderEntity.getSubject() == PERSON_CUSTOMER) {
					employTypeQueryResult.setSubject("个人");
				} else if (orderEntity.getSubject() == APP_CUSTOMER) {
					employTypeQueryResult.setSubject("APP");
				} else {
					CorporationEntity corporationEntity = corporationEntityMap.get(orderEntity.getCorpId());
					if (corporationEntity != null) {
						employTypeQueryResult.setSubject(corporationEntity.getName());
					} else {
						corporationEntity = corporationDao.find(orderEntity.getCorpId());
						if (corporationEntity != null) {
							corporationEntityMap.put(orderEntity.getCorpId(), corporationEntity);
							employTypeQueryResult.setSubject(corporationEntity.getName());
						}
					}
				}
			}
			return listResult;
		}catch (AryaServiceException arya) {
			arya.printStackTrace();


		} catch (Exception e) {
			elog.error(e.getMessage(), e);
		}
		return null;

	}

	/**
	 * 解析对账单查询请求
	 *
	 * @param order
	 */
	private void parseOrderBillQueryCommand(OrderBillListQueryCommand order) {
		// 如果是APP(3)来源就是线上 (1-4)
		if (isBlank(order.getCustomer_id())) {
			order.setCustomer_id(null);//查询所有的订单
			order.setSource(-2);
		} else if ("3".equals(order.getCustomer_id())) {
			// 来源不等于导入(1-4)
			order.setCustomer_id(null);
			order.setSource(-2);
			order.setSubject(3);

		} else {
			// 如果是个人 来源就是线下(5)
			if ("2".equals(order.getCustomer_id())) {
				order.setCustomer_id(null);
				order.setSubject(2);
			} else {
				order.setSubject(1);
			}
			order.setSource(5);
		}
	}

	/**
	 * 解析地区
	 *
	 * @param districtId
	 * @return
	 */
	private void parseDistrictIds(String districtId, OrderBillListQueryCommand order) {
		List<String> districtIds = new ArrayList<>();
		if (isNotBlank(districtId)) {
			// 如果是1000的话表示全国
			if (CHN_ID.equals(districtId)) {
				order.setDistrict_id(null);
				return;
			} else {
				// 需要把对应的子节点全部查询出来,使用in作为查询条件
				List<String> parents = new ArrayList<>();
				parents.add(districtId);
				districtIds.add(districtId);
				while (parents.size() > 0) {
					List<String> tempParents = new ArrayList<>();
					for (String parentId : parents) {
						List<String> children = aryaSoinDistrictDao.findChildrenIdsById(parentId);
						tempParents.addAll(children);
						districtIds.addAll(children);
					}
					parents.clear();
					parents.addAll(tempParents);
				}
			}
			order.setDistrictIds(districtIds);
		}
	}

	@Override
	public HttpResponse batchExport(String batchId, HttpServletResponse response) {
		HttpResponse httpResponse = null;
		try {
			// 查询订单
			List<AryaSoinOrderEntity> orders = soinOrderDao.findBatchAllOrders(batchId);
			if (orders.size() == 0) {
				httpResponse = new HttpResponse(ErrorCode.CODE_ORDER_BILL_BATCH_EMPTY);
				return httpResponse;
			}
			Collection<String> cookedOrderIds = new ArrayList<>();
			List<OrderExportResult> resultList = new ArrayList<>();
			for (AryaSoinOrderEntity orderEntity : orders) {
				if (cookedOrderIds.contains(orderEntity.getId())) {
					continue;
				} else {
					cookedOrderIds.add(orderEntity.getId());
				}
				Set<AryaSoinEntity> details = orderEntity.getDetails();
				for (AryaSoinEntity detail : details) {
					OrderExportResult orderExportResult = new OrderExportResult();
					BeanUtils.copyProperties(orderExportResult, detail);
					BeanUtils.copyProperties(orderExportResult, orderEntity);
					turnBeenBigDecimalHalfRoundUp(orderExportResult, 2);
					processOrderBillResult(orderExportResult);//统计的金额求和保留两位
					orderExportResult.setPayMonth(detail.getYear() + String.format("%02d", detail.getMonth()));
					orderExportResult.setSubject(orderEntity.getSubject());
					orderExportResult.entityToResult(detail);
					orderExportResult.setOtherPayment(detail.getOther() == null ? BigDecimal.ZERO.setScale(2) : detail.getOther());
					// 如果公司不为空, 那么缴纳主体就是公司名称
					if (isNotBlank(orderEntity.getCorpId())) {
						CorporationEntity corp = corporationDao.findCorporationById(orderEntity.getCorpId());
						if (corp != null) {
							orderExportResult.setSubject(corp.getName());
						}
					}
					orderExportResult.setMemo(detail.getMemo());
					if (detail.getStatusCode() != null) {
						orderExportResult.setStatusCodeName(soinPayStatus.get(detail.getStatusCode()));
					}

					SoinSupplierEntity supplier = soinSupplierDao.findSoinSupplier(orderEntity.getSupplierId());
					// 供应商名称
					if (supplier != null) {
						orderExportResult.setSupplierName(supplier.getSupplierName());
					}

					resultList.add(orderExportResult);
				}
			}

			excelExportHelper.export(
					aryaAdminConfigService.getExportTemplatePath() + AryaAdminConfigService.SOIN_ORDER_BILL_EXPORT,
					"对账单",
					new HashedMap() {{
						put("list", resultList);
					}},
					response
			);
			httpResponse = new HttpResponse(ErrorCode.CODE_OK);
			return httpResponse;
		} catch (Exception e) {
			elog.error(e.getMessage(), e);
			throw new AryaServiceException(ErrorCode.CODE_EXCEL_EXPORT_FILE);
		}

	}

	@Override
	public void adjustOrderBillsOtherPayment(AdjustSoinOrderOtherPaymentCommand command) throws
			AryaServiceException {
		List<AryaSoinEntity> soinEntities = soinDao.findSoinByIds(command.getIdList());
		StringBuffer logMsg = new StringBuffer("【对账单管理】修改对账单其他费用。");
		for (AryaSoinEntity soinEntity : soinEntities) {
			logMsg.append("修改" + soinEntity.getId() + "的总收账从" + soinEntity.getTotalPayment() + "为");
			soinEntity.setTotalPayment(SoinUtil.bigDecimalAdd(SoinUtil.bigDecimalSubtract(soinEntity.getTotalPayment(), soinEntity.getOther()), command.getOtherPayment()));
			logMsg.append(soinEntity.getTotalPayment() + ",");
			logMsg.append("总出账从" + soinEntity.getTotalOutPayment() + "为");
			soinEntity.setTotalOutPayment(SoinUtil.bigDecimalAdd(SoinUtil.bigDecimalSubtract(soinEntity.getTotalOutPayment(), soinEntity.getOther()), command.getOtherPayment()));
			logMsg.append(soinEntity.getTotalOutPayment() + ",");
			logMsg.append("其他费用从" + soinEntity.getOther() + "为");
			soinEntity.setOther(command.getOtherPayment());
			logMsg.append(soinEntity.getOther() + "。");
		}
		try {
			if (soinEntities.size() > 0) {
				soinDao.update(soinEntities);
				opLogService.successLog(SOIN_ORDER_ADJUST_OTHER_PAYMENT, logMsg, log);
			}
		} catch (Exception e) {
			elog.error(e.getMessage(), e);
			opLogService.successLog(SOIN_ORDER_ADJUST_OTHER_PAYMENT, logMsg, log);
			throw new AryaServiceException(ErrorCode.CODE_ORDER_BILL_ADJUST_OTHER_PAY_FAILED);
		}
	}

	@Override
	public SoinTypeMinMaxBaseModel getMinMaxBase(SoinTypeVersionEntity soinTypeVersionEntity) {
		SoinTypeMinMaxBaseModel model = new SoinTypeMinMaxBaseModel();
		getMinMaxBase(soinTypeVersionEntity.getRuleMedical(), model);
		getMinMaxBase(soinTypeVersionEntity.getRulePregnancy(), model);
		getMinMaxBase(soinTypeVersionEntity.getRulePension(), model);
		getMinMaxBase(soinTypeVersionEntity.getRuleInjury(), model);
		getMinMaxBase(soinTypeVersionEntity.getRuleUnemployment(), model);

		getMinMaxBase(soinTypeVersionEntity.getRuleDisability(), model);
		getMinMaxBase(soinTypeVersionEntity.getRuleSevereIllness(), model);
		getMinMaxBase(soinTypeVersionEntity.getRuleInjuryAddition(), model);
		return model;
	}

	@Override
	public List<AryaSoinOrderEntity> queryAllOrders(OrderBillListQueryCommand command) throws AryaServiceException {
		try {

			// 解析用户类型
			// ---------------------------------------------------------------------------------------------------------
			// 如果是APP(3)来源就是线上 (1-4)
			if (isAnyBlank(command.getCustomer_id())) {
				command.setCustomer_id(null);//查询所有的订单
				command.setSource(-2);
			} else if ("3".equals(command.getCustomer_id())) {
				// 来源不等于导入(1-4)
				command.setCustomer_id(null);
				command.setSource(-2);
				command.setSubject(3);

			} else {
				// 如果是个人 来源就是线下(5)
				if ("2".equals(command.getCustomer_id())) {
					command.setCustomer_id(null);
					command.setSubject(2);
				} else {
					command.setSubject(1);
				}
				command.setSource(5);
			}
			SysUserModel currentUser = sysUserService.getCurrentSysUser();
			if (systemRoleService.isSalesmanRole(currentUser.getId())) {
				command.setSalesmanId(currentUser.getId());
			}

			// ---------------------------------------------------------------------------------------------------------

			// 解析地区
			// ---------------------------------------------------------------------------------------------------------
			parseDistrictIds(command.getDistrict_id(), command);

			// ---------------------------------------------------------------------------------------------------------

			// 查询订单
			return soinOrderDao.billManageQuery(command);
		} catch (AryaServiceException arya) {
			arya.printStackTrace();
			throw new AryaServiceException(arya.getErrorCode());
		} catch (Exception e) {
			elog.error(e.getMessage(), e);
			throw new AryaServiceException(ErrorCode.CODE_SYS_ERR);
		}
	}

	@Override
	public boolean checkSoinIsInCommand(AryaSoinEntity soinEntity, OrderBillListQueryCommand command) {
		if (command.getPayed_status() == null) {
			return true;
		}
		if (soinEntity.getStatusCode() != null && soinEntity.getStatusCode().equals(command.getPayed_status())) {
			return true;
		}

		if (soinEntity.getStatusCode() == null && command.getPayed_status() == 3) {
			return true;
		} else {
			if (Objects.equals(soinEntity.getStatusCode(), command.getPayed_status())) {
				return true;
			}
		}

		return false;
	}

	@Override
	public void exportTemplateDownload(HttpServletResponse response, String templateType) throws IOException {
		String templatePath = "";
		response.setContentType("application/vnd.ms-excel");
		if (templateType.equals("company")) {
			String headStr = "attachment;filename=\"" + SysUtils.parseGBK("增减员导入模板-企业客户.xlsx") + "\"";
			response.setHeader("Content-Disposition", headStr);
			templatePath = aryaAdminConfigService.getExportTemplatePath() + AryaAdminConfigService.SOIN_ORDER_COMPANY_BILL_IMPORT;
		} else {
			String headStr = "attachment;filename=\"" + SysUtils.parseGBK("增减员导入模板-个人客户.xlsx") + "\"";
			response.setHeader("Content-Disposition", headStr);
			templatePath = aryaAdminConfigService.getExportTemplatePath() + AryaAdminConfigService.SOIN_ORDER_PERSONAL_BILL_IMPORT;
		}
		FileInputStream excelFileInputStream = new FileInputStream(templatePath);
		//读取模板
		XSSFWorkbook workbook = new XSSFWorkbook(excelFileInputStream);
		excelFileInputStream.close();

		//构造地区树
		DistrictTree districtTree = soinDistrictTreeService.buildDistrictTree(soinDistrictDao.findAllSorted());

		//构造社保类型map
		List<SoinTypeEntity> soinTypeEntities = soinTypeDao.findAllEnableSoinTypeOrderByDistrictASC();
		Map<String, List<SoinTypeEntity>> soinTypeMap = new HashMap();
		if (soinTypeEntities != null && !soinTypeEntities.isEmpty()) {
			for (SoinTypeEntity soinTypeEntity : soinTypeEntities) {
				soinTypeMap.computeIfAbsent(soinTypeEntity.getDistrictId(), k -> new ArrayList<>());
				List<SoinTypeEntity> soinTypes = soinTypeMap.get(soinTypeEntity.getDistrictId());
				soinTypes.add(soinTypeEntity);
			}
		}

		//字典sheet中的位置开始
		final int PROVINCE_COL = 0;//省在第一列
		final int CITY_COL = 1;//市第二
		final int COUNTY_COL = 1;//区第二
		final int SOIN_TYPE_COL = 2;//社保类型第三
		int soinDistrictNo = 0;//参保地区数量
		int soinTypeNo = 0;//社保类型数量
		int rowLastNo = 25;
		int provinceCount = 0;
		int lastRow = 0;
		//字典sheet中的位置结束
		//填充字典sheet
		Sheet dicSheet = workbook.createSheet("dic");
		workbook.setSheetHidden(1, true);
		Sheet sheet = workbook.getSheetAt(0);

		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
		System.out.println(sdf.format(date));;
		if (templateType.equals("company")) {
			sheet.getRow(0).getCell(0).setCellValue("苏州汇嘉企业社保代缴客户明细表-" + sdf.format(date));
		}else {
			sheet.getRow(0).getCell(0).setCellValue("苏州汇嘉个人社保代缴客户明细表-" + sdf.format(date));
		}

		//直辖市list
		List<DistrictTree.DistrictTreeItem> directCities = new ArrayList<>();
		if (!districtTree.isEmpty()) {
			//遍历省
			List<DistrictTree.DistrictTreeItem> provinces = districtTree.get(0).getNodes();
			provinceCount = provinces.size();
			log.info("【生成对账单导入模板】省数量：" + provinceCount);
			for (int p = 0; p <= provinces.size(); p++) {
				int cityHasSoinTypeCount = 0;//有社保类型的地级市数量
				int countyHasSoinTypeCount = 0;//有社保类型的区县数量
				DistrictTree.DistrictTreeItem province = null;
				String provinceName = null;
				if (p < provinces.size()) {
					province = provinces.get(p);
					log.info("【生成对账单导入模板】p：" + p + ",省：" + province.getText() + "，其下有" + (province.getNodes() == null ? 0 : province.getNodes().size()) + "个地级市");
					if (province.getNodes() == null || province.getNodes().isEmpty()) {
						continue;
					}
					if (province.getNodes().size() == 1) {
						//判断直辖市
						if (removeEnd(province.getNodes().get(0).getText(), "市").equals(province.getText())) {
							directCities.addAll(province.getNodes());
							log.info("【生成对账单导入模板】发现直辖市：" + province.getNodes().get(0).getText());
							continue;
						}
					}
					provinceName = removeEnd(province.getText(), "省");
//					getRow(dicSheet, lastRow++).createCell(PROVINCE_COL).setCellValue(provinceName);
				} else {
					log.info("【生成对账单导入模板】开始生成" + directCities.size() + "个直辖市及其社保类型");
					province = new DistrictTree.DistrictTreeItem();
					province.setNodes(directCities);
					provinceName = "直辖市";
				}


				//为某省关联其下所有市
				List<DistrictTree.DistrictTreeItem> cities = province.getNodes();
				//遍历市
				for (int c = 0; c < cities.size(); c++) {
					DistrictTree.DistrictTreeItem city = cities.get(c);
					String cityName = null;
					cityName = removeEnd(city.getText(), "市");
					//判断市一级是否存在社保类型
					List<SoinTypeEntity> citySoinTypes = soinTypeMap.get(city.getHref());
					if (citySoinTypes != null && !citySoinTypes.isEmpty()) {
						log.info("【生成对账单导入模板】" + provinceName + "-" + cityName + "有" + citySoinTypes.size() + "个社保类型，并添加至社保类型字典。");
						cityHasSoinTypeCount++;
						getRow(dicSheet, soinDistrictNo++).createCell(CITY_COL).setCellValue(cityName);
						for (SoinTypeEntity citySoinType : citySoinTypes) {
							getRow(dicSheet, soinTypeNo).createCell(SOIN_TYPE_COL).setCellValue(citySoinType.getTypeName());
							soinTypeNo++;
						}
						//为某城市关联其下所有社保类型
						createName(workbook, cityName, "dic!$C$" + (soinTypeNo - citySoinTypes.size() + 1) + ":$C$" + soinTypeNo);//这里表达式启始值从1开始
					}
					if (city.getNodes() == null || city.getNodes().isEmpty()) {
						log.info("【生成对账单导入模板】" + provinceName + "-" + cityName + "下没有区县，continue");
						continue;
					}
					List<DistrictTree.DistrictTreeItem> counties = city.getNodes();
					//遍历区县
					for (int co = 0; co < counties.size(); co++) {
						DistrictTree.DistrictTreeItem county = counties.get(co);
						List<SoinTypeEntity> countySoinTypes = soinTypeMap.get(county.getHref());
						log.info("【生成对账单导入模板】" + provinceName + "-" + cityName + "-" + county.getText() + "下有" + (countySoinTypes == null ? 0 : countySoinTypes.size()) + "个社保类型。目前总共有" + soinTypeNo + "个社保类型。");
						if (countySoinTypes == null || countySoinTypes.isEmpty()) {
							continue;
						}
						countyHasSoinTypeCount++;
						String countyName = null;
						if (county.getUpSuper() == Constants.TRUE) {
							//如果标记为上级地区并列则把地级市名称去掉
							countyName = removeEnd(county.getText(), "市");
						} else {
							countyName = cityName + removeEnd(county.getText(), "市");
						}
						getRow(dicSheet, soinDistrictNo++).createCell(COUNTY_COL).setCellValue(countyName);

						for (SoinTypeEntity countySoinType : countySoinTypes) {
							getRow(dicSheet, soinTypeNo).createCell(SOIN_TYPE_COL).setCellValue(countySoinType.getTypeName());
							soinTypeNo++;
						}

						log.info("【生成对账单导入模板】目前总共有" + soinTypeNo + "个社保类型。");
						//为某区县关联其下所有社保类型
						int soinTypeStartRowNo = (soinTypeNo - countySoinTypes.size() + 1);
						if (soinTypeNo >= soinTypeStartRowNo) {
							createName(workbook, countyName, "dic!$C$" + soinTypeStartRowNo + ":$C$" + soinTypeNo);//这里表达式启始值从1开始
						}
					}
				}
				//为某省关联市和市下所有区县
				int soinDistrictStartRowNo = soinDistrictNo - cityHasSoinTypeCount - countyHasSoinTypeCount + 1;
				if (soinDistrictNo >= soinDistrictStartRowNo) {
					getRow(dicSheet, lastRow++).createCell(PROVINCE_COL).setCellValue(provinceName);
					createName(workbook, provinceName, "dic!$B$" + soinDistrictStartRowNo + ":$B$" + soinDistrictNo);//这里表达式启始值从1开始
				}
			}
			//创建excel省列的关联名称
			if (lastRow > 0) {
				createName(workbook, "province", "dic!$A$1:$A$" + lastRow);
				setDataValidation(sheet, "province", 2, rowLastNo, getColumnNo(SOIN_DISTRICT_PROVINCE), getColumnNo(SOIN_DISTRICT_PROVINCE));
			}
			//加入参保地区与省的级联  社保类型和参保地区的级联
			setDataValidation(sheet, "=INDIRECT(F3)", 2, rowLastNo, getColumnNo(SOIN_DISTRICT_CITY), getColumnNo(SOIN_DISTRICT_CITY));
			setDataValidation(sheet, "=INDIRECT(G3)", 2, rowLastNo, getColumnNo(SOIN_TYPE), getColumnNo(SOIN_TYPE));
		}

		//增减员
		//创建excel增减员的关联名称
		createName(workbook, "modify", "dic!$A$" + (lastRow + 1) + ":$A$" + (lastRow + 2));
		setDataValidation(sheet, "modify", 2, rowLastNo, getColumnNo(MODIFY), getColumnNo(MODIFY));
		getRow(dicSheet, lastRow++).createCell(0).setCellValue("增员");
		getRow(dicSheet, lastRow++).createCell(0).setCellValue("减员");

		//户口性质
		createName(workbook, "hukou", "dic!$A$" + (lastRow + 1) + ":$A$" + (lastRow + 4));
		setDataValidation(sheet, "hukou", 2, rowLastNo, getColumnNo(HUKOU_TYPE), getColumnNo(HUKOU_TYPE));
		getRow(dicSheet, lastRow++).createCell(0).setCellValue("本地城镇");
		getRow(dicSheet, lastRow++).createCell(0).setCellValue("本地农村");
		getRow(dicSheet, lastRow++).createCell(0).setCellValue("外地城镇");
		getRow(dicSheet, lastRow++).createCell(0).setCellValue("外地农村");

		//业务员
		List<SysUserEntity> sysUserEntities = sysUserDao.findAll();//暂时找全部的系统用户
		for (int i = 0; i < sysUserEntities.size(); i++) {
			SysUserEntity sysUserEntity = sysUserEntities.get(i);
			if (!systemRoleService.isSalesmanRole(sysUserEntity)) {
				sysUserEntities.remove(sysUserEntity);
				i--;
				continue;
			}
			getRow(dicSheet, lastRow++).createCell(0).setCellValue(sysUserEntity.getRealName());//这里的行数是从0开始的
		}
		createName(workbook, "salesMan", "dic!$A$" + (lastRow - sysUserEntities.size() + 1) + ":$A$" + lastRow);//这里表达式里的行数是从1开始的
		setDataValidation(sheet, "salesMan", 2, rowLastNo, getColumnNo(SALESMAN), getColumnNo(SALESMAN));

		//输出
		workbook.write(response.getOutputStream());
		response.getOutputStream().close();
	}

	@Override
	public void processOrderBillResult(OrderExportResult orderExportResult) {
		if (orderExportResult == null) {
			return;
		}
		BigDecimal corpTotal = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
		BigDecimal personTotal = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
		int scale = 2;
		//医疗
		if (orderExportResult.getMedicalTotal() != null) {
			orderExportResult.setMedicalTotal(SoinUtil.plusHalfRoundUp(orderExportResult.getCompanyMedical(), orderExportResult.getPersonalMedical(), scale));
			corpTotal = SoinUtil.plusHalfRoundUp(corpTotal, orderExportResult.getCompanyMedical(), scale);
			personTotal = SoinUtil.plusHalfRoundUp(personTotal, orderExportResult.getPersonalMedical(), scale);
		}
		//养老
		if (orderExportResult.getPensionTotal() != null) {
			orderExportResult.setPensionTotal(SoinUtil.plusHalfRoundUp(orderExportResult.getCompanyPension(), orderExportResult.getPersonalPension(), scale));
			corpTotal = SoinUtil.plusHalfRoundUp(corpTotal, orderExportResult.getCompanyPension(), scale);
			personTotal = SoinUtil.plusHalfRoundUp(personTotal, orderExportResult.getPersonalPension(), scale);
		}
		//生育
		if (orderExportResult.getPregnancyTotal() != null) {
			orderExportResult.setPregnancyTotal(orderExportResult.getPregnancyTotal().setScale(scale, RoundingMode.HALF_UP));
			corpTotal = SoinUtil.plusHalfRoundUp(corpTotal, orderExportResult.getPregnancyTotal(), scale);
		}
		//失业
		if (orderExportResult.getUnemploymentTotal() != null) {
			orderExportResult.setUnemploymentTotal(SoinUtil.plusHalfRoundUp(orderExportResult.getCompanyUnemployment(), orderExportResult.getPersonalUnemployment(), scale));
			corpTotal = SoinUtil.plusHalfRoundUp(corpTotal, orderExportResult.getCompanyUnemployment(), scale);
			personTotal = SoinUtil.plusHalfRoundUp(personTotal, orderExportResult.getPersonalUnemployment(), scale);
		}
		//工伤
		if (orderExportResult.getInjuryTotal() != null) {
			orderExportResult.setInjuryTotal(orderExportResult.getInjuryTotal().setScale(scale, RoundingMode.HALF_UP));
			corpTotal = SoinUtil.plusHalfRoundUp(corpTotal, orderExportResult.getInjuryTotal(), scale);
		}
		//公积金
		if (orderExportResult.getHouseFundTotal() != null) {
			orderExportResult.setHouseFundTotal(SoinUtil.plusHalfRoundUp(orderExportResult.getCompanyHouseFund(), orderExportResult.getPersonalHouseFund(), scale));
			corpTotal = SoinUtil.plusHalfRoundUp(corpTotal, orderExportResult.getCompanyHouseFund(), scale);
			personTotal = SoinUtil.plusHalfRoundUp(personTotal, orderExportResult.getPersonalHouseFund(), scale);
		}

		//残保
		if (orderExportResult.getDisabilityTotal() != null) {
			orderExportResult.setDisabilityTotal(orderExportResult.getDisabilityTotal().setScale(scale, RoundingMode.HALF_UP));
			corpTotal = SoinUtil.plusHalfRoundUp(corpTotal, orderExportResult.getDisabilityTotal(), scale);
		}
		//大病医疗
		if (orderExportResult.getServerIllnessTotal() != null) {
			orderExportResult.setServerIllnessTotal(SoinUtil.plusHalfRoundUp(orderExportResult.getCompanyServerIllness(), orderExportResult.getPersonalServerIllness(), scale));
			corpTotal = SoinUtil.plusHalfRoundUp(corpTotal, orderExportResult.getCompanyServerIllness(), scale);
			personTotal = SoinUtil.plusHalfRoundUp(personTotal, orderExportResult.getPersonalServerIllness(), scale);
		}
		//工伤补充
		if (orderExportResult.getInjuryAdditionTotal() != null) {
			orderExportResult.setInjuryAdditionTotal(orderExportResult.getInjuryAdditionTotal().setScale(scale, RoundingMode.HALF_UP));
			corpTotal = SoinUtil.plusHalfRoundUp(corpTotal, orderExportResult.getInjuryAdditionTotal(), scale);
		}
		//采暖费
		if (orderExportResult.getHeatingTotal() != null) {
			orderExportResult.setHeatingTotal(orderExportResult.getHeatingTotal().setScale(scale, RoundingMode.HALF_UP));
			corpTotal = SoinUtil.plusHalfRoundUp(corpTotal, orderExportResult.getHeatingTotal(), scale);
		}
		//个人小计
		orderExportResult.setPersonalTotal(personTotal);
		//公司小计
		orderExportResult.setCompanyTotal(corpTotal);

		BigDecimal total = SoinUtil.plusHalfRoundUp(personTotal, corpTotal, scale);
		//总收账
		orderExportResult.setTotalPayment(SoinUtil.plusHalfRoundUp(total, orderExportResult.getFees(), scale));
		//总出账
		orderExportResult.setTotalOutPayment(SoinUtil.plusHalfRoundUp(total, orderExportResult.getFeesOut(), scale));

	}

	@Override
	public BillExtendResult extendQuery(BillExtendCommand command) {
		BillExtendResult result = new BillExtendResult();
		if (isBlank(command.getId())) {
			throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "客户id为空！");
		}
		//先查出一个最新的订单
		AryaSoinOrderEntity latestOrder = null;
		if (command.getId().equals(String.valueOf(PERSON_CUSTOMER))) {
			//个人订单
			latestOrder = soinOrderDao.findPersonCustomerLatestWithoutDecrease();
		} else {
			latestOrder = soinOrderDao.findCorpLatestWithoutDecrease(command.getId());
		}
		if (latestOrder == null) {
			return result;
		}

		List<AryaSoinOrderEntity> orderEntities = null;
		int orderCout = 0;
		if (command.getId().equals(String.valueOf(PERSON_CUSTOMER))) {
			//查询个人订单
			orderEntities = soinOrderDao.findPersonCustomerOrdersWithoutDecrease(latestOrder.getServiceYearMonth(), command.getPage(), command.getPage_size());
			orderCout = soinOrderDao.findPersonCustomerOrdersWithoutDecreaseCount(latestOrder.getServiceYearMonth());
		} else {
			orderEntities = soinOrderDao.findCorpCustomerOrdersWithoutDecrease(command.getId(), latestOrder.getServiceYearMonth(), command.getPage(), command.getPage_size());
			orderCout = soinOrderDao.findCorpCustomerOrdersWithoutDecreaseCount(command.getId(), latestOrder.getServiceYearMonth());
		}

		result.setPages(Utils.calculatePages(orderCout, command.getPage_size()));

		//收集参保人和供应商id
		Collection<String> soinPersonIds = new ArrayList<>();
		Collection<String> supplierIds = new ArrayList<>();
		Collection<String> corpIds = new ArrayList<>();
		for (AryaSoinOrderEntity orderEntity : orderEntities) {
			soinPersonIds.add(orderEntity.getSoinPersonId());
			supplierIds.add(orderEntity.getSupplierId());
			if (command.getId().equals(COPR_CUSTOMER)) {
				corpIds.add(orderEntity.getCorpId());
			}
		}

		List<AryaSoinPersonEntity> soinPersonEntities = soinPersonDao.findSoinPersonList(soinPersonIds);
		List<SoinSupplierEntity> soinSupplierEntities = soinSupplierDao.findSoinSuppliers(supplierIds);
		CorporationEntity corporationEntity = null;

		Map<String, AryaSoinPersonEntity> soinPersonEntityMap = new HashMap<>();
		Map<String, SoinSupplierEntity> soinSupplierEntityMap = new HashMap<>();
		for (AryaSoinPersonEntity personEntity : soinPersonEntities) {
			soinPersonEntityMap.put(personEntity.getId(), personEntity);
		}

		for (SoinSupplierEntity supplierEntity : soinSupplierEntities) {
			soinSupplierEntityMap.put(supplierEntity.getId(), supplierEntity);
		}
		if (!command.getId().equals(String.valueOf(PERSON_CUSTOMER))) {
			corporationEntity = corporationDao.find(command.getId());
		}


		for (AryaSoinOrderEntity orderEntity : orderEntities) {
			AryaSoinPersonEntity soinPersonEntity = soinPersonEntityMap.get(orderEntity.getSoinPersonId());
			SoinSupplierEntity supplierEntity = soinSupplierEntityMap.get(orderEntity.getSupplierId());
			if (soinPersonEntity == null) {
				soinPersonEntity.setIdcardNo("-");
			}
			if (supplierEntity == null) {
				supplierEntity.setSupplierName("-");
			}
			BillExtendResult.ExtendBillResult billResult = new BillExtendResult.ExtendBillResult();
			if (command.getId().equals(String.valueOf(PERSON_CUSTOMER))) {
				billResult.setSubject(subjectMap.get(orderEntity.getSubject()));
			} else {
				if (corporationEntity == null) {
					billResult.setSubject("企业未知");
				} else {
					billResult.setSubject(corporationEntity.getName());
				}
			}
			billResult.setName(orderEntity.getSoinPersonName());
			billResult.setIdcard(soinPersonEntity.getIdcardNo());
			billResult.setSoinDistrict(orderEntity.getDistrict());
			billResult.setSoinType(orderEntity.getSoinType());
			billResult.setServiceMonth(orderEntity.getServiceYearMonth());

			if (command.getId().equals(String.valueOf(PERSON_CUSTOMER))) {
				YearMonth serviceYearMonth = generateYearMonthStrToObj(orderEntity.getServiceYearMonth().toString());
				YearMonth postponeYearMonth = generateYearMonthStrToObj(orderEntity.getPostponeMonth().toString());
				if (serviceYearMonth.getYear() > postponeYearMonth.getYear()) {
					billResult.setPostponeDue(1);
				}
				if (serviceYearMonth.getYear() == postponeYearMonth.getYear()) {
					if (serviceYearMonth.getMonth() >= postponeYearMonth.getMonth()) {
						billResult.setPostponeDue(1);
					}
				}
			}

			StringStatusResult payYearMonth = new StringStatusResult();
			payYearMonth.setContent("-");
			List<StringStatusResult> backMonthStatus = new ArrayList<>();
			if (orderEntity.getCalculateType() != null) {
				if (orderEntity.getCalculateType() != SOIN_BACK_CALCULATE) {
					payYearMonth.setContent(orderEntity.getYear() + String.format("%02d", orderEntity.getStartMonth()));
					billResult.setPayMonth(payYearMonth);
				}

				if (orderEntity.getCalculateType() != SOIN_NORMAL_CALCULATE) {
					billResult.setBackMonth(backMonthStatus);
				}
			} else {
				payYearMonth.setContent(orderEntity.getYear() + String.format("%02d", orderEntity.getStartMonth()));
				billResult.setPayMonth(payYearMonth);
				billResult.setBackMonth(backMonthStatus);
			}

			List<AryaSoinEntity> details = new ArrayList<>(orderEntity.getDetails());

			Collections.sort(details, new Comparator<AryaSoinEntity>() {
				@Override
				public int compare(AryaSoinEntity o1, AryaSoinEntity o2) {
					int c1 = Integer.parseInt(o1.getYear() + String.format("%02d", o1.getMonth()));
					int c2 = Integer.parseInt(o2.getYear() + String.format("%02d", o2.getMonth()));
					return c1 - c2;
				}
			});

			//标记缴纳月份是否失败
			for (AryaSoinEntity soinEntity : details) {
				StringStatusResult stringStatusResult = new StringStatusResult();
				if (soinEntity.getCalculateType() != null && soinEntity.getCalculateType() == SOIN_BACK_CALCULATE) {
					//补缴的
					stringStatusResult.setContent(soinEntity.getMonth() + "月");
					backMonthStatus.add(stringStatusResult);
				} else {

				}

				if (soinEntity.getStatusCode() != null && soinEntity.getStatusCode() == SOIN_PAY_FAILED) {
					stringStatusResult.setStatus(SoinConstants.RESULT_WRONG);
				}
			}
			billResult.setCollectionSubtotal(SoinUtil.turnBigDecimalHalfRoundUpToString(orderEntity.getPayment(), 3));
			billResult.setChargeSubtotal(SoinUtil.turnBigDecimalHalfRoundUpToString(orderEntity.getTotalOutPayment(), 3));
			billResult.setSalesman(orderEntity.getSalesmanName());
			billResult.setSupplier(supplierEntity.getSupplierName());
			if (orderEntity.getModifyType() == null) {
				billResult.setModify("-");
			} else {
				billResult.setModify(NAME_LIST.get(orderEntity.getModifyType()));
			}
			result.getOrders().add(billResult);
		}

		return result;
	}

	@Override
	public String extend(String corpId) {
		BillExtendResult result = new BillExtendResult();
		if (isBlank(corpId)) {
			return "缺少客户id";
		}
		//先查出一个最新的订单
		AryaSoinOrderEntity latestOrder = null;
		if (corpId.equals(String.valueOf(PERSON_CUSTOMER))) {
			//个人订单
			latestOrder = soinOrderDao.findPersonCustomerLatestWithoutDecrease();
		} else {
			latestOrder = soinOrderDao.findCorpLatestWithoutDecrease(corpId);
		}
		if (latestOrder == null) {
			return "无订单可顺延";
		}

		// 查询公司最近月份的所有有效订单
		List<AryaSoinOrderEntity> latestOrderEntityList = null;
		if (corpId.equals(String.valueOf(PERSON_CUSTOMER))) {
			//查询个人订单
			latestOrderEntityList = soinOrderDao.findPersonCustomerOrdersWithoutDecrease(latestOrder.getServiceYearMonth());
		} else {
			latestOrderEntityList = soinOrderDao.findCorpCustomerOrdersWithoutDecrease(corpId, latestOrder.getServiceYearMonth());
		}

		Map<String, StringBuffer> errorMsgMap = new HashMap<>();
		int nullStatusSoinDetailCount = 0;
		//检查没有做缴纳操作的订单
		for (AryaSoinOrderEntity orderEntity : latestOrderEntityList) {
			for (AryaSoinEntity soinEntity : orderEntity.getDetails()) {
				if (soinEntity.getStatusCode() == null) {
					nullStatusSoinDetailCount++;
					StringBuffer msg = errorMsgMap.get(orderEntity.getId());
					if (msg == null) {
						msg = new StringBuffer(orderEntity.getSoinPersonName() + "存在没做操作的订单:");
						errorMsgMap.put(orderEntity.getId(), msg);
					}
					if (soinEntity.getCalculateType() == null) {
						msg.append(soinEntity.getYear() + "年" + soinEntity.getMonth() + "月");
					} else {
						msg.append(soinEntity.getYear() + "年" + soinEntity.getMonth() + "月" + (soinEntity.getCalculateType() == SOIN_BACK_CALCULATE ? "补缴" : ""));
					}
				}
			}
		}
		if (nullStatusSoinDetailCount > 0) {
			return join(errorMsgMap.values(), "/n") + "有" + nullStatusSoinDetailCount + "条订单详情尚未处理,无法批量顺延。";
		}

		List<AryaSoinOrderEntity> newOrders = new ArrayList<>();
		List<SoinInOrDecreaseEntity> inOrDecreaseEntities = new ArrayList<>();

		Map<String, SoinTypeVersionEntity> versionEntityMap = new HashMap<>();//社保类型版本map,key:typeId+year+month+(b or n),b:补缴 n:正常
		Map<String, AryaSoinPersonEntity> soinPersonEntityMap = new HashMap<>();//参保人map,key:id
		//获取当前用户
		SysUserModel currentUser = sysUserService.getCurrentSysUser();

		//批次
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmmss");
		String batchNo = dateFormat.format(new Date(System.currentTimeMillis()));

		SoinImportBatchEntity batchEntity = new SoinImportBatchEntity();
		batchEntity.setId(Utils.makeUUID());
		batchEntity.setBatchNo(batchNo);
		batchEntity.setOperatorId(currentUser.getId());
		batchEntity.setCreateTime(System.currentTimeMillis());
		batchEntity.setImportFileName("");

		//开始顺延
		int no = 0;
		for (AryaSoinOrderEntity latestOrderEntity : latestOrderEntityList) {
			if (corpId.equals(String.valueOf(PERSON_CUSTOMER))) {
				YearMonth serviceYearMonth = generateYearMonthStrToObj(latestOrderEntity.getServiceYearMonth().toString());
				YearMonth postponeYearMonth = generateYearMonthStrToObj(latestOrderEntity.getPostponeMonth().toString());
				if (serviceYearMonth.getYear() > postponeYearMonth.getYear()) {
					throw new AryaServiceException(ErrorCode.CODE_SOIN_PERSONAL_ORDER_EXIST_OVERDUE);
				}
				if (serviceYearMonth.getYear() == postponeYearMonth.getYear()) {
					if (serviceYearMonth.getMonth() >= postponeYearMonth.getMonth()) {
						throw new AryaServiceException(ErrorCode.CODE_SOIN_PERSONAL_ORDER_EXIST_OVERDUE);
					}
				}
			}
			AryaSoinOrderEntity newOrder = new AryaSoinOrderEntity();
			try {
				BeanUtils.copyProperties(newOrder, latestOrderEntity);
				newOrder.setId(Utils.makeUUID());
				newOrder.setOrderNo(generateOrderNo(no++));
				newOrder.setDetails(new HashSet<>());
				newOrder.setCreateTime(System.currentTimeMillis());
				newOrder.setBatchId(null);
				newOrder.setPayedMonth(null);
				newOrder.setStatusCode(SoinOrderStatus.ORDER_PAYED);
				newOrder.setBatchId(batchEntity.getId());
				newOrder.setModifyType(SoinOrderBillConstants.getCode(SOIN_PERSON_EXTEND));
			} catch (Exception e) {
				elog.error("顺延订单复制订单信息出错", e);
				continue;
			}
			SoinInOrDecreaseEntity inOrDecreaseEntity = new SoinInOrDecreaseEntity();

			int normalCalculateCount = 0;//正常计算详情数
			int backCalculateCount = 0;//补缴计算详情数
			for (AryaSoinEntity soinEntity : latestOrderEntity.getDetails()) {
				AryaSoinEntity newSoinEntity = new AryaSoinEntity();
				newSoinEntity.setStatusCode(null);
				try {
					BeanUtils.copyProperties(newSoinEntity, soinEntity);
					newSoinEntity.setId(Utils.makeUUID());
					//默认缴纳成功
					newSoinEntity.setStatusCode(SOIN_PAY_SUCCESS);
					newSoinEntity.setCreateTime(System.currentTimeMillis());
					newSoinEntity.setOrderId(newOrder.getId());
				} catch (Exception e) {
					elog.error("顺延补缴订单时复制信息失败", e);
					newOrder.getDetails().remove(newSoinEntity);
					continue;
				}
				newOrder.getDetails().add(newSoinEntity);
				if (soinEntity.getCalculateType() == SOIN_NORMAL_CALCULATE) {
					int nextYear = soinEntity.getYear();
					int nextMonth = soinEntity.getMonth() + 1;
					if (nextMonth > 12) {
						nextYear++;
						nextMonth = 1;
					}
					SoinTypeVersionEntity versionEntity = null;
					int calculateType = SOIN_NORMAL_CALCULATE;
					//正常缴纳
					if (soinEntity.getStatusCode() == SOIN_PAY_SUCCESS) {
						//缴纳成功
						versionEntity = versionEntityMap.get(soinEntity.getSoinTypeId() + nextYear + nextMonth + "n");
						if (versionEntity == null) {
							versionEntity = soinTypeVersionDao.findEffectVersionBySoinTypeIdAndYearMonth(soinEntity.getSoinTypeId(), nextYear, nextMonth, SOIN_VERSION_TYPE_NORMAL, null);
							if (versionEntity == null) {
								log.warn("因有效社保类型版本没有找到，因此略过此订单的顺延: " + soinEntity.getId());
								newOrder.getDetails().remove(newSoinEntity);
								continue;
							}
							versionEntityMap.put(soinEntity.getSoinTypeId() + nextYear + nextMonth + "n", versionEntity);
						}
						newSoinEntity.setYear(nextYear);
						newSoinEntity.setMonth(nextMonth);
						newSoinEntity.setModifyType(SoinOrderBillConstants.getCode(SOIN_PERSON_EXTEND));
					} else {
						//缴纳失败,顺延成补缴订单
						versionEntity = versionEntityMap.get(soinEntity.getSoinTypeId() + soinEntity.getYear() + soinEntity.getMonth() + "b");
						calculateType = SOIN_BACK_CALCULATE;
						if (versionEntity == null) {
							versionEntity = soinTypeVersionDao.findEffectVersionBySoinTypeIdAndYearMonth(soinEntity.getSoinTypeId(), soinEntity.getYear(), soinEntity.getMonth(), SOIN_VERSION_TYPE_BACK, null);
							if (versionEntity == null) {
								log.warn("因有效社保类型版本没有找到，因此略过此订单的顺延: " + soinEntity.getId());
								newOrder.getDetails().remove(newSoinEntity);
								continue;
							}
							versionEntityMap.put(soinEntity.getSoinTypeId() + soinEntity.getYear() + soinEntity.getMonth() + "b", versionEntity);
						}
						newSoinEntity.setCalculateType(SOIN_BACK_CALCULATE);//标记成补缴计算
						newSoinEntity.setModifyType(SoinOrderBillConstants.getCode(SOIN_PERSON_EXTEND));
					}
					//开始重新计算
					boolean isHouseFund = false;
					if (soinEntity.getHouseFundBase() != null) {
						isHouseFund = true;
					}

					AryaSoinPersonEntity personEntity = soinPersonEntityMap.get(latestOrderEntity.getSoinPersonId());
					if (personEntity == null) {
						personEntity = soinPersonDao.find(latestOrderEntity.getSoinPersonId());
						if (personEntity == null) {
							continue;
						}
						soinPersonEntityMap.put(latestOrderEntity.getSoinPersonId(), personEntity);
					}
					inOrDecreaseEntity.setIdcardNo(personEntity.getIdcardNo());
					userSoinService.assignmentSoinMoney(newSoinEntity, isHouseFund, versionEntity, soinEntity.getFees(), soinEntity.getFeesOut(), soinEntity.getOther(), soinEntity.getSoinBase(), soinEntity.getHouseFundBase(), soinEntity.getPersonalHouseFundPercentage(), soinEntity.getCompanyHouseFundPercentage(), newSoinEntity.getYear(), newSoinEntity.getMonth(), personEntity.getLastPayedYear(), personEntity.getLastPayedMonth(), calculateType);
				} else {
					//补缴
					if (soinEntity.getStatusCode() == SOIN_PAY_SUCCESS) {
						//补缴的缴纳成功,不用再顺延了
						newOrder.getDetails().remove(newSoinEntity);
						continue;
					} else {
						//缴纳失败,继续顺延成补缴订单
						AryaSoinPersonEntity personEntity = soinPersonEntityMap.get(latestOrderEntity.getSoinPersonId());
						if (personEntity == null) {
							personEntity = soinPersonDao.find(latestOrderEntity.getSoinPersonId());
							if (personEntity == null) {
								continue;
							}
							soinPersonEntityMap.put(latestOrderEntity.getSoinPersonId(), personEntity);
						}
						inOrDecreaseEntity.setIdcardNo(personEntity.getIdcardNo());
					}
				}
			}
			if (newOrder.getDetails().isEmpty()) {
				continue;
			}
			//开始赋值订单的缴纳年月 补缴年月 期数 金额等
			Integer normalStartYear = null;
			Integer normalStartMonth = null;
			Integer normalCount = null;
			Integer backStartYear = null;
			Integer backStartMonth = null;
			Integer backCount = null;
			BigDecimal otherPay = BigDecimal.ZERO;//其他费用
			BigDecimal outFeesTotal = BigDecimal.ZERO;//出账管理费总计
			BigDecimal inFeesTotal = BigDecimal.ZERO;//收账管理费总计
			BigDecimal paymentTotal = BigDecimal.ZERO;//总收账
			BigDecimal totalOutPayment = BigDecimal.ZERO;//总出账
			for (AryaSoinEntity soinEntity : newOrder.getDetails()) {
				if (soinEntity.getCalculateType() == SOIN_NORMAL_CALCULATE) {
					normalCalculateCount++;
					if (normalStartYear == null || normalStartYear < soinEntity.getYear()) {
						normalStartYear = soinEntity.getYear();
						normalStartMonth = soinEntity.getMonth();
					} else if (normalStartYear == soinEntity.getYear() && normalStartMonth < soinEntity.getMonth()) {
						normalStartMonth = soinEntity.getMonth();
					}
					if (normalCount == null) {
						normalCount = 1;
					} else {
						normalCount++;
					}
				} else {
					backCalculateCount++;
					if (backStartYear == null || backStartYear < soinEntity.getYear()) {
						backStartYear = soinEntity.getYear();
						backStartMonth = soinEntity.getMonth();
					} else if (backStartYear == soinEntity.getYear() && backStartMonth < soinEntity.getMonth()) {
						backStartMonth = soinEntity.getMonth();
					}
					if (backCount == null) {
						backCount = 1;
					} else {
						backCount++;
					}
				}
				otherPay = SoinUtil.bigDecimalAdd(otherPay, soinEntity.getOther());
				outFeesTotal = SoinUtil.bigDecimalAdd(outFeesTotal, soinEntity.getFeesOut());
				inFeesTotal = SoinUtil.bigDecimalAdd(inFeesTotal, soinEntity.getFees());
				paymentTotal = SoinUtil.bigDecimalAdd(paymentTotal, soinEntity.getTotalPayment());
				totalOutPayment = SoinUtil.bigDecimalAdd(totalOutPayment, soinEntity.getTotalOutPayment());
			}
			newOrder.setYear(normalStartYear == null ? 0 : normalStartYear);
			newOrder.setStartMonth(normalStartMonth == null ? 0 : normalStartMonth);
			newOrder.setCount(normalCount == null ? 0 : normalCount);
			newOrder.setBackYear(backStartYear);
			newOrder.setBackStartMonth(backStartMonth);
			newOrder.setBackCount(backCount);
			newOrder.setOtherPay(otherPay);
			newOrder.setFeeOut(outFeesTotal);
			newOrder.setFees(inFeesTotal);
			newOrder.setPayment(paymentTotal);
			newOrder.setTotalOutPayment(totalOutPayment);

			if (normalCalculateCount == 0) {
				newOrder.setCalculateType(SOIN_BACK_CALCULATE);
			} else if (backCalculateCount == 0) {
				newOrder.setCalculateType(SOIN_NORMAL_CALCULATE);
			} else {
				newOrder.setCalculateType(SOIN_MIX_CALCULATE);
			}

			String serviceYearMonth = latestOrderEntity.getServiceYearMonth().toString();
			Integer serviceYear = Integer.parseInt(serviceYearMonth.substring(0, 4));
			Integer serviceMonth = Integer.parseInt(serviceYearMonth.substring(4, 6));

			if (serviceMonth + 1 > 12) {
				serviceYear++;
				serviceMonth = 1;
			} else {
				serviceMonth++;
			}
			newOrder.setServiceYearMonth(Integer.parseInt(serviceYear + String.format("%02d", serviceMonth)));
			newOrders.add(newOrder);


			//生成增减员状态
			inOrDecreaseEntity.setId(Utils.makeUUID());
			inOrDecreaseEntity.setDistrictIds(latestOrderEntity.getDistrictId());
			inOrDecreaseEntity.setYear(serviceYear);
			inOrDecreaseEntity.setMonth(serviceMonth);
			inOrDecreaseEntity.setOperateType(SoinOrderBillConstants.getCode(SOIN_PERSON_EXTEND));
			inOrDecreaseEntity.setOrderId(newOrder.getId());
			inOrDecreaseEntity.setStatus(0);
			inOrDecreaseEntity.setSupplierId(latestOrderEntity.getSupplierId());
			inOrDecreaseEntity.setCreateTime(System.currentTimeMillis());
			inOrDecreaseEntities.add(inOrDecreaseEntity);
		}
		StringBuffer logMsg = new StringBuffer("【对账单顺延】批次号：" + batchNo + ",订单个数：" + latestOrderEntityList.size() + ",原服务年月为：" + latestOrder.getServiceYearMonth() + "。");
		try {
			batchDao.create(batchEntity);
			soinOrderDao.create(newOrders);
			soinInOrDecreaseDao.create(inOrDecreaseEntities);
			opLogService.successLog(SOIN_ORDER_BILL_EXTEND, logMsg, log);
		} catch (Exception e) {
			elog.error("保存顺延订单失败!", e);
			opLogService.failedLog(SOIN_ORDER_BILL_EXTEND, logMsg, log);
		}
		return "成功";
	}

	private SoinTypeMinMaxBaseModel getMinMaxBase(SoinRuleEntity soinRuleEntity, SoinTypeMinMaxBaseModel model) {
		if (soinRuleEntity == null) {
			return model;
		}

		if (soinRuleEntity.getMinBase().compareTo(model.getMinBase()) <= 0) {
			model.setMinBase(soinRuleEntity.getMinBase());
		}

		if (soinRuleEntity.getMaxBase().compareTo(model.getMaxBase()) >= 0) {
			model.setMaxBase(soinRuleEntity.getMaxBase());
		}
		return model;
	}

	/**
	 * 校验年月
	 *
	 * @param yearMonthStr
	 * @return
	 */

	boolean checkYearMonth(String yearMonthStr) {
		if (isBlank(yearMonthStr)) {
			return false;
		}
		if (!isNumeric(yearMonthStr)) {
			return false;
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
		Date date;
		try {
			date = simpleDateFormat.parse(yearMonthStr);
			String yearMontCompareStr = simpleDateFormat.format(date);
			if (!yearMonthStr.equals(yearMontCompareStr)) {
				return false;
			}
		} catch (ParseException e) {
			return false;
		}
		return true;
	}

	/**
	 * 将年月字符串转成对象
	 *
	 * @param yearMonthStr
	 * @return
	 */
	YearMonth generateYearMonthStrToObj(String yearMonthStr) {
		YearMonth yearMonth = new YearMonth();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
		Date date;
		try {
			date = simpleDateFormat.parse(yearMonthStr);
		} catch (ParseException e) {
			return yearMonth;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
		String[] yearMonthS = dateFormat.format(date).split("-");
		yearMonth.setYear(Integer.parseInt(yearMonthS[0]));
		yearMonth.setMonth(Integer.parseInt(yearMonthS[1]));
		return yearMonth;
	}


	public static class YearMonth {
		int year;

		int month;

		public int getYear() {
			return year;
		}

		public void setYear(int year) {
			this.year = year;
		}

		public int getMonth() {
			return month;
		}

		public void setMonth(int month) {
			this.month = month;
		}

	}

	/**
	 * 生成导入订单的编号,年月日时分秒加四位递增,所以每次导入最多9999条
	 *
	 * @return
	 */
	private static String generateOrderNo(int no) {
		String orderNo = new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date(System.currentTimeMillis()));
		orderNo += String.format("%04d", no);
		return orderNo;
	}

}