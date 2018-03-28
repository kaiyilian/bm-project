package com.bumu.arya.admin.welfare.controller;

import com.bumu.arya.admin.misc.result.SimpleResult;
import com.bumu.arya.admin.soin.controller.command.IdListCommand;
import com.bumu.arya.admin.welfare.result.*;
import com.bumu.arya.admin.welfare.service.WelfareService;
import com.bumu.arya.admin.welfare.controller.command.*;
import com.bumu.arya.response.HttpResponse;
import com.bumu.arya.welfare.result.WelfareNoticeResult;
import com.bumu.arya.welfare.service.WelfareServiceCommonService;
import com.bumu.exception.AryaServiceException;
import com.bumu.common.service.CommonFileService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * Created by CuiMengxin on 16/9/7.
 * 福库
 */
@Api(value = "Welfare",tags = "福库Welfare")
@Controller
public class WelfareController {

	Logger log = LoggerFactory.getLogger(WelfareController.class);

	@Autowired
	WelfareService welfareService;

	@Autowired
	WelfareServiceCommonService welfareServiceCommonService;

	@Autowired
	CommonFileService commonFileService;

	/**
	 * 获取公告
	 *
	 * @return
	 */
	@ApiOperation(httpMethod = "GET",notes = "获取公告内容",value = "获取公告")
	@RequestMapping(value = "admin/welfare/notice", method = RequestMethod.GET)
	public
	@ResponseBody
	HttpResponse<WelfareNoticeResult> getNotice() {
		try {
			return new HttpResponse(welfareServiceCommonService.getWelfareNotice());
		} catch (AryaServiceException e) {
			return new HttpResponse(e.getErrorCode());
		}
	}

	/**
	 * 编辑公告
	 *
	 * @param command
	 * @return
	 */
	@ApiOperation(httpMethod = "POST",notes = "如果公告不存在，则新建公告；如果公告已经存在，则修改公告的内容；\n" +
			"公告新建或者保存后，新建或者修改订单状态处理定时任务，设定运行时间为公告中活动结束时间后的一小时" +
			"（例如，活动12月1日晚0点结束，那么定时任务执行时间为12月2日1点）。订单处理定时任务执行时，将所有已支付的订单状态修改为已发货；将待支付的订单状态改为已取消。",value = "编辑公告")
	@RequestMapping(value = "admin/welfare/notice/update", method = RequestMethod.POST)
	public
	@ResponseBody
	HttpResponse<Object> editNotice(@ApiParam @RequestBody EditWelfareNoticeCommand command) {
		try {
			welfareService.updateNotice(command);
			return new HttpResponse();
		} catch (AryaServiceException e) {
			return new HttpResponse(e.getErrorCode());
		}
	}

	/**
	 * 订单管理
	 */


	/**
	 * 产品名称获取
	 *
	 * @return
	 */
	@ApiOperation(httpMethod = "GET",notes = "福库产品名称获取",value = "产品名称获取")
	@RequestMapping(value = "admin/welfare/order/names", method = RequestMethod.GET)
	public
	@ResponseBody
	HttpResponse<WelfareGoodNameList> getAllGoodsNames() {
		try {
			return new HttpResponse(welfareService.getAllGoodNames());
		} catch (AryaServiceException e) {
			return new HttpResponse(e.getErrorCode());
		}
	}

	/**
	 * 企业名称获取
	 * @return
	 */
	@ApiOperation(httpMethod = "GET",notes = "用于获取所有未标记为删除的公司的列表",value = "企业名称获取")
	@RequestMapping(value = "admin/corps/list",method = RequestMethod.GET)
	public
	@ResponseBody
	HttpResponse<WelfareCorpsListResult> getCorpsList(@ApiParam("公司业务类型，取值参考公司业务类型定义，不传表示查询") @RequestParam(value = "biz_type") Integer bizType){
		try {
			return new HttpResponse(welfareService.getCorpsList(bizType));
		}catch (AryaServiceException e){
			return new HttpResponse(e.getErrorCode());
		}
	}

	/**
	 * 订单列表
	 *
	 * @param command
	 * @return
	 */
	@ApiOperation(httpMethod = "GET",notes = "福库订单列表",value = "订单列表")
	@RequestMapping(value = "admin/welfare/order/list", method = RequestMethod.GET)
	public
	@ResponseBody
	HttpResponse<WelfareOrderListResult> getGoodsList(@ApiParam("获取订单列表请求参数") WelfareOrderListCommand command) {
		try {
			return new HttpResponse(welfareService.queryOrderList(command));
		} catch (AryaServiceException e) {
			return new HttpResponse(e.getErrorCode());
		}
	}

	/**
	 * 订单列表导出
	 *
	 * @param command
	 * @return
	 */
	@ApiOperation(httpMethod = "GET",notes = "订单列表导出",value = "订单列表导出")
	@RequestMapping(value = "admin/welfare/order/list/export", method = RequestMethod.GET)
	public
	@ResponseBody
	HttpResponse<HttpResponse> exportGoodsList(@ApiParam("订单列表导出请求参数") WelfareOrderListCommand command, HttpServletResponse response, String type) {
		try {
			type = "export_orderList";
			return new HttpResponse(welfareService.exportOrders(command, response, type));
		} catch (AryaServiceException e) {
			return new HttpResponse(e.getErrorCode());
		}
	}

	/**
	 * 货物签收单导出
	 *
	 * @param command
	 * @param response
	 * @return
	 */
	@ApiOperation(httpMethod = "GET",notes = "货物签收单导出",value = "货物签收单导出")
	@RequestMapping(value = "admin/welfare/order/list/export_delivery", method = RequestMethod.GET)
	public
	@ResponseBody
	HttpResponse<HttpResponse> exportGoodsReceiptList(@ApiParam("货物签收单导出请求参数") WelfareOrderListCommand command, HttpServletResponse response,@ApiParam("签收单类型") String type) {
		try {
			type = "export_delivery";
			return new HttpResponse(welfareService.exportOrders(command, response, type));
		} catch (AryaServiceException e) {
			return new HttpResponse(e.getErrorCode());
		}
	}


	/**
	 * 商品管理
	 */


	/**
	 * 上传商品图片
	 *
	 * @return
	 */
	@ApiOperation(httpMethod = "POST",notes = "上传商品图片",value = "上传商品图片")
	@RequestMapping(value = "admin/welfare/goods/image/upload", method = RequestMethod.POST)
	public
	@ResponseBody
	HttpResponse<WelfareUploadImageResult> uploadGoodsImage(@ApiParam("上传图片") @RequestParam MultipartFile file) {
		try {
			return new HttpResponse(welfareService.uploadGoodsImage(file));
		} catch (AryaServiceException e) {
			return new HttpResponse(e.getErrorCode());
		}
	}

	/**
	 * 编辑商品信息
	 *
	 * @param command
	 * @return
	 */
	@ApiOperation(httpMethod = "POST",notes = "提交商品信息。新增商品把商品顺序排在最前面",value = "编辑商品信息")
	@RequestMapping(value = "admin/welfare/goods/edit", method = RequestMethod.POST)
	public
	@ResponseBody
	HttpResponse<SimpleResult> editGoods(@ApiParam("编辑商品信息请求参数") @RequestBody WelfareGoodsEditCommand command) {
		try {
			SimpleResult simpleResult = new SimpleResult();
			simpleResult.setId(welfareService.editGoods(command));
			return new HttpResponse(simpleResult);
		} catch (AryaServiceException e) {
			return new HttpResponse(e.getErrorCode());
		}
	}

	/**
	 * 商品列表
	 *
	 * @param command
	 * @return
	 */
	@ApiOperation(httpMethod = "GET",notes = "商品列表根据设定的链表排序规则进行排序。商品上架和下架不影响商品排序",value = "商品列表")
	@RequestMapping(value = "admin/welfare/goods/list", method = RequestMethod.GET)
	public
	@ResponseBody
	HttpResponse<WelfareAdminGoodsListResult> getGoodsList(@ApiParam("商品列表请求参数") PaginationCommand command) {
		try {
			return new HttpResponse(welfareService.getGoodsList(command));
		} catch (AryaServiceException e) {
			return new HttpResponse(e.getErrorCode());
		}
	}

	/**
	 * 福库商品上下移动
	 *
	 * @param command
	 * @return
	 */
	@ApiOperation(httpMethod = "POST",notes = "福库商品上下移动",value = "福库商品上下移动")
	@RequestMapping(value = "admin/welfare/goods/change_position",method = RequestMethod.POST)
	public
	@ResponseBody
	HttpResponse<Object> moveGoods(@ApiParam("福库商品上下移动请求参数") @RequestBody WelfareGoodsChangePositionCommand command){
		try {
			welfareService.moveGoods(command.getGoodsId(),command.getDirection());
			return new HttpResponse();
		}catch (AryaServiceException e){
			return new HttpResponse(e.getErrorCode());
		}
	}


	/**
	 * 读取商品图片
	 *
	 * @param goodsId
	 * @param imageId
	 * @return
	 */
	@RequestMapping(value = "admin/welfare/goods/image", method = RequestMethod.GET)
	public
	@ResponseBody
	void getGoodsImage(@RequestParam("goods_id") String goodsId,
					   @RequestParam("image_id") String imageId,
					   HttpServletResponse httpServletResponse) {
		try {
			commonFileService.readWelfareGoodsImage(goodsId, imageId, httpServletResponse);
		} catch (AryaServiceException e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 获取商品详情
	 *
	 * @param id
	 */
	@ApiOperation(httpMethod = "GET",notes = "获取商品详情",value = "获取商品详情")
	@RequestMapping(value = "admin/welfare/goods/detail", method = RequestMethod.GET)
	public
	@ResponseBody
	HttpResponse<WelfareGoodsDetailResult> getGoodsDetail(@ApiParam("商品id") @RequestParam String id) {
		try {
			return new HttpResponse(welfareService.getGoodsDetail(id));
		} catch (AryaServiceException e) {
			log.error(e.getMessage(), e);
			return new HttpResponse(e.getErrorCode());
		}
	}

	/**
	 * 删除商品
	 *
	 * @param deleteCommand
	 * @return
	 */
	@ApiOperation(httpMethod = "POST",notes = "删除商品",value = "删除商品")
	@RequestMapping(value = "admin/welfare/goods/delete", method = RequestMethod.POST)
	public
	@ResponseBody
	HttpResponse<Object> deleteGoods(@ApiParam("删除商品请求参数") @RequestBody WelfareDeleteGoodsCommand deleteCommand) {
		try {
			welfareService.deleteGoods(deleteCommand.getGoodsId());
			return new HttpResponse();
		} catch (AryaServiceException e) {
			log.error(e.getMessage(), e);
			return new HttpResponse(e.getErrorCode());
		}
	}

	/**
	 * 获取全部商品分类
	 *
	 * @return
	 */
	@ApiOperation(httpMethod = "GET",notes = "获取全部商品分类",value = "获取全部商品分类")
	@RequestMapping(value = "admin/welfare/category/list", method = RequestMethod.GET)
	public
	@ResponseBody
	HttpResponse<WelfareAllCategoriesListResult> getAllCategories() {
		try {
			return new HttpResponse(welfareService.getAllCategories());
		} catch (AryaServiceException e) {
			log.error(e.getMessage(), e);
			return new HttpResponse(e.getErrorCode());
		}
	}

	/**
	 * 查询分类下的所有规格
	 *
	 * @param id
	 * @return
	 */
	@ApiOperation(httpMethod = "GET",notes = "查询分类下的所有规格",value = "查询分类下的所有规格")
	@RequestMapping(value = "admin/welfare/category/spec/list", method = RequestMethod.GET)
	public
	@ResponseBody
	HttpResponse<WelfareCategoriesAllSpecsListResult> getCategoryAllSpecs(@ApiParam("分类id") @RequestParam String id) {
		try {
			return new HttpResponse(welfareService.getCategoryAllSpecs(id));
		} catch (AryaServiceException e) {
			log.error(e.getMessage(), e);
			return new HttpResponse(e.getErrorCode());
		}
	}


	/**
	 * 获取指定商品的全部分类
	 *
	 * @return
	 */
	@ApiOperation(httpMethod = "GET",notes = "获取指定商品的全部分类",value = "获取指定商品的全部分类")
	@RequestMapping(value = "admin/welfare/goods/category/list", method = RequestMethod.GET)
	public
	@ResponseBody
	HttpResponse<WelfareAllCategoriesListResult> getGoodsAllCategories(@ApiParam("商品id") @RequestParam("goods_id") String goodsId) {
		try {
			return new HttpResponse(welfareService.getGoodsAllCategory(goodsId));
		} catch (AryaServiceException e) {
			log.error(e.getMessage(), e);
			return new HttpResponse(e.getErrorCode());
		}
	}

	/**
	 * 查询指定商品分类下的所有规格
	 *
	 * @param categoryId
	 * @return
	 */
	@ApiOperation(httpMethod = "GET",notes = "查询指定商品分类下的所有规格",value = "查询指定商品分类下的所有规格")
	@RequestMapping(value = "admin/welfare/goods/category/spec/list", method = RequestMethod.GET)
	public
	@ResponseBody
	HttpResponse<WelfareCategoriesAllSpecsListResult> getGoodsCategoryAllSpecs(@ApiParam("分类id") @RequestParam("category_id") String categoryId) {
		try {
			return new HttpResponse(welfareService.getGoodsCategoryAllSpecs(categoryId));
		} catch (AryaServiceException e) {
			log.error(e.getMessage(), e);
			return new HttpResponse(e.getErrorCode());
		}
	}

	/**
	 * 获取订单分类详情
	 *
	 * @param id
	 * @return
	 */
	@ApiOperation(httpMethod = "GET",notes = "获取订单分类详情",value = "获取订单分类详情")
	@RequestMapping(value = "admin/welfare/order/category/detail", method = RequestMethod.GET)
	public
	@ResponseBody
	HttpResponse<WelfareOrderCategoryDetailResult> getOrderCategoryDetail(@ApiParam("订单id") @RequestParam String id) {
		try {
			return new HttpResponse(welfareService.getOrderCategoryDetail(id));
		} catch (AryaServiceException e) {
			return new HttpResponse(e.getErrorCode());
		}
	}

	/**
	 * 修改订单分类详情
	 *
	 * @param command
	 * @return
	 */
	@ApiOperation(httpMethod = "POST",notes = "修改订单分类详情",value = "修改订单分类详情")
	@RequestMapping(value = "admin/welfare/order/category/adjust", method = RequestMethod.POST)
	public
	@ResponseBody
	HttpResponse<Object> changeOrderCategoryDetail(@ApiParam("修改订单分类详情请求参数") @RequestBody AdjustOrderCategoryDetailCommand command) {
		try {
			welfareService.adjustOrderCategoryDetail(command);
			return new HttpResponse();
		} catch (AryaServiceException e) {
			return new HttpResponse(e.getErrorCode());
		}
	}

	/**
	 * 删除订单
	 *
	 * @param command
	 * @return
	 */
	@ApiOperation(httpMethod = "POST",notes = "删除订单",value = "删除订单")
	@RequestMapping(value = "admin/welfare/order/delete", method = RequestMethod.POST)
	public
	@ResponseBody
	HttpResponse<Object> deleteOrder(@ApiParam("订单id") @RequestBody @Valid IdListCommand.IdCommand command, BindingResult result) {
		try {
			if (result.hasErrors()) {
				return new HttpResponse(result.getAllErrors().get(0));
			}
			welfareService.deleteOrder(command.getId());
			return new HttpResponse();
		} catch (AryaServiceException e) {
			return new HttpResponse(e.getErrorCode());
		}
	}

	/**
	 * 订单退款
	 *
	 * @param command
	 * @return
	 */
	@ApiOperation(httpMethod = "POST",notes = "订单退款",value = "订单退款")
	@RequestMapping(value = "admin/welfare/order/refund",method = RequestMethod.POST)
	public
	@ResponseBody
	HttpResponse<Object> orderRefund(@ApiParam("订单退款请求参数") @RequestBody OrderRefundCommand command){
//		return new HttpResponse(ErrorCode.CODE_SYS_ERR);
		try {
			welfareService.orderRefund(command);
			return new HttpResponse();
		}catch (AryaServiceException e){
			return new HttpResponse(e.getErrorCode());
		}
	}

	/**
	 * 福库券定义界面入口
	 *
	 * @return
	 */
	@RequestMapping(value = "admin/welfare/coupon_def/index", method = RequestMethod.GET)
	public String getWelfareCouponDef() {
		return "operation/fk_coupon_manage";
	}


	/**
	 * 福库劵定义保存
	 *
	 * @param command
	 * @return
	 */
	@ApiOperation(httpMethod = "POST", notes = "福库劵定义保存", value = "福库劵新增或者编辑后保存")
	@RequestMapping(value = "admin/welfare/coupon_def/save",method = RequestMethod.POST)
	public
	@ResponseBody
	HttpResponse<Object> couponDefSave(@ApiParam @RequestBody CouponDefSaveCommand command){
		try {
			welfareService.couponDefSave(command);
			return new HttpResponse();
		}catch (AryaServiceException e){
			return new HttpResponse(e.getErrorCode());
		}
	}

	/**
	 * 福库劵布局配置
	 *
	 * @return
	 */
	@ApiOperation(httpMethod = "GET", notes = "获取制券图片相关的布局参数用于实时显示券的效果（后台生成券图片的时候也是按照相同的参数值进行处理）", value = "福库劵布局配置")
	@RequestMapping(value = "admin/welfare/coupon_def/layout",method = RequestMethod.GET)
	public
	@ResponseBody
	HttpResponse<CouponDefLayoutResult2> couponDefLayout(){
		try {
			return new HttpResponse(welfareService.couponDefLayout());
		}catch (AryaServiceException e){
			return new HttpResponse(e.getErrorCode());
		}
	}

	/**
	 * 福库劵定义明细
	 *
	 * @param couponDefId
	 * @return
	 */
	@ApiOperation(httpMethod = "GET", notes = "福库劵定义明细", value = "福库劵定义的详情")
	@RequestMapping(value = "admin/welfare/coupon_def/detail",method = RequestMethod.GET)
	public
	@ResponseBody
	HttpResponse<CouponDefDetailResult> couponDefDetail(@ApiParam("劵定义id") @RequestParam(value = "coupon_def_id") String couponDefId){
		try {
			return new HttpResponse(welfareService.couponDefDetail(couponDefId));
		}catch (AryaServiceException e){
			return new HttpResponse(e.getErrorCode());
		}
	}

	/**
	 * 福库劵定义列表
	 *
	 * @param command
	 * @return
	 */
	@ApiOperation(httpMethod = "GET", notes = "查询结果按照给出的条件过滤并分页，标记为”已废弃“的券定义不出现在列表中。\n" +
			"查询条件中企业的选择范围为：已开通福库功能的未标记为删除的企业。", value = "福库劵定义列表")
	@RequestMapping(value = "admin/welfare/coupon_def/list",method = RequestMethod.GET)
	public
	@ResponseBody
	HttpResponse<CouponDefListResult> couponDefList(@ApiParam("福库券定义列表请求参数") CouponDefListCommand command){
		try{
			return new HttpResponse(welfareService.couponDefList(command));
		}catch (AryaServiceException e){
			return new HttpResponse(e.getErrorCode());
		}
	}

	/**
	 * 导出福库劵
	 *
	 * @param command
	 * @return
	 */
	@ApiOperation(httpMethod = "POST", notes = "操作人员需要导出福库券时，系统根据选择的福库券定义，生成相应数量的福库券二维码，并生成二维码图片，最后将二维码和福库券底图合并，并打包（zip）下载。导出的打包文件中按照不同券定义建立各自的子目录，子目录名字规则为：公司名+券定义ID。\n" +
			"生成的二维码放到coupon/compose目录下，以公司名+券定义ID建立子目录。\n" +
			"打包时把选定的所有券定义的二维码目录复制到 coupon/zip/福库券<日期>/目录下，并把该目录打包成zip文件。（每次打包前需要清除该目录）\n" +
			"如果选择了多个福库券定义则多个一起生成和导出。\n" +
			"如果福库券之前已经导出过，那么再次导出的时候只是把之前生成的二维码图片重新打包下载。不重新生成二维码。\n" +
			"二维码的明文格式如下：https://<域名>:<端口>/arya/api/welfare/coupon/recharge?coupon_id=<券ID>&coupon_code=<券编号>", value = "导出福库券（生成二维码")
	@RequestMapping(value = "admin/welfare/coupon/export",method = RequestMethod.POST)
	public
	@ResponseBody
	HttpResponse<CouponExportResult> couponExport(@ApiParam("导出福库劵请求参数") CouponExportCommand command){
		try{
			return new HttpResponse(welfareService.exportCoupon(command));
		}catch (AryaServiceException e){
			return new HttpResponse(e.getErrorCode());
		}
	}

	/**
	 * 读取福库劵zip
	 *
	 * @param generateTime
	 * @param httpServletResponse
	 */
	@RequestMapping(value = "admin/welfare/coupon/file", method = RequestMethod.GET)
	public
	@ResponseBody
	void getFile(@RequestParam("generate_time") String generateTime,
			      HttpServletResponse httpServletResponse) {
		try {
			welfareService.readFile(generateTime,httpServletResponse);
		} catch (AryaServiceException e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 删除福库劵定义
	 *
	 * @param command
	 * @return
	 */
	@ApiOperation(httpMethod = "GET", notes = "如果福库券二维码尚未生成并导出，直接删除此福库券定义；如果福库券二维码已经生成导出，则把此福库券定义标记为“已删除”，并把相关联的二维码设置成”作废“，这批二维码就无法充值到账户了。\n" +
			"删除福库券定义的同时也要删除其对应的底图文件。", value = "删除福库券定义")
	@RequestMapping(value = "admin/welfare/coupon_def/delete",method = RequestMethod.POST)
	public
	@ResponseBody
	HttpResponse<Object> couponDefDelete(@ApiParam("删除福库劵定义请求参数") @RequestBody CouponDefDeleteCommand command){
		try {
			welfareService.couponDefDelete(command);
			return new HttpResponse();
		}catch (AryaServiceException e){
			return new HttpResponse(e.getErrorCode());
		}
	}
}
