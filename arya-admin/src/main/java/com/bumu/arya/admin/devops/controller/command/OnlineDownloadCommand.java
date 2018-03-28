package com.bumu.arya.admin.devops.controller.command;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Guchaochao
 * @date 2018/1/10
 */
@ApiModel
public class OnlineDownloadCommand {

    @ApiModelProperty("文件名称")
    private String fileName;

    @ApiModelProperty("项目code")
    private String projectCode;

    @ApiModelProperty("文件的日期")
    private Long fileDate;

    @ApiModelProperty("文件part")
    private Integer filePart;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public Long getFileDate() {
        return fileDate;
    }

    public void setFileDate(Long fileDate) {
        this.fileDate = fileDate;
    }

    public Integer getFilePart() {
        return filePart;
    }

    public void setFilePart(Integer filePart) {
        this.filePart = filePart;
    }
}
