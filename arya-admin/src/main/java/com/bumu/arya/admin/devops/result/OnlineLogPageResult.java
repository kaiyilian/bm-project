package com.bumu.arya.admin.devops.result;

import com.bumu.common.result.PaginationResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author Guchaochao
 * @date 2018/1/10
 */
@ApiModel
public class OnlineLogPageResult extends PaginationResult {

    @ApiModelProperty("日志列表结果")
    List<OnlineLogReuslt> onlineLogResults;

    public List<OnlineLogReuslt> getOnlineLogResults() {
        return onlineLogResults;
    }

    public void setOnlineLogResults(List<OnlineLogReuslt> onlineLogResults) {
        this.onlineLogResults = onlineLogResults;
    }

    @ApiModel
    public static class OnlineLogReuslt{

        @ApiModelProperty("文件名称")
        private String fileName;

        @ApiModelProperty("文件日期")
        private Long fileDate;

        @ApiModelProperty("文件小时")
        private String fileHour;

        @ApiModelProperty("文件part")
        private String filePart;

        @ApiModelProperty("文件大小")
        private String fileSize;

        public String getFileSize() {
            return fileSize;
        }

        public void setFileSize(String fileSize) {
            this.fileSize = fileSize;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public Long getFileDate() {
            return fileDate;
        }

        public void setFileDate(Long fileDate) {
            this.fileDate = fileDate;
        }

        public String getFileHour() {
            return fileHour;
        }

        public void setFileHour(String fileHour) {
            this.fileHour = fileHour;
        }

        public String getFilePart() {
            return filePart;
        }

        public void setFilePart(String filePart) {
            this.filePart = filePart;
        }
    }
}
