package com.roydon.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.roydon.system.domain.SysLoginInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 系统访问日志情况信息 数据层
 */
@Mapper
public interface SysLoginInfoMapper extends BaseMapper<SysLoginInfo> {
    /**
     * 新增系统登录日志
     *
     * @param logininfor 访问日志对象
     */
    public void insertLoginInfo(SysLoginInfo logininfor);

    /**
     * 查询系统登录日志集合
     *
     * @param logininfor 访问日志对象
     * @return 登录记录集合
     */
    public List<SysLoginInfo> selectLoginInfoList(SysLoginInfo logininfor);

    /**
     * 批量删除系统登录日志
     *
     * @param infoIds 需要删除的登录日志ID
     * @return 结果
     */
    public int deleteLoginInfoByIds(Long[] infoIds);

    /**
     * 清空系统登录日志
     *
     * @return 结果
     */
    public int cleanLoginInfo();
}
