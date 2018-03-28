package com.bumu.arya.admin.soin.controller;

import com.bumu.arya.admin.common.controller.BaseController;
import com.bumu.arya.admin.soin.controller.command.UpdateSoinPersonDetailCommand;
import com.bumu.common.result.JQDatatablesPaginationResult;
import com.bumu.arya.admin.common.service.AryaAdminConfigService;
import com.bumu.arya.admin.soin.service.SoinPersonPushService;
import com.bumu.arya.admin.soin.service.SoinPersonService;
import com.bumu.arya.soin.constant.SoinPersonHukouType;
import com.bumu.arya.soin.constant.SoinPersonStatus;
import com.bumu.arya.model.DistrictDao;
import com.bumu.arya.soin.model.dao.InsurancePersonDao;
import com.bumu.arya.soin.model.entity.AryaSoinPersonEntity;
import com.bumu.arya.model.entity.DistrictEntity;
import com.bumu.arya.response.HttpResponse;
import com.bumu.arya.soin.service.SoinPersonIdcardService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.*;

import static com.bumu.arya.exception.ErrorCode.CODE_SYS_ERR;
import static com.bumu.arya.response.SoinPersonList.SocialInsurancePerson;

/**
 * 获取参保人信息管理相关
 *
 * @author CuiMengxin
 * @date 2015/11/16
 */
@Controller
public class SoinPersonController extends BaseController {

	@Autowired
	AryaAdminConfigService configService;

	@Autowired
	InsurancePersonDao insurancePersonDao;

	@Autowired
	DistrictDao districtDao;

	@Autowired
	SoinPersonIdcardService personIdcardService;

	@Autowired
	SoinPersonService personService;

	@Autowired
	SoinPersonPushService personPushService;

	static final String IDCARD_IMAGE_MAP = "admin/soin/person/idcard_img?file_id=";

	static final String IDCARD_EMPTY_IMAGE = "img/idcard-empty.jpg";

	/**
	 * 获取参保人信息管理页面
	 */
	@RequestMapping(value = "admin/soin/person/index", method = RequestMethod.GET)
	public String getSoinPersonManagerPage(ModelMap map) {
		map.put("person_status", SoinPersonStatus.getStatusMap());
		return "soin/soin_person_manager";
	}

	/**
	 * 参保人列表
	 */
	@RequestMapping(value = "/admin/soin/person/list")
	public
	@ResponseBody
	JQDatatablesPaginationResult soinPersonList(@RequestParam Map<String, String> params) {
		Integer draw = Integer.valueOf(params.get("draw"));
		Integer start = Integer.valueOf(params.get("start"));
		Integer pageSize = Integer.valueOf(params.get("length"));
		String personStatusCodesStr = params.get("person_status_codes");
		try {
			return personService.getSoinPersonList(draw, start / pageSize, pageSize, personStatusCodesStr);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 查看参保人详情
	 *
	 * @param personId
	 * @return
	 */
	@RequestMapping(value = "/admin/soin/person")
	public
	@ResponseBody
	HttpResponse<SocialInsurancePerson> getSoinPersonDetail(@RequestParam("person_id") String personId) {

		if (null != personId) {
			Map params = new HashMap();
			params.put(AryaSoinPersonEntity.COL_NAME_ID, personId);
			params.put(AryaSoinPersonEntity.COL_NAME_IS_DELETE, false);
			try {
				AryaSoinPersonEntity personEntity = insurancePersonDao.findByUniqueParams(params);

				SocialInsurancePerson person = new SocialInsurancePerson();
				person.setInsuredPersionId(personEntity.getId());
				person.setVerifyStatus(personEntity.getVerifyStatus());
				person.setIdcardNo(personEntity.getIdcardNo());
				person.setName(personEntity.getInsurancePersonName());
				person.setPhoneNo(personEntity.getPhoneNo());
				if (StringUtils.isNotBlank(personEntity.getHukou())) {
					Collection<String> districtIds = new ArrayList();
					//收集地区ID
					String[] districtIdStrs = personEntity.getHukou().split(":");
					for (String districtId : districtIdStrs) {
						if (!districtIds.contains(districtId)) {
							districtIds.add(districtId);
						}
					}
					//查出所有涉及到的地区
					List<DistrictEntity> districtEntities = districtDao.findDistrictList(districtIds);
					String[] hukouChinese = new String[districtIdStrs.length];
					//查出参保人的每个地名
					for (int j = 0; j < hukouChinese.length; j++) {
						for (DistrictEntity districtEntity : districtEntities) {
							if (districtIdStrs[j].equals(districtEntity.getId())) {
								hukouChinese[j] = districtEntity.getDistrictName();
								break;
							}
						}
					}

					//合并字符串
					//合并字符串
					String hukouCHN = StringUtils.join(hukouChinese, "-");
					person.setHukouName(hukouCHN);

				} else {
					person.setHukou("N/A");
				}

				if (personEntity.getHukouType() > 0 || personEntity.getHukouType() < 3) {
					person.setHukouType(personEntity.getHukouType());
					person.setHukouTypeName(SoinPersonHukouType.getHuKouTypeName(personEntity.getHukouType()));
				}

				person.setOrderCount(personEntity.getOrderCount());
				person.setCreateTime(personEntity.getCreateTime());

				//身份证正面
				if (personEntity.getIdcardFrontId() != null) {
					File file = new File(configService.getIdcardUploadPath() + personEntity.getIdcardFrontId() + ".jpg");
					if (file.exists()) {
						person.setIdCardFrontURL(IDCARD_IMAGE_MAP + personEntity.getIdcardFrontId());
					} else {
						person.setIdCardFrontURL(IDCARD_EMPTY_IMAGE);
					}
				} else {
					person.setIdCardFrontURL(IDCARD_EMPTY_IMAGE);
				}

				//身份证反面
				if (personEntity.getIdcardBackId() != null) {
					File file = new File(configService.getIdcardUploadPath() + personEntity.getIdcardBackId() + ".jpg");
					if (file.exists()) {
						person.setIdCardBackURL(IDCARD_IMAGE_MAP + personEntity.getIdcardBackId());
					} else {
						person.setIdCardBackURL(IDCARD_EMPTY_IMAGE);
					}
				} else {
					person.setIdCardBackURL(IDCARD_EMPTY_IMAGE);
				}

				return new HttpResponse(person);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return new HttpResponse(CODE_SYS_ERR);
	}

	/**
	 * 更新参保人
	 *
	 * @param command
	 * @return
	 */
	@RequestMapping(value = "/admin/soin/person/update", method = RequestMethod.POST)
	public
	@ResponseBody
	HttpResponse updateSoinPersonDetail(@RequestBody UpdateSoinPersonDetailCommand command) {

		if (null != command.getInsuredPersionId()) {
			Map params = new HashMap();
			params.put(AryaSoinPersonEntity.COL_NAME_ID, command.getInsuredPersionId());
			params.put(AryaSoinPersonEntity.COL_NAME_IS_DELETE, false);
			try {
				AryaSoinPersonEntity personEntity = insurancePersonDao.findByUniqueParams(params);
				if (command.getVerifyStatus() > 0) {
					personEntity.setVerifyStatus(command.getVerifyStatus());
				}
				insurancePersonDao.update(personEntity);
				//发推送
				personPushService.pushPersonStatusChange(personEntity.getId(), personEntity.getInsurancePersonName(),
						SoinPersonStatus.getStatusName(personEntity.getVerifyStatus()), personEntity.getAryaUserId());
				return new HttpResponse();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return new HttpResponse(CODE_SYS_ERR);
	}

	/**
	 * 获取身份证照片
	 *
	 * @param fileId
	 * @param response
	 */
	@RequestMapping(value = "/admin/soin/person/idcard_img", method = RequestMethod.GET)
	public
	@ResponseBody
	void getPersonIdCardImage(@RequestParam("file_id") String fileId, HttpServletResponse response) {
		try {
			personIdcardService.readIdcardImageFile(fileId + ".jpg", response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
