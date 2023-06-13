package com.roydon.app.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.roydon.common.core.domain.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * app端图文轮播图公告(AppNotice)实体类
 *
 * @author roydon
 * @since 2023-06-13 22:10:43
 */
@Data
@TableName("app_notice")
public class AppNotice extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -50404092356694641L;
    /**
     * 公告ID
     */
    @TableId("notice_id")
    private Integer noticeId;
    /**
     * 标题
     */
    private String noticeTitle;
    /**
     * 图片地址
     */
    private String noticeImgUrl;
    /**
     * 状态（0关闭 1展示）
     */
    private String showInApp;

}

