package com.bumu.arya.admin.soin.service.impl;

import com.bumu.arya.admin.soin.service.ResponseService;
import com.bumu.arya.response.HttpResponse;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by CuiMengxin on 2016/11/15.
 */
@Service
public class ResponseServiceImpl implements ResponseService {
	@Override
	public void writeErrorCodeToResponse(HttpServletResponse response, String errorCode) {
		HttpResponse responseResult = new HttpResponse(errorCode);
		try {
			response.setHeader("Content-type", "application/json;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(responseResult.toString());
			response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
