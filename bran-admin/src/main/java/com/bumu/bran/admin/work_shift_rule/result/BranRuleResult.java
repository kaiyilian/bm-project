package com.bumu.bran.admin.work_shift_rule.result;

import com.bumu.common.result.ModelResult;
import io.swagger.annotations.ApiModel;

/**
 * @author majun
 * @date 2017/2/10
 */
@ApiModel
public class BranRuleResult extends ModelResult{

	private Integer isPublished;

	public BranRuleResult(String id, String name, long version, Integer isPublished) {
		super(id, name, version);
		this.isPublished = isPublished;
	}

	public Integer getIsPublished() {
		return isPublished;
	}

	public void setIsPublished(Integer isPublished) {
		this.isPublished = isPublished;
	}
}
