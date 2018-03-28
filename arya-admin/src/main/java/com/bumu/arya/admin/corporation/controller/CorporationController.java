package com.bumu.arya.admin.corporation.controller;

import com.bumu.admin.constant.CorpConstants;
import com.bumu.arya.admin.common.controller.BaseController;
import com.bumu.common.result.JQDatatablesPaginationResult;
import com.bumu.arya.admin.corporation.controller.command.*;
import com.bumu.arya.admin.corporation.result.*;
import com.bumu.arya.admin.corporation.model.CorpModel;
import com.bumu.arya.admin.corporation.service.CorporationService;
import com.bumu.arya.model.CorporationDao;
import com.bumu.arya.model.entity.CorporationEntity;
import com.bumu.arya.response.HttpResponse;
import com.bumu.bran.common.Constants;
import com.bumu.exception.AryaServiceException;
import com.bumu.common.service.CommonFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by bumu-zhz on 2015/11/11.
 */
@Api(value = "CorporationController", tags = {"企业管理CorporationManager"})
@Controller
public class CorporationController extends BaseController {

    private Logger log = LoggerFactory.getLogger(CorporationController.class);

    @Autowired
    CommonFileService commonFileService;

    @Autowired
    CorporationDao corporationDao;

    @Autowired
    private CorporationService corporationService;

    /**
     * 企业信息管理页面
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "admin/corporation/info_manager", method = RequestMethod.GET)
    public String corporationInfoManagePage(ModelMap map) {
        map.put("corp_type", CorpConstants.corpTypeMap);
        return "corporation/info_manager";
    }

    @ApiOperation(httpMethod = "GET", notes = "	企业信息管理页面", value = "企业信息管理页面")
    @RequestMapping(value = "admin/corporation/corp_info_manager", method = RequestMethod.GET)
    public String corpInfoManagePage() {
        return "corporation/corp_info_manager";
    }

    @ApiOperation(httpMethod = "GET", notes = "一键入职页面", value = "一键入职页面")
    @RequestMapping(value = "admin/corporation/entry/page", method = RequestMethod.GET)
    public String entryPage() {
        return "corporation/corp_service_entry";
    }

    @ApiOperation(httpMethod = "GET", notes = "福库页面", value = "福库页面")
    @RequestMapping(value = "admin/corporation/welfare/page", method = RequestMethod.GET)
    public String welfarePage() {
        return "corporation/corp_service_fk";
    }

    @ApiOperation(httpMethod = "GET", notes = "考勤页面", value = "考勤页面")
    @RequestMapping(value = "admin/corporation/attendance/page", method = RequestMethod.GET)
    public String attendancePage() {
        return "corporation/corp_service_attendance";
    }


    /**
     * 查询所有公司
     *
     * @param request
     * @param response
     * @return
     * @deprecated
     */
    @RequestMapping(value = "/admin/corporation/name_list", method = RequestMethod.GET)
    public
    @ResponseBody
    HttpResponse<CorporationRetrieveResult> retrieveCorporation(HttpServletRequest request, HttpServletResponse response) {
        //调用service查询所有公司
        List<CorporationEntity> corporations = null;
        try {
            corporations = corporationService.retrieveCorporation();
        } catch (AryaServiceException e) {
            e.printStackTrace();
            return new HttpResponse<>(e.getErrorCode());
        }
        //定义结果返回类
        CorporationRetrieveResult result = new CorporationRetrieveResult();
        result.setCorporations(corporations);
        return new HttpResponse<>(result);
    }

    /**
     * 获取所有集团(母公司)列表
     *
     * @return
     * @deprecated
     */
    @RequestMapping(value = "/admin/corporation/group/list", method = RequestMethod.GET)
    public
    @ResponseBody
    HttpResponse<CorpListResult> getCorpGroupList() {
        try {
            return new HttpResponse<>(corporationService.getCorpGroupList());
        } catch (AryaServiceException e) {
            return new HttpResponse<>(e.getErrorCode());
        }
    }

    /**
     * 获取所有子公司列表
     *
     * @return
     * @deprecated
     */
    @RequestMapping(value = "/admin/corporation/subsidiary/list", method = RequestMethod.GET)
    public
    @ResponseBody
    JQDatatablesPaginationResult getSubsidiaryList(@RequestParam Map<String, String> params) {
        Integer start = Integer.valueOf(params.get("start"));
        Integer pageSize = Integer.valueOf(params.get("length"));
        Integer page = start / pageSize;
        String corpName = String.valueOf(params.get("corp_name"));
        String corpShortName = String.valueOf(params.get("corp_short_name"));
        String contactPerson = String.valueOf(params.get("contact_person"));
        try {
            return corporationService.getSubsidiaryList(corpName, corpShortName, contactPerson, page, pageSize);
        } catch (AryaServiceException e) {
            return new JQDatatablesPaginationResult();
        }
    }

    /**
     * 获取生成的公司checkinCode
     *
     * @return
     */
    @RequestMapping(value = "/admin/corporation/detail/checkin_code", method = RequestMethod.GET)
    public
    @ResponseBody
    HttpResponse<GenerateCorpCheckinCode> getSubsidiaryDetail() {
        try {
            GenerateCorpCheckinCode newCode = new GenerateCorpCheckinCode();
            newCode.setCode(corporationService.generateCheckinCode());
            return new HttpResponse<>(newCode);
        } catch (AryaServiceException e) {
            return new HttpResponse<>(e.getErrorCode());
        }
    }

    /**
     * 获取公司详情
     *
     * @param corpId
     * @return
     */
    @ApiOperation(httpMethod = "GET", notes = "获取集团或公司详情", value = "获取公司详情")
    @RequestMapping(value = "/admin/corporation/get/detail", method = RequestMethod.GET)
    public
    @ResponseBody
    HttpResponse<GetGroupOrCorporationDetailResult> getGroupOrCorporationDetail(@ApiParam("公司id") @RequestParam("corp_id") String corpId) {
        try {
            return new HttpResponse(corporationService.getCorpDetailInfo(corpId));
        } catch (AryaServiceException e) {
            return new HttpResponse<>(e.getErrorCode());
        }
    }

    /**
     * 获取集团或公司的一键入职详情
     *
     * @param corpId
     * @return
     */
    @ApiOperation(httpMethod = "GET", notes = "获取集团或公司的一键入职详情", value = "获取一键入职详情")
    @RequestMapping(value = "/admin/corporation/entry/detail", method = RequestMethod.GET)
    public
    @ResponseBody
    HttpResponse<GetCorporationEntryDetailResult> getCorporationEntryDetail(@ApiParam("公司id") @RequestParam("corp_id") String corpId) {
        try {
            return new HttpResponse(corporationService.getCorpEntryDetail(corpId));
        } catch (AryaServiceException e) {
            return new HttpResponse<>(e.getErrorCode());
        }
    }

    /**
     * 获取集团或公司的福库详情
     *
     * @param corpId
     * @return
     */
    @ApiOperation(httpMethod = "GET", notes = "获取集团或公司的福库详情", value = "获取福库详情")
    @RequestMapping(value = "/admin/corporation/welfare/detail", method = RequestMethod.GET)
    public
    @ResponseBody
    HttpResponse<GetCorporationWelfareDetailResult> getCorporationWelfareDetail(@ApiParam("公司id") @RequestParam("corp_id") String corpId) {
        try {
            return new HttpResponse(corporationService.getCorpWelfareDetail(corpId));
        } catch (AryaServiceException e) {
            return new HttpResponse<>(e.getErrorCode());
        }
    }


    /**
     * 获取公司详情
     *
     * @param corpId
     * @return
     */
    @RequestMapping(value = "/admin/corporation/detail", method = RequestMethod.GET)
    public
    @ResponseBody
    HttpResponse<CorpListResult.CorpInfo> getSubsidiaryDetail(@RequestParam("corp_id") String corpId) {
        try {
            return new HttpResponse<>(corporationService.getCorpDetail(corpId));
        } catch (AryaServiceException e) {
            return new HttpResponse<>(e.getErrorCode());
        }
    }

    /**
     * 更新或新增公司(新增集团，子公司，一级公司)
     *
     * @param corpId
     * @param parentId
     * @param checkinCode
     * @param corpName
     * @param corpShortName
     * @param districtId
     * @param contactName
     * @param contactPhone
     * @param contactMail
     * @param fax
     * @param status
     * @param desc
     * @param type
     * @param address
     * @param longitude
     * @param latitude
     * @param corpLogoFile
     * @param isGroup
     * @param businessType
     * @param corpImageFile
     * @return
     */
    @RequestMapping(value = "/admin/corporation/update_create", method = RequestMethod.POST)
    public
    @ResponseBody
    HttpResponse createOrUpdateCorporation(@RequestParam(value = "id", required = false) String corpId,
                                           @RequestParam(value = "parent_id", required = false) String parentId,
                                           @RequestParam(value = "corp_checkin_code", required = false) String checkinCode,
                                           @RequestParam(value = "name", required = false) String corpName,
                                           @RequestParam(value = "short_name", required = false) String corpShortName,
                                           @RequestParam(value = "district_id", required = false) String districtId,
                                           @RequestParam(value = "contact_name", required = false) String contactName,
                                           @RequestParam(value = "contact_phone", required = false) String contactPhone,
                                           @RequestParam(value = "contact_mail", required = false) String contactMail,
                                           @RequestParam(value = "fax", required = false) String fax,
                                           @RequestParam(value = "status", required = false) String status,
                                           @RequestParam(value = "desc", required = false) String desc,
                                           @RequestParam(value = "type", required = false) String type,
                                           @RequestParam(value = "address", required = false) String address,
                                           @RequestParam(value = "longitude", required = false) String longitude,
                                           @RequestParam(value = "latitude", required = false) String latitude,
                                           @RequestParam(value = "corp_logo_file", required = false) MultipartFile corpLogoFile,
                                           @RequestParam(value = "is_group", required = false) String isGroup,
                                           @RequestParam(value = "business_type", required = false) String businessType,
                                           @RequestParam(value = "welfare_corp_name", required = false) String welfareCorpName,
                                           @RequestParam(value = "corp_image_file", required = false) MultipartFile corpImageFile,
                                           @RequestParam(value = "is_human_pool_project", required = false) Integer isHumanPoolProject) {
        try {
            CorpModel corpModel = new CorpModel();
            corpModel.setCorpId(corpId);
            corpModel.setWelfareCorpName(welfareCorpName);
            if (StringUtils.isAnyBlank(parentId)) {
                corpModel.setParentId(null);
            } else {
                corpModel.setParentId(parentId);
            }
            corpModel.setCheckinCode(checkinCode);
            if (StringUtils.isNotBlank(corpName)) {
                corpModel.setCorpName(corpName.trim());
            }
            corpModel.setCorpShortName(corpShortName);
            corpModel.setDistrictId(districtId);
            corpModel.setContactName(contactName);
            corpModel.setContactPhone(contactPhone);
            corpModel.setFax(fax);
            corpModel.setContactMail(contactMail);
            corpModel.setDesc(desc);
            corpModel.setAddress(address);
            corpModel.setLongitude(longitude);
            corpModel.setLatitude(latitude);
            corpModel.setCorpLogoFile(corpLogoFile);
            if (isHumanPoolProject == null) {
                corpModel.setIsHumanPoolProject(Constants.FALSE);
            } else {
                corpModel.setIsHumanPoolProject(isHumanPoolProject);
            }
//            corpModel.setStatus(status);
            if (StringUtils.isNotBlank(type)) {
                corpModel.setCorpType(Integer.parseInt(type));
            }
            if (StringUtils.isNotBlank(isGroup)) {
                corpModel.setIsGroup(Integer.parseInt(isGroup));
            }
            if (StringUtils.isNotBlank(businessType)) {
                corpModel.setBusinessType(Integer.parseInt(businessType));
            }
            Session session = SecurityUtils.getSubject().getSession();
            String userId = session.getAttribute("user_id").toString();
            corpModel.setOperateUserId(userId);
            corpModel.setCorpImageFile(corpImageFile);
            corporationService.checkCorpCreateOrUpdateCommand(corpModel);
            if (StringUtils.isAnyBlank(corpId)) {
                return new HttpResponse(corporationService.createNewCorp(corpModel));
            } else {
                return new HttpResponse(corporationService.updateCorpInfo(corpModel));
            }
        } catch (AryaServiceException e) {
            return new HttpResponse(e.getErrorCode());
        }
    }

    /**
     * 更新或新增公司(新增集团，子公司，一级公司
     *
     * @param command
     * @return
     */
    @ApiOperation(httpMethod = "POST", notes = "更新或新增公司(新增集团，子公司，一级公司)", value = "更新或新增公司")
    @RequestMapping(value = "/admin/corporation/create_update", method = RequestMethod.POST)
    public
    @ResponseBody
    HttpResponse<CreateUpdateCorpResult> createOrUpdateCorporation(@ApiParam("更新或新增公司请求参数") @RequestBody CreateOrUpdateCorporationCommand command) throws Exception {
        CreateOrUpdateCorporationCommand corporationCommand = new CreateOrUpdateCorporationCommand();
        CorpModel corpModel = corporationCommand.init(command);
        corporationService.checkCorpCreateOrUpdateCommand(corpModel);
        if (StringUtils.isAnyBlank(command.getCorpId())) {
            return new HttpResponse(corporationService.createNewCorporation(command));
        } else {
            return new HttpResponse(corporationService.updateCorporationInfo(command));
        }
    }

    /**
     * 更新集团或公司下的一键入职
     *
     * @param corpId
     * @param parentId
     * @param districtId
     * @param contactMail
     * @param checkinCode
     * @param desc
     * @param type
     * @param address
     * @param longitude
     * @param latitude
     * @return
     */
    @ApiOperation(httpMethod = "POST", notes = "更新集团或公司下的一键入职", value = "更新一键入职")
    @RequestMapping(value = "/admin/corporation/entry/update", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse<CreateUpdateCorpResult> updateCorporationEntry(
            @ApiParam("集团或公司id") @RequestParam(value = "id") String corpId,
            @ApiParam("上级公司id") @RequestParam(value = "parent_id", required = false) String parentId,
            @ApiParam("地区id字符串 用冒号:隔开") @RequestParam(value = "district_id", required = false) String districtId,
            @ApiParam("企业联系人邮箱") @RequestParam(value = "contact_mail", required = false) String contactMail,
            @ApiParam("企业入职码") @RequestParam(value = "corp_checkin_code") String checkinCode,
            @ApiParam("企业描述") @RequestParam(value = "desc", required = false) String desc,
            @ApiParam("企业性质") @RequestParam(value = "type") int type,
            @ApiParam("企业详细地址") @RequestParam(value = "address", required = false) String address,
            @ApiParam("经度") @RequestParam(value = "longitude", required = false) String longitude,
            @ApiParam("纬度") @RequestParam(value = "latitude", required = false) String latitude,
            @ApiParam("企业logo图片") @RequestParam(value = "corp_logo_file", required = false) MultipartFile corpLogoFile,
            @ApiParam("企业照片") @RequestParam(value = "corp_image_file", required = false) MultipartFile corpImageFile,
            @ApiParam("薪资单短信通知时间") @RequestParam(value = "salarySmsHours", required = false) Integer salarySmsHours) {
        try {
            CorpModel corpModel = new CorpModel();
            corpModel = corpModel.initEntryModel(corpId, parentId, districtId, contactMail, checkinCode, desc, type,address, longitude, latitude, corpLogoFile, corpImageFile, salarySmsHours);
            corporationService.checkCorpCreateOrUpdateCommand(corpModel);
            return new HttpResponse(corporationService.updateCorpEntryInfo(corpModel));
        } catch (AryaServiceException e) {
            return new HttpResponse(e.getErrorCode());
        }
    }

    /**
     * 更新集团或公司下的福库
     *
     * @param corpId
     * @param parentId
     * @param welfareCorpName
     * @return
     */
    @ApiOperation(httpMethod = "POST", notes = "更新集团或公司下的福库", value = "更新福库")
    @RequestMapping(value = "/admin/corporation/welfare/update", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse<CreateUpdateCorpResult> updateCorporationwelfare(
            @ApiParam("集团或公司id") @RequestParam(value = "id") String corpId,
            @ApiParam("上级公司id") @RequestParam(value = "parent_id", required = false) String parentId,
            @ApiParam("福库公司名称") @RequestParam(value = "welfare_corp_name", required = false) String welfareCorpName) {
        try {
            return new HttpResponse(corporationService.updateCorpWelfareInfo(corpId, parentId, welfareCorpName));
        } catch (AryaServiceException e) {
            return new HttpResponse(e.getErrorCode());
        }
    }


    /**
     * 升级为集团
     *
     * @param command
     * @return
     */
    @RequestMapping(value = "/admin/corporation/upgrade", method = RequestMethod.POST)
    public
    @ResponseBody
    HttpResponse upgradeCorporationToGroup(@RequestBody UpgradeCorpToGroupCommand command) {
        try {
            corporationService.upgradeCorpToGroup(command.getId());
        } catch (AryaServiceException e) {
            return new HttpResponse(e.getErrorCode());
        }
        return new HttpResponse();
    }

    /**
     * 删除公司
     *
     * @param command
     * @return
     */
    @ApiOperation(httpMethod = "POST", notes = "删除公司", value = "删除公司")
    @RequestMapping(value = "/admin/corporation/delete", method = RequestMethod.POST)
    public
    @ResponseBody
    HttpResponse<Object> deleteCorporation(@ApiParam("删除集团或公司请求参数") @RequestBody DeleteCorpCommand command) {
        try {
            Session session = SecurityUtils.getSubject().getSession();
            String currentUserId = session.getAttribute("user_id").toString();
            corporationService.deleteCorp(command.getId(), currentUserId);
        } catch (AryaServiceException e) {
            return new HttpResponse(e.getErrorCode());
        }
        return new HttpResponse();
    }

    /**
     * 获取logo照片
     *
     * @param fileId
     * @param response
     */
    @RequestMapping(value = "/admin/corporation/detail/logo", method = RequestMethod.GET)
    public
    @ResponseBody
    void getCorpLogoImage(@RequestParam("file_id") String fileId, @RequestParam("corp_id") String branCorpId, HttpServletResponse response) {
        try {
            commonFileService.readCorpLogoImageFile(fileId, branCorpId, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取营业执照副本照片
     *
     * @param fileId
     * @param response
     */
    @RequestMapping(value = "/admin/corporation/detail/license", method = RequestMethod.GET)
    public
    @ResponseBody
    void getCorpLicenseImage(@RequestParam("file_id") String fileId, @RequestParam("corp_id") String corpId, HttpServletResponse response) {
        try {
            commonFileService.readCorpLicenseImageFile(fileId, corpId, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 企业照片上传
     *
     * @param aryaCorpId
     * @param imageFile
     * @return
     */
    @RequestMapping(value = "/admin/corporation/image/upload", method = RequestMethod.POST)
    public
    @ResponseBody
    HttpResponse uploadCorpImage(@RequestParam(value = "arya_corp_id") String aryaCorpId,
                                 @RequestParam(value = "corp_image_file") MultipartFile imageFile) {
        try {
            Session session = SecurityUtils.getSubject().getSession();
            String userId = session.getAttribute("user_id").toString();
            String branCorpId = corporationDao.findBranCorpIdByAryaCorpIdThrow(aryaCorpId);
            corporationService.uploadCorpImage(branCorpId, imageFile, userId);
            return new HttpResponse();
        } catch (AryaServiceException e) {
            e.printStackTrace();
            return new HttpResponse(e.getErrorCode());
        }
    }

    /**
     * 获取企业照片列表
     *
     * @param aryaCorpId
     * @return
     */
    @RequestMapping(value = "/admin/corporation/image/list", method = RequestMethod.GET)
    public
    @ResponseBody
    HttpResponse getCorpImageList(@RequestParam(value = "arya_corp_id") String aryaCorpId) {
        try {
            return new HttpResponse(corporationService.getCorpImageList(aryaCorpId));
        } catch (AryaServiceException e) {
            e.printStackTrace();
            return new HttpResponse(e.getErrorCode());
        }
    }

    /**
     * 删除企业照片
     *
     * @param command
     * @return
     */
    @RequestMapping(value = "/admin/corporation/image/delete", method = RequestMethod.POST)
    public
    @ResponseBody
    HttpResponse deleteCorpImage(@RequestBody DeleteCorpImageCommand command) {
        try {
            corporationService.deleteCorpImageById(command.getImageId());
            return new HttpResponse();
        } catch (AryaServiceException e) {
            e.printStackTrace();
            return new HttpResponse(e.getErrorCode());
        }
    }

    /**
     * 查看图片详情
     *
     * @param fileId
     * @param branCorpId
     * @param response
     */
    @RequestMapping(value = "/admin/corporation/image/detail", method = RequestMethod.GET)
    public
    @ResponseBody
    void getCorpImage(@RequestParam(value = "file_id") String fileId,
                      @RequestParam(value = "bran_corp_id") String branCorpId,
                      HttpServletResponse response) {
        try {
            commonFileService.readCorpOtherImageFile(fileId, branCorpId, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 新增通用部门
     *
     * @param command
     * @return
     */
    @ApiOperation(httpMethod = "POST", notes = "新增通用部门", value = "新增通用部门")
    @RequestMapping(value = "/admin/corporation/department/add", method = RequestMethod.POST)
    public
    @ResponseBody
    HttpResponse<AryaDepartmentDetailResult> updateDepartment(@ApiParam("新增通用部门请求参数") @RequestBody CreateAryaDepartmentCommand command) {
        try {
            return new HttpResponse(corporationService.createAryaDepartment(command));
        } catch (AryaServiceException e) {
            return new HttpResponse(e.getErrorCode());
        }
    }

    /**
     * 获取通用部门详情
     *
     * @param departmentId
     * @return
     */
    @ApiOperation(httpMethod = "GET", notes = "获取通用部门详情", value = "获取通用部门详情")
    @RequestMapping(value = "/admin/corporation/department/detail", method = RequestMethod.GET)
    public
    @ResponseBody
    HttpResponse<AryaDepartmentDetailResult> getDepartmentDetail(@ApiParam("通用部门Id") @RequestParam("department_id") String departmentId) {
        try {
            return new HttpResponse(corporationService.getAryaDepartmentDetail(departmentId));
        } catch (AryaServiceException e) {
            return new HttpResponse(e.getErrorCode());
        }
    }

    /**
     * 修改通用部门
     *
     * @param command
     * @return
     */
    @ApiOperation(httpMethod = "POST", notes = "修改通用部门", value = "修改通用部门")
    @RequestMapping(value = "/admin/corporation/department/update", method = RequestMethod.POST)
    public
    @ResponseBody
    HttpResponse<Object> updateDepartment(@ApiParam("修改通用部门请求参数") @RequestBody UpdateAryaDepartmentCommand command) {
        try {
            corporationService.updateDepartment(command);
            return new HttpResponse();
        } catch (AryaServiceException e) {
            return new HttpResponse(e.getErrorCode());
        }
    }

    /**
     * 删除通用部门
     *
     * @param command
     * @return
     */
    @ApiOperation(httpMethod = "POST", notes = "删除通用部门", value = "删除通用部门")
    @RequestMapping(value = "/admin/corporation/department/delete", method = RequestMethod.POST)
    public
    @ResponseBody
    HttpResponse<Object> deleteDepartment(
            @ApiParam("删除通用部门请求参数") @RequestBody DeleteAryaDepartmentCommand command) {
        try {
            corporationService.deleteAryaDepartment(command.getDepartmentId());
            return new HttpResponse();
        } catch (AryaServiceException e) {
            return new HttpResponse(e.getErrorCode());
        }
    }

    /**
     * 获取企业信息管理的组织树
     *
     * @return
     */
    @ApiOperation(httpMethod = "GET", notes = "获取企业信息管理的组织树", value = "企业信息管理的组织树")
    @RequestMapping(value = "/admin/corporation/organization/tree", method = RequestMethod.GET)
    public
    @ResponseBody
    HttpResponse<OrganizationTreeResult> getOrganizationTree() {
        try {
            return new HttpResponse(corporationService.generateOrganizationTree());
        } catch (AryaServiceException e) {
            return new HttpResponse(e.getErrorCode());
        }
    }

    @ApiOperation(httpMethod = "GET", notes = "获取一键入职、福库、考勤组织树", value = "一键入职、福库、考勤组织树")
    @RequestMapping(value = "/admin/corporation/group/organization/tree", method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse<OrganizationTreeResult> getGroupAndCorpOrganizationTree(
            @ApiParam("业务类型 1.社保业务 2.薪资代发 4.一键入职 8.福库 16.考勤(6 即代表薪资业务+一键入职)") @RequestParam("business_type") int businessType) {
        try {
            return new HttpResponse(corporationService.generateEntryAndWelfareOrganizationTree(businessType));
        } catch (AryaServiceException e) {
            return new HttpResponse(e.getErrorCode());
        }
    }

    @ApiOperation(httpMethod = "GET", notes = "获取考勤打卡方式", value = "获取考勤打卡方式")
    @RequestMapping(value = "/admin/corporation/attendance/clock/detail", method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse<CorpAttendanceClockTypeResult> getAttendanceClockWay(
            @ApiParam("公司Id") @RequestParam("corp_id") String corpId) {
        try {
            return new HttpResponse(corporationService.getAttendanceClockWay(corpId));
        } catch (AryaServiceException e) {
            return new HttpResponse(e.getErrorCode());
        }
    }

    @ApiOperation(httpMethod = "POST", notes = "更新考勤打卡方式", value = "更新考勤打卡方式")
    @RequestMapping(value = "/admin/corporation/attendance/clock/update", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse<Object> updateAttendanceClockWay(
            @ApiParam("更新考勤打卡方式请求参数") @RequestBody CorpAttendanceClockTypeCommand command) {
        try {
            corporationService.updateAttendanceClockWay(command);
            return new HttpResponse();
        } catch (AryaServiceException e) {
            return new HttpResponse(e.getErrorCode());
        }
    }

}

