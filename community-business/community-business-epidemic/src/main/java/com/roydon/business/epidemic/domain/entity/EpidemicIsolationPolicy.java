package com.roydon.business.epidemic.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.roydon.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 隔离政策对象 epidemic_isolation_policy
 *
 * @author roydon
 * @date 2023-08-21
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("epidemic_isolation_policy")
public class EpidemicIsolationPolicy extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId("policy_id")
    private Long policyId;

    /**
     * 隔离天数
     */
    private Long isolationDay;


}
