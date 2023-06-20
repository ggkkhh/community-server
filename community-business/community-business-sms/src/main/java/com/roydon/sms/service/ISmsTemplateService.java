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
     * 查询短信模板
     *
     * @param templateId 短信模板主键
     * @return 短信模板
     */
    public SmsTemplate selectSmsTemplateByTemplateId(String templateId);

    /**
     * 查询短信模板列表
     *
     * @param smsTemplate 短信模板
     * @return 短信模板集合
     */
    public List<SmsTemplate> selectSmsTemplateList(SmsTemplate smsTemplate);

    /**
     * 新增短信模板
     *
     * @param smsTemplate 短信模板
     * @return 结果
     */
    public int insertSmsTemplate(SmsTemplate smsTemplate);

    /**
     * 修改短信模板
     *
     * @param smsTemplate 短信模板
     * @return 结果
     */
    public int updateSmsTemplate(SmsTemplate smsTemplate);

    /**
     * 批量删除短信模板
     *
     * @param templateIds 需要删除的短信模板主键集合
     * @return 结果
     */
    public int deleteSmsTemplateByTemplateIds(String[] templateIds);

    /**
     * 删除短信模板信息
     *
     * @param templateId 短信模板主键
     * @return 结果
     */
    public int deleteSmsTemplateByTemplateId(String templateId);

    /**
     * 短信模板状态修改
     *
     * @param smsTemplate 短信模板
     * @return true
     */
    boolean changeTemplateStatus(SmsTemplate smsTemplate);
}
