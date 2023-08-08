package com.roydon.business.epidemic.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.roydon.business.epidemic.domain.entity.EpidemicHealthCode;
import com.roydon.common.core.domain.entity.SysUser;

import java.util.List;

/**
 * 居民健康码Service接口
 *
 * @author roydon
 * @date 2023-08-08
 */
public interface IEpidemicHealthCodeService extends IService<EpidemicHealthCode> {

    /**
     * 根据userId获取健康码
     *
     * @param sysUser 用户
     * @return 居民健康码
     */
    EpidemicHealthCode getHealthCodeByUser(SysUser sysUser);

    /**
     * 查询居民健康码
     *
     * @param codeId 居民健康码主键
     * @return 居民健康码
     */
    EpidemicHealthCode selectEpidemicHealthCodeByCodeId(Long codeId);

    /**
     * 查询居民健康码列表
     *
     * @param epidemicHealthCode 居民健康码
     * @return 居民健康码集合
     */
    List<EpidemicHealthCode> selectEpidemicHealthCodeList(EpidemicHealthCode epidemicHealthCode);

    /**
     * 新增居民健康码
     *
     * @param epidemicHealthCode 居民健康码
     * @return 结果
     */
    int insertEpidemicHealthCode(EpidemicHealthCode epidemicHealthCode);

    /**
     * 修改居民健康码
     *
     * @param epidemicHealthCode 居民健康码
     * @return 结果
     */
    int updateEpidemicHealthCode(EpidemicHealthCode epidemicHealthCode);

    /**
     * 批量删除居民健康码
     *
     * @param codeIds 需要删除的居民健康码主键集合
     * @return 结果
     */
    int deleteEpidemicHealthCodeByCodeIds(Long[] codeIds);

    /**
     * 删除居民健康码信息
     *
     * @param codeId 居民健康码主键
     * @return 结果
     */
    int deleteEpidemicHealthCodeByCodeId(Long codeId);
}
