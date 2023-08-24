package com.roydon.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.roydon.system.domain.entity.SysFeedback;

import java.util.List;

/**
 * 系统反馈Mapper接口
 *
 * @author roydon
 * @date 2023-08-24
 */
public interface SysFeedbackMapper extends BaseMapper<SysFeedback> {

    /**
     * 查询系统反馈
     *
     * @param feedbackId 系统反馈主键
     * @return 系统反馈
     */
    SysFeedback selectSysFeedbackByFeedbackId(Long feedbackId);

    /**
     * 查询系统反馈列表
     *
     * @param sysFeedback 系统反馈
     * @return 系统反馈集合
     */
    List<SysFeedback> selectSysFeedbackList(SysFeedback sysFeedback);

    /**
     * 新增系统反馈
     *
     * @param sysFeedback 系统反馈
     * @return 结果
     */
    int insertSysFeedback(SysFeedback sysFeedback);

    /**
     * 修改系统反馈
     *
     * @param sysFeedback 系统反馈
     * @return 结果
     */
    int updateSysFeedback(SysFeedback sysFeedback);

    /**
     * 删除系统反馈
     *
     * @param feedbackId 系统反馈主键
     * @return 结果
     */
    int deleteSysFeedbackByFeedbackId(Long feedbackId);

    /**
     * 批量删除系统反馈
     *
     * @param feedbackIds 需要删除的数据主键集合
     * @return 结果
     */
    int deleteSysFeedbackByFeedbackIds(Long[] feedbackIds);
}
