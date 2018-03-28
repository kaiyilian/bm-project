package com.bumu.arya.salary.result;

import com.bumu.common.result.FileUploadFileResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: Gujianchao
 * @Description:
 * @Date： 2017/7/4
 */
@ApiModel
public class ContractUploadResult extends FileUploadFileResult{

    @ApiModelProperty(value = "转存后的图片文件id")
    String id;

    @ApiModelProperty(value = "图片下载路径")
    String url;

    @ApiModelProperty(value = "图片路径dir")
    String dir;

    @ApiModelProperty(value = "图片名称")
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    @Override
    public String toString() {
        return "ContractUploadResult{" +
                "id='" + id + '\'' +
                ", url='" + url + '\'' +
                ", dir='" + dir + '\'' +
                '}';
    }
}
