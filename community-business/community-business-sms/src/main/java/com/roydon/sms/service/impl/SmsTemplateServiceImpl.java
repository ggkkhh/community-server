package com.roydon.sms.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
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
     * 查询短信模板
     *
     * @param templateId 短信模板主键
     * @return 短信模板
     */
    @Override
    public SmsTemplate selectSmsTemplateByTemplateId(String templateId) {
        return smsTemplateMapper.selectSmsTemplateByTemplateId(templateId);
    }

    /**
     * 查询短信模板列表
     *
     * @param smsTemplate 短信模板
     * @return 短信模板
     */
    @Override
    public List<SmsTemplate> selectSmsTemplateList(SmsTemplate smsTemplate) {
        return smsTemplateMapper.selectSmsTemplateList(smsTemplate);
    }

    /**
     * 新增短信模板
     *
     * @param smsTemplate 短信模板
     * @return 结果
     */
    @Override
    public int insertSmsTemplate(SmsTemplate smsTemplate) {
        smsTemplate.setCreateTime(DateUtils.getNowDate());
        smsTemplate.setTemplateId(IdGenerator.generatorId());
        return smsTemplateMapper.insertSmsTemplate(smsTemplate);
    }

    /**
     * 修改短信模板
     *
     * @param smsTemplate 短信模板
     * @return 结果
     */
    @Override
    public int updateSmsTemplate(SmsTemplate smsTemplate) {
        smsTemplate.setUpdateTime(DateUtils.getNowDate());
        return smsTemplateMapper.updateSmsTemplate(smsTemplate);
    }

    /**
     * 批量删除短信模板
     *
     * @param templateIds 需要删除的短信模板主键
     * @return 结果
     */
    @Override
    public int deleteSmsTemplateByTemplateIds(String[] templateIds) {
        return smsTemplateMapper.deleteSmsTemplateByTemplateIds(templateIds);
    }

    /**
     * 删除短信模板信息
     *
     * @param templateId 短信模板主键
     * @return 结果
     */
    @Override
    public int deleteSmsTemplateByTemplateId(String templateId) {
        return smsTemplateMapper.deleteSmsTemplateByTemplateId(templateId);
    }

    /**
     * 短信模板状态修改
     *
     * @param smsTemplate 短信模板
     * @return true
     */
    @Override
    public boolean changeTemplateStatus(SmsTemplate smsTemplate) {
        LambdaUpdateWrapper<SmsTemplate> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SmsTemplate::getTemplateId, smsTemplate.getTemplateId()).set(SmsTemplate::getStatus, smsTemplate.getStatus());
        return update(updateWrapper);

    }
}
