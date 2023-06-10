package com.roydon.business.mall.domain.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.roydon.business.mall.domain.entity.MallOrder;
import lombok.Data;

/**
 * MallOrderDTO
 *
 * @AUTHOR: roydon
 * @DATE: 2023/5/21
 **/
@Data
public class MallOrderDTO extends MallOrder {

    @TableField(exist = false)
    private Integer pageNum;

    @TableField(exist = false)
    private Integer pageSize;
}
