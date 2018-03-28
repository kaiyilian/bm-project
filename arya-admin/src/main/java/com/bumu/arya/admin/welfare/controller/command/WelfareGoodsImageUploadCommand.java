package com.bumu.arya.admin.welfare.controller.command;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by CuiMengxin on 16/9/13.
 */
public class WelfareGoodsImageUploadCommand {

    MultipartFile file;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
