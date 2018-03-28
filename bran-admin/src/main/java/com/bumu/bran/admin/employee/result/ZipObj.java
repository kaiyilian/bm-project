package com.bumu.bran.admin.employee.result;

import java.io.File;
import java.util.List;

/**
 * 封装下载附件
 */
public class ZipObj {

	private String dir;
	private List<File> fileList;

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public List<File> getFileList() {
		return fileList;
	}

	public void setFileList(List<File> fileList) {
		this.fileList = fileList;
	}
}
