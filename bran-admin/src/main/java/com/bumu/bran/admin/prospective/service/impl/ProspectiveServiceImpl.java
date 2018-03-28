package com.bumu.bran.admin.prospective.service.impl;

import com.bumu.bran.admin.config.service.BranAdminConfigService;
import com.bumu.bran.admin.prospective.service.ProspectiveService;
import com.bumu.bran.common.util.DownloadUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;

/**
 * 待入职serviceImpl
 *
 * @author majun
 * @date 2016/9/18
 */
@Service
public class ProspectiveServiceImpl implements ProspectiveService {

	private Logger logger = LoggerFactory.getLogger(ProspectiveServiceImpl.class);

	@Autowired
	private BranAdminConfigService branAdminConfigService;


	@Override
	public void download(HttpServletResponse response) throws Exception {
		DownloadUtils.download(new File(branAdminConfigService.getExcelTemplateLocation() + File.separator +
				"待入职员工导入模版.xls"), response);
	}
}
