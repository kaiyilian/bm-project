package com.bumu.arya.admin.system.result;

import com.bumu.arya.exception.BumuException;
import com.bumu.arya.response.HttpResponse;

/**
 * Created by allen on 2017/3/7.
 */
public class SimpleUrlResultResponse extends HttpResponse<SimpleUrlResult> {
	public SimpleUrlResultResponse() {
	}

	public SimpleUrlResultResponse(SimpleUrlResult result) {
		super(result);
	}

	public SimpleUrlResultResponse(SimpleUrlResult result, String code) {
		super(result, code);
	}

	public SimpleUrlResultResponse(String code) {
		super(code);
	}

	public SimpleUrlResultResponse(String code, String msg) {
		super(code, msg);
	}

	public SimpleUrlResultResponse(String code, SimpleUrlResult result) {
		super(code, result);
	}

	public SimpleUrlResultResponse(BumuException bumuEx) {
		super(bumuEx);
	}
}
