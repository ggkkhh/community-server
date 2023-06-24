package com.roydon.business.mall.domain.dto;

import com.roydon.business.mall.domain.entity.MallUserCart;
import lombok.Data;

/**
 * MallUserCartDTO
 *
 * @AUTHOR: roydon
 * @DATE: 2023/6/25
 **/
@Data
public class MallUserCartDTO extends MallUserCart {
    private Integer pageNum;
    private Integer pageSize;
}
