package com.bumu.arya.admin.corporation.service.impl;

import com.bumu.arya.admin.common.service.AryaAdminConfigService;
import com.bumu.arya.admin.corporation.service.AryaAdminFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author CuiMengxin
 * @date 2016/5/28
 */
@Service
public class AryaAdminFileServiceImpl implements AryaAdminFileService {

	@Autowired
	AryaAdminConfigService aryaAdminConfigService;

	@Override
	public String generateCorpImageUrl(String fileId, String branCorpId) {
		return  "admin/corporation/image/detail?file_id=" + fileId + "&bran_corp_id=" + branCorpId;
	}
}
