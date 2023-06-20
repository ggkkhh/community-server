package com.roydon.sms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.roydon.common.utils.DateUtils;
import com.roydon.common.utils.uniqueid.IdGenerator;
import com.roydon.sms.domain.entity.SmsProvider;
import com.roydon.sms.mapper.SmsProviderMapper;
import com.roydon.sms.service.ISmsProviderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 系统短信服务供应商(SmsProvider)表服务实现类
 *
 * @author roydon
 * @since 2023-05-24 19:07:30
 */
@Service("smsProviderService")
public class SmsProviderServiceImpl extends ServiceImpl<SmsProviderMapper, SmsProvider> implements ISmsProviderService {

    @Resource
    private SmsProviderMapper smsProviderMapper;

    /**
     * 查询系统短信服务供应商
     *
     * @param providerId 系统短信服务供应商主键
     * @return 系统短信服务供应商
     */
    @Override
    public SmsProvider selectSmsProviderByProviderId(String providerId) {
        return smsProviderMapper.selectSmsProviderByProviderId(providerId);
    }

    /**
     * 查询系统短信服务供应商列表
     *
     * @param smsProvider 系统短信服务供应商
     * @return 系统短信服务供应商
     */
    @Override
    public List<SmsProvider> selectSmsProviderList(SmsProvider smsProvider) {
        return smsProviderMapper.selectSmsProviderList(smsProvider);
    }

    /**
     * 新增系统短信服务供应商
     *
     * @param smsProvider 系统短信服务供应商
     * @return 结果
     */
    @Override
    public int insertSmsProvider(SmsProvider smsProvider) {
        smsProvider.setCreateTime(DateUtils.getNowDate());
        smsProvider.setProviderId(IdGenerator.generatorId());
        return smsProviderMapper.insertSmsProvider(smsProvider);
    }

    /**
     * 修改系统短信服务供应商
     *
     * @param smsProvider 系统短信服务供应商
     * @return 结果
     */
    @Override
    public int updateSmsProvider(SmsProvider smsProvider) {
        smsProvider.setUpdateTime(DateUtils.getNowDate());
        return smsProviderMapper.updateSmsProvider(smsProvider);
    }

    /**
     * 批量删除系统短信服务供应商
     *
     * @param providerIds 需要删除的系统短信服务供应商主键
     * @return 结果
     */
    @Override
    public int deleteSmsProviderByProviderIds(String[] providerIds) {
        return smsProviderMapper.deleteSmsProviderByProviderIds(providerIds);
    }

    /**
     * 删除系统短信服务供应商信息
     *
     * @param providerId 系统短信服务供应商主键
     * @return 结果
     */
    @Override
    public int deleteSmsProviderByProviderId(String providerId) {
        return smsProviderMapper.deleteSmsProviderByProviderId(providerId);
    }

    /**
     * 短信余量自减1
     *
     * @param providerId 系统短信服务供应商主键
     */
    @Override
    public void decrementResidueCountByProviderId(String providerId) {
        SmsProvider smsProvider = getOne(new LambdaQueryWrapper<SmsProvider>().eq(SmsProvider::getProviderId, providerId));
        LambdaUpdateWrapper<SmsProvider> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SmsProvider::getProviderId, providerId).set(SmsProvider::getResidueCount, smsProvider.getResidueCount() - 1l);
        update(updateWrapper);
    }
}
