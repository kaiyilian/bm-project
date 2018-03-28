package com.bumu.arya.admin.misc.controller.command;

import com.bumu.arya.command.PagerCommand;

/**
 * Created by CuiMengxin on 2016/10/18.
 */
public class AryaUserListCommand extends PagerCommand {

	String keyword;

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
}
