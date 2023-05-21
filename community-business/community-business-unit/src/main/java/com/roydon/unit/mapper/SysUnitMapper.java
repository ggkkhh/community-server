package com.roydon.unit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.roydon.unit.domain.entity.SysUnit;
import org.apache.ibatis.annotations.Mapper;

/**
 * 单元表(SysUnit)表数据库访问层
 *
 * @author roydon
 * @since 2023-05-22 03:53:01
 */
@Mapper
public interface SysUnitMapper extends BaseMapper<SysUnit>{

}

