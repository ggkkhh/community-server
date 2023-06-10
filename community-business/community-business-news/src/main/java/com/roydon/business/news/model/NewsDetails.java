package com.roydon.business.news.model;

import lombok.Data;

import java.util.List;

/**
 * NewsDetails
 *
 * @AUTHOR: roydon
 * @DATE: 2023/5/13
 **/
@Data
public class NewsDetails {

    private List<Images> images;
    private String title;
    private String content;

}
