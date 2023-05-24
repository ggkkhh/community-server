package com.roydon.sms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.roydon.common.utils.DateUtils;
import com.roydon.common.utils.uniqueid.IdGenerator;
import com.roydon.sms.domain.entity.SmsTemplate;
import com.roydon.sms.mapper.SmsTemplateMapper;
import com.roydon.sms.service.ISmsTemplateService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
     * 查询【请填写功能名称】
     *
     * @param templateId 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public SmsTemplate selectSmsTemplateByTemplateId(String templateId) {
        return smsTemplateMapper.selectSmsTemplateByTemplateId(templateId);
    }

    /**
     * 查询【请填写功能名称】列表
     *
     * @param smsTemplate 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<SmsTemplate> selectSmsTemplateList(SmsTemplate smsTemplate) {
        return smsTemplateMapper.selectSmsTemplateList(smsTemplate);
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param smsTemplate 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertSmsTemplate(SmsTemplate smsTemplate) {
        smsTemplate.setCreateTime(DateUtils.getNowDate());
        smsTemplate.setTemplateId(IdGenerator.generatorId());
        return smsTemplateMapper.insertSmsTemplate(smsTemplate);
    }

    /**
     * 修改【请填写功能名称】
     *
     * @param smsTemplate 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateSmsTemplate(SmsTemplate smsTemplate) {
        smsTemplate.setUpdateTime(DateUtils.getNowDate());
        return smsTemplateMapper.updateSmsTemplate(smsTemplate);
    }

    /**
     * 批量删除【请填写功能名称】
     *
     * @param templateIds 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteSmsTemplateByTemplateIds(String[] templateIds) {
        return smsTemplateMapper.deleteSmsTemplateByTemplateIds(templateIds);
    }

    /**
     * 删除【请填写功能名称】信息
     *
     * @param templateId 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteSmsTemplateByTemplateId(String templateId) {
        return smsTemplateMapper.deleteSmsTemplateByTemplateId(templateId);
    }
}
