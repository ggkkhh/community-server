package com.roydon.unit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.roydon.unit.domain.entity.SysUnit;

/**
 * 单元表(SysUnit)表数据库访问层
 *
 * @author roydon
 * @since 2023-05-22 03:53:01
 */
public interface SysUnitMapper extends BaseMapper<SysUnit>{

    SysUnit selectUnitById(Long unitId);

    int selectNormalChildrenUnitById(Long unitId);

}

