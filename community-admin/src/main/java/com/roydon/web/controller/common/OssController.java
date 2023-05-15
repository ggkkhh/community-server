package com.roydon.web.controller.common;

import com.roydon.business.oss.service.OssService;
import com.roydon.common.core.domain.AjaxResult;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@Api("文件存储服务")
@RestController
@RequestMapping("/system/oss")
public class OssController {

    @Resource
    private OssService ossService;

    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public AjaxResult uploadOssFile(MultipartFile file) {
        String url = ossService.uploadFile(file);
        return AjaxResult.success(url);
    }

}
