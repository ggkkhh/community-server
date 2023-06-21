package com.roydon.business.mall.domain.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.roydon.business.mall.domain.entity.MallGoods;
import lombok.Data;

/**
 * MallGoodsDTO
 *
 * @AUTHOR: roydon
 * @DATE: 2023/5/21
 **/
@Data
public class MallGoodsDTO extends MallGoods {

    @TableField(exist = false)
    private Integer pageNum;

    @TableField(exist = false)
    private Integer pageSize;

}
