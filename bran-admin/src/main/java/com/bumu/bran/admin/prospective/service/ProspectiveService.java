package com.bumu.bran.admin.prospective.service;

import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;

/**
 * 待入职service
 *
 * @author majun
 * @date 2016/9/18
 */
@Transactional
public interface ProspectiveService {

	void download(HttpServletResponse response) throws Exception;
}
