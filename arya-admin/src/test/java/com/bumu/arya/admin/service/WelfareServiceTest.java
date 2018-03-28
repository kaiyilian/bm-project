package com.bumu.arya.admin.service;

import com.bumu.arya.Utils;
import com.bumu.arya.admin.common.service.AryaAdminConfigService;
import com.bumu.arya.admin.common.util.ZXingUtil;
import com.bumu.arya.admin.welfare.service.impl.WelfareServiceImpl;
import com.bumu.arya.common.service.AryaConfigService;
import com.bumu.arya.model.entity.CorporationEntity;
import com.bumu.arya.welfare.model.entity.WelfareCouponEntity;
import org.junit.Test;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 二维码福库券测试
 * Created by allen on 2016/12/29.
 */
public class WelfareServiceTest {

	AryaAdminConfigService configService = new AryaAdminConfigService();

	AryaConfigService config = new AryaConfigService();

	WelfareServiceImpl welfareService;

	@Test
	public void test() {
		// saveConfig()
//		configService.setWelfareCouponWidth(1949);
//		configService.setWelfareCouponHeight(827);
//		configService.setWelfareCouponQrcodeX(1531);
//		configService.setWelfareCouponQrcodeY(251);
//		configService.setWelfareCouponQrcodeWidth(300);
//		configService.setWelfareCouponQrcodeHeight(300);

		WelfareCouponEntity couponEntity = new WelfareCouponEntity();
		couponEntity.setCouponDefId(Utils.makeUUID());
		couponEntity.setQrCodeFileId(Utils.makeUUID());
		CorporationEntity corporationEntity = new CorporationEntity();
		corporationEntity.setName("公司");

		BufferedImage couponQRCodeimage =
				ZXingUtil.encodeQRCodeImage("ajsjwjeojdsljflsjfjwejsldf", null,
						300, 300);

		couponEntity.setId("");
		welfareService = new WelfareServiceImpl();
		welfareService.setConfig(configService);
		welfareService.setAryaConfigService(config);
		try {
			welfareService.overlapImage(12, "/Users/allen/tmp/coupon_bg.jpg", couponQRCodeimage, couponEntity, corporationEntity, "20161228");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
