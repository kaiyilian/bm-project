package com.bumu.bran.admin.employee.controller.command;

import com.bumu.bran.validated.annotation.ExcelFormat;
import com.bumu.prospective.validated.ValidationMessages;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;

/**
 * majun
 */
public class ImportEmploeesConfirm {

    @JsonProperty("file_id")
    @NotBlank(message = "文件id" + ValidationMessages.NOT_BLANK)
    private String fileId;

    @ExcelFormat
    private String fileTypeStr;

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileTypeStr() {
        return fileTypeStr;
    }

    public void setFileTypeStr(String fileTypeStr) {
        this.fileTypeStr = fileTypeStr;
    }
}
