package com.bumu.arya.admin.misc.controller;

import com.bumu.arya.admin.misc.controller.command.CreateOrUpdateAdsCommand;
import com.bumu.arya.admin.misc.result.UploadAdsPicResult;
import com.bumu.arya.admin.misc.service.AdminAdsService;
import com.bumu.arya.response.HttpResponse;
import com.bumu.exception.AryaServiceException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * 运营管理
 *
 * @author CuiMengxin
 * @date 2016/6/13
 */
@Controller
public class OperationController {

	@Autowired
	AdminAdsService adminAdsService;

	@Autowired
	com.bumu.arya.service.AdsService commonAdsService;

	/**
	 * 广告管理页面
	 *
	 * @return
	 */
	@RequestMapping(value = "/admin/operation/ads/manage", method = RequestMethod.GET)
	public String getAdsManagePage() {
		return "operation/advertise_manage";
	}

	/**
	 * 福库商品管理
	 *
	 * @return
	 */
	@RequestMapping(value = "/admin/operation/fk/good/manage", method = RequestMethod.GET)
	public String getFkGoodManagePage() {
		return "operation/fk_good_manage";
	}

	/**
	 * 福库订单管理
	 *
	 * @return
	 */
	@RequestMapping(value = "/admin/operation/fk/order/manage", method = RequestMethod.GET)
	public String getFkOrderManagePage() {
		return "operation/fk_order_manage";
	}

	/**
	 * 福库公告管理
	 *
	 * @return
	 */
	@RequestMapping(value = "/admin/operation/fk/notice/manage", method = RequestMethod.GET)
	public String getFkNoticeManagePage() {
		return "operation/fk_notice_manage";
	}

	/**
	 * 福库券 管理页面
	 *
	 * @return
	 */
//	@RequestMapping(value = "/admin/welfare/coupon_def/index", method = RequestMethod.GET)
//	public String getFkCouponManagePage() {
//		return "operation/fk_coupon_manage";
//	}

	/**
	 * 分页获取广告列表
	 *
	 * @param page
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/admin/operation/ads/manage/list", method = RequestMethod.GET)
	public
	@ResponseBody
	HttpResponse getAdsList(@RequestParam(value = "page", defaultValue = "1") int page,
							@RequestParam(value = "page_size", defaultValue = "10") int pageSize) {
		try {
			return new HttpResponse(adminAdsService.getAdsPagnationList(page - 1, pageSize));
		} catch (AryaServiceException e) {
			e.printStackTrace();
			return new HttpResponse(e.getErrorCode());
		}
	}

	/**
	 * 读取广告图片
	 *
	 * @param fileName
	 */
	@RequestMapping(value = "/admin/operation/ads/manage/pic", method = RequestMethod.GET)
	public
	@ResponseBody
	void getAdsPic(@RequestParam("file_name") String fileName, HttpServletResponse response) {
		commonAdsService.readImageFile(fileName, response);
	}

	/**
	 * 上传广告图片
	 *
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/admin/operation/ads/manage/pic/upload", method = RequestMethod.POST)
	public
	@ResponseBody
	HttpResponse uploadAdsPic(@RequestParam(value = "pic_file") MultipartFile file) {
		try {
			UploadAdsPicResult result = new UploadAdsPicResult();
			result.setFileName(adminAdsService.saveAdsPic(file));
			result.setUrl("admin/operation/ads/manage/pic?file_name=" + result.getFileName());
			return new HttpResponse(result);
		} catch (AryaServiceException e) {
			return new HttpResponse(e.getErrorCode());
		}
	}

	/**
	 * 新增或修改广告
	 *
	 * @param command
	 * @return
	 */
	@RequestMapping(value = "/admin/operation/ads/manage/create_update", method = RequestMethod.POST)
	public
	@ResponseBody
	HttpResponse createOrUpdateAds(@RequestBody CreateOrUpdateAdsCommand command) {
		try {
			if (StringUtils.isAnyBlank(command.getId())) {
				return new HttpResponse(adminAdsService.createAds(command));
			} else {
				adminAdsService.updateAds(command);
				return new HttpResponse();
			}
		} catch (AryaServiceException e) {
			return new HttpResponse(e.getErrorCode());
		}
	}

	/**
	 * 删除广告
	 *
	 * @param command
	 * @return
	 */
	@RequestMapping(value = "/admin/operation/ads/manage/delete", method = RequestMethod.POST)
	public
	@ResponseBody
	HttpResponse deleteAds(@RequestBody CreateOrUpdateAdsCommand command) {
		try {
			adminAdsService.deleteAds(command.getId());
			return new HttpResponse();
		} catch (AryaServiceException e) {
			return new HttpResponse(e.getErrorCode());
		}
	}
}
