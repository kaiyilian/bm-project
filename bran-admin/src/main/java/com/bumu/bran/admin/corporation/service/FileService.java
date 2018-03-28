package com.bumu.bran.admin.corporation.service;

import org.apache.poi.poifs.filesystem.OfficeXmlFileException;

import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

/**
 * @author CuiMengxin
 * @date 2016/5/18
 */
public interface FileService {

	/**
	 * 证件照路由
	 */
	String EMPLOYEE_PROSPECTIVE_FACE_ROUTE = "admin/employee/prospective/detail/face";
	String EMPLOYEE_FACE_ROUTE = "admin/employee/detail/face";
	String EMPLOYEE_LEAVE_FACE_ROUTE = "admin/employee/leave/detail/face";

	/**
	 * 身份证照片路由
	 */
	String EMPLOYEE_PROSPECTIVE_IDACRD_IMG_ROUTE = "admin/employee/prospective/detail/idcard";

	String EMPLOYEE_IDACRD_IMG_ROUTE = "admin/employee/detail/idcard";

	String EMPLOYEE_LEAVE_IDACRD_IMG_ROUTE = "admin/employee/leave/detail/idcard";

	/**
	 * 学历照片路由
	 */
	String EMPLOYEE_PROSPECTIVE_EDUCATION_IMG_ROUTE = "admin/employee/prospective/detail/education";

	String EMPLOYEE_EDUCATION_IMG_ROUTE = "admin/employee/detail/education";

	String EMPLOYEE_LEAVE_EDUCATION_IMG_ROUTE = "admin/employee/leave/detail/education";

	/**
	 * 离职证明照片路由
	 */
	String EMPLOYEE_PROSPECTIVE_DIMISSION_IMG_ROUTE = "admin/employee/prospective/detail/leave";

	String EMPLOYEE_DIMISSION_IMG_ROUTE = "admin/employee/detail/leave";

	String EMPLOYEE_LEAVE_DIMISSION_IMG_ROUTE = "admin/employee/leave/detail/leave";

	// 获取企业的全部照片
	String EMPLOYEE_ALL_PHOTO = "admin/employee/detail/all";

	// 获取待入职员工的全部照片
	String PRE_ALL_PHOTO = "admin/employee/prospective/detail/image/all";

	/**
	 * 读取员工身份证照片
	 *
	 * @param fileName
	 * @param branUserId
	 * @param response
	 */
	void readEmployeeIdcardImageFile(String fileName, String branUserId, HttpServletResponse response) throws Exception;

	/**
	 * 读取员工学历照片
	 *
	 * @param fileName
	 * @param branUserId
	 * @param response
	 */
	void readEmployeeEducationImageFile(String fileName, String branUserId, HttpServletResponse response) throws Exception;

	/**
	 * 读取员工离职证明照片
	 *
	 * @param fileName
	 * @param branUserId
	 * @param response
	 */
	void readEmployeeLeaveImageFile(String fileName, String branUserId, HttpServletResponse response) throws Exception;

	/**
	 * 读取员工证件照
	 *
	 * @param fileName
	 * @param branUserId
	 * @param response
	 */
	void readEmployeeFaceImageFile(String fileName, String branUserId, HttpServletResponse response) throws Exception;

	/**
	 * 获取员工各种照片读取URL
	 *
	 * @param route
	 * @param fileName
	 * @param branUserId
	 * @return
	 */
	String getEmployeeAllKindImageURL(String route, String fileName, String branUserId);

	/**
	 * 把word转成html
	 *
	 * @param wordPath
	 * @param htmlSavePath
	 * @param wordFileName
	 * @param imageUrlLocation
	 * @throws TransformerException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws OfficeXmlFileException
	 */
	void convertWord2Html(String wordPath, String htmlSavePath, String wordFileName, String imageUrlLocation) throws TransformerException, IOException,
			ParserConfigurationException, OfficeXmlFileException;

	/**
	 * 读取厂车路线预览Html文件
	 *
	 * @param branCorpId
	 * @param suffix
	 * @param response
	 */
	void readCorpBusPreviewHtmlFile(String branCorpId, String suffix, HttpServletResponse response);

	/**
	 * 读取厂车路线预览Html关联的图片文件
	 *
	 * @param fileName
	 * @param response
	 */
	void readCorpBusPreviewHtmlImageFile(String fileName, String corpId, HttpServletResponse response);

	/**
	 * 拼接厂车路线html访问Url
	 *
	 * @param branCorpId
	 * @return
	 */
	String generateBusHtmlUrl(String branCorpId, String suffix);

	/**
	 * 拼接厂车路线预览html访问Url
	 *
	 * @param branCorpId
	 * @return
	 */
	String generateBusHtmlPreviewUrl(String branCorpId, String suffix);

	/**
	 * 拼接厂车路线预览html图片的访问Url，用于word转html时图片标签赋值<image src=''/>
	 *
	 * @param branCorpId
	 * @return
	 */
	String generateBusHtmlPreviewImageUrl(String branCorpId);

	/**
	 * 拼接厂车路线html图片的访问Url，用于word转html时图片标签赋值<image src=''/>
	 *
	 * @param branCorpId
	 * @return
	 */
	String generateBusHtmlImageUrl(String branCorpId);

	/**
	 * 读取员工手册预览Html文件
	 *  @param branCorpId
	 * @param suffix
	 * @param response
	 */
	void readCorpHandBookPreviewHtmlFile(String branCorpId, String suffix, HttpServletResponse response);

	/**
	 * 拼接员工手册预览html图片的访问Url，用于word转html时图片标签赋值<image src=''/>
	 *
	 * @param branCorpId
	 * @return
	 */
	String generateHandBookHtmlPreviewImageUrl(String branCorpId);

	/**
	 * 拼接员工手册html图片的访问Url，用于word转html时图片标签赋值<image src=''/>
	 *
	 * @param branCorpId
	 * @return
	 */
	String generateHandBookHtmlImageUrl(String branCorpId);

	/**
	 * 读取员工手册预览Html关联的图片文件
	 *
	 * @param fileName
	 * @param response
	 */
	void readCorpHandBookPreviewHtmlImageFile(String fileName, String corpId, HttpServletResponse response);

	/**
	 * 拼接员工手册html访问Url
	 *
	 * @param branCorpId
	 * @return
	 */
	String generateHandBookHtmlUrl(String branCorpId, String suffix);

	/**
	 * 拼接员工手册预览html访问Url
	 *
	 * @param branCorpId
	 * @param suffix
	 * @return
	 */
	String generateHandBookHtmlPreviewUrl(String branCorpId, String suffix);

	/**
	 * 获取企业图片URL
	 *
	 * @param fileName
	 * @return
	 */
	String getBranCorpImageUrl(String fileName);

	/**
	 * 读取企业详情的图片
	 *
	 * @param fileName
	 * @param branCorpId
	 * @param response
	 */
	void readCorpDetailImageFile(String fileName, String branCorpId, HttpServletResponse response);

	/**
	 * 生成二维码图片url
	 *
	 * @param code
	 * @return
	 */
	String generateCorpCheckinQRCodeUrl(String code);

	String getEmpIdCardFontImg(String fileName, String empId, String corpId);

	String getEmpIdCardBackImg(String fileName, String empId, String corpId);

	String getEmpIdCardFaceImg(String fileName, String empId, String corpId);

	String getEmpFaceImg(String fileName, String empId, String corpId);

	String getEmpLeaveCertImg(String fileName, String empId, String corpId);

	String getEmpEducationImg(String fileName, String empId, String corpId);

	void getEmpAllImage(String fileName, String empId, String corpId, HttpServletResponse response) throws Exception;

	String getEmpAllImageUrl(String fileName, String branUserId, String type);

	void getEmployeeImage(String fileName, String branUserId, String type, HttpServletResponse response);
}
