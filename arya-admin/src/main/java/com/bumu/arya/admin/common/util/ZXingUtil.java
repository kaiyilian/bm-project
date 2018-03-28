package com.bumu.arya.admin.common.util;

import com.bumu.arya.exception.ErrorCode;
import com.bumu.exception.AryaServiceException;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by DaiAoXiang on 2016/12/1.
 */
public class ZXingUtil {

	private ZXingUtil() {
	}

	/**
	 * 为二维码图片增加logo头像
	 * @param imagePath 二维码图片存放路径(含文件名)
	 * @param logoPath  logo头像存放路径(含文件名)
	 */

	private static void overlapImage(String imagePath, String logoPath) throws IOException {
		BufferedImage image = ImageIO.read(new File(imagePath));
		int logoWidth = image.getWidth() / 3;   //设置logo图片宽度为二维码图片的五分之一
		int logoHeight = image.getHeight() / 3; //设置logo图片高度为二维码图片的五分之一
		int logoX = (image.getWidth() - logoWidth) / 2;   //设置logo图片的位置,这里令其居中
		int logoY = (image.getHeight() - logoHeight) / 2; //设置logo图片的位置,这里令其居中
		Graphics2D graphics = image.createGraphics();
		graphics.drawImage(ImageIO.read(new File(logoPath)), logoX, logoY, logoWidth, logoHeight, null);
		graphics.dispose();
		ImageIO.write(image, imagePath.substring(imagePath.lastIndexOf(".") + 1), new File(imagePath));
	}

	/**
	 * 生成二维码
	 *
	 * @param content   二维码内容
	 * @param charset   编码二维码内容时采用的字符集(传null时默认采用UTF-8编码)
	 * @param width     生成的二维码图片宽度
	 * @param height    生成的二维码图片高度
	 * @return 生成二维码结果(true or false)
	 */

	public static BufferedImage encodeQRCodeImage(String content, String charset, int width, int height) throws AryaServiceException {
		Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
		hints.put(EncodeHintType.MARGIN, 1);
		//指定编码格式
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		//指定纠错级别(L--7%,M--15%,Q--25%,H--30%)
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		//编码内容,编码类型(这里指定为二维码),生成图片宽度,生成图片高度,设置参数
		BitMatrix bitMatrix = null;
		try {
			bitMatrix = new MultiFormatWriter().encode(new String(content.getBytes(charset == null ? "UTF-8" : charset), "ISO-8859-1"), BarcodeFormat.QR_CODE, width, height, hints);
		} catch (Exception e) {
			throw new AryaServiceException(ErrorCode.CODE_WELFARE_COUPON_QR_CODE_FILED);
		}
		//生成的二维码图片默认背景为白色,前景为黑色,但是在加入logo图像后会导致logo也变为黑白色,至于是什么原因还没有仔细去读它的源码
		//所以这里对其第一个参数黑色将ZXing默认的前景色0xFF000000稍微改了一下0xFF000001,最终效果也是白色背景黑色前景的二维码,且logo颜色保持原有不变
		MatrixToImageConfig config = new MatrixToImageConfig(0xFF000001, 0xFFFFFFFF);
		//这里要显式指定MatrixToImageConfig,否则还会按照默认处理将logo图像也变为黑白色(如果打算加logo的话,反之则不须传MatrixToImageConfig参数)
		try {
			BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix,config);
			return image;
		} catch (Exception e) {
			throw new AryaServiceException(ErrorCode.CODE_WELFARE_COUPON_QR_CODE_FILED);
		}
	}

	/**
	 * 解析二维码
	 *
	 * @param imagePath 二维码图片存放路径(含文件名)
	 * @param charset   解码二维码内容时采用的字符集(传null时默认采用UTF-8编码)
	 * @return 解析成功后返回二维码文本, 否则返回空字符串
	 */
	public static String decodeQRCodeImage(String imagePath, String charset) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(imagePath));
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
		if (null == image) {
			System.out.println("Could not decode QRCodeImage");
			return "";
		}
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));
		Map<DecodeHintType, String> hints = new HashMap<DecodeHintType, String>();
		hints.put(DecodeHintType.CHARACTER_SET, charset == null ? "UTF-8" : charset);
		Result result = null;
		try {
			result = new MultiFormatReader().decode(bitmap, hints);
			return result.getText();
		} catch (NotFoundException e) {
			System.out.println("二维码图片[" + imagePath + "]解析失败,堆栈轨迹如下");
			e.printStackTrace();
			return "";
		}
	}

}
