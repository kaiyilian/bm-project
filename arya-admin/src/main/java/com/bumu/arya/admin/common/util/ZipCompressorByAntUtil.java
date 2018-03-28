package com.bumu.arya.admin.common.util;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;

import java.io.File;

/**
 * Created by DaiAoXiang on 2016/12/1.
 */
public class ZipCompressorByAntUtil {
	private File zipFile;

	/**
	 * 压缩文件构造函数
	 *
	 * @param finalFile 最终压缩生成的压缩文件：目录+压缩文件名.zip
	 */
	public ZipCompressorByAntUtil(String finalFile) {
		zipFile = new File(finalFile);
	}

	/**
	 * 执行压缩操作
	 *
	 * @param srcPathName 需要被压缩的文件/文件夹
	 */
	public void compressExe(String srcPathName) {
		File srcdir = new File(srcPathName);
		if (!srcdir.exists()) {
			throw new RuntimeException(srcPathName + "不存在！");
		}
		Project prj = new Project();
		Zip zip = new Zip();
		zip.setProject(prj);
		zip.setDestFile(zipFile);
		FileSet fileSet = new FileSet();
		fileSet.setProject(prj);
		fileSet.setDir(srcdir);
		//fileSet.setIncludes("**/*.java"); //包括哪些文件或文件夹 eg:zip.setIncludes("*.java");
		//fileSet.setExcludes(...); //排除哪些文件或文件夹
		zip.addFileset(fileSet);
		zip.execute();
	}
}
