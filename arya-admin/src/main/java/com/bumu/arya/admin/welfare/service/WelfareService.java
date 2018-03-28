package com.bumu.arya.admin.welfare.service;

import com.bumu.arya.admin.welfare.controller.command.*;
import com.bumu.arya.admin.welfare.result.*;
import com.bumu.arya.model.entity.*;
import com.bumu.arya.response.HttpResponse;
import com.bumu.arya.welfare.model.entity.*;
import com.bumu.exception.AryaServiceException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by CuiMengxin on 16/9/7.
 */
@Transactional
public interface WelfareService {

    /**
     * 修改公告
     *
     * @param command
     * @throws AryaServiceException
     */
    void updateNotice(EditWelfareNoticeCommand command) throws AryaServiceException;


    /**
     * 获取所有产品名称
     *
     * @return
     * @throws AryaServiceException
     */
    WelfareGoodNameList getAllGoodNames() throws AryaServiceException;

    /**
     *  获取所有企业名称
     * @return
     * @throws AryaServiceException
     */
    WelfareCorpsListResult getCorpsList(Integer bizType) throws AryaServiceException;

    /**
     * 查询订单
     *
     * @param command
     * @return
     * @throws AryaServiceException
     */
    WelfareOrderListResult queryOrderList(WelfareOrderListCommand command) throws AryaServiceException;

    /**
     * 导出订单
     *
     * @param command
     * @param response
     * @return
     * @throws AryaServiceException
     */
    HttpResponse exportOrders(WelfareOrderListCommand command, HttpServletResponse response,String type) throws AryaServiceException;

    /**
     * 编辑商品信息
     *
     * @param command
     * @return 商品id
     * @throws AryaServiceException
     */
    String editGoods(WelfareGoodsEditCommand command) throws AryaServiceException;

    /**
     * 获取商品列表
     *
     * @param command
     * @return
     * @throws AryaServiceException
     */
    WelfareAdminGoodsListResult getGoodsList(PaginationCommand command) throws AryaServiceException;

    /**
     * 福库商品上下移动
     *
     * @param goodsId
     * @throws AryaServiceException
     */
    void moveGoods(String goodsId,int direction) throws AryaServiceException;

    /**
     * 生成福库商品图片url
     *
     * @param goodsId
     * @param imageId
     * @return
     */
    String generateWelfareGoodsImageUrl(String goodsId, String imageId);

    /**
     * 生成福库劵二维码url
     * @param couponId
     * @param couponCode
     * @return
     */
    String generateCouponQRCodeUrl(String couponId, String couponCode);

    /**
     * 获取轮播图的第一张
     *
     * @param imageEntitySet
     * @return
     */
    WelfareImageEntity getFirstImage(Set<WelfareImageEntity> imageEntitySet);

    /**
     * 获取商品详情
     *
     * @param goodsId
     * @return
     * @throws AryaServiceException
     */
    WelfareGoodsDetailResult getGoodsDetail(String goodsId) throws AryaServiceException;

    /**
     * 获取全部商品分类
     *
     * @return
     * @throws AryaServiceException
     */
    WelfareAllCategoriesListResult getAllCategories() throws AryaServiceException;

    /**
     * 查询商品分类下的所有规格
     *
     * @param categoryId
     * @return
     * @throws AryaServiceException
     */
    WelfareCategoriesAllSpecsListResult getCategoryAllSpecs(String categoryId) throws AryaServiceException;

    /**
     * 上传商品图片,存在临时目录
     *
     * @param file
     * @return
     * @throws AryaServiceException
     * @deprecated
     */
    WelfareUploadImageResult uploadGoodsImage(MultipartFile file) throws AryaServiceException;

    /**
     * 移除轮播图
     *
     * @param images
     * @param removeImage
     */
    WelfareImageEntity removeRollImage(String goodsId, List<WelfareImageEntity> images, WelfareImageEntity removeImage);

    /**
     * 新增轮播图
     *
     * @param images
     * @param newImage
     * @return
     */
    WelfareImageEntity addRollImage(String goodsId, List<WelfareImageEntity> images, WelfareImageEntity newImage);

    /**
     * 处理商品缩略图,新增或删除
     *
     * @param goodsEntity
     * @param existThumb
     * @param commandThumbId
     * @return
     */
    WelfareImageEntity dealGoodsThumbImage(String goodsId, WelfareGoodsEntity goodsEntity, WelfareImageEntity existThumb, String commandThumbId);

    /**
     * 生成新的轮播图
     *
     * @param imageEntities
     * @param newImageId
     */
    void generateGoodsImage(String goodsId, List<WelfareImageEntity> imageEntities, String newImageId);

    /**
     * 处理商品编辑的分类编辑
     *
     * @param command
     * @param existGoodsCategories
     * @return
     * @throws AryaServiceException
     */
    Set<WelfareGoodsCategoryEntity> dealWithGoodsCategory(String goodsId, WelfareGoodsEditCommand command, Set<WelfareGoodsCategoryEntity> existGoodsCategories) throws AryaServiceException;

    /**
     * 删除商品
     *
     * @param goodsId
     * @throws AryaServiceException
     */
    void deleteGoods(String goodsId) throws AryaServiceException;

    /**
     * 组装订单返回值
     *
     * @param orderEntities
     * @return
     */
    List<WelfareOrderListResult.WelfareOrderResult> generateOrderList(List<WelfareOrderEntity> orderEntities,boolean isExport);

    /**
     * 获取订单分类详情
     *
     * @param orderId
     * @return
     */
    WelfareOrderCategoryDetailResult getOrderCategoryDetail(String orderId);

    /**
     * 修改订单分类详情
     *
     * @param command
     * @throws AryaServiceException
     */
    void adjustOrderCategoryDetail(AdjustOrderCategoryDetailCommand command) throws AryaServiceException;

    /**
     * 获取指定商品下的所有分类
     *
     * @param goodsId
     * @return
     * @throws AryaServiceException
     */
    WelfareAllCategoriesListResult getGoodsAllCategory(String goodsId) throws AryaServiceException;

    /**
     * 获取指定商品下分类的所有规格
     *
     * @param categoryId
     * @return
     * @throws AryaServiceException
     */
    WelfareCategoriesAllSpecsListResult getGoodsCategoryAllSpecs(String categoryId) throws AryaServiceException;

    /**
     * 生成订单规格实体
     *
     * @param goodsCategoryId
     * @param goodsSpecId
     * @return
     */
    WelfareOrderItemSpecEntity generateNewOrderItemSpec(String goodsCategoryId, String goodsSpecId, Map<String, WelfareCategoryEntity> welfareCategoryEntityMap, Map<String, WelfareGoodsCategoryEntity> goodsCategoryEntityMap) throws AryaServiceException;

    /**
     * 删除订单
     *
     * @param orderId
     * @throws AryaServiceException
     */
    void deleteOrder(String orderId) throws AryaServiceException;

    /**
     * 订单退款
     *
     * @param command
     * @throws AryaServiceException
     */
    void orderRefund(OrderRefundCommand command) throws AryaServiceException;

    /**
     * 福库劵定义保存
     *
     * @param command
     * @throws AryaServiceException
     */
    void couponDefSave(CouponDefSaveCommand command)throws AryaServiceException;

    /**
     * 福库劵布局配置
     *
     * @return
     */
    CouponDefLayoutResult2 couponDefLayout();

    /**
     * 福库劵定义明细
     *
     * @param couponId
     * @return
     * @throws AryaServiceException
     */
    CouponDefDetailResult couponDefDetail(String couponId) throws AryaServiceException;

    /**
     * 福库劵定义列表
     *
     * @param command
     * @return
     * @throws AryaServiceException
     */
    CouponDefListResult couponDefList(CouponDefListCommand command) throws AryaServiceException;

    /**
     * 导出福库劵
     *
     * @param command
     * @return
     * @throws AryaServiceException
     */
    CouponExportResult exportCoupon(CouponExportCommand command) throws AryaServiceException;

    /**
     * 合成福库劵
     *
     * @param couponBackgroundPath
     * @param couponQRCodeImage
     * @param couponEntity
     * @throws AryaServiceException
     */
    void overlapImage(int i, String couponBackgroundPath, BufferedImage couponQRCodeImage, WelfareCouponEntity couponEntity, CorporationEntity corporationEntity, String dateStr) throws AryaServiceException, IOException;

    /**
     * 读取福库劵压缩包
     *
     * @param response
     */
    void readFile(String generateTime, HttpServletResponse response);

    /**
     * 生成福库劵压缩包的url
     *
     * @param generateTime
     * @return
     */
    String generateFileUrl(String generateTime);

    /**
     * 福库劵生成
     *
     * @param couponDefEntity
     * @throws AryaServiceException
     */
    WelfareCouponEntity createCoupon(WelfareCouponDefEntity couponDefEntity) throws AryaServiceException;

    /**
     * 删除福库劵定义
     *
     * @param command
     * @return
     * @throws AryaServiceException
     */
   void couponDefDelete(CouponDefDeleteCommand command)throws AryaServiceException;


    /**
     * 存储福库商品图片
     *
     * @param file
     * @return
     * @throws AryaServiceException
     */
    String tempSaveWelfareGoodsImage(MultipartFile file) throws AryaServiceException;


}
