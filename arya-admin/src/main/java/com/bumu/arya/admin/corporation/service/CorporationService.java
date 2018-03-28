package com.bumu.arya.admin.corporation.service;

import com.bumu.arya.admin.corporation.controller.command.*;
import com.bumu.arya.admin.corporation.model.CorpModel;
import com.bumu.arya.admin.corporation.result.*;
import com.bumu.arya.model.entity.CorpBusinessDetailEntity;
import com.bumu.arya.model.entity.CorporationEntity;
import com.bumu.bran.model.entity.BranCorporationEntity;
import com.bumu.common.result.JQDatatablesPaginationResult;
import com.bumu.exception.AryaServiceException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * Created by bumu-zhz on 2015/11/11.
 */
@Transactional
public interface CorporationService {
	/**
	 * 公司logo读取接口
	 */
	String CORP_LOGO_IMAGE_MAP = "admin/corporation/detail/logo?file_id=";
	/**
	 * 公司营业执照读取接口
	 */
	String CORP_LICENSE_IMAGE_MAP = "admin/corporation/detail/license?file_id=";
	String CORP_IMAGE_MAP = "admin/corporation/image/detail?file_id=";

	List<CorporationEntity> retrieveCorporation() throws AryaServiceException;

	/**
	 * 获取集团(母公司)列表
	 *
	 * @return
	 * @throws AryaServiceException
	 */
	CorpListResult getCorpGroupList() throws AryaServiceException;

	/**
	 * 获取子公司列表
	 *
	 * @param corpName
	 * @param corpShortName
	 * @param contactPerson
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws AryaServiceException
	 */
	JQDatatablesPaginationResult getSubsidiaryList(String corpName, String corpShortName, String contactPerson, int page, int pageSize) throws AryaServiceException;

	/**
	 * 获取公司详情
	 *
	 * @param corpId
	 * @return
	 * @deprecated
	 */
	CorpListResult.CorpInfo getCorpDetail(String corpId);

	/**
	 * 获取公司详情
	 *
	 * @param corpId
	 * @return
	 */
	GetGroupOrCorporationDetailResult getCorpDetailInfo(String corpId);

	/**
	 * 获取公司一键入职详情
	 *
	 * @param corpId
	 * @return
	 */
	GetCorporationEntryDetailResult getCorpEntryDetail(String corpId);

	/**
	 * 获取公司福库详情
	 *
	 * @param corpId
	 * @return
	 */
	GetCorporationWelfareDetailResult getCorpWelfareDetail(String corpId);

	/**
	 * 生成checkinCode
	 *
	 * @return
	 */
	String generateCheckinCode();

	/**
	 * 查询公司，如果公司是集团则子公司也一并查询
	 * key是公司id，value是公司实体
	 *
	 * @param groupId
	 * @return
	 */
	Map<String, CorporationEntity> getGroupAndSubCorpMap(String groupId);

	/**
	 * 查询集团下的所有部门名称并组装成Map返回
	 *
	 * @param groupId
	 * @return
	 */
	Map<String, String> getGroupDepartmentsMap(String groupId);

	/**
	 * 更新公司信息
	 *
	 * @param model
	 * @deprecated
	 */
	CreateUpdateCorpResult updateCorpInfo(CorpModel model);

	/**
	 * 更新公司信息
	 *
	 * @param command
	 * @return
	 */
	CreateUpdateCorpResult updateCorporationInfo(CreateOrUpdateCorporationCommand command) throws Exception;

	/**
	 * 新增branCorp
	 *
	 * @param model
	 */
	BranCorporationEntity createBranCorp(CorpModel model) throws AryaServiceException;

	/**
	 * 更新branCorp
	 *
	 * @param model
	 * @return
	 * @throws AryaServiceException
	 */
	BranCorporationEntity updateBranCorp(String branCorpId, CorpModel model) throws AryaServiceException;

	/**
	 * 新增考勤业务类型详情
	 *
	 * @param corpId
	 * @param business
	 * @return
	 * @throws AryaServiceException
	 */
	CorpBusinessDetailEntity creatCorpBusinessDetail(String corpId, int business)throws AryaServiceException;

	/**
	 * 新增公司
	 *
	 * @param model
	 * @deprecated
	 */
	CreateUpdateCorpResult createNewCorp(CorpModel model);


	/**
	 * 新增公司
	 *
	 * @param command
	 * @return
	 */
	CreateUpdateCorpResult createNewCorporation(CreateOrUpdateCorporationCommand command);

	/**
	 * 更新一键入职信息
	 *
	 * @param corpModel
	 * @return
	 */
	CreateUpdateCorpResult updateCorpEntryInfo(CorpModel corpModel);

	/**
	 * 更新福库信息
	 *
	 * @param corpId
	 * @param parentId
	 * @param welfareCorpName
	 * @return
	 */
	CreateUpdateCorpResult updateCorpWelfareInfo(String corpId,String parentId,String welfareCorpName);

	/**
	 * 删除公司
	 *
	 * @param corpId
	 */
	void deleteCorp(String corpId, String operator);

	/**
	 * 判断公司是否已使用
	 *
	 * @param aryaCorpId
	 * @return
	 * @throws AryaServiceException
	 */
	boolean isCorpInUse(String aryaCorpId) throws AryaServiceException;

	/**
	 * 上传企业图片
	 *
	 * @param branCorpId
	 * @param file
	 */
	void uploadCorpImage(String branCorpId, MultipartFile file, String operator) throws AryaServiceException;

	/**
	 * 获取企业图片列表
	 *
	 * @param aryaCorpId
	 * @return
	 */
	CorpImageListResult getCorpImageList(String aryaCorpId);

	/**
	 * 删除企业照片
	 *
	 * @param imageId
	 * @throws AryaServiceException
	 */
	void deleteCorpImageById(String imageId) throws AryaServiceException;

	/**
	 * 新增通用部门
	 *
	 * @param command
	 * @throws AryaServiceException
	 */
	AryaDepartmentDetailResult createAryaDepartment(CreateAryaDepartmentCommand command) throws AryaServiceException;

	/**
	 * 获取通用部门详情
	 *
	 * @param departmentId
	 * @return
	 * @throws AryaServiceException
	 */
	AryaDepartmentDetailResult getAryaDepartmentDetail(String departmentId) throws AryaServiceException;

	/**
	 * 修改部门
	 *
	 * @param command
	 */
	void updateDepartment(UpdateAryaDepartmentCommand command);

	/**
	 * 删除通用部门
	 *
	 * @param departmentId
	 * @throws AryaServiceException
	 */
	void deleteAryaDepartment(String departmentId) throws AryaServiceException;

	/**
	 * 获取薪资计算的组织树
	 *
	 * @return
	 */
	OrganizationTreeResult generateSalaryOrganizationTree();

	/**
	 * 获取企业管理的组织树
	 *
	 * @return
	 */
	OrganizationTreeResult generateOrganizationTree();

	/**
	 * 获取入职的入职树
	 *
	 * @return
	 */
	OrganizationTreeResult generateEntryOrganizationTree();

	/**
	 *  获取一键入职和福库组织树
	 *
	 * @param businessType
	 * @return
	 */
	OrganizationTreeResult generateEntryAndWelfareOrganizationTree(int businessType );

	/**
	 * 获取多个集团的所有部门
	 *
	 * @param groupIds
	 * @return
	 */
	List<OrganizationTreeResult.OrganizationResult> getGroupsAllAryaDepartmentsTreeResult(List<String> groupIds);

	/**
	 * 获取多个集团的所有子公司
	 *
	 * @param groupIds
	 * @return
	 */
	List<OrganizationTreeResult.OrganizationResult> getGroupsAllSubCorpsTreeResult(List<String> groupIds);

	/**
	 * 获取企业用户列表
	 *
	 * @param corpId
	 * @return
	 * @throws AryaServiceException
	 */
	CorpAdminListResult getCorpAdminList(String corpId) throws AryaServiceException;

	/**
	 * 新增企业管理员
	 *
	 * @param command
	 * @throws AryaServiceException
	 */
	void addCorpAdmin(CreateUpdateCorpAdminCommand command, String operator) throws AryaServiceException;

	/**
	 * 更新企业管理员
	 *
	 * @param command
	 * @param operator
	 * @throws AryaServiceException
	 */
	void updateCorpAdmin(CreateUpdateCorpAdminCommand command, String operator) throws AryaServiceException;

	/**
	 * 获取企业的入职码和二维码
	 *
	 * @param corpId
	 * @throws AryaServiceException
	 */
	GetCorpCheckInCodeAndQRCodeResult getCorpCheckInCodeAndQRCode(String corpId) throws AryaServiceException;


	/**
	 * 升级公司为集团
	 *
	 * @param corpId
	 * @throws AryaServiceException
	 */
	void upgradeCorpToGroup(String corpId) throws AryaServiceException;


	/**
	 * 校验公司新增和更新时的参数是否合法
	 *
	 * @param corpModel
	 * @throws AryaServiceException
	 */
	void checkCorpCreateOrUpdateCommand(CorpModel corpModel) throws AryaServiceException;

	/**
	 * 获取考勤打卡方式
	 *
	 * @param corpId
	 * @return
	 * @throws AryaServiceException
	 */
	CorpAttendanceClockTypeResult getAttendanceClockWay(String corpId) throws AryaServiceException;

	/**
	 * 更新考勤打卡方式
	 *
	 * @param command
	 * @throws AryaServiceException
	 */
	void updateAttendanceClockWay(CorpAttendanceClockTypeCommand command)throws AryaServiceException;

	List<CorporationEntity> findNoParentCorpsByBusinessType(int corpBusinessWalletpays);

	CorporationEntity findByBranId(String branId);
}
