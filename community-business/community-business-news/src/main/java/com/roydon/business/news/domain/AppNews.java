package com.roydon.business.news.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @TableName app_news
 */
@TableName(value ="app_news")
@Data
public class AppNews implements Serializable {

    @TableId
    private String newsId;

    private String newsTitle;

    private String coverImg;

    private String source;

    private Integer newsType;

    private String thereNewsId;

    private String digest;

    private LocalDateTime postTime;

    private String newsContent;

    private String contentImages;

    private Date createTime;

    private Date updateTime;

    private Integer delFlag;

    private static final long serialVersionUID = 1L;


}