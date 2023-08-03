package com.roydon.openai.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 实体类
 */
@Data
@AllArgsConstructor
public class OpenAi {

    String id;
    String name;
    String desc;
    String model;
    // 提示模板
    String prompt;
    // 创新采样
    Double temperature;
    // 情绪采样
    Double topP;
    // 结果条数
    Double n = 1d;
    // 频率处罚系数
    Double frequencyPenalty;
    // 重复处罚系数
    Double presencePenalty;
    // 停用词
    String stop;

}
