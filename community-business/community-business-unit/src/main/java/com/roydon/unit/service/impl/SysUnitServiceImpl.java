package com.roydon.unit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.roydon.common.constant.UserConstants;
import com.roydon.common.core.domain.entity.SysDept;
import com.roydon.common.core.domain.entity.SysUser;
import com.roydon.common.exception.ServiceException;
import com.roydon.common.utils.SecurityUtils;
import com.roydon.common.utils.StringUtil;
import com.roydon.common.utils.StringUtils;
import com.roydon.common.utils.spring.SpringUtils;
import com.roydon.unit.domain.entity.SysUnit;
import com.roydon.unit.mapper.SysUnitMapper;
import com.roydon.unit.service.ISysUnitService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 单元表(SysUnit)表服务实现类
 *
 * @author roydon
 * @since 2023-05-22 03:53:04
 */
@Service
public class SysUnitServiceImpl extends ServiceImpl<SysUnitMapper, SysUnit> implements ISysUnitService {

    @Resource
    private SysUnitMapper sysUnitMapper;

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

    @Override
    public void checkUnitDataScope(Long unitId) {
        if (!SysUser.isAdmin(SecurityUtils.getUserId())) {
            SysUnit unit = new SysUnit();
            unit.setUnitId(unitId);
            List<SysUnit> unitList = SpringUtils.getAopProxy(this).selectUnitList(unit);
            if (StringUtils.isEmpty(unitList)) {
                throw new ServiceException("没有权限访问数据！");
            }
        }
    }

    @Override
    public SysUnit selectUnitById(Long unitId) {
        return sysUnitMapper.selectUnitById(unitId);
    }

    @Override
    public String checkUnitNameUnique(SysUnit unit) {
        Long unitId = StringUtils.isNull(unit.getUnitId()) ? -1L : unit.getUnitId();
        LambdaQueryWrapper<SysUnit> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUnit::getUnitName, unit.getUnitName()).eq(SysUnit::getParentId, unit.getParentId()).eq(SysUnit::getDelFlag, "0");
        SysUnit one = getOne(queryWrapper);
        if (StringUtils.isNotNull(one) && one.getUnitId().longValue() != unitId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public boolean insertUnit(SysUnit unit) {
        SysUnit info = sysUnitMapper.selectUnitById(unit.getParentId());
        // 如果父节点被禁用,则不允许新增子节点
        if (!UserConstants.DEPT_NORMAL.equals(info.getStatus())) {
            throw new ServiceException("单元被停用，不允许新增");
        }
        unit.setAncestors(info.getAncestors() + "," + unit.getParentId());
        return save(unit);
    }

    @Override
    public int selectNormalChildrenUnitById(Long deptId) {
        return sysUnitMapper.selectNormalChildrenUnitById(deptId);
    }

    @Override
    public boolean hasChildByUnitId(Long deptId) {
        LambdaQueryWrapper<SysUnit> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUnit::getParentId, deptId).eq(SysUnit::getDelFlag, "0");
        return StringUtil.isNotEmpty(getOne(queryWrapper)) ? true : false;
    }
}
