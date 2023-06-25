package com.roydon.business.mall.domain.dto;

import lombok.Data;

import java.util.List;

/**
 * MallOrderCreateDTO
 *
 * @AUTHOR: roydon
 * @DATE: 2023/6/25
 **/
@Data
public class MallOrderCreateDTO {

    private String addressId;
    private List<String> goodsIds;

}
