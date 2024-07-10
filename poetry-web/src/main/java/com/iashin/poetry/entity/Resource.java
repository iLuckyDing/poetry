package com.iashin.poetry.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 资源信息
 * @TableName resource
 */
@TableName(value ="resource")
@Data
public class Resource implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 资源类型
     */
    private String type;

    /**
     * 资源路径
     */
    private String path;

    /**
     * 资源内容的大小,单位：字节
     */
    private Integer size;

    /**
     * 文件名称
     */
    private String originalName;

    /**
     * 资源的 MIME 类型
     */
    private String mimeType;

    /**
     * 是否启用[0:否,1:是]
     */
    private Integer status;

    /**
     * 存储平台
     */
    private String storeType;

    /**
     * 创建时间
     */
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}