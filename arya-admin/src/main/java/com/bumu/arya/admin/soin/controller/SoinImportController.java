package com.bumu.arya.admin.soin.controller;

import com.bumu.arya.Utils;
import com.bumu.arya.admin.common.controller.BaseController;
import com.bumu.arya.admin.soin.controller.command.ExecuteSoinImportCommand;
import com.bumu.arya.admin.soin.result.SoinImportResult;
import com.bumu.arya.admin.soin.result.SoinImportResult.SoinErrorMsgBean;
import com.bumu.arya.admin.soin.result.SoinImportResult.SoinOutputBean;
import com.bumu.arya.admin.common.service.AryaAdminConfigService;
import com.bumu.arya.admin.soin.service.ReadSoinFileService;
import com.bumu.arya.admin.soin.service.SocialInsuranceService;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.response.HttpResponse;
import com.bumu.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 社保导入
 * Created by allen on 15/11/24.
 * Edit by cmx 2015/11/25
 */
@Controller
public class SoinImportController extends BaseController {

	Logger log = LoggerFactory.getLogger(SoinImportController.class);

	@Autowired
	AryaAdminConfigService configService;

	@Autowired
	ReadSoinFileService readSoinFileService;

	@Autowired
	private SocialInsuranceService socialInsuranceService;

	/**
	 * 获取社保信息导入页面
	 */
	@RequestMapping(value = "admin/soin/soin_info_import/index", method = RequestMethod.GET)
	public String soinInfoImport() {
		return "soin/soin_info_import";
	}

	/**
	 * 企业社保信息导入接口
	 */
	@RequestMapping(value = "admin/soin/import/verify", method = RequestMethod.POST)
	public
	@ResponseBody
	HttpResponse<SoinImportResult> verifySoinFile(HttpServletRequest request) {
		SoinImportResult result = new SoinImportResult();//结果集
		List<SoinOutputBean> excelDatas = new ArrayList<>();//Excel导入数据信息
		List<SoinErrorMsgBean> errorMsgs = new ArrayList<>();//错误消息

		if (StringUtil.isEmpty(request.getParameter("corporation_id"))) {//公司ID
			readSoinFileService.putMsgList(errorMsgs, "", "请选择公司");
		}

		if (StringUtil.isEmpty(request.getParameter("district_id"))) {//城市ID
			readSoinFileService.putMsgList(errorMsgs, "", "请选择城市");
		}

		if (StringUtil.isEmpty(request.getParameter("soin_type_id"))) {//社保类型ID
			readSoinFileService.putMsgList(errorMsgs, "", "请选择社保类型");
		}

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile multipartFile = multipartRequest.getFile("upload_file");
		if (multipartFile != null && (multipartFile.getOriginalFilename().contains(".xls")
				|| multipartFile.getOriginalFilename().contains(".xlsx"))) {
			try {
				excelDatas = readSoinFileService.readSalaryExcel(multipartFile.getInputStream());
				readSoinFileService.checkInsert(excelDatas, errorMsgs);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				return new HttpResponse(ErrorCode.CODE_INSURANCE_IMPORT_UPLOAD_FILE_READ_FAILD);//读取上传的参保信息文件失败
			}
		} else {
			readSoinFileService.putMsgList(errorMsgs, "", "请选择文件");
		}

		if (errorMsgs != null && errorMsgs.size() > 0) {
			result.setErrMsgs(errorMsgs);
			excelDatas = null;
			HttpResponse<SoinImportResult> verifyNotPass = new HttpResponse<>(result);
			verifyNotPass.setCode(ErrorCode.CODE_INSURANCE_IMPORT_FAILD);//导入文件不符合要求
			return verifyNotPass;
		} else {
			try {
				// 转存文件
				String fileName = Utils.makeUUID();
				multipartFile.transferTo(new File(configService.getSoinUploadPath() + fileName));
				result.setFileId(fileName);
				result.setData(excelDatas);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				return new HttpResponse(ErrorCode.CODE_INSURANCE_IMPORT_FILE_SAVE_FAILD);//参保信息文件转存失败
			}
		}
		return new HttpResponse<>(result);
	}

	@RequestMapping(value = "admin/soin/import/execute", method = RequestMethod.POST)
	public
	@ResponseBody
	HttpResponse<SoinImportResult> importSoinFileExecute(@RequestBody ExecuteSoinImportCommand command) {
		SoinImportResult result = new SoinImportResult();
		List<SoinOutputBean> excelDatas;//Excel导入数据信息
		String filePath = configService.getSoinUploadPath() + command.getFileId();
		try {
			File sourceFile = new File(filePath);
			InputStream inputStream = new FileInputStream(sourceFile);
			excelDatas = readSoinFileService.readSalaryExcel(inputStream);
			List<SoinErrorMsgBean> errorMsgBeans = socialInsuranceService.insert(command.getCorporationId(), command.getDistrictId(), command.getSoinTypeId(), excelDatas);
			inputStream.close();
			if (errorMsgBeans == null || errorMsgBeans.size() == 0) {
			} else {
				result.setErrMsgs(errorMsgBeans);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new HttpResponse(ErrorCode.CODE_INSURANCE_IMPORT_SAVED_FILE_READ_FAILD);// 读取转存的参保信息文件失败
		}
		return new HttpResponse<>(result);
	}
}
