package com.roydon.business.news.domain.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * project : community-server
 * <p>  </p>
 *
 * @author roydon
 * @date 2023-07-17【星期一】
 **/
@Data
public class HotNews implements Serializable {
    private static final long serialVersionUID = 1L;

    private String newsId;

    private String newsTitle;

    private String coverImg;

    private String newsType;

    private String digest;

    private Date postTime;

    private String showType;

    private Integer viewNum;

}
