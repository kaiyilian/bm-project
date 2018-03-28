package com.bumu.arya.admin.common.result;

import com.bumu.arya.model.entity.BaseTxVersionEntity;
import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.util.Collection;

/**
 * JQuery Datatables 通用分页查询结果
 * @deprecated 已移动到common，临时复制一份过来避免未提交的引用方编译错误
 */
@ApiModel
//@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class JQDatatablesPaginationResult<T extends BaseTxVersionEntity> implements Serializable {

	int draw;

	int recordsTotal;

	int recordsFiltered;

	Collection<T> data;

	public int getDraw() {
		return draw;
	}

	public void setDraw(int draw) {
		this.draw = draw;
	}

	public int getRecordsTotal() {
		return recordsTotal;
	}

	public void setRecordsTotal(int recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

	public int getRecordsFiltered() {
		return recordsFiltered;
	}

	public void setRecordsFiltered(int recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

	public Collection<T> getData() {
		return data;
	}

	public void setData(Collection<T> data) {
		this.data = data;
	}
}
