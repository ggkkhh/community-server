package com.roydon.business.mall.domain.dto;

import com.roydon.business.mall.domain.entity.MallUserAddress;
import lombok.Data;

/**
 * MallUserAddressDTO
 *
 * @AUTHOR: roydon
 * @DATE: 2023/6/25
 **/
@Data
public class MallUserAddressDTO extends MallUserAddress {
    private Integer pageNum;
    private Integer pageSize;
}
