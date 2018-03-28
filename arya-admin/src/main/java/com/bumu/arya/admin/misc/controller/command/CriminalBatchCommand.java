package com.bumu.arya.admin.misc.controller.command;

import java.util.List;

/**
 * @author majun
 * @date 2017/3/9
 */
public class CriminalBatchCommand {

	private List<CriminalCommand> batch;

	public List<CriminalCommand> getBatch() {
		return batch;
	}

	public void setBatch(List<CriminalCommand> batch) {
		this.batch = batch;
	}
}
