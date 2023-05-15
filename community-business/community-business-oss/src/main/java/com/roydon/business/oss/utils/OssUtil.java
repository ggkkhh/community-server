package com.roydon.business.oss.utils;

import org.springframework.stereotype.Component;

/**
 * @USER: roydon
 * @DATE: 2023/5/15 14:30
 * @Description OssUtil
 **/
@Component
public class OssUtil {
    /**
     * Description: 判断OSS服务文件上传时文件的contentType
     *
     * @param suffix 文件后缀
     * @return String HTTP Content-type
     */
    public String getContentType(String suffix) {
        if (suffix.equalsIgnoreCase("bmp")) {
            return "image/bmp";
        } else if (suffix.equalsIgnoreCase("gif")) {
            return "image/gif";
        } else if (suffix.equalsIgnoreCase("jpeg") || suffix.equalsIgnoreCase("jpg")) {
            return "image/jpeg";
        } else if (suffix.equalsIgnoreCase("png")) {
            return "image/png";
        } else if (suffix.equalsIgnoreCase("html")) {
            return "text/html";
        } else if (suffix.equalsIgnoreCase("txt")) {
            return "text/plain";
        } else if (suffix.equalsIgnoreCase("vsd")) {
            return "application/vnd.visio";
        } else if (suffix.equalsIgnoreCase("pptx") || suffix.equalsIgnoreCase("ppt")) {
            return "application/vnd.ms-powerpoint";
        } else if (suffix.equalsIgnoreCase("docx") || suffix.equalsIgnoreCase("doc")) {
            return "application/msword";
        } else if (suffix.equalsIgnoreCase("xls") || suffix.equalsIgnoreCase("xlsx")) {
            return "application/vnd.ms-excel";
        } else if (suffix.equalsIgnoreCase("xml")) {
            return "text/xml";
        } else if (suffix.equalsIgnoreCase("mp3")) {
            return "audio/mp3";
        } else if (suffix.equalsIgnoreCase("amr")) {
            return "audio/amr";
        } else if (suffix.equalsIgnoreCase("pdf")) {
            return "application/pdf";
        } else {
            return "text/plain";
        }
    }
}
