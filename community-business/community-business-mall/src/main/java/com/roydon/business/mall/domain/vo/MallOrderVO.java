package com.roydon.business.mall.domain.vo;

import com.roydon.business.mall.domain.entity.MallOrder;
import lombok.Data;

import java.util.List;

/**
 * MallOrderVo
 *
 * @AUTHOR: roydon
 * @DATE: 2023/5/21
 **/
@Data
public class MallOrderVO extends MallOrder {

    private List<MallOrderGoodsVO> mallOrderGoodsVOList;

}
