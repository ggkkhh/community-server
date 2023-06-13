package com.roydon.app.domain.vo;

import lombok.Data;

/**
 * @USER: roydon
 * @DATE: 2023/6/9 12:38
 * @Description 新闻分类vo
 **/
@Data
public class NewsCategoryVO {
    private Long dictCode;
    private Long dictValue;
    private String dictLabel;
}
