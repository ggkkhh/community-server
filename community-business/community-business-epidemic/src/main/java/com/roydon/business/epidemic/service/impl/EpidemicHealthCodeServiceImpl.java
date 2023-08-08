package com.roydon.business.epidemic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.roydon.business.epidemic.domain.entity.EpidemicHealthCode;
import com.roydon.business.epidemic.mapper.EpidemicHealthCodeMapper;
import com.roydon.business.epidemic.service.IEpidemicHealthCodeService;
import com.roydon.common.core.domain.entity.SysUser;
import com.roydon.common.utils.DateUtils;
import com.roydon.common.utils.StringUtils;
import com.roydon.qrcode.enums.ColorEnum;
import com.roydon.qrcode.util.QRCodeUtils;
import com.roydon.system.service.ISysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 居民健康码Service业务层处理
 *
 * @author roydon
 * @date 2023-08-08
 */
@Service
public class EpidemicHealthCodeServiceImpl extends ServiceImpl<EpidemicHealthCodeMapper, EpidemicHealthCode> implements IEpidemicHealthCodeService {

    @Resource
    private EpidemicHealthCodeMapper epidemicHealthCodeMapper;

    @Resource
    private ISysUserService userService;

    @Override
    public EpidemicHealthCode getHealthCodeByUser(SysUser sysUser) {
        // 先查询是否有此用户的健康码
        LambdaQueryWrapper<EpidemicHealthCode> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(EpidemicHealthCode::getUserId, sysUser.getUserId());
        EpidemicHealthCode one = getOne(queryWrapper);
        if (StringUtils.isNotNull(one)) {
            // 数据表存在该用户健康码
            return one;
        } else {
            // 数据表[不存在]该用户健康码，生成健康码
            return genHealthCode(sysUser);
        }
    }

    /**
     * 生成健康码
     *
     * @return base64 string
     */
    private EpidemicHealthCode genHealthCode(SysUser sysUser) {
        EpidemicHealthCode epidemicHealthCode = new EpidemicHealthCode();
        // 使用用户名userName生成绿码base64
        String base64QRCode = QRCodeUtils.getBase64QRCode(sysUser.getUserName(), ColorEnum.GREEN);
        epidemicHealthCode.setUserId(sysUser.getUserId());
        epidemicHealthCode.setUserName(sysUser.getUserName());
        epidemicHealthCode.setCodeBase64(base64QRCode);
        epidemicHealthCode.setCodeImage(null);
        epidemicHealthCode.setCodeStatus("0"); // 绿码
        epidemicHealthCode.setCreateBy(sysUser.getUserName());
        epidemicHealthCode.setCreateTime(new Date());
        epidemicHealthCode.setUpdateTime(new Date());
        save(epidemicHealthCode);
        return epidemicHealthCode;


    }

    /**
     * 查询居民健康码
     *
     * @param codeId 居民健康码主键
     * @return 居民健康码
     */
    @Override
    public EpidemicHealthCode selectEpidemicHealthCodeByCodeId(Long codeId) {
        return epidemicHealthCodeMapper.selectEpidemicHealthCodeByCodeId(codeId);
    }

    /**
     * 查询居民健康码列表
     *
     * @param epidemicHealthCode 居民健康码
     * @return 居民健康码
     */
    @Override
    public List<EpidemicHealthCode> selectEpidemicHealthCodeList(EpidemicHealthCode epidemicHealthCode) {
        return epidemicHealthCodeMapper.selectEpidemicHealthCodeList(epidemicHealthCode);
    }

    /**
     * 新增居民健康码
     *
     * @param epidemicHealthCode 居民健康码
     * @return 结果
     */
    @Override
    public int insertEpidemicHealthCode(EpidemicHealthCode epidemicHealthCode) {
        epidemicHealthCode.setCreateTime(DateUtils.getNowDate());
        return epidemicHealthCodeMapper.insertEpidemicHealthCode(epidemicHealthCode);
    }

    /**
     * 修改居民健康码
     *
     * @param epidemicHealthCode 居民健康码
     * @return 结果
     */
    @Override
    public int updateEpidemicHealthCode(EpidemicHealthCode epidemicHealthCode) {
        epidemicHealthCode.setUpdateTime(DateUtils.getNowDate());
        return epidemicHealthCodeMapper.updateEpidemicHealthCode(epidemicHealthCode);
    }

    /**
     * 批量删除居民健康码
     *
     * @param codeIds 需要删除的居民健康码主键
     * @return 结果
     */
    @Override
    public int deleteEpidemicHealthCodeByCodeIds(Long[] codeIds) {
        return epidemicHealthCodeMapper.deleteEpidemicHealthCodeByCodeIds(codeIds);
    }

    /**
     * 删除居民健康码信息
     *
     * @param codeId 居民健康码主键
     * @return 结果
     */
    @Override
    public int deleteEpidemicHealthCodeByCodeId(Long codeId) {
        return epidemicHealthCodeMapper.deleteEpidemicHealthCodeByCodeId(codeId);
    }
}
