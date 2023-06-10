package com.roydon.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.roydon.common.core.domain.entity.SysDept;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 社区管理 数据层
 */
public interface SysDeptMapper extends BaseMapper<SysDept> {
    /**
     * 查询单元管理数据
     *
     * @param dept 单元信息
     * @return 单元信息集合
     */
    public List<SysDept> selectDeptList(SysDept dept);

    /**
     * 根据角色ID查询单元树信息
     *
     * @param roleId            角色ID
     * @param deptCheckStrictly 单元树选择项是否关联显示
     * @return 选中单元列表
     */
    public List<Long> selectDeptListByRoleId(@Param("roleId") Long roleId, @Param("deptCheckStrictly") boolean deptCheckStrictly);

    /**
     * 根据单元ID查询信息
     *
     * @param deptId 单元ID
     * @return 单元信息
     */
    public SysDept selectDeptById(Long deptId);

    /**
     * 根据ID查询所有子单元
     *
     * @param deptId 单元ID
     * @return 单元列表
     */
    public List<SysDept> selectChildrenDeptById(Long deptId);

    /**
     * 根据ID查询所有子单元（正常状态）
     *
     * @param deptId 单元ID
     * @return 子单元数
     */
    public int selectNormalChildrenDeptById(Long deptId);

    /**
     * 是否存在子节点
     *
     * @param deptId 单元ID
     * @return 结果
     */
    public int hasChildByDeptId(Long deptId);

    /**
     * 查询单元是否存在用户
     *
     * @param deptId 单元ID
     * @return 结果
     */
    public int checkDeptExistUser(Long deptId);

    /**
     * 校验单元名称是否唯一
     *
     * @param deptName 单元名称
     * @param parentId 父单元ID
     * @return 结果
     */
    public SysDept checkDeptNameUnique(@Param("deptName") String deptName, @Param("parentId") Long parentId);

    /**
     * 新增单元信息
     *
     * @param dept 单元信息
     * @return 结果
     */
    public int insertDept(SysDept dept);

    /**
     * 修改单元信息
     *
     * @param dept 单元信息
     * @return 结果
     */
    public int updateDept(SysDept dept);

    /**
     * 修改所在单元正常状态
     *
     * @param deptIds 单元ID组
     */
    public void updateDeptStatusNormal(Long[] deptIds);

    /**
     * 修改子元素关系
     *
     * @param depts 子元素
     * @return 结果
     */
    public int updateDeptChildren(@Param("depts") List<SysDept> depts);

    /**
     * 删除单元管理信息
     *
     * @param deptId 单元ID
     * @return 结果
     */
    public int deleteDeptById(Long deptId);

    /**
     * 获取系统社区数量
     *
     * @return Integer
     */
    public Integer getSysHouseAmount();
}
