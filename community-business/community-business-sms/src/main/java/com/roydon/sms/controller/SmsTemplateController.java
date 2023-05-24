package com.roydon.sms.controller;

import com.roydon.common.core.domain.AjaxResult;
import com.roydon.sms.domain.entity.SmsTemplate;
import com.roydon.sms.service.ISmsTemplateService;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (SmsTemplate)表控制层
 *
 * @author roydon
 * @since 2023-05-24 19:08:58
 */
@RestController
@RequestMapping("/smsTemplate")
public class SmsTemplateController {

    @Resource
    private ISmsTemplateService smsTemplateService;

    /**
     * 分页查询
     *
     * @param smsTemplate 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @GetMapping
    public AjaxResult queryByPage(SmsTemplate smsTemplate, PageRequest pageRequest) {
        return AjaxResult.success(this.smsTemplateService.queryByPage(smsTemplate, pageRequest));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public AjaxResult queryById(@PathVariable("id") String id) {
        return AjaxResult.success(this.smsTemplateService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param smsTemplate 实体
     * @return 新增结果
     */
    @PostMapping
    public AjaxResult add(SmsTemplate smsTemplate) {
        return AjaxResult.success(this.smsTemplateService.insert(smsTemplate));
    }

    /**
     * 编辑数据
     *
     * @param smsTemplate 实体
     * @return 编辑结果
     */
    @PutMapping
    public AjaxResult edit(SmsTemplate smsTemplate) {
        return AjaxResult.success(this.smsTemplateService.update(smsTemplate));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public AjaxResult removeById(String id) {
        return AjaxResult.success(this.smsTemplateService.deleteById(id));
    }

}

