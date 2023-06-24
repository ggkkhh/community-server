package com.roydon.business.mall.domain.vo;

import com.roydon.business.mall.domain.entity.MallUserCart;
import lombok.Data;

/**
 * MallUserCartVo
 *
 * @AUTHOR: roydon
 * @DATE: 2023/6/25
 **/
@Data
public class MallUserCartVO extends MallUserCart {

    private String goodsTitle;
    private Double goodsPrice;
    private String goodsImg;
}
