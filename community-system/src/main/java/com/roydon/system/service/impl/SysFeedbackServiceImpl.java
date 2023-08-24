package com.roydon.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.roydon.common.exception.ServiceException;
import com.roydon.common.utils.DateUtils;
import com.roydon.common.utils.PhoneUtils;
import com.roydon.common.utils.SecurityUtils;
import com.roydon.system.domain.entity.SysFeedback;
import com.roydon.system.mapper.SysFeedbackMapper;
import com.roydon.system.service.ISysFeedbackService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 系统反馈Service业务层处理
 *
 * @author roydon
 * @date 2023-08-24
 */
@Service
public class SysFeedbackServiceImpl extends ServiceImpl<SysFeedbackMapper, SysFeedback> implements ISysFeedbackService {

    @Resource
    private SysFeedbackMapper sysFeedbackMapper;

    /**
     * 查询系统反馈
     *
     * @param feedbackId 系统反馈主键
     * @return 系统反馈
     */
    @Override
    public SysFeedback selectSysFeedbackByFeedbackId(Long feedbackId) {
        return sysFeedbackMapper.selectSysFeedbackByFeedbackId(feedbackId);
    }

    /**
     * 查询系统反馈列表
     *
     * @param sysFeedback 系统反馈
     * @return 系统反馈
     */
    @Override
    public List<SysFeedback> selectSysFeedbackList(SysFeedback sysFeedback) {
        return sysFeedbackMapper.selectSysFeedbackList(sysFeedback);
    }

    /**
     * 新增系统反馈
     *
     * @param sysFeedback 系统反馈
     * @return 结果
     */
    @Override
    public int insertSysFeedback(SysFeedback sysFeedback) {
        sysFeedback.setCreateTime(DateUtils.getNowDate());
        sysFeedback.setCreateBy(SecurityUtils.getUsername());
        if (!PhoneUtils.isMobile(sysFeedback.getTelephone())) {
            throw new ServiceException("手机号格式错误");
        }
        sysFeedback.setUserId(SecurityUtils.getUserId());
        sysFeedback.setUsername(SecurityUtils.getUsername());
        return sysFeedbackMapper.insertSysFeedback(sysFeedback);
    }

    /**
     * 修改系统反馈
     *
     * @param sysFeedback 系统反馈
     * @return 结果
     */
    @Override
    public int updateSysFeedback(SysFeedback sysFeedback) {
        sysFeedback.setUpdateTime(DateUtils.getNowDate());
        sysFeedback.setUpdateBy(SecurityUtils.getUsername());
        return sysFeedbackMapper.updateSysFeedback(sysFeedback);
    }

    /**
     * 批量删除系统反馈
     *
     * @param feedbackIds 需要删除的系统反馈主键
     * @return 结果
     */
    @Override
    public int deleteSysFeedbackByFeedbackIds(Long[] feedbackIds) {
        return sysFeedbackMapper.deleteSysFeedbackByFeedbackIds(feedbackIds);
    }

    /**
     * 删除系统反馈信息
     *
     * @param feedbackId 系统反馈主键
     * @return 结果
     */
    @Override
    public int deleteSysFeedbackByFeedbackId(Long feedbackId) {
        return sysFeedbackMapper.deleteSysFeedbackByFeedbackId(feedbackId);
    }
}
