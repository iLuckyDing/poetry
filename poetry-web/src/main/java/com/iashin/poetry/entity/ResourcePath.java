package com.iashin.poetry.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 资源聚合
 * @TableName resource_path
 */
@TableName(value ="resource_path")
@Data
public class ResourcePath implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 标题
     */
    private String title;

    /**
     * 分类
     */
    private String classify;

    /**
     * 封面
     */
    private String cover;

    /**
     * 链接
     */
    private String url;

    /**
     * 简介
     */
    private String introduction;

    /**
     * 资源类型
     */
    private String type;

    /**
     * 是否启用[0:否,1:是]
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}