package com.bumu.bran.admin.employee.util;

import com.bumu.arya.exception.ErrorCode;
import com.bumu.bran.admin.employee.result.ZipObj;
import com.bumu.exception.AryaServiceException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Function : 文件压缩成zip
 *
 * @author : lqf
 * @Date : 2015-12-15
 */
public class ZipCompressing {
	private static final Logger logger = LoggerFactory.getLogger(ZipCompressing.class);

	static int k = 1; // 定义递归次数变量

	public ZipCompressing() {
	}


	/**
	 * 下载附件
	 *
	 * @param out
	 * @param zipObjList
	 * @return
	 */
	public static boolean zip(ZipOutputStream out, List<ZipObj> zipObjList) {

		BufferedOutputStream bo = null;
		try {

			for (int i = 0; i < zipObjList.size(); i++) {
				ZipObj zipObj = zipObjList.get(i);
				if (zipObj != null) {
					if (StringUtils.isNotBlank(zipObj.getDir())) {
						createDir(zipObj.getDir());
						out.putNextEntry(new ZipEntry(zipObj.getDir() + File.separator));  // 创建zip实体
					}

					if (zipObj.getFileList() != null && !zipObj.getFileList().isEmpty()) {

						for (int j = 0; j < zipObj.getFileList().size(); j++) {

							File file = zipObj.getFileList().get(j);
							if (file != null && StringUtils.isNotBlank(file.getName())) {
								ZipEntry zipEntry = new ZipEntry(zipObj.getDir() + File.separator + file.getName());
								out.putNextEntry(zipEntry);
								logger.info("zipName: "+zipEntry.getName());
								FileInputStream in = new FileInputStream(file);
								BufferedInputStream bi = new BufferedInputStream(in);
								int b;
								while ((b = bi.read()) != -1) {
									out.write(b); // 将字节流写入当前zip目录
								}
								in.close(); // 输入流关闭
							}

						}

					}

				}
			}
			logger.info("压缩完成");
			return true;
		} catch (FileNotFoundException fileE) {
			logger.error(fileE.getMessage());
			throw new AryaServiceException(fileE.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new AryaServiceException(ErrorCode.CODE_SYS_ERR);
		}
	}

	/**
	 * 执行压缩
	 *
	 * @param out  ZIP输入流
	 * @param f    被压缩的文件
	 * @param base 被压缩的文件名
	 */
	private static void zip(ZipOutputStream out, File f, String base) { // 方法重载
		try {
			if (f.isDirectory()) {//压缩目录
				try {
					File[] fl = f.listFiles();
					if (fl.length == 0) {
						out.putNextEntry(new ZipEntry(base + "/"));  // 创建zip实体
						logger.info(base + "/");
					}
					for (int i = 0; i < fl.length; i++) {
						zip(out, fl[i], base + "/" + fl[i].getName()); // 递归遍历子文件夹
					}
					//System.out.println("第" + k + "次递归");
					k++;
				} catch (IOException e) {
					logger.error(e.getMessage());
					e.printStackTrace();
				}
			} else { //压缩单个文件
				logger.info(base);
				out.putNextEntry(new ZipEntry("13817850588/" + base)); // 创建zip实体
				FileInputStream in = new FileInputStream(f);
				BufferedInputStream bi = new BufferedInputStream(in);
				int b;
				while ((b = bi.read()) != -1) {
					out.write(b); // 将字节流写入当前zip目录
				}
				out.closeEntry(); //关闭zip实体
				in.close(); // 输入流关闭
			}

		} catch (IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}


	/**
	 * 目录不存在时，先创建目录
	 *
	 * @param zipFileName
	 */
	private static void createDir(String zipFileName) {
		String filePath = StringUtils.substringBeforeLast(zipFileName, "/");
		File targetFile = new File(filePath);
		if (!targetFile.exists()) {//目录不存在时，先创建目录
			targetFile.mkdirs();
		}
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
//			ZipCompressing.zip("d:/test1.zip",new File("d:/t2.txt"));    //测试单个文件
//			ZipCompressing.zip("d:/test2.zip", new File("d:/t2.txt"), new File("d:/menu.lst"));   //测试多个文件
//			ZipCompressing.zip("d:/test3.zip", new File("d:/培训")); //测试压缩目录
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
