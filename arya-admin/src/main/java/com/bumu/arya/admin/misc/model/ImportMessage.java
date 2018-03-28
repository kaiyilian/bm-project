package com.bumu.arya.admin.misc.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by CuiMengxin on 2016/12/28.
 */
public class ImportMessage {

	Map<Integer, StringBuffer> errorMsgs;//错误消息

	Map<Integer, StringBuffer> warnMsgs;//警告消息

	Map<Integer, StringBuffer> normalMsgs;//正常消息

	public ImportMessage() {
		errorMsgs = new HashMap<>();
		warnMsgs = new HashMap<>();
		normalMsgs = new HashMap<>();
	}

	public void appendErrorMsg(int no, String msg) {
		if (errorMsgs.get(no) == null) {
			errorMsgs.put(no, new StringBuffer("第" + no + "行："));
		}
		errorMsgs.get(no).append(msg);
	}

	public void appendWarnMsg(int no, String msg) {
		if (warnMsgs.get(no) == null) {
			warnMsgs.put(no, new StringBuffer("第" + no + "行："));
		}
		warnMsgs.get(no).append(msg);
	}

	public void appendNormalMsg(int no, String msg) {
		if (normalMsgs.get(no) == null) {
			if (no == -1) {
				normalMsgs.put(-1, new StringBuffer());
			} else {
				normalMsgs.put(no, new StringBuffer("第" + no + "行："));
			}
		}
		normalMsgs.get(no).append(msg);
	}


	public Map<Integer, StringBuffer> getErrorMsgs() {
		return errorMsgs;
	}

	public void setErrorMsgs(Map<Integer, StringBuffer> errorMsgs) {
		this.errorMsgs = errorMsgs;
	}


	public Map<Integer, StringBuffer> getWarnMsgs() {
		return warnMsgs;
	}

	public void setWarnMsgs(Map<Integer, StringBuffer> warnMsgs) {
		this.warnMsgs = warnMsgs;
	}

	public Map<Integer, StringBuffer> getNormalMsgs() {
		return normalMsgs;
	}

	public void setNormalMsgs(Map<Integer, StringBuffer> normalMsgs) {
		this.normalMsgs = normalMsgs;
	}
}
