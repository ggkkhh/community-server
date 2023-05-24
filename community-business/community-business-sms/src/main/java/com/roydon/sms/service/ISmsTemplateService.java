package com.roydon.sms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.roydon.sms.domain.entity.SmsTemplate;

import java.util.List;

/**
 * (SmsTemplate)表服务接口
 *
 * @author roydon
 * @since 2023-05-24 19:08:58
 */
public interface ISmsTemplateService extends IService<SmsTemplate> {
    /**
     * 查询【请填写功能名称】
     *
     * @param templateId 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public SmsTemplate selectSmsTemplateByTemplateId(String templateId);

    /**
     * 查询【请填写功能名称】列表
     *
     * @param smsTemplate 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<SmsTemplate> selectSmsTemplateList(SmsTemplate smsTemplate);

    /**
     * 新增【请填写功能名称】
     *
     * @param smsTemplate 【请填写功能名称】
     * @return 结果
     */
    public int insertSmsTemplate(SmsTemplate smsTemplate);

    /**
     * 修改【请填写功能名称】
     *
     * @param smsTemplate 【请填写功能名称】
     * @return 结果
     */
    public int updateSmsTemplate(SmsTemplate smsTemplate);

    /**
     * 批量删除【请填写功能名称】
     *
     * @param templateIds 需要删除的【请填写功能名称】主键集合
     * @return 结果
     */
    public int deleteSmsTemplateByTemplateIds(String[] templateIds);

    /**
     * 删除【请填写功能名称】信息
     *
     * @param templateId 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteSmsTemplateByTemplateId(String templateId);
}
