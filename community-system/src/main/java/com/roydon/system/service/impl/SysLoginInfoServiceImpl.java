package com.roydon.system.service.impl;

import com.roydon.system.domain.SysLoginInfo;
import com.roydon.system.mapper.SysLoginInfoMapper;
import com.roydon.system.service.ISysLoginInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统访问日志情况信息 服务层处理
 *
 */
@Service
public class SysLoginInfoServiceImpl implements ISysLoginInfoService
{

    @Autowired
    private SysLoginInfoMapper loginInfoMapper;

    /**
     * 新增系统登录日志
     * 
     * @param logininfor 访问日志对象
     */
    @Override
    public void insertLogininfor(SysLoginInfo logininfor)
    {
        loginInfoMapper.insertLogininfor(logininfor);
    }

    /**
     * 查询系统登录日志集合
     * 
     * @param logininfor 访问日志对象
     * @return 登录记录集合
     */
    @Override
    public List<SysLoginInfo> selectLogininforList(SysLoginInfo logininfor)
    {
        return loginInfoMapper.selectLogininforList(logininfor);
    }

    /**
     * 批量删除系统登录日志
     * 
     * @param infoIds 需要删除的登录日志ID
     * @return 结果
     */
    @Override
    public int deleteLogininforByIds(Long[] infoIds)
    {
        return loginInfoMapper.deleteLogininforByIds(infoIds);
    }

    /**
     * 清空系统登录日志
     */
    @Override
    public void cleanLogininfor()
    {
        loginInfoMapper.cleanLogininfor();
    }
}
