package com.roydon.business.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * OssService
 *
 * @AUTHOR: roydon
 * @DATE: 2023/5/15
 **/
public interface OssService {

    String uploadFile(MultipartFile file);

    String uploadUserAvatar(Long userId, String userName, MultipartFile file);

    String uploadNoticeFile(MultipartFile file);

    String uploadCommonFile(MultipartFile file);

    String uploadGoodsImgFile(MultipartFile file);

    /**
     * 疫苗接种审核图片
     *
     * @param file
     * @return
     */
    String uploadInoculationAuditImgFile(MultipartFile file);

}
