package com.roydon.business.news.model;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * News
 *
 * @AUTHOR: roydon
 * @DATE: 2023/5/13
 **/
@Data
public class News {

    private String title;
    private List<String> imgList;
    private String source;
    private String newsId;
    private String digest;
    private Date postTime;
    private String videoList;

}
