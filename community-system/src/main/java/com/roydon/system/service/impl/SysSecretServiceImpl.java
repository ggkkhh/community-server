package com.roydon.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.roydon.common.utils.DateUtils;
import com.roydon.system.domain.SysSecret;
import com.roydon.system.mapper.SysSecretMapper;
import com.roydon.system.service.ISysSecretService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 密钥Service业务层处理
 *
 * @author roydon
 * @date 2023-08-03
 */
@Service
public class SysSecretServiceImpl extends ServiceImpl<SysSecretMapper, SysSecret> implements ISysSecretService {

    @Resource
    private SysSecretMapper sysSecretMapper;

    /**
     * 查询密钥
     *
     * @param secretId 密钥主键
     * @return 密钥
     */
    @Override
    public SysSecret selectSysSecretBySecretId(Long secretId) {
        return sysSecretMapper.selectSysSecretBySecretId(secretId);
    }

    /**
     * 查询密钥列表
     *
     * @param sysSecret 密钥
     * @return 密钥
     */
    @Override
    public List<SysSecret> selectSysSecretList(SysSecret sysSecret) {
        return sysSecretMapper.selectSysSecretList(sysSecret);
    }

    /**
     * 新增密钥
     *
     * @param sysSecret 密钥
     * @return 结果
     */
    @Override
    public int insertSysSecret(SysSecret sysSecret) {
        sysSecret.setCreateTime(DateUtils.getNowDate());
        return sysSecretMapper.insertSysSecret(sysSecret);
    }

    /**
     * 修改密钥
     *
     * @param sysSecret 密钥
     * @return 结果
     */
    @Override
    public int updateSysSecret(SysSecret sysSecret) {
        sysSecret.setUpdateTime(DateUtils.getNowDate());
        return sysSecretMapper.updateSysSecret(sysSecret);
    }

    /**
     * 批量删除密钥
     *
     * @param secretIds 需要删除的密钥主键
     * @return 结果
     */
    @Override
    public int deleteSysSecretBySecretIds(Long[] secretIds) {
        return sysSecretMapper.deleteSysSecretBySecretIds(secretIds);
    }

    /**
     * 删除密钥信息
     *
     * @param secretId 密钥主键
     * @return 结果
     */
    @Override
    public int deleteSysSecretBySecretId(Long secretId) {
        return sysSecretMapper.deleteSysSecretBySecretId(secretId);
    }

    @Override
    public SysSecret selectOneBySecretKey(String secretKey) {
        LambdaQueryWrapper<SysSecret> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysSecret::getSecretKey, secretKey);
        return getOne(queryWrapper);
    }
}
