package com.roydon.business.oss.utils;

import org.springframework.stereotype.Component;

/**
 * @USER: roydon
 * @DATE: 2023/5/15 14:30
 * @Description OssUtil
 **/
public class OssUtil {

    //用户头像文件夹前缀
    public static final String USER_AVATAR_FILE = "user-avatar/";
    //用户头像文件前缀
    public static final String USER_AVATAR_PREFIX = "/user-avatar_";
    /**
     * 判断OSS服务文件上传时文件的contentType
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

    /**
     * 通过文件名判断并获取OSS服务文件上传时文件的contentFormat(文件格式)
     * @author tony
     * @param fileName 文件名
     * @return 文件的格式代码
     */
    public static  int getContentFormat(String fileName) {
        String FilenameExtension = fileName.substring(fileName.lastIndexOf("."));

        if (FilenameExtension.equalsIgnoreCase(".jpeg") ||
                FilenameExtension.equalsIgnoreCase(".jpg") ||
                FilenameExtension.equalsIgnoreCase(".png") ||
                FilenameExtension.equalsIgnoreCase(".gif")) {
            return 1; // 图片
        }
        if (FilenameExtension.equalsIgnoreCase(".html")||
                FilenameExtension.equalsIgnoreCase(".txt") ||
                FilenameExtension.equalsIgnoreCase(".pptx") ||
                FilenameExtension.equalsIgnoreCase(".ppt") ||
                FilenameExtension.equalsIgnoreCase(".docx") ||
                FilenameExtension.equalsIgnoreCase(".doc") ||
                FilenameExtension.equalsIgnoreCase(".xla") ||
                FilenameExtension.equalsIgnoreCase(".xlc")||
                FilenameExtension.equalsIgnoreCase(".xlm")||
                FilenameExtension.equalsIgnoreCase(".xls")||
                FilenameExtension.equalsIgnoreCase(".xlt")||
                FilenameExtension.equalsIgnoreCase(".xlw") ||
                FilenameExtension.equalsIgnoreCase(".xml") ||
                FilenameExtension.equalsIgnoreCase(".pdf") ||
                FilenameExtension.equalsIgnoreCase(".zip") ||
                FilenameExtension.equalsIgnoreCase(".tar")) {
            return 2;  //文档
        }
        if (FilenameExtension.equalsIgnoreCase(".mp3")) {
            return 3; //音乐
        }
        if (FilenameExtension.equalsIgnoreCase(".avi") ||
                FilenameExtension.equalsIgnoreCase(".mp4")) {
            return 4;     //视频
        }

        //其他
        return 6;
    }




}
