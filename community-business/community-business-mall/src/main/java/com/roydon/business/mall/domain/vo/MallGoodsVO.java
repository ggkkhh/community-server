package com.roydon.business.mall.domain.vo;

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
public class MallGoodsVO extends MallGoods {

    /**
     * 用户昵称
     */
    @TableField(exist = false)
    private String nickName;

    /**
     * 用户头像
     */
    @TableField(exist = false)
    private String avatar;


}
