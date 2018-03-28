package com.bumu.arya.admin.misc.service;

import com.bumu.arya.admin.misc.controller.command.CreateOrUpdateAdsCommand;
import com.bumu.arya.admin.misc.result.CreateAdsResult;
import com.bumu.arya.admin.misc.result.AdsListResult;
import com.bumu.exception.AryaServiceException;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author CuiMengxin
 * @date 2016/6/13
 */
public interface AdminAdsService {

    /**
     * 分页获取广告列表
     *
     * @param page
     * @param pageSize
     * @return
     * @throws AryaServiceException
     */
    AdsListResult getAdsPagnationList(int page, int pageSize) throws AryaServiceException;

    /**
     * 保存广告图片
     *
     * @param file
     * @return
     * @throws AryaServiceException
     */
    String saveAdsPic(MultipartFile file) throws AryaServiceException;

    /**
     * 新增广告
     *
     * @param command
     * @return
     * @throws AryaServiceException
     */
    CreateAdsResult createAds(CreateOrUpdateAdsCommand command) throws AryaServiceException;

    /**
     * 修改广告
     *
     * @param command
     * @throws AryaServiceException
     */
    void updateAds(CreateOrUpdateAdsCommand command) throws AryaServiceException;

    /**
     * 删除广告
     *
     * @param id
     * @throws AryaServiceException
     */
    void deleteAds(String id) throws AryaServiceException;
}
