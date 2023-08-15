package com.roydon.business.epidemic.service.impl;

import com.aliyun.oss.ServiceException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.roydon.app.domain.entity.AppMessage;
import com.roydon.app.enums.MessageStatusEnum;
import com.roydon.app.service.IAppMessageService;
import com.roydon.business.epidemic.domain.entity.EpidemicInoculationAudit;
import com.roydon.business.epidemic.domain.entity.EpidemicInoculationHistory;
import com.roydon.business.epidemic.enums.AuditStatusEnum;
import com.roydon.business.epidemic.mapper.EpidemicInoculationAuditMapper;
import com.roydon.business.epidemic.service.IEpidemicInoculationAuditService;
import com.roydon.business.epidemic.service.IEpidemicInoculationHistoryService;
import com.roydon.common.utils.DateUtils;
import com.roydon.common.utils.SecurityUtils;
import com.roydon.common.utils.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 疫苗接种审核Service业务层处理
 *
 * @author roydon
 * @date 2023-08-10
 */
@Service
public class EpidemicInoculationAuditServiceImpl extends ServiceImpl<EpidemicInoculationAuditMapper, EpidemicInoculationAudit> implements IEpidemicInoculationAuditService {

    @Resource
    private EpidemicInoculationAuditMapper epidemicInoculationAuditMapper;

    @Resource
    private IEpidemicInoculationHistoryService epidemicInoculationHistoryService;

    @Resource
    private IAppMessageService appMessageService;

    /**
     * 查询疫苗接种审核
     *
     * @param auditId 疫苗接种审核主键
     * @return 疫苗接种审核
     */
    @Override
    public EpidemicInoculationAudit selectEpidemicInoculationAuditByAuditId(Long auditId) {
        return epidemicInoculationAuditMapper.selectEpidemicInoculationAuditByAuditId(auditId);
    }

    /**
     * 查询疫苗接种审核列表
     *
     * @param epidemicInoculationAudit 疫苗接种审核
     * @return 疫苗接种审核
     */
    @Override
    public List<EpidemicInoculationAudit> selectEpidemicInoculationAuditList(EpidemicInoculationAudit epidemicInoculationAudit) {
        return epidemicInoculationAuditMapper.selectEpidemicInoculationAuditList(epidemicInoculationAudit);
    }

    /**
     * 新增疫苗接种审核
     *
     * @param epidemicInoculationAudit 疫苗接种审核
     * @return 结果
     */
    @Override
    public int insertEpidemicInoculationAudit(EpidemicInoculationAudit epidemicInoculationAudit) {
        EpidemicInoculationAudit one = getOne(new LambdaQueryWrapper<EpidemicInoculationAudit>().eq(EpidemicInoculationAudit::getUserId, SecurityUtils.getUserId()));
        if (StringUtil.isNotEmpty(one)) {
            // 已上报
            throw new ServiceException("请勿重复上报");
        }
        epidemicInoculationAudit.setCreateTime(DateUtils.getNowDate());
        epidemicInoculationAudit.setCreateBy(SecurityUtils.getUsername());
        epidemicInoculationAudit.setUserId(SecurityUtils.getUserId());
        epidemicInoculationAudit.setUsername(SecurityUtils.getUsername());
        return epidemicInoculationAuditMapper.insertEpidemicInoculationAudit(epidemicInoculationAudit);
    }

    /**
     * 修改疫苗接种审核
     *
     * @param epidemicInoculationAudit 疫苗接种审核
     * @return 结果
     */
    @Transactional
    @Override
    public int updateEpidemicInoculationAudit(EpidemicInoculationAudit epidemicInoculationAudit) {
        String loginUsername = SecurityUtils.getUsername();
        epidemicInoculationAudit.setUpdateTime(DateUtils.getNowDate());
        epidemicInoculationAudit.setUpdateBy(loginUsername);
        int i = epidemicInoculationAuditMapper.updateEpidemicInoculationAudit(epidemicInoculationAudit);
        // 如果审核通过
        if (epidemicInoculationAudit.getAuditStatus().equals(AuditStatusEnum.PASS.getCode())) {
            // 添加到疫苗接种记录表
            try {
                EpidemicInoculationHistory epidemicInoculationHistory = new EpidemicInoculationHistory();
                epidemicInoculationHistory.setUserId(epidemicInoculationAudit.getUserId());
                epidemicInoculationHistory.setUsername(epidemicInoculationAudit.getUsername());
                epidemicInoculationHistory.setRealName(epidemicInoculationAudit.getRealName());
                epidemicInoculationHistory.setTelephone(epidemicInoculationAudit.getTelephone());
                epidemicInoculationHistory.setIdCard(epidemicInoculationAudit.getIdCard());
                epidemicInoculationHistory.setCreateTime(new Date());
                epidemicInoculationHistory.setCreateBy(loginUsername);
                epidemicInoculationHistoryService.save(epidemicInoculationHistory);
            } catch (Exception e) {
                throw new ServiceException("系统调用失败，请联系管理员");
            }
            // 添加一条消息【接种记录审核成功】
            AppMessage appMessage = new AppMessage();
            appMessage.setUserId(epidemicInoculationAudit.getUserId());
            appMessage.setMessageContent("您的疫苗接种记录审核已通过");
            appMessage.setMessageStatus(MessageStatusEnum.NOT_READ.getCode());
            appMessage.setCreateTime(new Date());
            appMessage.setCreateBy(loginUsername);
            appMessageService.save(appMessage);
        }
        if (epidemicInoculationAudit.getAuditStatus().equals(AuditStatusEnum.NOT_PASS.getCode())) {
            // 审核未通过添加一条消息【接种记录审核未成功】
            AppMessage appMessage = new AppMessage();
            appMessage.setUserId(epidemicInoculationAudit.getUserId());
            appMessage.setMessageContent("您的疫苗接种记录审核未通过");
            appMessage.setMessageStatus(MessageStatusEnum.NOT_READ.getCode());
            appMessage.setCreateTime(new Date());
            appMessage.setCreateBy(loginUsername);
            appMessageService.save(appMessage);
        }
        return i;
    }

    /**
     * 批量删除疫苗接种审核
     *
     * @param auditIds 需要删除的疫苗接种审核主键
     * @return 结果
     */
    @Override
    public int deleteEpidemicInoculationAuditByAuditIds(Long[] auditIds) {
        return epidemicInoculationAuditMapper.deleteEpidemicInoculationAuditByAuditIds(auditIds);
    }

    /**
     * 删除疫苗接种审核信息
     *
     * @param auditId 疫苗接种审核主键
     * @return 结果
     */
    @Override
    public int deleteEpidemicInoculationAuditByAuditId(Long auditId) {
        return epidemicInoculationAuditMapper.deleteEpidemicInoculationAuditByAuditId(auditId);
    }
}
