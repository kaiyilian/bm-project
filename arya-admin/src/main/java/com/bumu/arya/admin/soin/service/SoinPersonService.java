package com.bumu.arya.admin.soin.service;

import com.bumu.common.result.JQDatatablesPaginationResult;

/**
 * @author CuiMengxin
 * @date 2015/12/25
 */
public interface SoinPersonService {

	/**
	 * 获取分页参保人列表（根据状态条件过滤）
	 * @param draw
	 * @param page
	 * @param pageSize
	 * @param personStatusCodesStr
	 * @return
	 */
	JQDatatablesPaginationResult getSoinPersonList(int draw,int page, int pageSize, String personStatusCodesStr);
}
