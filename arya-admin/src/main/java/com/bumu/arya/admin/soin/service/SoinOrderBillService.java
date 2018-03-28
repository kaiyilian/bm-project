package com.bumu.arya.admin.soin.service;

import com.bumu.arya.admin.soin.controller.command.*;
import com.bumu.arya.admin.soin.result.OrderExportResult;
import com.bumu.arya.admin.soin.model.SoinTypeMinMaxBaseModel;
import com.bumu.arya.admin.soin.model.entity.SoinOrderBillCalculateModel;
import com.bumu.arya.admin.soin.model.entity.SoinOrderBillCalculateStructure;
import com.bumu.arya.admin.soin.model.entity.SoinOrderBillCountModel;
import com.bumu.arya.admin.soin.model.entity.SoinOrderBillExcelReadModel;
import com.bumu.arya.admin.soin.result.*;
import com.bumu.arya.command.OrderBillListQueryCommand;
import com.bumu.arya.soin.model.entity.AryaSoinEntity;
import com.bumu.arya.soin.model.entity.AryaSoinOrderEntity;
import com.bumu.arya.soin.model.entity.SoinTypeVersionEntity;
import com.bumu.arya.response.HttpResponse;
import com.bumu.exception.AryaServiceException;
import com.bumu.arya.common.result.ZtreeDistrictListResult;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by CuiMengxin on 16/8/2.
 */
@Transactional
public interface SoinOrderBillService {

	/**
	 * 获取已开通的社保地区
	 *
	 * @return
	 * @throws AryaServiceException
	 */
	ZtreeDistrictListResult getAllSoinDistricts() throws AryaServiceException;

	/**
	 * 保存文件并计算对账单
	 *
	 * @param fileName
	 * @return
	 * @throws AryaServiceException
	 */
	@Transactional (readOnly = false)
	OrderBillUploadAndCalculateListResult uploadAndCalculateOrderBill(String fileName) throws AryaServiceException;

	/**
	 * 计算对账单
	 *
	 * @param fileName
	 * @return
	 * @throws AryaServiceException
	 */
	SoinOrderBillCalculateStructure readFileAndCalculateOrderBill(String fileName) throws AryaServiceException;

	/**
	 * 通过Excel的读取模型转换成计算模型
	 *
	 * @param readModels
	 * @return
	 * @throws AryaServiceException
	 */
	List<SoinOrderBillCalculateModel> generateAndCheckSoinOrderBillCalculateModelsFromReadModels(List<SoinOrderBillExcelReadModel> readModels, SoinOrderBillCalculateStructure structure) throws AryaServiceException;

	/**
	 * 校验计算模型的户籍地址
	 *
	 * @param structure
	 * @throws AryaServiceException
	 */
	void checkOrderBillHukouDistrict(SoinOrderBillCalculateStructure structure) throws AryaServiceException;

	/**
	 * 校验参保地区和社保类型
	 *
	 * @param structure
	 * @throws AryaServiceException
	 */
	void checkOrderBillSoinDistrictAndSoinType(SoinOrderBillCalculateStructure structure) throws AryaServiceException;

	/**
	 * 校验参保人是否存在
	 *
	 * @param structure
	 * @throws AryaServiceException
	 */
	void checkOrderBillSoinPerson(SoinOrderBillCalculateStructure structure) throws AryaServiceException;

	/**
	 * 判断是否需要增员
	 *
	 * @param structure
	 * @throws AryaServiceException
	 */
	@Transactional(readOnly = true)
	void checkSoinPersonIncrease(SoinOrderBillCalculateStructure structure) throws AryaServiceException;

	/**
	 * 校验企业客户是否存在
	 *
	 * @throws AryaServiceException
	 */
	void checkOrderBillCorpCustomer(SoinOrderBillCalculateStructure structure) throws AryaServiceException;

	/**
	 * 校验供应商
	 *
	 * @param structure
	 * @throws AryaServiceException
	 */
	void checkOrderBillSupplier(SoinOrderBillCalculateStructure structure) throws AryaServiceException;

	/**
	 * 校验业务员
	 *
	 * @param structure
	 * @throws AryaServiceException
	 */
	void checkOrderBillSalesman(SoinOrderBillCalculateStructure structure) throws AryaServiceException;

	/**
	 * 计算订单
	 *
	 * @param structure
	 * @throws AryaServiceException
	 */
	void soinOrderBillCalculate(SoinOrderBillCalculateStructure structure) throws AryaServiceException;

	/**
	 * 校验文件内订单重复
	 *
	 * @param structure
	 * @throws AryaServiceException
	 */
	void checkDuplicateSoinOrderBillInFile(SoinOrderBillCalculateStructure structure) throws AryaServiceException;

	/**
	 * 校验合法的订单在数据库中是否存在
	 *
	 * @param structure
	 * @throws AryaServiceException
	 */
	void checkSoinOrderBillExistInDB(SoinOrderBillCalculateStructure structure) throws AryaServiceException;

	/**
	 * 统计
	 *
	 * @param structure
	 * @return
	 * @throws AryaServiceException
	 */
	SoinOrderBillCountModel soinOrderBillCount(SoinOrderBillCalculateStructure structure) throws AryaServiceException;


	/**
	 * 确认导入
	 *
	 * @param command
	 * @throws AryaServiceException
	 */
	String soinOrderBillImportConfirm(SoinOrderBillImportConfirmCommand command) throws AryaServiceException;

	/**
	 * 查询所有客户,App和个人特殊处理
	 *
	 * @return
	 * @throws AryaServiceException
	 */
	SoinOrderCustomerListResult getAllSoinOrderCustomers() throws AryaServiceException;

	/**
	 * 删除订单,导入的订单置删除,线上订单置为取消
	 *
	 * @param deleteListCommand
	 * @throws AryaServiceException
	 */
	void deleteSoinOrders(IdListCommand deleteListCommand) throws AryaServiceException;

	/**
	 * 订单缴纳成功
	 *
	 * @param command
	 * @throws AryaServiceException
	 */
	void paySuccessSoinOrders(IdListCommand command) throws AryaServiceException;

	/**
	 * 全部订单缴纳成功
	 *
	 * @param command
	 * @throws AryaServiceException
	 */
	void paySuccessAllSoinOrders(OrderBillListQueryCommand command) throws AryaServiceException;

	/**
	 * 订单缴纳失败
	 *
	 * @param command
	 * @throws AryaServiceException
	 */
	void payFailedSoinOrders(SoinOrderBillPayFailedCommand command) throws AryaServiceException;

	/**
	 * 全部订单缴纳失败
	 *
	 * @param command
	 * @throws AryaServiceException
	 */
	void payFailedAllSoinOrders(OrderBillListQueryCommand command) throws AryaServiceException;

	/**
	 * 条件查询对账单
	 *
	 * @param command
	 * @throws AryaServiceException
	 */
	SoinOrderBillQueryListResult queryOrderBill(QueryOrderBillCommand command) throws AryaServiceException;


	/**
	 * 供应商查询
	 *
	 * @param district_id district_id 如果district_id为空则返回所有供应商
	 * @return
	 */
	HttpResponse<SoinSupplierListResult> suppliersList(String district_id);

	/**
	 * 订单查询
	 *
	 * @param order 封装了查询参数
	 * @return 相应信息
	 */
	@Transactional(readOnly = true)
    SoinBillListResult billManageQuery(OrderBillListQueryCommand order);

	/**
	 * 订单查询(新)
	 * @param order
	 * @return
	 */
	@Transactional(readOnly = true)
	SoinBillListResult billManageNewQuery(OrderBillListQueryCommand order);

	/**
	 * 订单详情列表查询
	 *
	 * @param id
	 * @return
	 */
	@Transactional(readOnly = true)
    SoinBillDetailList billManageDetailList(String id);

	/**
	 * 查询社保缴纳详情
	 *
	 * @param id
	 * @return
	 */
	@Transactional(readOnly = true)
    SoinBillDetailResult billManageDetail(String id);

	/**
	 * 导出对账单
	 * @param order
	 * @param response
	 * @return
	 */
	@Transactional(readOnly = true)
	HttpResponse export(OrderBillListQueryCommand order, HttpServletResponse response);

	/**
	 * 导出增减员excel
	 * @param order
	 * @param response
	 * @return
	 */
	@Transactional(readOnly = true)
	HttpResponse employeeModifyExport(OrderBillListQueryCommand order, HttpServletResponse response);
	/**
	 *
	 * @param order
	 * @param response
	 * @return
	 */
	@Transactional(readOnly = true)
	EmployTypeListResult employeeTypeDetailQuery(OrderBillListQueryCommand order, HttpServletResponse response);

	/**
	 * 按批次导出
	 *
	 * @param batchId
	 * @param response
	 * @return
	 */
	@Transactional(readOnly = true)
	HttpResponse batchExport(String batchId, HttpServletResponse response);

	/**
	 * 调整对账单的其他费用
	 *
	 * @param command
	 * @throws AryaServiceException
	 */
	void adjustOrderBillsOtherPayment(AdjustSoinOrderOtherPaymentCommand command) throws AryaServiceException;

	/**
	 * 找出社保类型版本中的最低最高社保基数
	 *
	 * @param soinTypeVersionEntity
	 * @return
	 */
	SoinTypeMinMaxBaseModel getMinMaxBase(SoinTypeVersionEntity soinTypeVersionEntity);

	/**
	 * 按照查询条件查询所有订单
	 *
	 * @param command
	 * @return
	 * @throws AryaServiceException
	 */
	List<AryaSoinOrderEntity> queryAllOrders(OrderBillListQueryCommand command) throws AryaServiceException;

	/**
	 * 判断社保记录是否满足查询条件
	 *
	 * @param command
	 * @return
	 */
	boolean checkSoinIsInCommand(AryaSoinEntity soinEntity, OrderBillListQueryCommand command);

	/**
	 * 下载导入模板
	 *
	 * @param response
	 */
	void exportTemplateDownload(HttpServletResponse response,String templateType) throws IOException;

	/**
	 * 对账单查询结果做导出时再进一步加工，把各个险种的总金额和对账单总金额重新计算四舍五入后的值，
	 *
	 * @param orderExportResult
	 */
	void processOrderBillResult(OrderExportResult orderExportResult);

	/**
	 * 批量顺延订单查询
	 *
	 * @param command
	 * @return
	 */
	BillExtendResult extendQuery(BillExtendCommand command);

	/**
	 * 顺延
	 *
	 * @param id
	 */
	String extend(String id);

}