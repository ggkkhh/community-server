package com.roydon.sms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.roydon.sms.domain.entity.SmsProvider;

import java.util.List;

/**
 * 系统短信服务供应商(SmsProvider)表数据库访问层
 *
 * @author roydon
 * @since 2023-05-24 19:07:28
 */
public interface SmsProviderMapper extends BaseMapper<SmsProvider>{
    /**
     * 查询系统短信服务供应商
     *
     * @param providerId 系统短信服务供应商主键
     * @return 系统短信服务供应商
     */
    public SmsProvider selectSmsProviderByProviderId(String providerId);

    /**
     * 查询系统短信服务供应商列表
     *
     * @param smsProvider 系统短信服务供应商
     * @return 系统短信服务供应商集合
     */
    public List<SmsProvider> selectSmsProviderList(SmsProvider smsProvider);

    /**
     * 新增系统短信服务供应商
     *
     * @param smsProvider 系统短信服务供应商
     * @return 结果
     */
    public int insertSmsProvider(SmsProvider smsProvider);

    /**
     * 修改系统短信服务供应商
     *
     * @param smsProvider 系统短信服务供应商
     * @return 结果
     */
    public int updateSmsProvider(SmsProvider smsProvider);

    /**
     * 删除系统短信服务供应商
     *
     * @param providerId 系统短信服务供应商主键
     * @return 结果
     */
    public int deleteSmsProviderByProviderId(String providerId);

    /**
     * 批量删除系统短信服务供应商
     *
     * @param providerIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSmsProviderByProviderIds(String[] providerIds);
}
