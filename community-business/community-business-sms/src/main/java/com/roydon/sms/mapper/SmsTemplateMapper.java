package com.roydon.sms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.roydon.sms.domain.entity.SmsTemplate;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * (SmsTemplate)表数据库访问层
 *
 * @author roydon
 * @since 2023-05-24 19:08:58
 */
public interface SmsTemplateMapper extends BaseMapper<SmsTemplate>{

    /**
     * 通过ID查询单条数据
     *
     * @param templateId 主键
     * @return 实例对象
     */
    SmsTemplate queryById(String templateId);

    /**
     * 查询指定行数据
     *
     * @param smsTemplate 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<SmsTemplate> queryAllByLimit(SmsTemplate smsTemplate, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param smsTemplate 查询条件
     * @return 总行数
     */
    long count(SmsTemplate smsTemplate);

    /**
     * 新增数据
     *
     * @param smsTemplate 实例对象
     * @return 影响行数
     */
    int insert(SmsTemplate smsTemplate);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<SmsTemplate> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<SmsTemplate> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<SmsTemplate> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<SmsTemplate> entities);

    /**
     * 修改数据
     *
     * @param smsTemplate 实例对象
     * @return 影响行数
     */
    int update(SmsTemplate smsTemplate);

    /**
     * 通过主键删除数据
     *
     * @param templateId 主键
     * @return 影响行数
     */
    int deleteById(String templateId);

}

