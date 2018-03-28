package com.bumu.arya.admin.soin.service;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by CuiMengxin on 2016/11/15.
 */
public interface ResponseService {

	void writeErrorCodeToResponse(HttpServletResponse response, String errorCode);
}
