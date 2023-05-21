package com.roydon.unit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.roydon.common.utils.StringUtil;
import com.roydon.unit.domain.entity.SysUnit;
import com.roydon.unit.mapper.SysUnitMapper;
import com.roydon.unit.service.ISysUnitService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 部门表(SysUnit)表服务实现类
 *
 * @author roydon
 * @since 2023-05-22 03:53:04
 */
@Service
public class SysUnitServiceImpl extends ServiceImpl<SysUnitMapper, SysUnit> implements ISysUnitService {

    @Override
    public List<SysUnit> selectUnitList(SysUnit unit) {
        LambdaQueryWrapper<SysUnit> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtil.isNotEmpty(unit.getUnitId()), SysUnit::getUnitId, unit.getUnitId())
                .eq(StringUtil.isNotEmpty(unit.getParentId()), SysUnit::getParentId, unit.getParentId())
                .eq(StringUtil.isNotEmpty(unit.getUnitName()), SysUnit::getUnitName, unit.getUnitName())
                .eq(StringUtil.isNotEmpty(unit.getStatus()), SysUnit::getStatus, unit.getStatus())
                .eq(SysUnit::getDelFlag, "0")
                .orderByAsc(SysUnit::getParentId, SysUnit::getOrderNum);
        return list(queryWrapper);
    }
}
