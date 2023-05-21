package com.roydon.business.mall.domain.vo;

import com.roydon.business.mall.domain.entity.MallGoods;
import com.roydon.business.mall.domain.entity.MallOrderGoods;
import lombok.Data;

/**
 * MallOrderGoodsVO
 *
 * @AUTHOR: roydon
 * @DATE: 2023/5/21
 **/
@Data
public class MallOrderGoodsVO extends MallOrderGoods {

    private MallGoods mallGoods;

}
