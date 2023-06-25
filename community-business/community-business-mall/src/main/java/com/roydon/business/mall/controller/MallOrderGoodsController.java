package com.roydon.business.mall.controller;

import com.roydon.business.mall.service.IMallOrderGoodsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * (MallOrderGoods)表控制层
 *
 * @author roydon
 * @since 2023-05-18 23:13:55
 */
@RestController
@RequestMapping("/app/mallOrderGoods")
public class MallOrderGoodsController {

    @Resource
    private IMallOrderGoodsService mallOrderGoodsService;


}

