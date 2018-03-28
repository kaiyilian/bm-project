package com.bumu.arya.admin.misc.service;

import com.bumu.exception.AryaServiceException;
import org.springframework.web.multipart.MultipartFile;

/**
 * 需要拆分
 * @author CuiMengxin
 * @date 2016/3/28
 */
public interface FileService {
    /**
     * 转存广告图片文件，返回文件id
     *
     * @param file
     * @return
     * @throws AryaServiceException
     */
    String saveAdsPic(MultipartFile file) throws AryaServiceException;

    /**
     * 通用储存图片
     * @deprecated
     * @param file
     * @return
     * @throws AryaServiceException
     */
    String tempSaveFile(MultipartFile file, Integer type) throws AryaServiceException;

    /**
     * 通用文件储存
     * 文件后缀
     * @deprecated
     * @param file
     * @return
     * @throws AryaServiceException
     */
    String tempSaveFile(MultipartFile file, Integer type, String suffix) throws AryaServiceException;

    /**
     * 获取通用储存图片的url
     *
     * @param fileName
     * @return
     * @throws AryaServiceException
     */
    String getTempFileUrl(String fileName, Integer type) throws AryaServiceException;

}
