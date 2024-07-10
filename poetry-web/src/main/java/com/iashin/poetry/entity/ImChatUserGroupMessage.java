package com.iashin.poetry.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 群聊记录
 * @TableName im_chat_user_group_message
 */
@TableName(value ="im_chat_user_group_message")
@Data
public class ImChatUserGroupMessage implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 群ID
     */
    private Integer groupId;

    /**
     * 发送ID
     */
    private Integer fromId;

    /**
     * 接收ID
     */
    private Integer toId;

    /**
     * 内容
     */
    private String content;

    /**
     * 创建时间
     */
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}