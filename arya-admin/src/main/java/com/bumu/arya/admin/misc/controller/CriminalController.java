package com.bumu.arya.admin.misc.controller;

import com.bumu.arya.admin.misc.controller.command.CriminalCommand;
import com.bumu.arya.admin.misc.result.CriminalDownloadTemplateResult;
import com.bumu.arya.admin.misc.result.CriminalResult;
import com.bumu.arya.admin.misc.service.CriminalService;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.response.HttpResponse;
import com.bumu.common.annotation.ParamCheck;
import com.bumu.exception.AryaServiceException;
import com.bumu.common.util.StringUtil;
import com.bumu.common.validator.annotation.CriminalValidationGroup;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author majun
 * @date 2017/3/9
 */
@Api(value = "CriminalController", tags = {"犯罪记录接口Criminal"})
@Controller
@RequestMapping(value = "/admin/criminal")
public class CriminalController extends InitBinderController {

	@Autowired
	private CriminalService criminalService;

	@ApiOperation(httpMethod = "GET", notes = "查询单个", value = "犯罪记录查询")
	@ApiResponse(code = 200, message = "查询成功", response = CriminalResult.class)
	@RequestMapping(value = "one", method = RequestMethod.GET)
	@ResponseBody
	@ParamCheck
	public HttpResponse<CriminalResult> queryOne(@ApiParam("犯罪记录请求对象") @Validated(value = CriminalValidationGroup.class) CriminalCommand param,
										 BindingResult bindingResult) throws Exception {
		return new HttpResponse<>(ErrorCode.CODE_OK, criminalService.queryOne(param));
	}

	@ApiOperation(httpMethod = "GET", notes = "模板下载", value = "犯罪记录导入模板下载")
	@ApiResponse(code = 200, message = "OK")
	@RequestMapping(value = "download", method = RequestMethod.GET)
	@ResponseBody
	public HttpResponse<String> download(HttpServletRequest request) {

		return new HttpResponse(ErrorCode.CODE_OK, criminalService.downloadLink(request,"/admin/criminal/download/file"));
	}

	@ApiOperation(httpMethod = "POST", notes = "确认导入,成功之后会直接导出excel", value = "犯罪记录确认导入")
	@ApiResponse(code = 200, message = "OK")
	@RequestMapping(value = "confirm", method = RequestMethod.POST)
	@ResponseBody
	public HttpResponse<CriminalDownloadTemplateResult> confirm(@ApiParam @RequestParam("file") MultipartFile param,
                                                        HttpServletResponse response,
														HttpServletRequest request) throws Exception{
		HttpSession session = request.getSession();
		String tokenSession = (String) session.getAttribute("token");
		if(!StringUtil.isEmpty(tokenSession)){
			session.removeAttribute("token");
			throw  new AryaServiceException(ErrorCode.CODE_SYS_ERR, "不要重复提交");
		}

		session.setAttribute("token","com.bumu.arya.admin.controller.verify");

		if(param==null){
			throw new AryaServiceException(ErrorCode.CODE_SYS_ERR,"请选择文件");
		}
		String filename="";
		try{
		  filename = criminalService.confirm(param, response);}
		catch (Exception e){
			session.removeAttribute("token");
			throw e;
		}
		CriminalDownloadTemplateResult criminalDownloadTemplateResult = new CriminalDownloadTemplateResult();
		String url="";
		String contextPath = request.getContextPath();
		String requestURL = request.getRequestURL().toString();
		String[] split = requestURL.split(contextPath);
		url = split[0]+contextPath+"/admin/criminal/download/criminalfile?filename="+filename;
		criminalDownloadTemplateResult.setUrl(url);
		session.removeAttribute("token");
		return new HttpResponse<>(ErrorCode.CODE_OK, criminalDownloadTemplateResult);
	}

	@ApiOperation(httpMethod = "GET", notes = "预留接口,暂不使用", value = "统计查询较真接口次数")
	@ApiResponse(code = 200, message = "OK")
	@RequestMapping(value = "count", method = RequestMethod.GET)
	@ResponseBody
	public HttpResponse<Integer> count() {
		return new HttpResponse(criminalService.count());
	}



	@RequestMapping(value = "download/file", method = RequestMethod.GET)
	@ResponseBody
	public HttpResponse downloadFile(HttpServletResponse response) {

		return criminalService.download(response);
	}


	@RequestMapping(value = "download/criminalfile", method = RequestMethod.GET)
	@ResponseBody
	public HttpResponse downloadExcelFile(@RequestParam(value = "filename")String fielname, HttpServletResponse response) {

		return criminalService.downloadExcelFile(fielname,response);
	}



}
