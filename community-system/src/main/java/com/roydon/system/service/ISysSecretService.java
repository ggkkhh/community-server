package com.roydon.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.roydon.system.domain.SysSecret;

import java.util.List;

/**
 * 密钥Service接口
 *
 * @author roydon
 * @date 2023-08-03
 */
public interface ISysSecretService extends IService<SysSecret> {
    /**
     * 查询密钥
     *
     * @param secretId 密钥主键
     * @return 密钥
     */
    SysSecret selectSysSecretBySecretId(Long secretId);

    /**
     * 查询密钥列表
     *
     * @param sysSecret 密钥
     * @return 密钥集合
     */
    List<SysSecret> selectSysSecretList(SysSecret sysSecret);

    /**
     * 新增密钥
     *
     * @param sysSecret 密钥
     * @return 结果
     */
    int insertSysSecret(SysSecret sysSecret);

    /**
     * 修改密钥
     *
     * @param sysSecret 密钥
     * @return 结果
     */
    int updateSysSecret(SysSecret sysSecret);

    /**
     * 批量删除密钥
     *
     * @param secretIds 需要删除的密钥主键集合
     * @return 结果
     */
    int deleteSysSecretBySecretIds(Long[] secretIds);

    /**
     * 删除密钥信息
     *
     * @param secretId 密钥主键
     * @return 结果
     */
    int deleteSysSecretBySecretId(Long secretId);

    /**
     * 通过关键字检索一条
     *
     * @param secretKey
     * @return
     */
    SysSecret selectOneBySecretKey(String secretKey);

}
