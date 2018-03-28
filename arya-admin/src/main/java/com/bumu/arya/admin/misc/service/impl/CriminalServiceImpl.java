package com.bumu.arya.admin.misc.service.impl;

import com.bumu.SysUtils;
import com.bumu.arya.Utils;
import com.bumu.arya.admin.common.service.AryaAdminConfigService;
import com.bumu.arya.admin.system.service.OpLogService;
import com.bumu.arya.admin.misc.controller.command.CriminalBatchCommand;
import com.bumu.arya.admin.misc.controller.command.CriminalCommand;
import com.bumu.arya.admin.misc.result.CriminalRecordExcelReadResult;
import com.bumu.arya.admin.misc.service.CriminalFileService;
import com.bumu.arya.admin.misc.service.CriminalService;
import com.bumu.arya.admin.model.SysUserModel;
import com.bumu.arya.admin.misc.model.dao.CriminalDao;
import com.bumu.arya.admin.misc.model.CriminalRecordExcelModel;
import com.bumu.arya.admin.misc.result.CriminalBatchResult;
import com.bumu.arya.admin.misc.result.CriminalDownloadTemplateResult;
import com.bumu.arya.admin.misc.result.CriminalResult;
import com.bumu.arya.admin.service.*;
import com.bumu.arya.admin.misc.result.RealShowResult;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.admin.misc.model.entity.CriminalEntity;
import com.bumu.arya.admin.misc.model.entity.CriminalMockEntity;
import com.bumu.arya.admin.misc.model.entity.CriminalRecordEntity;
import com.bumu.arya.response.HttpResponse;
import com.bumu.arya.webservice.Poster;
import com.bumu.exception.AryaServiceException;
import com.bumu.exception.Assert;
import com.bumu.common.service.ConfigService;
import com.bumu.common.service.ReadFileResponseService;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.commons.collections.map.HashedMap;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author majun
 * @date 2017/3/9
 */
@Service
public class CriminalServiceImpl implements CriminalService {

	private static Logger logger = LoggerFactory.getLogger(CriminalServiceImpl.class);

	@Autowired
	@Qualifier(value = "criminalDaoImpl")
	private CriminalDao criminalDao;

	@Autowired
	@Qualifier(value = "criminalMockDaoImpl")
	private CriminalDao criminalMockDao;

	@Autowired
	@Qualifier(value = "criminalRecordDaoImpl")
	private CriminalDao criminalRecordDao;

	@Autowired
	private Poster poster;

	@Autowired
	private AryaAdminConfigService aryaAdminConfigService;

	@Autowired
    CriminalFileService fileService;

	@Autowired
	ReadFileResponseService readFileResponseService;

	@Autowired
    OpLogService opLogService;

	@Autowired
	private ConfigService configService;

	@Autowired
	private SysUserService sysUserService;

	@Override
	public CriminalBatchResult queryBatch(CriminalBatchCommand batch) throws Exception {
		if (batch == null || batch.getBatch() == null || batch.getBatch().isEmpty()) {
			throw new AryaServiceException("excel导入有误, 数据为空");
		}

		CriminalBatchResult result = new CriminalBatchResult();
		logger.info("批量查询开始: ");
		int count = 0;
		for (CriminalCommand one : batch.getBatch()) {
			logger.info("当前查询到: " + (count++));
			result.add(queryOne(one));
		}
		return result;
	}

	@Override
	public CriminalResult queryOne(CriminalCommand param) throws Exception {
		// 调用本地接口
		CriminalEntity entity = (CriminalEntity) criminalDao.findByParam(param);
		// 开关 0 使用Mock接口 1 使用较真接口
		int isMock = configService.getIntKey("arya.criminal.mock");
		RealShowResult realShowResult;

		if (isMock == 0) {
			// 调用Mock接口
			logger.info("调用Mock接口... ");
			CriminalMockEntity criminalMockEntity = (CriminalMockEntity) criminalMockDao.findByParam(param);
			Assert.notNull(criminalMockEntity, "请先模拟数据");
			entity = createOrUpdate(criminalMockEntity, entity);
		} else {
			logger.info("调用较真接口... ");
			// 调用较真接口
			realShowResult = RealShowResult.createByJson(poster.post(param.toRealShowParams()));
			// 对比 新增/更新
			entity = createOrUpdate(realShowResult.toCriminalCommand(param), entity);
		}
		// 保存接口调用记录
		logger.info("保存调用记录...");
		criminalRecordDao.create(CriminalRecordEntity.createByCriminal(entity, sysUserService.getCurrentSysUser().getId()));
		// 返回
		return CriminalResult.createByEntity(entity);
	}

	private CriminalEntity createOrUpdate(CriminalCommand criminalCommand, CriminalEntity entity) {
		if (entity == null) {
			entity = new CriminalEntity();
			criminalCommand.toCreateEntity(entity);
		} else {
			criminalCommand.toUpdateEntity(entity);
		}
		criminalDao.createOrUpdate(entity);
		return entity;

	}

	private CriminalEntity createOrUpdate(CriminalMockEntity criminalMockEntity, CriminalEntity entity) throws Exception {
		SysUserModel user = sysUserService.getCurrentSysUser();
		if (entity == null) {
			entity = new CriminalEntity();
			criminalMockEntity.toCreateEntity(entity, user.getId());
		} else {
			criminalMockEntity.toUpdateEntity(entity, user.getId());
		}
		criminalDao.createOrUpdate(entity);
		return entity;

	}

	@Override
	public HttpResponse download(HttpServletResponse httpServletResponse) {
		try {
			String templatePath = "";
			httpServletResponse.setContentType("application/vnd.ms-excel");
			String headStr = "attachment;filename=\"" + SysUtils.parseEncoding("犯罪记录导入模板.xlsx", "utf-8") + "\"";
			httpServletResponse.setHeader("Content-Disposition", headStr);
			templatePath = aryaAdminConfigService.getExportTemplatePath() + AryaAdminConfigService.CRIMINAL_RECORD_IMPORT;
			logger.info("导出模板的获取地址" + templatePath);
			File file = new File(templatePath);
			if (!file.exists()) {
				logger.debug("模板文件不存在");
				return new HttpResponse(ErrorCode.CODE_ORDER_TEMPLATE_IS_NOT_EXIST);
			}


			readFileResponseService.readFileToResponse(templatePath, 1024 * 200, httpServletResponse);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new HttpResponse(ErrorCode.CODE_OK);
	}

	@Override
	public String confirm(MultipartFile param, HttpServletResponse response) throws Exception {
		String filePath = fileService.saveCriminalRecorExcelFile(param);
		//读取文件
		CriminalBatchCommand criminalBatchCommand = processExcelData(filePath);
		List<CriminalCommand> criminalBatchCommandBatch = criminalBatchCommand.getBatch();
		if (criminalBatchCommandBatch != null & criminalBatchCommandBatch.size() > 0) {
			//得到查询结构
			CriminalBatchResult criminalBatchResult = queryBatch(criminalBatchCommand);
			List<CriminalResult> batch = criminalBatchResult.getBatch();
			if (batch != null && batch.size() > 0) {
				for (CriminalResult result : batch) {
					if (result.getQueryStatus() > 0) {
						result.setQueryStatusName("失败");
					} else {
						result.setQueryStatusName("成功");
					}
				}
			}


			String downloadFilePath = saveXlsx(
					aryaAdminConfigService.getExportTemplatePath() + AryaAdminConfigService.CRIMINAL_RECORD_EXPORT,
					Utils.makeUUID(),
					new HashedMap() {{
						put("list", criminalBatchResult);
					}},
					response
			);
			return downloadFilePath;
		} else {
			throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "模板中没有查询数据");

		}
	}

	@Override
	public CriminalDownloadTemplateResult downloadLink(HttpServletRequest request, String urlPath) {
		CriminalDownloadTemplateResult criminalDownloadTemplateResult = new CriminalDownloadTemplateResult();
		String url = "";
		String contextPath = request.getContextPath();
		String requestURL = request.getRequestURL().toString();
		String[] split = requestURL.split(contextPath);
		url = split[0] + contextPath + urlPath;
		criminalDownloadTemplateResult.setUrl(url);
		return criminalDownloadTemplateResult;
	}

	@Override
	public int count() {
		return 0;
	}


	public CriminalBatchCommand processExcelData(String fileName) throws Exception {
		CriminalBatchCommand criminalBatchResult = new CriminalBatchCommand();
		List<CriminalCommand> commands = new ArrayList<>();
		criminalBatchResult.setBatch(commands);
		CriminalRecordExcelReadResult excelReadResult = fileService.readCriminalRecordExcelFile(fileName);
		if (excelReadResult != null) {
			List<CriminalRecordExcelModel> models = excelReadResult.getModels();
			if (models.size() > 0) {
				CriminalCommand criminalCommand = null;
				for (CriminalRecordExcelModel model : models) {
					criminalCommand = new CriminalCommand();
					criminalCommand.setIdCardNo(model.getIdCardNo());
					criminalCommand.setName(model.getName());
					commands.add(criminalCommand);
				}
			}
		}
		return criminalBatchResult;
	}


	public String saveXlsx(String config, String fileName, Map<String, Object> params, HttpServletResponse response) {
		XLSTransformer transformer = new XLSTransformer();
		FileInputStream in = null;
		try {
			logger.info("config: " + config);
			logger.info("fileName: " + fileName);
			logger.info("list: " + params.get("list"));
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
			String str = sdf.format(date);
			String exportName = fileName + "_" + str + ".xlsx";
			in = new FileInputStream(config);
			//将beans通过模板输入流写到workbook中
			Workbook workbook = transformer.transformXLS(in, params);

			//转存文件
			logger.info("【犯罪记录查询】开始copy文件。" + fileName + "。");
			fileService.saveCriminalRecordExcelFile(workbook, exportName);
			logger.info("【犯罪记录查询】copy文件完成。" + fileName + "。");

			return exportName;
		} catch (Exception e) {
			e.printStackTrace();
			throw new AryaServiceException(ErrorCode.CODE_EXCEL_EXPORT_FILE);
		}finally {
			if (in!=null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
					logger.info("读取犯罪记录出现问题" + fileName + "。" );
					throw new AryaServiceException(ErrorCode.CODE_EXCEL_EXPORT_FILE);
				}
			}
		}
	}

	@Override
	public HttpResponse downloadExcelFile(String filename, HttpServletResponse httpServletResponse) {
		try {
			String templatePath = "";
			httpServletResponse.setContentType("application/vnd.ms-excel");
			String headStr = "attachment;filename=\"" + SysUtils.parseEncoding("犯罪记录查询.xlsx", "utf-8") + "\"";
			httpServletResponse.setHeader("Content-Disposition", headStr);
			templatePath = aryaAdminConfigService.getCriminalRecordExcelExportPath() + "/" + filename;
			logger.info("犯罪记录查询结果地址" + templatePath);
			File file = new File(templatePath);
			if (!file.exists()) {
				logger.debug("模板文件不存在");
				return new HttpResponse(ErrorCode.CODE_ORDER_TEMPLATE_IS_NOT_EXIST);
			}
			readFileResponseService.readFileToResponse(templatePath, 1024 * 200, httpServletResponse);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new HttpResponse(ErrorCode.CODE_OK);
	}



}
