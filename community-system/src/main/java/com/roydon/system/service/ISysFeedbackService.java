package com.roydon.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.roydon.system.domain.entity.SysFeedback;

import java.util.List;

/**
 * 系统反馈Service接口
 *
 * @author roydon
 * @date 2023-08-24
 */
public interface ISysFeedbackService extends IService<SysFeedback> {
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
     * 批量删除系统反馈
     *
     * @param feedbackIds 需要删除的系统反馈主键集合
     * @return 结果
     */
    int deleteSysFeedbackByFeedbackIds(Long[] feedbackIds);

    /**
     * 删除系统反馈信息
     *
     * @param feedbackId 系统反馈主键
     * @return 结果
     */
    int deleteSysFeedbackByFeedbackId(Long feedbackId);
}
