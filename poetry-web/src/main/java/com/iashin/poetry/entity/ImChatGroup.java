package com.iashin.poetry.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 聊天群
 * @TableName im_chat_group
 */
@TableName(value ="im_chat_group")
@Data
public class ImChatGroup implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 群名称
     */
    private String groupName;

    /**
     * 群主用户ID
     */
    private Integer masterUserId;

    /**
     * 群头像
     */
    private String avatar;

    /**
     * 简介
     */
    private String introduction;

    /**
     * 公告
     */
    private String notice;

    /**
     * 进入方式[0:无需验证,1:需要群主或管理员同意]
     */
    private Integer inType;

    /**
     * 类型[1:聊天群,2:话题]
     */
    private Integer groupType;

    /**
     * 创建时间
     */
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}