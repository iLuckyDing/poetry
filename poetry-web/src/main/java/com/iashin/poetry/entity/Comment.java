package com.iashin.poetry.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 文章评论表
 * @TableName comment
 */
@TableName(value ="comment")
@Data
public class Comment implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 评论来源标识
     */
    private Integer source;

    /**
     * 评论来源类型
     */
    private String type;

    /**
     * 父评论ID
     */
    private Integer parentCommentId;

    /**
     * 发表用户ID
     */
    private Integer userId;

    /**
     * 楼层评论ID
     */
    private Integer floorCommentId;

    /**
     * 父发表用户名ID
     */
    private Integer parentUserId;

    /**
     * 点赞数
     */
    private Integer likeCount;

    /**
     * 评论内容
     */
    private String commentContent;

    /**
     * 评论额外信息
     */
    private String commentInfo;

    /**
     * 创建时间
     */
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}