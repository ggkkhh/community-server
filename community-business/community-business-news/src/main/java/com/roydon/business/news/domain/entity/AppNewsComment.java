package com.roydon.business.news.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * (AppNewsComment)实体类
 *
 * @author roydon
 * @since 2023-10-08 21:07:06
 */
@Data
@TableName("app_news_comment")
public class AppNewsComment implements Serializable {
    private static final long serialVersionUID = 208795609552844935L;
    /**
     * 评论id
     */
    @TableId(value = "comment_id", type = IdType.AUTO)
    private Long commentId;
    /**
     * 新闻id
     */
    private String newsId;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 父id
     */
    private Long parentId;
    /**
     * 评论内容
     */
    private String content;
    /**
     * 状态：0默认1禁止
     */
    private String status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;


}

