package com.roydon.unit.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.roydon.unit.domain.entity.SysUnit;

import java.util.List;

/**
 * 部门表(SysUnit)表服务接口
 *
 * @author roydon
 * @since 2023-05-22 03:53:03
 */
public interface ISysUnitService extends IService<SysUnit> {

    List<SysUnit> selectUnitList(SysUnit unit);

}
