package com.roydon.sms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.roydon.sms.domain.entity.SmsTemplate;
import com.roydon.sms.mapper.SmsTemplateMapper;
import com.roydon.sms.service.ISmsTemplateService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * (SmsTemplate)表服务实现类
 *
 * @author roydon
 * @since 2023-05-24 19:08:58
 */
@Service("smsTemplateService")
public class SmsTemplateServiceImpl extends ServiceImpl<SmsTemplateMapper, SmsTemplate> implements ISmsTemplateService {
    @Resource
    private SmsTemplateMapper smsTemplateMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param templateId 主键
     * @return 实例对象
     */
    @Override
    public SmsTemplate queryById(String templateId) {
        return this.smsTemplateMapper.queryById(templateId);
    }

    /**
     * 分页查询
     *
     * @param smsTemplate 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<SmsTemplate> queryByPage(SmsTemplate smsTemplate, PageRequest pageRequest) {
        long total = this.smsTemplateMapper.count(smsTemplate);
        return new PageImpl<>(this.smsTemplateMapper.queryAllByLimit(smsTemplate, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param smsTemplate 实例对象
     * @return 实例对象
     */
    @Override
    public SmsTemplate insert(SmsTemplate smsTemplate) {
        this.smsTemplateMapper.insert(smsTemplate);
        return smsTemplate;
    }

    /**
     * 修改数据
     *
     * @param smsTemplate 实例对象
     * @return 实例对象
     */
    @Override
    public SmsTemplate update(SmsTemplate smsTemplate) {
        this.smsTemplateMapper.update(smsTemplate);
        return this.queryById(smsTemplate.getTemplateId());
    }

    /**
     * 通过主键删除数据
     *
     * @param templateId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String templateId) {
        return this.smsTemplateMapper.deleteById(templateId) > 0;
    }
}
