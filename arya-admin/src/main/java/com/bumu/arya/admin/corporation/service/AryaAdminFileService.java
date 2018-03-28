package com.bumu.arya.admin.corporation.service;

/**
 * @author CuiMengxin
 * @date 2016/5/28
 */
public interface AryaAdminFileService {

	/**
	 * 生成企业图片访问url
	 * @param fileId
	 * @param branCorpId
	 * @return
	 */
	String generateCorpImageUrl(String fileId,String branCorpId);
}
