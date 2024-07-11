package com.iashin.poetry.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 聊天群成员
 * @TableName im_chat_group_user
 */
@TableName(value ="im_chat_group_user")
@Data
public class ImChatGroupUser implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 群ID
     */
    private Integer groupId;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 审核用户ID
     */
    private Integer verifyUserId;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否管理员[0:否,1:是]
     */
    private Integer adminFlag;

    /**
     * 用户状态[0:未审核,1:审核通过,2:禁言]
     */
    private Integer userStatus;

    /**
     * 创建时间
     */
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}