package com.roydon.business.news.model;

import lombok.Data;

/**
 * NewsDetailsRespResult
 *
 * @AUTHOR: roydon
 * @DATE: 2023/5/13
 **/
@Data
public class NewsDetailsRespResult {

    private Integer code;
    private String msg;
    private NewsDetails data;
}
