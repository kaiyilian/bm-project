package com.bumu.arya.admin.misc.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * 批量犯罪记录响应对象
 * @author majun
 * @date 2017/3/9
 */
@ApiModel()
public class CriminalBatchResult {

	@ApiModelProperty(value = "batch")
	private List<CriminalResult> batch = new ArrayList<>();

	public List<CriminalResult> getBatch() {
		return batch;
	}

	public void setBatch(List<CriminalResult> batch) {
		this.batch = batch;
	}

	public void add(CriminalResult result){
		if(result != null){
			batch.add(result);
		}
	}
}
