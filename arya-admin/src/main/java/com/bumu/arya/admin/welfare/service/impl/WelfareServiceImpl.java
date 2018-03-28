package com.bumu.arya.admin.welfare.service.impl;

import com.bumu.SysUtils;
import com.bumu.arya.Utils;
import com.bumu.arya.admin.common.service.AryaAdminConfigService;
import com.bumu.arya.admin.system.service.OpLogService;
import com.bumu.arya.admin.common.util.ZXingUtil;
import com.bumu.arya.admin.common.util.ZipCompressorByAntUtil;
import com.bumu.arya.admin.corporation.constant.CorpConstants;
import com.bumu.arya.admin.misc.result.SimpleResult;
import com.bumu.arya.admin.misc.service.FileService;
import com.bumu.arya.admin.welfare.controller.command.*;
import com.bumu.arya.admin.welfare.result.*;
import com.bumu.arya.admin.welfare.service.WelfareService;
import com.bumu.arya.welfare.constant.WelfareOrderStatus;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.model.*;
import com.bumu.arya.model.entity.*;
import com.bumu.arya.response.HttpResponse;
import com.bumu.arya.response.SimpleIdNameResult;
import com.bumu.arya.welfare.model.dao.*;
import com.bumu.arya.welfare.result.WelfareNoticeResult;
import com.bumu.arya.common.service.AryaConfigService;
import com.bumu.arya.welfare.service.WelfareOrderStatusScheduleService;
import com.bumu.arya.welfare.service.WelfareServiceCommonService;
import com.bumu.arya.welfare.model.entity.*;
import com.bumu.bran.helper.ExcelExportHelper;
import com.bumu.bran.model.dao.BranCorporationDao;
import com.bumu.bran.model.entity.BranCorporationEntity;
import com.bumu.common.util.DateTimeUtils;
import com.bumu.exception.AryaServiceException;
import com.bumu.common.result.Pager;
import com.bumu.common.service.CommonFileService;
import com.bumu.common.service.FileUploadService;
import com.bumu.common.service.ReadFileResponseService;
import com.bumu.common.service.impl.BaseBumuService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.math3.analysis.function.Divide;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.bumu.arya.common.OperateConstants.*;

/**
 * Created by CuiMengxin on 16/9/7.
 */
@Service
public class WelfareServiceImpl extends BaseBumuService implements WelfareService {

	public static final String WELFARE_GOODS_IMAGE_URL = "admin/welfare/goods/image";
	public static final String COUPON_QR_CODE_URL = "arya/api/welfare/coupon/recharge";
	public static final String COUPON_URL = "admin/welfare/coupon/file";
	public static final int couponImageReadLength = 200 * 1024;//每次读取200k
	Logger log = LoggerFactory.getLogger(WelfareServiceImpl.class);
	@Autowired
	WelfareNoticeDao welfareNoticeDao;

	@Autowired
	WelfareGoodsDao goodsDao;

	@Autowired
	WelfareOrderDao orderDao;

	@Autowired
	WelfareImageDao imageDao;

	@Autowired
    WelfareCategoryDao welfareCategoryDao;

	@Autowired
	WelfareSpecDao welfareSpecDao;

	@Autowired
    WelfareGoodsCategoryDao goodsCategoryDao;

	@Autowired
    WelfareGoodsCategorySpecDao goodsCategorySpecDao;

	@Autowired
    OpLogService opLogService;

	@Autowired
	CommonFileService commonFileService;

	@Autowired
	AryaAdminConfigService config;

	@Autowired
	FileService fileService;

	@Autowired
	FileUploadService fileUploadService;

	@Autowired
	WelfareServiceCommonService welfareServiceCommonService;
	@Autowired
	WelfareOrderItemDao orderItemDao;
	@Autowired
	CorporationDao corporationDao;
	@Autowired
	BranCorporationDao branCorporationDao;
	@Autowired
	AryaUserDao userDao;
	@Autowired
    WelfareCouponDao couponDao;
	@Autowired
    WelfareCouponDefDao couponDefDao;
	@Autowired
	AryaConfigService aryaConfigService;
	@Autowired
	ReadFileResponseService readFileResponseService;
	@Autowired
	WelfareOrderStatusScheduleService welfareOrderStatusScheduleService;
	@Autowired
	private ExcelExportHelper excelExportHelper;

	@Override
	public void updateNotice(EditWelfareNoticeCommand command) throws AryaServiceException {
		if (command.getBeginTime() == null) {
			throw new AryaServiceException(ErrorCode.CODE_WELFARE_ACTIVITY_BEGIN_TIME_BLANK);
		}

		if (command.getEndTime() == null) {
			throw new AryaServiceException(ErrorCode.CODE_WELFARE_ACTIVITY_END_TIME_BLANK);
		}
		WelfareNoticeResult result = new WelfareNoticeResult();
		List<WelfareNoticeEntity> noticeEntities = welfareNoticeDao.findAllWelfareNotice();
		if (noticeEntities.size() > 0) {
			WelfareNoticeEntity noticeEntity = noticeEntities.get(0);
			noticeEntity.setUpdateTime(System.currentTimeMillis());
			noticeEntity.setBeginTime(command.getBeginTime());
			noticeEntity.setEndTime(command.getEndTime());
			noticeEntity.setNoticeContent(command.getContent());
			noticeEntity.setPreNoticeContent(command.getPreContent());
			StringBuffer logMsg = new StringBuffer("【福库公告】修改公告。");
			try {
				//删除未执行的定时任务并重新安排一个定时任务
				welfareOrderStatusScheduleService.rescheduleWelfareOrderStatus(noticeEntity.getId(), command.getEndTime());

				welfareNoticeDao.update(noticeEntity);
				opLogService.successLog(WELFARE_NOTICE_UPDATE, logMsg, log);
			} catch (Exception e) {
				elog.error(e.getMessage(), e);
				opLogService.failedLog(WELFARE_NOTICE_UPDATE, logMsg, log);
				throw new AryaServiceException(ErrorCode.CODE_WELFARE_NOTICE_UPDATE_FAILED);
			}
		} else {
			//新增
			WelfareNoticeEntity noticeEntity = new WelfareNoticeEntity();
			noticeEntity.setId(Utils.makeUUID());
			noticeEntity.setCreateTime(System.currentTimeMillis());
			noticeEntity.setBeginTime(command.getBeginTime());
			noticeEntity.setEndTime(command.getEndTime());
			noticeEntity.setNoticeContent(command.getContent());
			noticeEntity.setPreNoticeContent(command.getPreContent());
			StringBuffer logMsg = new StringBuffer("【福库公告】新增公告。");
			try {
				//安排一个订单状态改变的定时任务
				welfareOrderStatusScheduleService.scheduleWelfareOrderStatus(noticeEntity.getId(), command.getEndTime());

				welfareNoticeDao.create(noticeEntity);
				opLogService.successLog(WELFARE_NOTICE_CREATE, logMsg, log);
			} catch (Exception e) {
				elog.error(e.getMessage(), e);
				opLogService.failedLog(WELFARE_NOTICE_CREATE, logMsg, log);
				throw new AryaServiceException(ErrorCode.CODE_WELFARE_NOTICE_UPDATE_FAILED);
			}
		}
	}

	@Override
	public WelfareGoodNameList getAllGoodNames() throws AryaServiceException {
		WelfareGoodNameList nameList = new WelfareGoodNameList();
		List<SimpleResult> results = new ArrayList<>();
		nameList.setGoods(results);
		List<WelfareGoodsEntity> goodsEntities = goodsDao.findAllWelfareGoods();
		for (WelfareGoodsEntity goodsEntity : goodsEntities) {
			SimpleResult result = new SimpleResult();
			result.setId(goodsEntity.getId());
			result.setName(goodsEntity.getGoodsName());
			results.add(result);
		}
		return nameList;
	}

	@Override
	public WelfareCorpsListResult getCorpsList(Integer bizType) throws AryaServiceException {
		WelfareCorpsListResult corpsList = new WelfareCorpsListResult();
		List<SimpleResult> results = new ArrayList<>();
		List<CorporationEntity> corporationEntities = corporationDao.findCorpsList(bizType);
		for (CorporationEntity corporationEntity : corporationEntities) {
			SimpleResult result = new SimpleResult();
			result.setId(corporationEntity.getId());
			result.setName(corporationEntity.getName());
			results.add(result);
		}
		corpsList.setCorps(results);
		return corpsList;
	}

	@Override
	public WelfareOrderListResult queryOrderList(WelfareOrderListCommand command) throws AryaServiceException {
		try {
			command.setBegin_time(SysUtils.getOneDayStartTime(command.getBegin_time()));
			command.setEnd_time(DateTimeUtils.getOneDayLastTime(command.getEnd_time()));
		} catch (ParseException e) {
			elog.error(e.getMessage(), e);
		}
		if (StringUtils.isNotBlank(command.getReceiver_key_word())) {
			command.setReceiver_key_word(command.getReceiver_key_word().trim());
		}
		WelfareOrderListResult listResult = new WelfareOrderListResult();
		Pager<WelfareOrderEntity> orderEntities = orderDao.findPaginationWelfareOrders(command.getGoods_id(), command.getCorp_id(), command.getOrder_status(), command.getReceiver_key_word(), command.getBegin_time(), command.getEnd_time(), command.getPage(), command.getPage_size());
		List<WelfareOrderListResult.WelfareOrderResult> orderResults = generateOrderList(orderEntities.getResult(), false);
		listResult.setOrders(orderResults);
		listResult.setPages(Utils.calculatePages(orderEntities.getRowCount(), orderEntities.getPageSize()));
		return listResult;
	}

	@Override
	public HttpResponse exportOrders(WelfareOrderListCommand command, HttpServletResponse response, String type) throws AryaServiceException {
		HttpResponse httpResponse = null;
		try {
			command.setBegin_time(SysUtils.getOneDayStartTime(command.getBegin_time()));
			command.setEnd_time(DateTimeUtils.getOneDayLastTime(command.getEnd_time()));
		} catch (ParseException e) {
			elog.error(e.getMessage(), e);
		}
		if (StringUtils.isNotBlank(command.getReceiver_key_word())) {
			command.setReceiver_key_word(command.getReceiver_key_word().trim());
		}
		try {
			List<WelfareOrderEntity> orderEntities = orderDao.findWelfareOrdersCriteria(command.getGoods_id(), command.getCorp_id(), command.getOrder_status(), command.getReceiver_key_word(), command.getBegin_time(), command.getEnd_time()).list();
			List<WelfareOrderListResult.WelfareOrderResult> orderResults = generateOrderList(orderEntities, true);
			if ("export_orderList".equals(type)) {
				excelExportHelper.export(
						config.getExportTemplatePath() + AryaAdminConfigService.WELFARE_ORDER_LIST_EXPORT,
						"福库订单",
						new HashMap() {{
							put("list", orderResults);
						}},
						response
				);
			}
			if ("export_delivery".equals(type)) {
				excelExportHelper.export(
						config.getExportTemplatePath() + AryaAdminConfigService.GOODS_RECEIPT_LIST_EXPORT,
						"货物签收单",
						new HashMap() {{
							put("list", orderResults);
						}},
						response
				);
			}
			httpResponse = new HttpResponse(ErrorCode.CODE_OK);
		} catch (AryaServiceException e) {
			elog.error(e.getMessage(), e);
			httpResponse = new HttpResponse(e.getErrorCode());
		}
		return httpResponse;
	}

	@Override
	public String editGoods(WelfareGoodsEditCommand command) throws AryaServiceException {
		if (command.getImageIds() != null) {
			for (int i = 0; i < command.getImageIds().size(); i++) {
				String rollImageId = command.getImageIds().get(i);
				if (StringUtils.isAnyBlank(rollImageId)) {
					command.getImageIds().remove(rollImageId);
					i--;
				}
			}
		}

		if (command.getDealPrice().compareTo(BigDecimal.ZERO) == 0 || command.getMarkedPrice().compareTo(BigDecimal.ZERO) == 0) {
			throw new AryaServiceException(ErrorCode.CODE_WELFARE_ORDER_PRICE_CAN_NOT_ZERO);
		}

		if (StringUtils.isAnyBlank(command.getThumbId())) {
			throw new AryaServiceException(ErrorCode.CODE_WELFARE_GOODS_THUMB_CAN_NOT_EMPTY);
		}

		if (command.getImageIds() == null || command.getImageIds().size() == 0) {
			throw new AryaServiceException(ErrorCode.CODE_WELFARE_GOODS_ROLL_IMAGES_CAN_NOT_EMPTY);
		}

		if (command.getDealPrice().compareTo(new BigDecimal(99999999.99)) > 0 || command.getDealPrice().toString().length() > 11) {
			throw new AryaServiceException(ErrorCode.CODE_WELFARE_GOODS_DEAL_PRICE_TOO_BIG);
		}

		if (command.getMarkedPrice().compareTo(new BigDecimal(99999999.99)) > 0 || command.getMarkedPrice().toString().length() > 11) {
			throw new AryaServiceException(ErrorCode.CODE_WELFARE_GOODS_MARKED_PRICE_TOO_BIG);
		}

		WelfareGoodsEntity goodsEntity = new WelfareGoodsEntity();
		WelfareGoodsEntity firstGoods = goodsDao.findGoodsByIsFirst();
		StringBuffer logMsg = new StringBuffer("【福库商品管理】");
		WelfareImageEntity newThumb = null;
		List<WelfareImageEntity> imageEntities = new ArrayList<>();
		List<WelfareImageEntity> needDeleteList = new ArrayList<>();
		if (StringUtils.isNotBlank(command.getId())) {
			//修改商品
			goodsEntity = goodsDao.findWelfareGoodsThrow(command.getId());
			goodsEntity.setUpdateTime(System.currentTimeMillis());
			logMsg.append("修改商品");
			List<String> imageIds = new ArrayList<>();
			imageIds.addAll(command.getImageIds());
			//查出所有图片
			imageEntities = new ArrayList<>(goodsEntity.getImages());
			WelfareImageEntity thumbImage = null;
			//暂时剔除缩略图
			if (StringUtils.isNotBlank(goodsEntity.getThumbnailFileName())) {
				for (int i = 0; i < imageEntities.size(); i++) {
					WelfareImageEntity imageEntity = imageEntities.get(i);
					if (imageEntity.getId().equals(goodsEntity.getThumbnailFileName())) {
						thumbImage = imageEntity;
						imageEntities.remove(imageEntity);
						i--;
					}
				}
			}
			//判断已有轮播图是否要删除
			List<String> existImageIds = new ArrayList<>();
			for (int i = 0; i < imageEntities.size(); i++) {
				WelfareImageEntity imageEntity = imageEntities.get(i);
				existImageIds.add(imageEntity.getId());
				if (!imageIds.contains(imageEntity.getId())) {
					//删除图片
					removeRollImage(goodsEntity.getId(), imageEntities, imageEntity);
					i--;
				}
			}
			//判断是否新增轮播图
			for (String imageId : imageIds) {
				if (!existImageIds.contains(imageId)) {
					generateGoodsImage(goodsEntity.getId(), imageEntities, imageId);
				}
			}
			//处理缩略图
			if (StringUtils.isNotBlank(command.getThumbId()) && command.getThumbId().equals(goodsEntity.getThumbnailFileName())) {
				imageEntities.add(thumbImage);//放回缩略图
			} else {
				newThumb = dealGoodsThumbImage(goodsEntity.getId(), goodsEntity, thumbImage, command.getThumbId());
				if (newThumb != null) {
					imageEntities.add(newThumb);//放回缩略图
				}
			}
		} else {
			//新增商品
			if (goodsDao.findWelfareGoodsByName(command.getGoodsName()) != null) {
				throw new AryaServiceException(ErrorCode.CODE_WELFARE_GOODS_SAME_NAME_EXIST);
			}
			goodsEntity.setId(Utils.makeUUID());
			goodsEntity.setCreateTime(System.currentTimeMillis());
			goodsEntity.setIsDelete(CorpConstants.FALSE);
			logMsg.append("新增商品");
			//处理缩略图
			newThumb = dealGoodsThumbImage(goodsEntity.getId(), goodsEntity, null, command.getThumbId());
			//处理轮播图
			for (String newRollImageId : command.getImageIds()) {
				generateGoodsImage(goodsEntity.getId(), imageEntities, newRollImageId);
			}
			if (firstGoods != null){
				firstGoods.setIsFirst(CorpConstants.FALSE);
				firstGoods.setPrevId(goodsEntity.getId());
				goodsEntity.setNextId(firstGoods.getId());
			}
			goodsEntity.setIsFirst(CorpConstants.TRUE);
		}
		goodsEntity.setImages(new HashSet<>(imageEntities));
		//处理分类和规格
		goodsEntity.setCategories(dealWithGoodsCategory(goodsEntity.getId(), command, goodsEntity.getCategories()));

		logMsg.append(",id:" + goodsEntity.getId());
		goodsEntity.setMarkedPrice(command.getMarkedPrice());
		logMsg.append(",市场价为:" + goodsEntity.getMarkedPrice());
		goodsEntity.setDealPrice(command.getDealPrice());
		logMsg.append(",成交价为:" + goodsEntity.getDealPrice());
		goodsEntity.setInventoryCount(command.getInventoryCount());
		logMsg.append(",每单最大购买数量:" + goodsEntity.getBuyLimit());
		goodsEntity.setBuyLimit(command.getBuyLimit());
		logMsg.append(",库存量为:" + goodsEntity.getInventoryCount());
		goodsEntity.setGoodsBrand(command.getBrand());
		logMsg.append(",品牌名称为:" + goodsEntity.getGoodsBrand());
		goodsEntity.setGoodsName(command.getGoodsName());
		logMsg.append(",名称为:" + goodsEntity.getGoodsName());
		goodsEntity.setDeliveryTimeDesc(command.getDeliveryTimeDesc());
		logMsg.append(",发货时间为:" + goodsEntity.getDeliveryTimeDesc());
		goodsEntity.setGoodsDesc(command.getDesc());
		logMsg.append(",描述为:" + goodsEntity.getGoodsDesc());
		goodsEntity.setIsOnSale(command.getOnSale());
		logMsg.append(",是否开售:" + goodsEntity.getIsOnSale());
		goodsEntity.setThumbnailFileName(command.getThumbId());
		logMsg.append(",缩略图为:" + goodsEntity.getThumbnailFileName());
		try {
			goodsEntity.setImages(new HashSet<>(imageEntities));
			goodsDao.createOrUpdate(goodsEntity);
			if (firstGoods != null){
				goodsDao.update(firstGoods);
			}
			if (needDeleteList.size() > 0) {
				imageDao.update(needDeleteList);
			}
			if (newThumb != null) {
				imageDao.create(newThumb);
			}
			opLogService.successLog(WELFARE_GOODS_EDIT, logMsg, log);
		} catch (Exception e) {
			elog.error(e.getMessage(), e);
			opLogService.failedLog(WELFARE_GOODS_EDIT, logMsg, log);
			throw new AryaServiceException(ErrorCode.CODE_WELFARE_GOODS_EDIT_FAILED);
		}
		return goodsEntity.getId();
	}

	@Override
	public WelfareAdminGoodsListResult getGoodsList(PaginationCommand command) throws AryaServiceException {
		WelfareAdminGoodsListResult listResult = new WelfareAdminGoodsListResult();
		List<WelfareAdminGoodsListResult.Goods> goodsList = new ArrayList<>();
		listResult.setGoods(goodsList);
		List<WelfareGoodsEntity> welfareGoodsEntities = goodsDao.findAllWelfareGoods();
		if (welfareGoodsEntities.size() != 0){
			List<WelfareGoodsEntity> goodsOrdered = EntityHelper.toOrderedList(welfareGoodsEntities);
			welfareGoodsEntities = goodsOrdered;
		}
		List<WelfareGoodsEntity> currentPageEntities = null;
		int total = welfareGoodsEntities.size();
		listResult.setPages(Utils.calculatePages(total, command.getPage_size()));
		currentPageEntities = welfareGoodsEntities.subList(command.getPage_size() * command.getPage(), ((command.getPage_size() * (command.getPage() + 1)) > total ? total : (command.getPage_size() * (command.getPage() + 1))));
		for (WelfareGoodsEntity welfareGoodsEntity : currentPageEntities) {
			WelfareAdminGoodsListResult.Goods goods = new WelfareAdminGoodsListResult.Goods();
			goods.setId(welfareGoodsEntity.getId());
			goods.setGoodsName(welfareGoodsEntity.getGoodsName());
			goods.setMarkedPrice(welfareGoodsEntity.getMarkedPrice());
			goods.setDealPrice(welfareGoodsEntity.getDealPrice());
			goods.setGoodsDetail(welfareGoodsEntity.getGoodsDesc());
			goods.setOnSale(welfareGoodsEntity.getIsOnSale());
			if (StringUtils.isNotBlank(welfareGoodsEntity.getThumbnailFileName())) {
				goods.setThumbUrl(generateWelfareGoodsImageUrl(welfareGoodsEntity.getId(), welfareGoodsEntity.getThumbnailFileName()));
			}
			Set<WelfareImageEntity> imageEntities = welfareGoodsEntity.getImages();
			if (imageEntities.size() > 0) {
				WelfareImageEntity imageEntity = getFirstImage(imageEntities);
				if (imageEntity != null) {
					goods.setShufflingImgUrl(generateWelfareGoodsImageUrl(welfareGoodsEntity.getId(), imageEntity.getId()));
				}
			}
			goodsList.add(goods);
		}

		List<WelfareNoticeEntity> noticeEntities = welfareNoticeDao.findAllWelfareNotice();
		if (noticeEntities.size() > 0) {
			WelfareNoticeEntity noticeEntity = noticeEntities.get(0);
			listResult.setBeginTime(noticeEntity.getBeginTime());
			listResult.setEndTime(noticeEntity.getEndTime());
		}
		return listResult;
	}

	@Override
	public void moveGoods(String goodsId, int direction) throws AryaServiceException {
		if (goodsId == null) {
			throw new AryaServiceException(ErrorCode.CODE_WELFARE_GOODS_NOT_FOUND);
		}
		try {
			WelfareGoodsEntity prevGoods = null;
			WelfareGoodsEntity nextGoods = null;
			WelfareGoodsEntity prevGoodsPrev = null;
			WelfareGoodsEntity nextGoodsNext = null;
			WelfareGoodsEntity goodEntity = goodsDao.findWelfareGoodsThrow(goodsId);
			if (StringUtils.isNotBlank(goodEntity.getPrevId())) {
				//查找上一个商品
				prevGoods = goodsDao.findWelfareGoodsThrow(goodEntity.getPrevId());
			}
			if (StringUtils.isNotBlank(goodEntity.getNextId())) {
				//查找下一个商品
				nextGoods = goodsDao.findWelfareGoodsThrow(goodEntity.getNextId());
			}
			if (direction == 1) {
				if (StringUtils.isNotBlank(goodEntity.getNextId()) && StringUtils.isNotBlank(prevGoods.getPrevId())) {
					goodEntity.setPrevId(prevGoods.getPrevId());
					goodEntity.setNextId(prevGoods.getId());
					prevGoodsPrev = goodsDao.findWelfareGoodsThrow(prevGoods.getPrevId());
					prevGoodsPrev.setNextId(goodEntity.getId());
					prevGoods.setPrevId(goodEntity.getId());
					prevGoods.setNextId(nextGoods.getId());
					nextGoods.setPrevId(prevGoods.getId());
					goodsDao.update(goodEntity);
					goodsDao.update(prevGoodsPrev);
					goodsDao.update(prevGoods);
					goodsDao.update(nextGoods);
				}
				if (StringUtils.isBlank(goodEntity.getNextId()) && StringUtils.isNotBlank(prevGoods.getPrevId())) {
					goodEntity.setPrevId(prevGoods.getPrevId());
					goodEntity.setNextId(prevGoods.getId());
					prevGoodsPrev = goodsDao.findWelfareGoodsThrow(prevGoods.getPrevId());
					prevGoodsPrev.setNextId(goodEntity.getId());
					prevGoods.setPrevId(goodEntity.getId());
					prevGoods.setNextId(null);
					goodsDao.update(goodEntity);
					goodsDao.update(prevGoodsPrev);
					goodsDao.update(prevGoods);
				}
				if (StringUtils.isBlank(prevGoods.getPrevId()) && StringUtils.isNotBlank(goodEntity.getNextId())) {
					prevGoods.setPrevId(goodEntity.getId());
					prevGoods.setNextId(goodEntity.getNextId());
					prevGoods.setIsFirst(CorpConstants.FALSE);
					goodEntity.setPrevId(null);
					goodEntity.setNextId(prevGoods.getId());
					goodEntity.setIsFirst(CorpConstants.TRUE);
					nextGoods.setPrevId(prevGoods.getId());
					goodsDao.update(prevGoods);
					goodsDao.update(goodEntity);
					goodsDao.update(nextGoods);
				}
				if (StringUtils.isBlank(goodEntity.getNextId()) && StringUtils.isBlank(prevGoods.getPrevId())) {
					goodEntity.setNextId(goodEntity.getPrevId());
					goodEntity.setPrevId(null);
					goodEntity.setIsFirst(CorpConstants.TRUE);
					prevGoods.setPrevId(goodEntity.getId());
					prevGoods.setNextId(null);
					prevGoods.setIsFirst(CorpConstants.FALSE);
					goodsDao.update(goodEntity);
					goodsDao.update(prevGoods);
				}
			}
			if (direction == 2) {
				if (StringUtils.isNotBlank(goodEntity.getPrevId()) && StringUtils.isNotBlank(nextGoods.getNextId())) {
					goodEntity.setPrevId(nextGoods.getId());
					goodEntity.setNextId(nextGoods.getNextId());
					nextGoodsNext = goodsDao.findWelfareGoodsThrow(nextGoods.getNextId());
					nextGoodsNext.setPrevId(goodEntity.getId());
					nextGoods.setPrevId(prevGoods.getId());
					nextGoods.setNextId(goodEntity.getId());
					prevGoods.setNextId(nextGoods.getId());
					goodsDao.update(goodEntity);
					goodsDao.update(nextGoodsNext);
					goodsDao.update(nextGoods);
					goodsDao.update(prevGoods);
				}
				if (StringUtils.isBlank(goodEntity.getPrevId()) && StringUtils.isNotBlank(nextGoods.getNextId())) {
					goodEntity.setPrevId(nextGoods.getId());
					goodEntity.setNextId(nextGoods.getNextId());
					goodEntity.setIsFirst(CorpConstants.FALSE);
					nextGoodsNext = goodsDao.findWelfareGoodsThrow(nextGoods.getNextId());
					nextGoodsNext.setPrevId(goodEntity.getId());
					nextGoods.setPrevId(null);
					nextGoods.setNextId(goodEntity.getId());
					nextGoods.setIsFirst(CorpConstants.TRUE);
					goodsDao.update(goodEntity);
					goodsDao.update(nextGoodsNext);
					goodsDao.update(nextGoods);
				}
				if (StringUtils.isNotBlank(goodEntity.getPrevId()) && StringUtils.isBlank(nextGoods.getNextId())) {
					goodEntity.setPrevId(nextGoods.getId());
					goodEntity.setNextId(null);
					prevGoods.setNextId(nextGoods.getId());
					nextGoods.setPrevId(prevGoods.getId());
					nextGoods.setNextId(goodEntity.getId());
					goodsDao.update(goodEntity);
					goodsDao.update(prevGoods);
					goodsDao.update(nextGoods);
				}
				if (StringUtils.isBlank(goodEntity.getPrevId()) && StringUtils.isBlank(nextGoods.getNextId())) {
					goodEntity.setPrevId(nextGoods.getId());
					goodEntity.setNextId(null);
					goodEntity.setIsFirst(CorpConstants.FALSE);
					nextGoods.setNextId(goodEntity.getId());
					nextGoods.setPrevId(null);
					nextGoods.setIsFirst(CorpConstants.TRUE);
					goodsDao.update(goodEntity);
					goodsDao.update(nextGoods);
				}
			}
		} catch (Exception e) {
			elog.error(e.getMessage(), e);
			throw new AryaServiceException(ErrorCode.CODE_WELFARE_GOODS_MOVE_FILED);
		}
	}

	@Override
	public String generateWelfareGoodsImageUrl(String goodsId, String imageId) {
		return WELFARE_GOODS_IMAGE_URL + "?goods_id=" + goodsId + "&image_id=" + imageId;
	}

	@Override
	public String generateCouponQRCodeUrl(String couponId, String couponCode) {
		return aryaConfigService.getServerURL() + COUPON_QR_CODE_URL + "?coupon_id=" + couponId + "&coupon_code=" + couponCode;
	}

	@Override
	public WelfareImageEntity getFirstImage(Set<WelfareImageEntity> imageEntitySet) {
		if (imageEntitySet != null && imageEntitySet.size() > 0) {
			for (WelfareImageEntity imageEntity : imageEntitySet) {
				if (imageEntity.getIsFirst() == CorpConstants.TRUE) {
					return imageEntity;
				}
			}
		}
		return null;
	}

	@Override
	public WelfareGoodsDetailResult getGoodsDetail(String goodsId) throws AryaServiceException {
		WelfareGoodsDetailResult detailResult = new WelfareGoodsDetailResult();
		WelfareGoodsEntity goodsEntity = goodsDao.findWelfareGoodsThrow(goodsId);
		if (StringUtils.isNotBlank(goodsEntity.getThumbnailFileName())) {
			WelfareGoodsDetailResult.Image thumb = new WelfareGoodsDetailResult.Image();
			thumb.setId(goodsEntity.getThumbnailFileName());
			thumb.setUrl(generateWelfareGoodsImageUrl(goodsEntity.getId(), goodsEntity.getThumbnailFileName()));
			detailResult.setThumb(thumb);
		}
		detailResult.setOnSale(goodsEntity.getIsOnSale());
		detailResult.setInventoryCount(goodsEntity.getInventoryCount());
		detailResult.setBuyLimit(goodsEntity.getBuyLimit());
		detailResult.setDealPrice(goodsEntity.getDealPrice());
		detailResult.setMarkedPrice(goodsEntity.getMarkedPrice());
		detailResult.setGoodsName(goodsEntity.getGoodsName());
		detailResult.setBrand(goodsEntity.getGoodsBrand());
		detailResult.setDesc(goodsEntity.getGoodsDesc());
		detailResult.setDeliveryTimeDesc(goodsEntity.getDeliveryTimeDesc());
		List<WelfareGoodsDetailResult.Image> images = new ArrayList<>();
		for (WelfareImageEntity imageEntity : welfareServiceCommonService.getGoodsSortedImages(new ArrayList<>(goodsEntity.getImages()), goodsEntity.getThumbnailFileName())) {
			if (imageEntity.getId().equals(goodsEntity.getThumbnailFileName())) {
				continue;
			}
			WelfareGoodsDetailResult.Image image = new WelfareGoodsDetailResult.Image();
			image.setId(imageEntity.getId());
			image.setUrl(generateWelfareGoodsImageUrl(goodsEntity.getId(), imageEntity.getId()));
			images.add(image);
		}
		List<WelfareGoodsDetailResult.Category> categories = new ArrayList<>();
		for (WelfareGoodsCategoryEntity goodsCategoryEntity : goodsEntity.getCategories()) {
			//赋值分类
			WelfareGoodsDetailResult.Category category = new WelfareGoodsDetailResult.Category();
			WelfareCategoryEntity categoryEntity = goodsCategoryEntity.getCategoryEntity();
			if (categoryEntity == null) {
				continue;
			}
			category.setId(categoryEntity.getId());
			//赋值规格
			List<String> specIds = new ArrayList<>();
			for (WelfareGoodsCategorySpecEntity goodsCategorySpecEntity : goodsCategoryEntity.getSpecs()) {
				WelfareSpecEntity specEntity = goodsCategorySpecEntity.getSpecEntity();
				if (specEntity == null) {
					continue;
				}
				specIds.add(specEntity.getId());
			}
			category.setSpecIds(specIds);
			categories.add(category);
		}
		detailResult.setImages(images);
		detailResult.setExistCategories(categories);
		return detailResult;
	}

	@Override
	public WelfareAllCategoriesListResult getAllCategories() throws AryaServiceException {
		WelfareAllCategoriesListResult categoriesListResult = new WelfareAllCategoriesListResult();
		List<SimpleIdNameResult> results = new ArrayList<>();
		List<WelfareCategoryEntity> categoryEntities = welfareCategoryDao.findAllWelfareCategory();
		for (WelfareCategoryEntity categoryEntity : categoryEntities) {
			SimpleIdNameResult result = new SimpleIdNameResult();
			result.setId(categoryEntity.getId());
			result.setName(categoryEntity.getCategoryName());
			results.add(result);
		}
		categoriesListResult.setCategories(results);
		return categoriesListResult;
	}

	@Override
	public WelfareCategoriesAllSpecsListResult getCategoryAllSpecs(String categoryId) throws AryaServiceException {
		WelfareCategoriesAllSpecsListResult specsListResult = new WelfareCategoriesAllSpecsListResult();
		List<SimpleIdNameResult> results = new ArrayList<>();
		List<WelfareSpecEntity> welfareSpecEntities = welfareSpecDao.findCategoryAllSpecs(categoryId);

		List<WelfareSpecEntity> ordered = EntityHelper.toOrderedList(welfareSpecEntities);

		for (WelfareSpecEntity welfareSpecEntity : ordered) {
			SimpleIdNameResult simpleIdNameResult = new SimpleIdNameResult();
			simpleIdNameResult.setId(welfareSpecEntity.getId());
			simpleIdNameResult.setName(welfareSpecEntity.getGoodsSpecName());
			results.add(simpleIdNameResult);
		}
		specsListResult.setSpecs(results);
		return specsListResult;
	}

	@Override
	public WelfareUploadImageResult uploadGoodsImage(MultipartFile file) throws AryaServiceException {
		WelfareUploadImageResult result = new WelfareUploadImageResult();
		result.setId(tempSaveWelfareGoodsImage(file));
		result.setUrl(generateWelfareGoodsImageUrl("temp", result.getId()));
		return result;
	}

	@Override
	public WelfareImageEntity removeRollImage(String goodsId, List<WelfareImageEntity> images, WelfareImageEntity removeImage) {
		removeImage.setIsDelete(CorpConstants.TRUE);
		images.remove(removeImage);
		commonFileService.deleteFile(config.getWelfareGoodsImagePath(goodsId) + removeImage.getId() + ".jpg");
		if (images.size() == 0) {
			return removeImage;
		}

		if (removeImage.getIsFirst() == CorpConstants.TRUE) {
			//如果移除的是第一张图的话
			WelfareImageEntity newFirstImage = images.get(0);
			newFirstImage.setIsFirst(CorpConstants.TRUE);
			newFirstImage.setPrevPropId(null);
		}

		//修改上一张图片的下一张图片
		for (WelfareImageEntity imageEntity : images) {
			if (StringUtils.isNotBlank(imageEntity.getNextImgId()) && imageEntity.getNextImgId().equals(removeImage.getId())) {
				imageEntity.setNextImgId(removeImage.getNextImgId());
				break;
			}
		}

		//修改下一张图片的上一张图片
		if (StringUtils.isNotBlank(removeImage.getNextImgId())) {
			for (WelfareImageEntity imageEntity : images) {
				if (imageEntity.getId().equals(removeImage.getNextImgId())) {
					imageEntity.setPrevPropId(removeImage.getPrevPropId());
					break;
				}
			}
		}
		return removeImage;
	}

	@Override
	public WelfareImageEntity addRollImage(String goodsId, List<WelfareImageEntity> images, WelfareImageEntity newImage) {
		if (images.size() == 0) {
			images.add(newImage);
			newImage.setIsFirst(CorpConstants.TRUE);
			return newImage;
		}
		WelfareImageEntity lastImage = images.get(images.size() - 1);
		lastImage.setNextImgId(newImage.getId());
		newImage.setPrevPropId(lastImage.getId());
		newImage.setIsFirst(CorpConstants.FALSE);
		return newImage;
	}

	@Override
	public WelfareImageEntity dealGoodsThumbImage(String goodsId, WelfareGoodsEntity goodsEntity, WelfareImageEntity existThumb, String commandThumbId) {
		if (StringUtils.isNotBlank(commandThumbId)) {
			if (existThumb != null) {
				//删除旧的
				imageDao.deleteImage(existThumb.getId());
				//删除图片
				commonFileService.deleteFile(config.getWelfareGoodsImagePath(goodsId) + commandThumbId + ".jpg");

				//修改
				goodsEntity.setThumbnailFileName(commandThumbId);
			}
			//新增
			goodsEntity.setThumbnailFileName(commandThumbId);
			WelfareImageEntity newThumb = new WelfareImageEntity();
			newThumb.setId(commandThumbId);
			newThumb.setIsThumbnail(CorpConstants.FALSE);
			newThumb.setIsDelete(CorpConstants.FALSE);
			newThumb.setCreateTime(System.currentTimeMillis());
			newThumb.setIsFirst(0);
			newThumb.setGoodsId(goodsEntity.getId());
			//移动图片存放位置
			commonFileService.moveFile(config.getWelfareGoodsImagePath("temp") + commandThumbId + ".jpg", config.getWelfareGoodsImagePath(goodsId) + commandThumbId + ".jpg");
			return newThumb;
		} else {
			if (existThumb != null) {
				goodsEntity.setThumbnailFileName(null);
				//删除
				imageDao.deleteImage(existThumb.getId());
				//删除图片
				commonFileService.deleteFile(config.getWelfareGoodsImagePath(goodsId) + commandThumbId + ".jpg");
			}
		}
		return null;
	}

	@Override
	public void generateGoodsImage(String goodsId, List<WelfareImageEntity> imageEntities, String newImageId) {
		WelfareImageEntity newImage = new WelfareImageEntity();
		newImage.setId(newImageId);
		newImage.setIsDelete(CorpConstants.FALSE);
		newImage.setIsThumbnail(CorpConstants.FALSE);
		newImage.setCreateTime(System.currentTimeMillis());
		newImage.setGoodsId(goodsId);
		imageEntities.add(addRollImage(goodsId, imageEntities, newImage));
		//移动图片存放位置
		commonFileService.moveFile(config.getWelfareGoodsImagePath("temp") + newImageId + ".jpg", config.getWelfareGoodsImagePath(goodsId) + newImageId + ".jpg");
	}

	@Override
	public Set<WelfareGoodsCategoryEntity> dealWithGoodsCategory(String goodsId, WelfareGoodsEditCommand command, Set<WelfareGoodsCategoryEntity> existGoodsCategories) throws AryaServiceException {
		if (command.getCategories() == null || command.getCategories().size() == 0) {
			return null;
		}

		List<String> existCategoryIds = new ArrayList<>();
		Map<String, WelfareGoodsEditCommand.Category> commandCategoryMap = new HashMap<>();
		for (WelfareGoodsEditCommand.Category category : command.getCategories()) {
			commandCategoryMap.put(category.getId(), category);
		}

		for (WelfareGoodsCategoryEntity existGoodsCategory : existGoodsCategories) {
			WelfareCategoryEntity welfareCategory = existGoodsCategory.getCategoryEntity();
			existCategoryIds.add(welfareCategory.getId());
			if (!commandCategoryMap.containsKey(welfareCategory.getId())) {
				//判断已有的分类是否需要删除
				existGoodsCategories.remove(existGoodsCategory);
				goodsCategorySpecDao.deleteGoodsSpecsByGoodsCategoryId(existGoodsCategory.getId());
				goodsCategoryDao.deleteGoodsCategory(existGoodsCategory.getId());
			} else {
				//判断分类下是否修改规格
				//是否需要删除
				List<WelfareGoodsCategorySpecEntity> existGoodsCategorySpecEntities = new ArrayList<>(existGoodsCategory.getSpecs());
				Map<String, WelfareSpecEntity> thisCategoryExistSpecsMap = new HashMap<>();
				for (int i = 0; i < existGoodsCategorySpecEntities.size(); i++) {
					WelfareGoodsCategorySpecEntity goodsCategorySpecEntity = existGoodsCategorySpecEntities.get(i);
					WelfareSpecEntity thisGoodCategroySpecSpec = goodsCategorySpecEntity.getSpecEntity();
					if (thisGoodCategroySpecSpec != null) {
						thisCategoryExistSpecsMap.put(thisGoodCategroySpecSpec.getId(), thisGoodCategroySpecSpec);
					} else {
						continue;
					}
					WelfareGoodsEditCommand.Category category = commandCategoryMap.get(welfareCategory.getId());
					if (category == null) {
						existGoodsCategorySpecEntities.remove(category);
						i--;
						existGoodsCategory.setSpecs(new HashSet<>(existGoodsCategorySpecEntities));
						continue;
					}
					if (!category.getSpecIds().contains(thisGoodCategroySpecSpec.getId())) {
						existGoodsCategorySpecEntities.remove(goodsCategorySpecEntity);
						i--;
						existGoodsCategory.setSpecs(new HashSet<>(existGoodsCategorySpecEntities));
						continue;
					}
				}

				//判断是否需要新增
				WelfareGoodsEditCommand.Category category = commandCategoryMap.get(existGoodsCategory.getCategoryEntity().getId());
				for (String commandSepcId : category.getSpecIds()) {
					if (!thisCategoryExistSpecsMap.containsKey(commandSepcId)) {
						//新增
						WelfareSpecEntity welfareSpecEntity = welfareSpecDao.findWelfareSpecThrow(commandSepcId);
						WelfareGoodsCategorySpecEntity newGoodsSpec = new WelfareGoodsCategorySpecEntity();
						newGoodsSpec.setId(Utils.makeUUID());
						newGoodsSpec.setCreateTime(System.currentTimeMillis());
						newGoodsSpec.setSpecEntity(welfareSpecEntity);
						Set<WelfareGoodsCategorySpecEntity> goodsCategorySpecEntities = existGoodsCategory.getSpecs();
						goodsCategorySpecEntities.add(newGoodsSpec);
						existGoodsCategory.setSpecs(goodsCategorySpecEntities);
						log.info("【新增商品规格】新建规格ID:" + newGoodsSpec.getId() + ",关联WelfareSpecId:" + welfareSpecEntity.getId());
					}
				}
			}
		}

		//判断是否需要新增
		log.info("【新增商品分类及规格】开始判断是否需要新增商品分类。");
		for (WelfareGoodsEditCommand.Category category : command.getCategories()) {
			if (!existCategoryIds.contains(category.getId())) {
				//新增
				WelfareCategoryEntity welfareCategoryEntity = welfareCategoryDao.findWelfareCategoryThrow(category.getId());
				WelfareGoodsCategoryEntity goodsCategoryEntity = new WelfareGoodsCategoryEntity();
				goodsCategoryEntity.setId(Utils.makeUUID());
				goodsCategoryEntity.setWelfareGoodsId(goodsId);
				goodsCategoryEntity.setCreateTime(System.currentTimeMillis());
				goodsCategoryEntity.setCategoryEntity(welfareCategoryEntity);
				log.info("【新增商品分类】新建分类ID:" + goodsCategoryEntity.getId() + ",关联WelfareCategoryId:" + welfareCategoryEntity.getId());
				for (String specId : category.getSpecIds()) {
					WelfareSpecEntity spec = welfareSpecDao.findWelfareSpecThrow(specId);
					WelfareGoodsCategorySpecEntity goodsCategorySpec = new WelfareGoodsCategorySpecEntity();
					goodsCategorySpec.setId(Utils.makeUUID());
					goodsCategorySpec.setCreateTime(System.currentTimeMillis());
					goodsCategorySpec.setSpecEntity(spec);
					Set<WelfareGoodsCategorySpecEntity> goodsCategorySpecEntities = goodsCategoryEntity.getSpecs();
					goodsCategorySpecEntities.add(goodsCategorySpec);
					goodsCategoryEntity.setSpecs(goodsCategorySpecEntities);
					log.info("【新增商品规格】新建规格ID:" + goodsCategorySpec.getId() + ",关联WelfareSpecId:" + spec.getId());
				}
				existGoodsCategories.add(goodsCategoryEntity);
			}
		}

		return existGoodsCategories;
	}

	@Override
	public void deleteGoods(String goodsId) throws AryaServiceException {
		WelfareGoodsEntity goodsEntity = goodsDao.findWelfareGoodsThrow(goodsId);
		//检查商品是否存在订单
		WelfareOrderItemEntity latestOrderItemEntity = orderItemDao.findGoodsLatestOrderItem(goodsId);
		if (latestOrderItemEntity != null) {
			throw new AryaServiceException(ErrorCode.CODE_WELFARE_GOODS_EXIST_ORDER_CAN_NOT_DELETE);
		}
		try {
			Set<WelfareGoodsCategoryEntity> goodsCategoryEntities = goodsEntity.getCategories();
			List<String> goodsCategoryIds = new ArrayList<>();
			for (WelfareGoodsCategoryEntity goodsCategoryEntity : goodsCategoryEntities) {
				goodsCategoryIds.add(goodsCategoryEntity.getId());
				List<String> goodsSpecIds = new ArrayList<>();
				for (WelfareGoodsCategorySpecEntity goodsCategorySpecEntity : goodsCategoryEntity.getSpecs()) {
					goodsSpecIds.add(goodsCategorySpecEntity.getId());
				}
				if (goodsSpecIds.size() > 0) {
					goodsCategorySpecDao.deleteGoodsSpecs(goodsSpecIds);
				}
			}
			if (goodsCategoryIds.size() > 0) {
				goodsCategoryDao.deleteGoodsCategories(goodsCategoryIds);
			}
			List<String> imageIds = new ArrayList<>();
			for (WelfareImageEntity imageEntity : goodsEntity.getImages()) {
				imageIds.add(imageEntity.getId());
			}
			if (imageIds.size() > 0) {
				imageDao.deleteImages(imageIds);
			}
			WelfareGoodsEntity nextGoods = goodsDao.findWelfareGoodsThrow(goodsEntity.getNextId());
			WelfareGoodsEntity prevGoods = goodsDao.findWelfareGoodsThrow(goodsEntity.getPrevId());
			if (goodsEntity.isFirst() == CorpConstants.TRUE) {
				nextGoods.setIsFirst(CorpConstants.TRUE);
				nextGoods.setPrevId(null);
			} else if (goodsEntity.getNextId() == null) {
				prevGoods.setNextId(null);
			} else {
				nextGoods.setPrevId(goodsEntity.getPrevId());
				prevGoods.setNextId(goodsEntity.getNextId());
			}
			goodsDao.update(nextGoods);
			goodsDao.update(prevGoods);
			goodsDao.deleteGoods(goodsEntity.getId());
		} catch (Exception e) {
			elog.error(e.getMessage(), e);
			throw new AryaServiceException(ErrorCode.CODE_WELFARE_GOODS_DELETE_FAILED);
		}
	}

	@Override
	public List<WelfareOrderListResult.WelfareOrderResult> generateOrderList(List<WelfareOrderEntity> orderEntities, boolean isExport) {
		List<WelfareOrderListResult.WelfareOrderResult> orderResults = new ArrayList<>();
		Map<String, CorporationEntity> aryaCorporationEntityMap = new HashMap<>();
		Map<String, BranCorporationEntity> branCorporationEntityMap = new HashMap<>();
		for (WelfareOrderEntity orderEntity : orderEntities) {
			List<WelfareOrderItemEntity> orderItems = new ArrayList<>(orderEntity.getOrderItems());
			if (orderItems.size() == 0) {
				continue;
			}
			WelfareOrderItemEntity orderItemEntity = orderItems.get(0);
			//测试商品的订单不导出
			if (orderItemEntity.getGoodsName().equals("测试商品")) {
				continue;
			}
			WelfareGoodsEntity goodsEntity = goodsDao.findWelfareGoods(orderItemEntity.getGoodsId());
			CorporationEntity corporationEntity = null;
			if (aryaCorporationEntityMap.containsKey(orderEntity.getCorpId())) {
				corporationEntity = aryaCorporationEntityMap.get(orderEntity.getCorpId());
			} else {
				corporationEntity = corporationDao.findCorporationById(orderEntity.getCorpId());
				if (corporationEntity != null) {
					aryaCorporationEntityMap.put(corporationEntity.getId(), corporationEntity);
				}
			}


			WelfareOrderListResult.WelfareOrderResult result = new WelfareOrderListResult.WelfareOrderResult();
			result.setId(orderEntity.getId());
			result.setCreateTime(orderEntity.getCreateTime());
			result.setOrderNo(orderEntity.getOrderNo());
			result.setOrderStatus(orderEntity.getOrderStatus());
			result.setGoodName(orderItemEntity.getGoodsName());
			result.setGoodCount(orderItemEntity.getCount());
			result.setReceiverName(orderEntity.getReceiver());
			result.setReceiverCorp(orderEntity.getCorpName());
			result.setReceiverDepartment(orderEntity.getDepartmentName());
			result.setReceiverAddress(orderEntity.getCorpName() + "-" + orderEntity.getDepartmentName());
			result.setReceiverAddressDetail("");
			result.setPayPlatformType(orderEntity.getPayPlatformType());
			if (corporationEntity != null && StringUtils.isNotBlank(corporationEntity.getBranCorpId())) {
				BranCorporationEntity branCorporationEntity = null;
				if (branCorporationEntityMap.containsKey(corporationEntity.getBranCorpId())) {
					branCorporationEntity = branCorporationEntityMap.get(corporationEntity.getBranCorpId());
				} else {
					branCorporationEntity = branCorporationDao.findCorpById(corporationEntity.getBranCorpId());
					if (branCorporationEntity != null) {
						branCorporationEntityMap.put(branCorporationEntity.getId(), branCorporationEntity);
					}
				}
				if (branCorporationEntity != null) {
					result.setReceiverAddressDetail(branCorporationEntity.getAddress());
				}
			}

			result.setReceiverPhone(orderEntity.getReceiverPhoneNo());
			result.setPayment(orderEntity.getPayment());
			result.setPayBalance(orderEntity.getPayBalance());
			result.setPayOnline(orderEntity.getPayOnline());
			result.setCreateTimeStr(SysUtils.getDateSecondStringFormTimestamp(orderEntity.getCreateTime()));
			if (goodsEntity != null) {
				result.setGoodsId(goodsEntity.getId());
				result.setBrand(goodsEntity.getGoodsBrand());
			} else {
				result.setGoodsId(null);
				result.setBrand("商品已被删除");
			}
			List<String> specs = new ArrayList<>();
			for (WelfareOrderItemSpecEntity orderItemSpecEntity : orderItemEntity.getItemSpecs()) {
				specs.add(orderItemSpecEntity.getGoodsSpecName());
			}
			result.setGoodSpecName(StringUtils.join(specs, ","));
			orderResults.add(result);
		}
		return orderResults;
	}

	@Override
	public WelfareOrderCategoryDetailResult getOrderCategoryDetail(String orderId) {
		WelfareOrderCategoryDetailResult detailResult = new WelfareOrderCategoryDetailResult();
		WelfareOrderEntity orderEntity = orderDao.findWelfareOrderWithDeletedThrow(orderId);
		detailResult.setOrderId(orderId);
		List<WelfareOrderItemEntity> itemEntities = new ArrayList<>(orderEntity.getOrderItems());
		if (itemEntities.size() == 0) {
			return detailResult;
		}
		WelfareOrderItemEntity itemEntity = itemEntities.get(0);
		List<WelfareOrderItemSpecEntity> orderItemSpecEntities = new ArrayList<>(itemEntity.getItemSpecs());
		if (orderItemSpecEntities.size() == 0) {
			return detailResult;
		}
		List<String> goodsCategoryIds = new ArrayList<>();
		Map<String, WelfareGoodsCategoryEntity> goodsCategoryId2GoodsCategoryEntityMap = new HashMap<>();
		for (WelfareOrderItemSpecEntity itemSpecEntity : orderItemSpecEntities) {
			if (!goodsCategoryIds.contains(itemSpecEntity.getGoodsCategoryId())) {
				goodsCategoryIds.add(itemSpecEntity.getGoodsCategoryId());
			}
		}
		if (goodsCategoryIds.size() == 0) {
			return detailResult;
		}
		for (String goodsCategoryId : goodsCategoryIds) {
			if (!goodsCategoryId2GoodsCategoryEntityMap.containsKey(goodsCategoryId)) {
				WelfareGoodsCategoryEntity goodsCategoryEntity = goodsCategoryDao.findWelfareGoodsCategory(goodsCategoryId);
				if (goodsCategoryEntity == null) {
					continue;
				}
				goodsCategoryId2GoodsCategoryEntityMap.put(goodsCategoryId, goodsCategoryEntity);
			}
		}

		//组装返回数据
		List<WelfareGoodsDetailResult.Category> categories = new ArrayList<>();
		Map<String, WelfareGoodsDetailResult.Category> welfareCategoryId2CategoryResultMap = new HashMap<>();
		for (WelfareOrderItemSpecEntity itemSpecEntity : orderItemSpecEntities) {
			WelfareGoodsCategoryEntity goodsCategoryEntity = goodsCategoryId2GoodsCategoryEntityMap.get(itemSpecEntity.getGoodsCategoryId());
			if (!categories.contains(goodsCategoryEntity.getId())) {
				WelfareGoodsDetailResult.Category category = new WelfareGoodsDetailResult.Category();
				category.setId(goodsCategoryEntity.getId());
				List<String> specIds = new ArrayList<>();
				category.setSpecIds(specIds);
				detailResult.setCategories(categories);
				welfareCategoryId2CategoryResultMap.put(goodsCategoryEntity.getId(), category);
				categories.add(category);
			}
			WelfareGoodsCategorySpecEntity goodsCategorySpecEntity = goodsCategorySpecDao.findWelfareGoodsCategorySpec(itemSpecEntity.getGoodsSpecId());
			if (goodsCategorySpecEntity == null) {
				continue;
			}
			WelfareGoodsDetailResult.Category category = welfareCategoryId2CategoryResultMap.get(goodsCategoryEntity.getId());
			category.getSpecIds().add(goodsCategorySpecEntity.getId());
		}
		return detailResult;
	}

	@Override
	public void adjustOrderCategoryDetail(AdjustOrderCategoryDetailCommand command) throws AryaServiceException {
		WelfareOrderEntity welfareOrderEntity = orderDao.findWelfareOrderWithDeletedThrow(command.getOrderId());
		List<WelfareOrderItemEntity> welfareOrderItemEntities = new ArrayList<>(welfareOrderEntity.getOrderItems());
		if (welfareOrderItemEntities.size() == 0) {
			return;
		}
		WelfareOrderItemEntity orderItemEntity = welfareOrderItemEntities.get(0);
		Map<String, WelfareGoodsDetailResult.Category> categoryMap = new HashMap<>();//key:categoryId,key:category
		for (WelfareGoodsDetailResult.Category category : command.getCategories()) {
			if (!categoryMap.containsKey(category.getId())) {
				categoryMap.put(category.getId(), category);
			}
		}
		List<WelfareOrderItemSpecEntity> orderItemSpecEntities = new ArrayList<>(orderItemEntity.getItemSpecs());
		//判断是否要删除分类或删除规格
		for (int i = 0; i < orderItemSpecEntities.size(); i++) {
			WelfareOrderItemSpecEntity itemSpecEntity = orderItemSpecEntities.get(i);
			WelfareGoodsDetailResult.Category category = categoryMap.get(itemSpecEntity.getGoodsCategoryId());
			if (category != null) {
				//判断规格是否要删除
				if (!category.getSpecIds().contains(itemSpecEntity.getGoodsSpecId())) {
					//删除
					orderItemSpecEntities.remove(itemSpecEntity);
					i--;
				}
			} else {
				//删除
				orderItemSpecEntities.remove(itemSpecEntity);
				i--;
			}
		}
		orderItemEntity.setItemSpecs(new HashSet<>(orderItemSpecEntities));

		//收集剩下的分类实体和规格实体ID
		List<String> existCategoryIds = new ArrayList<>();
		List<String> existSpecIds = new ArrayList<>();
		for (WelfareOrderItemSpecEntity orderItemSpecEntity : orderItemSpecEntities) {
			if (!existCategoryIds.contains(orderItemSpecEntity.getGoodsCategoryId())) {
				existCategoryIds.add(orderItemSpecEntity.getGoodsCategoryId());
			}
			if (!existSpecIds.contains(orderItemSpecEntity.getGoodsSpecId())) {
				existSpecIds.add(orderItemSpecEntity.getGoodsSpecId());
			}
		}

		Map<String, WelfareGoodsCategoryEntity> goodsCategoryEntityMap = new HashMap<>();//key:goodsCategoryId,value:goodsCategoryEntity
		Map<String, WelfareCategoryEntity> welfareCategoryEntityMap = new HashMap<>();//key;goodsCategoryId,value:welfareCategoryEntity
		//判断是否要新增分类或规格
		for (String categoryId : categoryMap.keySet()) {
			WelfareGoodsDetailResult.Category category = categoryMap.get(categoryId);
			for (String specId : category.getSpecIds()) {
				if (!existSpecIds.contains(specId)) {
					//新增规格
					orderItemEntity.getItemSpecs().add(generateNewOrderItemSpec(categoryId, specId, welfareCategoryEntityMap, goodsCategoryEntityMap));
					orderItemEntity.setItemSpecs(orderItemEntity.getItemSpecs());
				}
			}
		}
		try {
			orderItemDao.update(orderItemEntity);
		} catch (Exception e) {
			elog.error(e.getMessage(), e);
			throw new AryaServiceException(ErrorCode.CODE_WELFARE_ADJUST_ORDER_CATEGORY_OR_SPEC_FAILED);
		}
	}

	@Override
	public WelfareAllCategoriesListResult getGoodsAllCategory(String goodsId) throws AryaServiceException {
		WelfareGoodsEntity goodsEntity = goodsDao.findWelfareGoodsThrow(goodsId);
		WelfareAllCategoriesListResult listResult = new WelfareAllCategoriesListResult();
		List<SimpleIdNameResult> categories = new ArrayList<>();
		listResult.setCategories(categories);
		for (WelfareGoodsCategoryEntity goodsCategoryEntity : goodsEntity.getCategories()) {
			WelfareCategoryEntity categoryEntity = goodsCategoryEntity.getCategoryEntity();
			if (categoryEntity == null) {
				continue;
			}
			SimpleIdNameResult simpleIdNameResult = new SimpleIdNameResult();
			simpleIdNameResult.setId(goodsCategoryEntity.getId());
			simpleIdNameResult.setName(categoryEntity.getCategoryName());
			categories.add(simpleIdNameResult);
		}
		return listResult;
	}

	@Override
	public WelfareCategoriesAllSpecsListResult getGoodsCategoryAllSpecs(String categoryId) throws AryaServiceException {
		WelfareGoodsCategoryEntity goodsCategoryEntity = goodsCategoryDao.findWelfareGoodsCategoryThrow(categoryId);
		WelfareCategoriesAllSpecsListResult listResult = new WelfareCategoriesAllSpecsListResult();
		List<SimpleIdNameResult> specs = new ArrayList<>();
		listResult.setSpecs(specs);
		for (WelfareGoodsCategorySpecEntity goodsCategorySpecEntity : goodsCategoryEntity.getSpecs()) {
			WelfareSpecEntity specEntity = goodsCategorySpecEntity.getSpecEntity();
			if (specEntity == null) {
				continue;
			}
			SimpleIdNameResult simpleIdNameResult = new SimpleIdNameResult();
			simpleIdNameResult.setId(goodsCategorySpecEntity.getId());
			simpleIdNameResult.setName(specEntity.getGoodsSpecName());
			specs.add(simpleIdNameResult);
		}
		return listResult;
	}

	@Override
	public WelfareOrderItemSpecEntity generateNewOrderItemSpec(String goodsCategoryId, String goodsSpecId, Map<String, WelfareCategoryEntity> welfareCategoryEntityMap, Map<String, WelfareGoodsCategoryEntity> goodsCategoryEntityMap) throws AryaServiceException {
		WelfareGoodsCategoryEntity goodsCategoryEntity = goodsCategoryEntityMap.get(goodsCategoryId);
		if (goodsCategoryEntity == null) {
			goodsCategoryEntity = goodsCategoryDao.findWelfareGoodsCategoryThrow(goodsCategoryId);
		}
		WelfareCategoryEntity welfareCategoryEntity = welfareCategoryEntityMap.get(goodsSpecId);
		if (welfareCategoryEntity == null) {
			welfareCategoryEntity = goodsCategoryEntity.getCategoryEntity();
			if (welfareCategoryEntity == null) {
				throw new AryaServiceException(ErrorCode.CODE_WELFARE_CATEGORY_NO_FOUND);
			}
		}
		WelfareGoodsCategorySpecEntity goodsCategorySpecEntity = goodsCategorySpecDao.findWelfareGoodsCategorySpecThrow(goodsSpecId);
		WelfareSpecEntity welfareSpecEntity = goodsCategorySpecEntity.getSpecEntity();
		if (welfareSpecEntity == null) {
			throw new AryaServiceException(ErrorCode.CODE_WELFARE_CATEGORY_SPEC_NO_FOUND);
		}
		WelfareOrderItemSpecEntity itemSpecEntity = new WelfareOrderItemSpecEntity();
		itemSpecEntity.setId(Utils.makeUUID());
		itemSpecEntity.setCreateTime(System.currentTimeMillis());
		itemSpecEntity.setGoodsCategoryId(goodsCategoryId);
		itemSpecEntity.setGoodsCategoryName(welfareCategoryEntity.getCategoryName());
		itemSpecEntity.setGoodsSpecId(goodsSpecId);
		itemSpecEntity.setGoodsSpecName(welfareSpecEntity.getGoodsSpecName());
		return itemSpecEntity;
	}

	@Override
	public void deleteOrder(String orderId) throws AryaServiceException {
		if (StringUtils.isAnyBlank(orderId)) {
			throw new AryaServiceException(ErrorCode.CODE_VALIDATION_ID_BLANK);
		}
		StringBuffer logMsg = new StringBuffer("【福库订单】删除福库订单。");
		logMsg.append("订单ID:" + orderId);
		WelfareOrderEntity welfareOrderEntity = orderDao.findWelfareOrderThrow(orderId);
		welfareOrderEntity.setIsDelete(CorpConstants.TRUE);
		try {
			orderDao.update(welfareOrderEntity);
			opLogService.successLog(WELFARE_ORDER_DELETE, logMsg, log);
		} catch (Exception e) {
			elog.error(e.getMessage(), e);
			opLogService.failedLog(WELFARE_ORDER_DELETE, logMsg, log);
			throw new AryaServiceException(ErrorCode.CODE_WELFARE_DELETE_ORDER_FAILED);
		}
	}

	@Override
	public void orderRefund(OrderRefundCommand command) throws AryaServiceException {
		WelfareOrderEntity orderEntity = orderDao.findWelfareOrderThrow(command.getOrderId());
		if (orderEntity.getOrderStatus() != WelfareOrderStatus.ORDER_PAYED && orderEntity.getOrderStatus() != WelfareOrderStatus.ORDER_SHIPPED) {
			throw new AryaServiceException(ErrorCode.CODE_WELFARE_NOT_PAY);
		}
		Set<WelfareOrderItemEntity> orderItemEntities = orderEntity.getOrderItems();
		WelfareGoodsEntity goodsEntity = null;
		for (WelfareOrderItemEntity orderItemEntity : orderItemEntities) {
			goodsEntity = goodsDao.findWelfareGoodsThrow(orderItemEntity.getGoodsId());
			goodsEntity.setInventoryCount(goodsEntity.getInventoryCount() + orderItemEntity.getCount());
		}
		orderEntity.setOrderStatus(WelfareOrderStatus.ORDER_REFUNDED);
		String userId = orderEntity.getUserId();
		BigDecimal payBalance = orderEntity.getPayBalance();
		AryaUserEntity userEntity = userDao.findUserByIdThrow(userId);
		BigDecimal balance = userEntity.getBalance();
		log.info("账户余额:" + balance);
		balance = payBalance.add(balance);
		userEntity.setBalance(balance);
		StringBuffer logMsg = new StringBuffer("【福库订单】订单退款。");
		try {
			log.info("取消订单并更新用户账户余额");
			orderDao.update(orderEntity);
			userDao.update(userEntity);
			opLogService.successLog(WELFARE_ORDER_REFUND, logMsg, log);
		} catch (Exception e) {
			opLogService.failedLog(WELFARE_ORDER_REFUND, logMsg, log);
			throw new AryaServiceException(ErrorCode.CODE_WELFARE_ORDER_REFUNDED_FILED);
		}
	}

	@Override
	public void couponDefSave(CouponDefSaveCommand command) throws AryaServiceException {
		if (command.getCouponBackgroundFileId() == null) {
			throw new AryaServiceException(ErrorCode.CODE_WELFARE_COUPON_IMAGE_NOT_EMPTY);
		}
		if (command.getCount() == 0) {
			throw new AryaServiceException(ErrorCode.CODE_WELFARE_COUPON_COUNT_NOT_EMPTY);
		}
		if (command.getPrice() == null) {
			throw new AryaServiceException(ErrorCode.CODE_WELFARE_COUPON_PRICE_NOT_EMPTY);
		}
		WelfareCouponDefEntity couponDefEntity = new WelfareCouponDefEntity();
		StringBuffer logMsg = new StringBuffer("【福库劵管理】");
		if (StringUtils.isBlank(command.getCouponDefId())) {
			couponDefEntity.setId(Utils.makeUUID());
			couponDefEntity.setAryaCorpId(command.getCorpId());
			//判断用户输入的数量和金额是否为数字
			Pattern pattern = Pattern.compile("[0-9]*");
			Matcher countIsNum = pattern.matcher(command.getCount().toString());
			if (!countIsNum.matches()) {
				throw new AryaServiceException(ErrorCode.CODE_WELFARE_COUPON_VALUE_NOT_NUMBER);
			} else if (command.getCount().intValue() > 100000 || command.getCount() < 0) {
				throw new AryaServiceException(ErrorCode.CODE_WELFARE_COUPON_COUNT_TO_MANY);
			} else {
				couponDefEntity.setCount(command.getCount());
			}
			Matcher priceIsNum = pattern.matcher(command.getPrice().toString());
			if (!priceIsNum.matches()) {
				throw new AryaServiceException(ErrorCode.CODE_WELFARE_COUPON_VALUE_NOT_NUMBER);
			} else if (command.getPrice().compareTo(new BigDecimal("10000")) > 0) {
				throw new AryaServiceException(ErrorCode.CODE_WELFARE_COUPON_PRICE_TO_BIG);
			} else {
				couponDefEntity.setPrice(command.getPrice());
			}
			couponDefEntity.setBackgroundFileId(command.getCouponBackgroundFileId());
			//移动图片存放位置
			commonFileService.moveFile(config.getFilePath("temp", 2) + command.getCouponBackgroundFileId() + ".jpg", config.getFilePath(couponDefEntity.getId(), 2) + command.getCouponBackgroundFileId() + ".jpg");
			couponDefEntity.setActiveTime(command.getActiveTime());
			couponDefEntity.setExpireTime(command.getExpireTime());
			couponDefEntity.setIsExported(CorpConstants.FALSE);
			couponDefEntity.setIsDeprecated(CorpConstants.FALSE);
			couponDefEntity.setCreateTime(System.currentTimeMillis());
			logMsg.append("新增福库劵定义ID:" + couponDefEntity.getId());
			try {
				couponDefDao.create(couponDefEntity);
				opLogService.successLog(WELFARE_COUPONDEF_CREATE, logMsg, log);
			} catch (Exception e) {
				opLogService.failedLog(WELFARE_COUPONDEF_CREATE, logMsg, log);
				throw new AryaServiceException(ErrorCode.CODE_WELFARE_COUPON_EDIT_FILED);
			}
		} else {
			logMsg.append("编辑福库劵定义ID:" + command.getCouponDefId());
			if (command.getCouponDefId() == null) {
				throw new AryaServiceException(ErrorCode.CODE_WELFARE_COUPONDEF_IS_NULL);
			}
			couponDefEntity = couponDefDao.findCouponDefById(command.getCouponDefId());
			couponDefEntity.setAryaCorpId(command.getCorpId());
			//判断用户输入的数量和金额是否为数字
			Pattern pattern = Pattern.compile("[0-9]*");
			Matcher countIsNum = pattern.matcher(command.getCount().toString());
			if (!countIsNum.matches()) {
				throw new AryaServiceException(ErrorCode.CODE_WELFARE_COUPON_VALUE_NOT_NUMBER);
			} else if (command.getCount().intValue() > 100000 || command.getCount() < 0) {
				throw new AryaServiceException(ErrorCode.CODE_WELFARE_COUPON_COUNT_TO_MANY);
			} else {
				couponDefEntity.setCount(command.getCount());
			}
			Matcher priceIsNum = pattern.matcher(command.getPrice().toString());
			if (!priceIsNum.matches()) {
				throw new AryaServiceException(ErrorCode.CODE_WELFARE_COUPON_VALUE_NOT_NUMBER);
			} else if (command.getPrice().compareTo(new BigDecimal("10000")) > 0) {
				throw new AryaServiceException(ErrorCode.CODE_WELFARE_COUPON_PRICE_TO_BIG);
			} else {
				couponDefEntity.setPrice(command.getPrice());
			}
			if (StringUtils.isNotBlank(command.getCouponBackgroundFileId())) {
				//删除旧图片
				commonFileService.deleteFile(config.getFilePath(couponDefEntity.getId(), 2) + couponDefEntity.getBackgroundFileId() + ".jpg");
				//移动图片存放位置
				commonFileService.moveFile(config.getFilePath("temp", 2) + command.getCouponBackgroundFileId() + ".jpg", config.getFilePath(command.getCouponDefId(), 2) + command.getCouponBackgroundFileId() + ".jpg");
				couponDefEntity.setBackgroundFileId(command.getCouponBackgroundFileId());
			}
			List<WelfareCouponEntity> couponEntities = null;
			if (couponDefEntity.getIsExported() == CorpConstants.TRUE) {
				couponEntities = couponDao.findCouponDefByCouponDefId(command.getCouponDefId());
				for (WelfareCouponEntity couponEntity : couponEntities) {
					couponEntity.setActiveTime(command.getActiveTime());
					couponEntity.setExpireTime(command.getExpireTime());
				}
			}
			couponDefEntity.setActiveTime(command.getActiveTime());
			couponDefEntity.setExpireTime(command.getExpireTime());
			couponDefEntity.setUpdateTime(System.currentTimeMillis());
			try {
				couponDao.update(couponEntities);
				couponDefDao.update(couponDefEntity);
				opLogService.successLog(WELFARE_COUPONDEF_EDIT, logMsg, log);
			} catch (Exception e) {
				opLogService.failedLog(WELFARE_COUPONDEF_EDIT, logMsg, log);
				throw new AryaServiceException(ErrorCode.CODE_WELFARE_COUPON_EDIT_FILED);
			}
		}
	}

	@Override
	public CouponDefLayoutResult2 couponDefLayout() {
		CouponDefLayoutResult2 layout = new CouponDefLayoutResult2();
		layout.setCouponWidthHeight(String.valueOf(new Divide().value(config.getWelfareCouponWidth(), config.getWelfareCouponHeight())));
		layout.setQrcodeXCouponWidth(String.valueOf(new Divide().value(config.getWelfareCouponQrcodeX(), config.getWelfareCouponWidth())));
		layout.setQrcodeYCouponHeight(String.valueOf(new Divide().value(config.getWelfareCouponQrcodeY(), config.getWelfareCouponHeight())));
		layout.setQrcodeWidthCouponWidth(String.valueOf(new Divide().value(config.getWelfareCouponQrcodeWidth(), config.getWelfareCouponWidth())));
		layout.setQrcodeHeightCouponHeight(String.valueOf(new Divide().value(config.getWelfareCouponQrcodeHeight(), config.getWelfareCouponHeight())));
		return layout;
	}

	@Override
	public CouponDefDetailResult couponDefDetail(String couponId) throws AryaServiceException {
		CouponDefDetailResult result = new CouponDefDetailResult();
		WelfareCouponDefEntity welfareCouponDefEntity = new WelfareCouponDefEntity();
		welfareCouponDefEntity = couponDefDao.findCouponDefById(couponId);
		result.setIsExported(welfareCouponDefEntity.getIsExported());

		String backgroundFileId = welfareCouponDefEntity.getBackgroundFileId();
		String couponBackgroundUrl = fileUploadService.generateFileUrl(welfareCouponDefEntity.getId(), backgroundFileId, 2);
		result.setCouponBackgroundUrl(couponBackgroundUrl);
		result.setCorpId(welfareCouponDefEntity.getAryaCorpId());

		if (welfareCouponDefEntity.getActiveTime() == 0) {
			result.setIsTimeLimit(CorpConstants.FALSE);
		} else {
			result.setIsTimeLimit(CorpConstants.TRUE);
			result.setActiveTime(welfareCouponDefEntity.getActiveTime());
			result.setExpireTime(welfareCouponDefEntity.getExpireTime());
		}
		result.setCount(welfareCouponDefEntity.getCount());
		result.setPrice(welfareCouponDefEntity.getPrice());
		result.setCreateTime(welfareCouponDefEntity.getCreateTime());

		return result;
	}

	//合成福库劵
	@Override
	public void overlapImage(int i, String couponBackgroundPath, BufferedImage couponQRCodeImage, WelfareCouponEntity couponEntity, CorporationEntity corporationEntity, String dateStr) throws AryaServiceException, IOException {
//		try {
		if (!new File(couponBackgroundPath).exists()) {
			throw new IOException("底图文件不存在" + couponBackgroundPath);
		}
		BufferedImage couponBackgroundImage = ImageIO.read(new File(couponBackgroundPath));
		int newWidth = config.getWelfareCouponWidth();
		int newHeight = config.getWelfareCouponHeight();
		BufferedImage image = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_BGR);
		Graphics2D g = image.createGraphics();
		g.drawImage(couponBackgroundImage, 0, 0, newWidth, newHeight, null);

		int x = config.getWelfareCouponQrcodeX();
		int y = config.getWelfareCouponQrcodeY();
		g.drawImage(couponQRCodeImage, x, y, config.getWelfareCouponQrcodeWidth(), config.getWelfareCouponQrcodeHeight(), null);
//			BasicStroke basicStroke = new BasicStroke(30);
//			g.setStroke(basicStroke);
		Font f = new Font("宋体", Font.PLAIN, config.getWelfareCouponNumFontSize());
		g.getFontRenderContext();
		g.setFont(f);
		g.setColor(Color.BLACK);
		String strSn = StringUtils.leftPad(String.format("%d", i), 4, '0');
		g.drawString("NO: " + strSn, config.getWelfareCouponNumX(), config.getWelfareCouponNumY());
		log.info("绘制编号");
		g.dispose();

		String filePath = aryaConfigService.getCouponImagePath(
				aryaConfigService.getCouponsImagePath() + "compose/" +
						corporationEntity.getName() + couponEntity.getCouponDefId() + "/")
				+ strSn + "_" + couponEntity.getQrCodeFileId() + ".jpg";
		log.debug("合成二维码路径:" + filePath);
		ImageIO.write(image, "jpg", new File(filePath));
//		} catch (Exception e) {
//			throw new AryaServiceException(ErrorCode.CODE_COUPON_QR_CODE_FILED);
//		}
	}

	@Override
	public CouponDefListResult couponDefList(CouponDefListCommand command) throws AryaServiceException {
		CouponDefListResult couponDefList = new CouponDefListResult();
		List<CouponDefListResult.CouponDefResult> couponDefResults = new ArrayList<>();
		Pager<WelfareCouponDefEntity> welfareCouponDefEntitys = couponDefDao.findPaginationWelfareCouponDefs(command.getCorp_id(), command.getCreate_date(), command.getPage(), command.getPage_size());
		if (welfareCouponDefEntitys.getResult().size() == 0) {
			log.info("福库劵定义表为空");
			return couponDefList;
		}
		for (WelfareCouponDefEntity couponDefEntity : welfareCouponDefEntitys.getResult()) {
			CouponDefListResult.CouponDefResult result = new CouponDefListResult.CouponDefResult();
			result.setCouponDefId(couponDefEntity.getId());

			String backgroundFileId = couponDefEntity.getBackgroundFileId();
			String couponBackgroundUrl = fileUploadService.generateFileUrl(couponDefEntity.getId(), backgroundFileId, 2);
			result.setThumbnailUrl(couponBackgroundUrl);

			CorporationEntity corporationEntity = corporationDao.findCorporationByIdThrow(couponDefEntity.getAryaCorpId());
			result.setCorpName(corporationEntity.getName());

			result.setCount(couponDefEntity.getCount());
			result.setActiveTime(couponDefEntity.getActiveTime());
			result.setExpireTime(couponDefEntity.getExpireTime());
			result.setPrice(couponDefEntity.getPrice());
			result.setIsExported(couponDefEntity.getIsExported());
			result.setCreateTime(couponDefEntity.getCreateTime());

			couponDefResults.add(result);
		}
		couponDefList.setCoupon_defs(couponDefResults);
		couponDefList.setPages(Utils.calculatePages(welfareCouponDefEntitys.getRowCount(), welfareCouponDefEntitys.getPageSize()));
		return couponDefList;
	}

	@Override
	public WelfareCouponEntity createCoupon(WelfareCouponDefEntity couponDefEntity) throws AryaServiceException {
		if (couponDefEntity == null) {
			throw new AryaServiceException(ErrorCode.CODE_WELFARE_COUPON_CREATE_FILED);
		}
		WelfareCouponEntity couponEntity = new WelfareCouponEntity();
		couponEntity.setId(Utils.makeUUID());
		couponEntity.setCouponDefId(couponDefEntity.getId());
		couponEntity.setCouponCode(Utils.makeUUID());
		couponEntity.setQrCodeFileId(Utils.makeUUID());
		couponEntity.setPrice(couponDefEntity.getPrice());
		couponEntity.setActiveTime(couponDefEntity.getActiveTime());
		couponEntity.setExpireTime(couponDefEntity.getExpireTime());
		couponEntity.setIsDeprecated(CorpConstants.FALSE);
		couponEntity.setIsUsed(CorpConstants.FALSE);

		return couponEntity;
	}

	@Override
	public void readFile(String generateTime, HttpServletResponse response) {
		response.setContentType("application/ostet-stream");
		response.addHeader("Content-Disposition", "attachment; filename=\"" +
				SysUtils.parseEncoding("福库劵" + generateTime + ".zip", "UTF-8") + "\"");
		readFileResponseService.readFileToResponse(aryaConfigService.getCouponsImagePath() + "zip/福库劵" + generateTime + ".zip", couponImageReadLength, response);
	}

	@Override
	public String generateFileUrl(String generateTime) {
		return COUPON_URL + "?generate_time=" + generateTime;
	}

	@Override
	public CouponExportResult exportCoupon(CouponExportCommand command) throws AryaServiceException {
		CouponExportResult exportResult = new CouponExportResult();
		WelfareCouponDefEntity couponDefEntity = new WelfareCouponDefEntity();
		CorporationEntity corporationEntity = null;
		List<WelfareCouponEntity> couponEntities = new ArrayList<>();
		List<String> couponDefIds = command.getCoupon_def_ids();
		log.info("福库劵定义id：" + command.getCoupon_def_ids());
		int count = 0;
		String couponUrl = null;
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String dateStr = sdf.format(date);
		StringBuffer logMsg = new StringBuffer("【开始导出福库劵】");
		//如果福库劵zip文件夹存在则删除
		File fileZip = new File(aryaConfigService.getCouponsImagePath() + "zip");
		if (fileZip.exists()) {
			try {
				FileUtils.deleteDirectory(fileZip);
			} catch (IOException e) {
				elog.error(e.getMessage(), e);
			}
		}
		for (String couponDefId : couponDefIds) {
			log.info("福库劵定义ID:" + couponDefId);
			couponDefEntity = couponDefDao.findCouponDefById(couponDefId);
			corporationEntity = corporationDao.findCorporationByIdThrow(couponDefEntity.getAryaCorpId());
			log.info("福库劵数量：" + couponDefEntity.getCount());
			logMsg.append("劵金额:" + couponDefEntity.getPrice());
			int couponCount = couponDefEntity.getCount();
			String couponBackgroundImagePath = config.getFilePath(couponDefEntity.getId(), 2) + couponDefEntity.getBackgroundFileId() + ".jpg";
			if (!new File(couponBackgroundImagePath).exists()) {
				throw new AryaServiceException(ErrorCode.CODE_WELFARE_COUPON_BACKGROUNDIMAGE_NULL);
			}
			File srcDir = new File(aryaConfigService.getCouponImagePath(aryaConfigService.getCouponsImagePath() + "compose/" + corporationEntity.getName() + couponDefId));
			File destDir = new File(aryaConfigService.getCouponImagePath(aryaConfigService.getCouponsImagePath() + "zip/福库劵" + dateStr));
			if (couponDefEntity.getIsExported() == CorpConstants.TRUE) {
				try {
					FileUtils.copyDirectoryToDirectory(srcDir, destDir);
				} catch (IOException e) {
					elog.error(e.getMessage(), e);
				}
				count += couponCount;
			} else {
				log.info("福库劵背景图片path：" + couponBackgroundImagePath);
				for (int i = 0; i < couponCount; i++) {
					log.info("开始生成福库劵");
					couponEntities.add(createCoupon(couponDefEntity));
				}
				StringBuffer couponLogMsg = new StringBuffer("【开始生成福库劵】");
				logMsg.append("新增福库劵,数量:" + couponCount + "劵金额:" + couponDefEntity.getPrice());
				try {
					couponDao.create(couponEntities);
					opLogService.successLog(WELFARE_COUPON_CREATE, couponLogMsg, log);
				} catch (Exception e) {
					opLogService.failedLog(WELFARE_COUPON_CREATE, couponLogMsg, log);
					throw new AryaServiceException(ErrorCode.CODE_WELFARE_COUPON_CREATE_FILED);
				}
				System.out.println("生成福库劵结束");
//				couponEntityList = couponDao.findCouponDefByCouponDefId(couponDefId);
				int i = 0;
				for (WelfareCouponEntity couponEntity : couponEntities) {
					log.info("开始生成二维码");
					String couponQRCodeUrl = generateCouponQRCodeUrl(couponEntity.getId(), couponEntity.getCouponCode());
					log.info("二维码url:" + couponQRCodeUrl);
					BufferedImage couponQRCodeimage = ZXingUtil.encodeQRCodeImage(couponQRCodeUrl, null, config.getWelfareCouponQrcodeWidth(), config.getWelfareCouponQrcodeHeight());
					log.info("开始合成福库劵");
					try {
						overlapImage(++i, couponBackgroundImagePath, couponQRCodeimage, couponEntity, corporationEntity, dateStr);
					} catch (Exception e) {
						throw new AryaServiceException(ErrorCode.CODE_COUPON_QR_CODE_FILED);
					}
				}
				try {
					FileUtils.copyDirectoryToDirectory(srcDir, destDir);
				} catch (IOException e) {
					elog.error(e.getMessage(), e);
				}
				couponDefEntity.setIsExported(CorpConstants.TRUE);
				count += couponCount;
			}
		}
		log.info("开始压缩福库劵");
		String couponFileZipPath = aryaConfigService.getCouponsImagePath() + "zip/福库劵" + dateStr + ".zip";
		log.info("压缩文件路径:" + couponFileZipPath);
		ZipCompressorByAntUtil zca = new ZipCompressorByAntUtil(couponFileZipPath);
		zca.compressExe(aryaConfigService.getCouponImagePath(aryaConfigService.getCouponsImagePath() + "zip/福库劵" + dateStr));
		couponUrl = generateFileUrl(dateStr);
		logMsg.append("导出劵数量:" + count);

		try {
			opLogService.successLog(WELFARE_COUPON_EXPORT, logMsg, log);
			exportResult.setCount(count);
			exportResult.setCoupon_url(couponUrl);
			couponDefDao.update(couponDefEntity);
		} catch (Exception e) {
			opLogService.failedLog(WELFARE_COUPON_EXPORT, logMsg, log);
			throw new AryaServiceException(ErrorCode.CODE_WELFARE_EXPORT_COUPON_FILED);
		}
		return exportResult;
	}

	@Override
	public void couponDefDelete(CouponDefDeleteCommand command) throws AryaServiceException {
		List<Map<String, String>> couponDefIds = command.getIds();
		WelfareCouponDefEntity couponDefEntity = new WelfareCouponDefEntity();
		List<WelfareCouponEntity> couponEntitys = new ArrayList<>();
		StringBuffer logMsg = new StringBuffer("【福库劵管理】");
		for (Map<String, String> map : couponDefIds) {
			for (String s : map.keySet()) {
				String couponDefId = map.get(s);
				if (couponDefId == null) {
					throw new AryaServiceException(ErrorCode.CODE_VALIDATION_ID_BLANK);
				}
				logMsg.append("删除福库劵定义");
				log.info("福库劵定义ID:" + couponDefId);
				couponDefEntity = couponDefDao.findCouponDefById(couponDefId);
				if (couponDefEntity == null) {
					throw new AryaServiceException(ErrorCode.CODE_WELFARE_COUPON_ALREADY_DELETE);
				}
				logMsg.append("福库劵定义ID:" + couponDefId);
				couponEntitys = couponDao.findCouponDefByCouponDefId(couponDefId);
				if (couponEntitys != null) {
					for (WelfareCouponEntity couponEntitie : couponEntitys) {
						couponEntitie.setIsDeprecated(CorpConstants.TRUE);
					}
				}
				couponDefEntity.setIsDeprecated(CorpConstants.TRUE);
			}
		}
		try {
			couponDefDao.update(couponDefEntity);
			couponDao.update(couponEntitys);
			opLogService.successLog(WELFARE_COUPONDEF_DELETE, logMsg, log);
		} catch (Exception e) {
			opLogService.failedLog(WELFARE_COUPONDEF_DELETE, logMsg, log);
			elog.error(e.getMessage(), e);
			throw new AryaServiceException(ErrorCode.CODE_WELFARE_COUPON_DELETE_FILED);
		}
	}


    @Override
    public String tempSaveWelfareGoodsImage(MultipartFile file) throws AryaServiceException {
        String fileName = Utils.makeUUID();
        String filePath = config.getWelfareGoodsImagePath("temp") + fileName + ".jpg";
        try {
            file.transferTo(new File(filePath));// 转存文件
        } catch (Exception e) {
            throw new AryaServiceException(ErrorCode.CODE_WELFARE_GOODS_IMAGE_SAVE_FAILED);
        }
        return fileName;
    }

	public void setConfig(AryaAdminConfigService config) {
		this.config = config;
	}

	public void setAryaConfigService(AryaConfigService aryaConfigService) {
		this.aryaConfigService = aryaConfigService;
	}
}
