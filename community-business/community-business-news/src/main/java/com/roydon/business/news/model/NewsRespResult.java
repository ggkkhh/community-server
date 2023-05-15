package com.roydon.business.news.model;

import lombok.Data;

import java.util.List;

/**
 * 接口返回数据
 *
 * @AUTHOR: roydon
 * @DATE: 2023/5/13
 **/
@Data
public class NewsRespResult {
    private Integer code;
    private String msg;
    private List<News> data;
}
