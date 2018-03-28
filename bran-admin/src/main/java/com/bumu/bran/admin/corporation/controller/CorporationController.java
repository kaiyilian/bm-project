package com.bumu.bran.admin.corporation.controller;

import com.bumu.SysUtils;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.model.SysLogDao;
import com.bumu.arya.response.HttpResponse;
import com.bumu.bran.admin.config.service.BranAdminConfigService;
import com.bumu.bran.admin.corporation.controller.command.ConfirmUploadCommand;
import com.bumu.bran.admin.corporation.result.URLResult;
import com.bumu.bran.admin.corporation.service.BranCorpService;
import com.bumu.bran.admin.corporation.service.FileService;
import com.bumu.bran.common.Constants;
import com.bumu.bran.common.model.dao.BranOpLogDao;
import com.bumu.bran.model.entity.BranOpLogEntity;
import com.bumu.common.service.CommonFileService;
import com.bumu.common.service.QRCodeService;
import com.bumu.exception.AryaServiceException;
import org.apache.commons.io.FileUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.OutputStream;

/**
 * @author CuiMengxin
 * @date 2016/5/11
 */
@Controller
public class CorporationController {

	@Autowired
	BranCorpService branCorpService;
	@Autowired
	FileService fileService;
	@Autowired
	CommonFileService commonFileService;
	@Autowired
	BranAdminConfigService branAdminConfigService;
	@Autowired
	QRCodeService qrCodeService;
	@Autowired
	BranOpLogDao branOpLogDao;
	private Logger logger = LoggerFactory.getLogger(CorporationController.class);

	/**
	 * 已有员工手册查询
	 *
	 * @return
	 */
	@RequestMapping(value = "/admin/corporation/info/handbook/html/url", method = RequestMethod.GET)
	@ResponseBody
	public HttpResponse getCorpHandBookUrl() throws Exception {
		Session session = SecurityUtils.getSubject().getSession();
		String branCorpId = session.getAttribute("bran_corp_id").toString();
		URLResult urlResult = new URLResult();
		File file = commonFileService.isCorpHandBookHtmlFileExist(branCorpId);
		if (file != null) {
			urlResult.setUrl(fileService.generateHandBookHtmlUrl(branCorpId, SysUtils.getFileType(file)));
		}
		return new HttpResponse<>(ErrorCode.CODE_OK, urlResult);
	}


	/**
	 * 已有厂车路线查询
	 *
	 * @return
	 */
	@RequestMapping(value = "/admin/corporation/info/bus/html/url", method = RequestMethod.GET)
	@ResponseBody
	public HttpResponse getCorpBusUrl() throws Exception {
		Session session = SecurityUtils.getSubject().getSession();
		String branCorpId = session.getAttribute("bran_corp_id").toString();
		URLResult urlResult = new URLResult();
		File file = commonFileService.isCorpBusHtmlFileExist(branCorpId);
		if (file != null) {
			urlResult.setUrl(fileService.generateBusHtmlUrl(branCorpId, SysUtils.getFileType(file)));
		}
		return new HttpResponse<>(ErrorCode.CODE_OK, urlResult);
	}

	/**
	 * 上传厂车路线获取预览厂车路线URL
	 *
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/admin/corporation/info/bus/preview/upload", method = RequestMethod.POST)
	@ResponseBody
	public HttpResponse getPreviewCorpBusHtmlUrl(@RequestParam MultipartFile file) throws Exception {
		HttpResponse httpResponse = null;
		Session session = SecurityUtils.getSubject().getSession();
		String branCorpId = session.getAttribute("bran_corp_id").toString();

		if (file == null) {
			throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "文件没有上传");
		}

		logger.debug("file type: " + file.getOriginalFilename());
		logger.debug("file size: " + file.getSize());
		if (file.getSize() > Constants.FILE_MAX_SIZE) {
			throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, Constants.FILE_MAX_SIZE_ERROR);
		}

		if (!(file.getOriginalFilename().contains(".doc") || file.getOriginalFilename().contains(".pdf"))) {
			throw new AryaServiceException(ErrorCode.CODE_CORP_BUS_WORD_NOT_SUPPORT);//只支持.doc格式厂车文件
		}

		String filePath = branAdminConfigService.getCorpBusWordHtmlUploadPath(branCorpId) + file.getOriginalFilename();
		logger.debug("预览的文件目录filePath: " + filePath);
		File temp = new File(filePath);
		try {
			file.transferTo(temp);// 转存文件
		} catch (Exception e) {
		    e.printStackTrace();
			throw new AryaServiceException(ErrorCode.CODE_CORP_BUS_WORD_UPLOAD_FAILED);//厂车文件上传失败
		}

		String suffix = null;
		if (file.getOriginalFilename().contains(".pdf")) {
			logger.debug("pdf... process");
			FileUtils.copyFile(temp, new File(branAdminConfigService.getCorpBusHtmlPreviewPath(branCorpId) + "index.pdf"));
			suffix = ".pdf";

		} else {
			logger.debug("html... process");
			fileService.convertWord2Html(branAdminConfigService.getCorpBusWordHtmlUploadPath(branCorpId),
					branAdminConfigService.getCorpBusHtmlPreviewPath(branCorpId), file.getOriginalFilename(),
					fileService.generateBusHtmlPreviewImageUrl(branCorpId));
			suffix = ".html";
		}
		URLResult result = new URLResult();
		result.setUrl(fileService.generateBusHtmlPreviewUrl(branCorpId, suffix));
		result.setName(file.getOriginalFilename());
		httpResponse = new HttpResponse<>(ErrorCode.CODE_OK, result);
		return httpResponse;
	}

	/**
	 * 上传员工手册获取预览员工手册URL
	 *
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/admin/corporation/info/handbook/upload", method = RequestMethod.POST)
	@ResponseBody
	public HttpResponse getPreviewCorpHandBookHtmlUrl(@RequestParam MultipartFile file) throws Exception {
		Session session = SecurityUtils.getSubject().getSession();
		String branCorpId = session.getAttribute("bran_corp_id").toString();
		if (file == null) {
			throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "文件没有上传");
		}
		logger.debug("file size: " + file.getSize());
		if (file.getSize() > Constants.FILE_MAX_SIZE) {
			throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, Constants.FILE_MAX_SIZE_ERROR);
		}
		if (!(file.getOriginalFilename().contains(".doc") || file.getOriginalFilename().contains(".pdf"))) {
			throw new AryaServiceException(ErrorCode.CODE_CORP_HANDBOOK_WORD_NOT_SUPPORT);//只支持.doc格式员工手册
		}
		String filePath = branAdminConfigService.getCorpHandBookWordHtmlUploadPath(branCorpId) + file.getOriginalFilename();
		File temp = null;
		try {
			temp = new File(filePath);
			file.transferTo(temp);// 转存文件
		} catch (Exception e) {
			throw new AryaServiceException(ErrorCode.CODE_CORP_HANDBOOK_WORD_UPLOAD_FAILED);//员工手册文件上传失败
		}

		String suffix = null;
		if (file.getOriginalFilename().contains(".pdf")) {
			logger.debug("pdf... process");
			FileUtils.copyFile(temp, new File(branAdminConfigService.getCorpHandBookHtmlPreviewPath(branCorpId) + "index.pdf"));
			suffix = ".pdf";
		} else {
			fileService.convertWord2Html(branAdminConfigService.getCorpHandBookWordHtmlUploadPath(branCorpId),
					branAdminConfigService.getCorpHandBookHtmlPreviewPath(branCorpId), file.getOriginalFilename(),
					fileService.generateHandBookHtmlPreviewImageUrl(branCorpId));
			suffix = ".html";
		}

		URLResult result = new URLResult();
		result.setUrl(fileService.generateHandBookHtmlPreviewUrl(branCorpId, suffix));
		result.setName(file.getOriginalFilename());
		return new HttpResponse<>(ErrorCode.CODE_OK, result);
	}

	/**
	 * 预览厂车路线Html
	 *
	 * @param response
	 */
	@RequestMapping(value = "/admin/corporation/info/bus/preview/html", method = RequestMethod.GET)
	@ResponseBody
	public void getPreviewCorpBusHtml(String suffix, HttpServletResponse response) throws Exception {
		Session session = SecurityUtils.getSubject().getSession();
		String branCorpId = session.getAttribute("bran_corp_id").toString();
		fileService.readCorpBusPreviewHtmlFile(branCorpId, suffix, response);
	}

	/**
	 * 预览员工手册Html
	 *
	 * @param response
	 */
	@RequestMapping(value = "/admin/corporation/info/handbook/preview/html", method = RequestMethod.GET)
	@ResponseBody
	public void getPreviewCorpHandBookHtml(String suffix, HttpServletResponse response) throws Exception {
		Session session = SecurityUtils.getSubject().getSession();
		String branCorpId = session.getAttribute("bran_corp_id").toString();
		fileService.readCorpHandBookPreviewHtmlFile(branCorpId, suffix, response);
	}

	/**
	 * 预览厂车路线Html所需要的图片
	 *
	 * @param response
	 */
	@RequestMapping(value = "/admin/corporation/info/bus/preview/img", method = RequestMethod.GET)
	@ResponseBody
	public void getPreviewCorpBusHtmlImage(@RequestParam("file_name") String fileName, HttpServletResponse response)
			throws Exception {
		Session session = SecurityUtils.getSubject().getSession();
		String branCorpId = session.getAttribute("bran_corp_id").toString();
		fileService.readCorpBusPreviewHtmlImageFile(fileName, branCorpId, response);
	}

	/**
	 * 确认导入厂车路线
	 *
	 * @param command
	 * @return
	 */
	@RequestMapping(value = "/admin/corporation/info/bus/upload/confirm", method = RequestMethod.POST)
	@ResponseBody
	public HttpResponse confirmUploadCorpBus(@RequestBody ConfirmUploadCommand command) throws Exception {
		Session session = SecurityUtils.getSubject().getSession();
		String branCorpId = session.getAttribute("bran_corp_id").toString();
		String currentUserId = session.getAttribute("user_id").toString();
		String docPath = branAdminConfigService.getCorpBusWordHtmlUploadPath(branCorpId) + command.getFileName();
		logger.debug("更新的目录docPath: " + docPath);
		File temp = new File(docPath);
		if (!temp.exists()) {
			throw new AryaServiceException(ErrorCode.CODE_CORP_BUS_WORD_NOT_FOUND);//厂车文件不存在
		}

		if (command.getFileName().contains(".pdf")) {
			logger.debug("pdf... process");
			FileUtils.copyFile(temp, new File(branAdminConfigService.getCorpBusWordHtmlUploadPath(branCorpId) + "index.pdf"));
			temp = new File(branAdminConfigService.getCorpBusWordHtmlUploadPath(branCorpId) + "index.html");
			if (temp.exists()) {
				temp.delete();
			}
		} else {
			fileService.convertWord2Html(branAdminConfigService.getCorpBusWordHtmlUploadPath(branCorpId),
					branAdminConfigService.getCorpBusWordHtmlUploadPath(branCorpId),
					command.getFileName(),
					fileService.generateBusHtmlImageUrl(branCorpId));
			temp = new File(branAdminConfigService.getCorpBusWordHtmlUploadPath(branCorpId) + "index.pdf");
			if (temp.exists()) {
				temp.delete();
			}
		}
		SysLogDao.SysLogExtInfo info = new SysLogDao.SysLogExtInfo();
		info.setMsg("修改了厂车路线。");
		branOpLogDao.success(BranOpLogEntity.OP_MODULE_BUS, BranOpLogEntity.OP_TYPE_UPDATE, currentUserId, info);
		return new HttpResponse<>(ErrorCode.CODE_OK);
	}

	/**
	 * 确认导入员工手册
	 *
	 * @param command
	 * @return
	 */
	@RequestMapping(value = "/admin/corporation/info/handbook/upload/confirm", method = RequestMethod.POST)
	@ResponseBody
	public HttpResponse confirmUploadCorpHandBook(@RequestBody ConfirmUploadCommand command) throws Exception {
		Session session = SecurityUtils.getSubject().getSession();
		String branCorpId = session.getAttribute("bran_corp_id").toString();
		String currentUserId = session.getAttribute("user_id").toString();
		String docPath = branAdminConfigService.getCorpHandBookWordHtmlUploadPath(branCorpId) + command.getFileName();
		File temp = new File(docPath);
		if (!temp.exists()) {
			throw new AryaServiceException(ErrorCode.CODE_CORP_HANDBOOK_WORD_NOT_FOUND);//员工手册文件不存在
		}

		if (command.getFileName().contains(".pdf")) {
			logger.debug("pdf... process");
			FileUtils.copyFile(temp, new File(branAdminConfigService.getCorpHandBookWordHtmlUploadPath(branCorpId) + "index.pdf"));
			temp = new File(branAdminConfigService.getCorpHandBookWordHtmlUploadPath(branCorpId) + "index.html");
			if (temp.exists()) {
				temp.delete();
			}
		} else {
			fileService.convertWord2Html(branAdminConfigService.getCorpHandBookWordHtmlUploadPath(branCorpId),
					branAdminConfigService.getCorpHandBookHtmlConvertPath(branCorpId), command.getFileName(), fileService.generateHandBookHtmlImageUrl(branCorpId));
			temp = new File(branAdminConfigService.getCorpHandBookHtmlConvertPath(branCorpId) + "index.pdf");
			if (temp.exists()) {
				temp.delete();
			}
		}
		SysLogDao.SysLogExtInfo info = new SysLogDao.SysLogExtInfo();
		info.setMsg("修改了员工手册。");
		branOpLogDao.success(BranOpLogEntity.OP_MODULE_HANDBOOK, BranOpLogEntity.OP_TYPE_UPDATE, currentUserId, info);
		return new HttpResponse<>(ErrorCode.CODE_OK);
	}


	/**
	 * 厂车路线Html
	 *
	 * @param response
	 */
	@RequestMapping(value = "/admin/corporation/info/bus/html", method = RequestMethod.GET)
	@ResponseBody
	public void getCorpBusHtml(String suffix, HttpServletResponse response) throws Exception {
		Session session = SecurityUtils.getSubject().getSession();
		String branCorpId = session.getAttribute("bran_corp_id").toString();
		commonFileService.readCorpBusHtmlFile(branCorpId, suffix, response);
	}

	/**
	 * 厂车路线Html所需要的图片
	 *
	 * @param response
	 */
	@RequestMapping(value = "/admin/corporation/info/bus/img", method = RequestMethod.GET)
	@ResponseBody
	public void getCorpBusHtmlImage(@RequestParam("file_name") String fileName,
									@RequestParam("bran_corp_id") String branCorpId,
									HttpServletResponse response) throws Exception {
		commonFileService.readCorpBusHtmlImageFile(fileName, branCorpId, response);
	}

	/**
	 * 预览员工手册Html所需要的图片
	 *
	 * @param response
	 */
	@RequestMapping(value = "/admin/corporation/info/handbook/preview/img", method = RequestMethod.GET)
	@ResponseBody
	public void getPreviewCorpHandBookHtmlImage(@RequestParam("file_name") String fileName, HttpServletResponse response)
			throws Exception {
		Session session = SecurityUtils.getSubject().getSession();
		String branCorpId = session.getAttribute("bran_corp_id").toString();
		fileService.readCorpHandBookPreviewHtmlImageFile(fileName, branCorpId, response);
	}

	/**
	 * 员工手册Html
	 *
	 * @param response
	 */
	@RequestMapping(value = "/admin/corporation/info/handbook/html", method = RequestMethod.GET)
	@ResponseBody
	public void getCorpHandBookHtml(String suffix, HttpServletResponse response) throws Exception {
		Session session = SecurityUtils.getSubject().getSession();
		String branCorpId = session.getAttribute("bran_corp_id").toString();
		commonFileService.readCorpHandBookHtmlFile(branCorpId, suffix, response);
	}

	/**
	 * 员工手册Html所需要的图片
	 *
	 * @param response
	 */
	@RequestMapping(value = "/admin/corporation/info/handbook/img", method = RequestMethod.GET)
	@ResponseBody
	public void getCorpHandBookHtmlImage(@RequestParam("file_name") String fileName,
										 @RequestParam("bran_corp_id") String branCorpId,
										 HttpServletResponse response) throws Exception {
		commonFileService.readCorpHandBookHtmlImageFile(fileName, branCorpId, response);
	}

	/**
	 * 获取企业信息
	 *
	 * @return
	 */
	@RequestMapping(value = "/admin/corporation/info/detail", method = RequestMethod.GET)
	@ResponseBody
	public HttpResponse getCorpInfoDetail() throws Exception {
		Session session = SecurityUtils.getSubject().getSession();
		String branCorpId = session.getAttribute("bran_corp_id").toString();
		return new HttpResponse<>(ErrorCode.CODE_OK, branCorpService.getCorpInfoDetail(branCorpId));
	}

	/**
	 * 读取企业详情的图片
	 *
	 * @param fileName
	 * @param response
	 */
	@RequestMapping(value = "/admin/corporation/info/detail/image", method = RequestMethod.GET)
	@ResponseBody
	public void getCorpInfoDetailImage(@RequestParam("file_name") String fileName,
									   HttpServletResponse response) throws Exception {
		Session session = SecurityUtils.getSubject().getSession();
		String branCorpId = session.getAttribute("bran_corp_id").toString();
		fileService.readCorpDetailImageFile(fileName, branCorpId, response);
	}

	/**
	 * 获取企业入职码图片
	 *
	 * @param response
	 */
	@RequestMapping(value = "/admin/corporation/info/detail/qrcode", method = RequestMethod.GET)
	@ResponseBody
	public void getCorpInfoDetailQRCodeImage(@RequestParam String code, HttpServletResponse response) throws Exception {
		OutputStream stream = response.getOutputStream();
		ImageIO.write(qrCodeService.createImage(code), "jpg", stream);
		stream.close();
	}
}
