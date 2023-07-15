package com.roydon.business.news.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.roydon.common.core.domain.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName app_news
 */
@Data
@TableName(value = "app_news")
public class AppNews extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId
    private String newsId;

    private String newsTitle;

    private String coverImg;

    private String source;

    private String newsType;

    private String thereNewsId;

    private String digest;

    private Date postTime;

    private String newsContent;

    private String contentImages;

    private String showInApp;

    private String showType;

    private String delFlag;

    private Integer viewNum;

}
