package com.iashin.poetry.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 单聊记录
 * @TableName im_chat_user_message
 */
@TableName(value ="im_chat_user_message")
@Data
public class ImChatUserMessage implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

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
     * 是否已读[0:未读,1:已读]
     */
    private Integer messageStatus;

    /**
     * 创建时间
     */
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}