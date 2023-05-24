package com.roydon.sms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.roydon.sms.domain.entity.SmsTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * (SmsTemplate)表服务接口
 *
 * @author roydon
 * @since 2023-05-24 19:08:58
 */
public interface ISmsTemplateService extends IService<SmsTemplate> {

    /**
     * 通过ID查询单条数据
     *
     * @param templateId 主键
     * @return 实例对象
     */
    SmsTemplate queryById(String templateId);

    /**
     * 分页查询
     *
     * @param smsTemplate 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<SmsTemplate> queryByPage(SmsTemplate smsTemplate, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param smsTemplate 实例对象
     * @return 实例对象
     */
    SmsTemplate insert(SmsTemplate smsTemplate);

    /**
     * 修改数据
     *
     * @param smsTemplate 实例对象
     * @return 实例对象
     */
    SmsTemplate update(SmsTemplate smsTemplate);

    /**
     * 通过主键删除数据
     *
     * @param templateId 主键
     * @return 是否成功
     */
    boolean deleteById(String templateId);

}
