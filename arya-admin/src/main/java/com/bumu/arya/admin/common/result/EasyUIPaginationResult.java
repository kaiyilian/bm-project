package com.bumu.arya.admin.common.result;


import com.bumu.arya.model.entity.BaseTxVersionEntity;

import java.io.Serializable;
import java.util.List;

/**
 * JQuery EasyUI 的通用分页查询结果
 * @deprecated
 * @Author Allen Wang
 */
public class EasyUIPaginationResult<T extends BaseTxVersionEntity> implements Serializable {

	/**
	 * 总数
	 */
	int total;

	/**
	 * 一页的数据
	 */
	List<T> rows;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}
}
