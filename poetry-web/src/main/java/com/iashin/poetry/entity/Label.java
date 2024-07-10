package com.iashin.poetry.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 标签
 * @TableName label
 */
@TableName(value ="label")
@Data
public class Label implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 分类ID
     */
    private Integer sortId;

    /**
     * 标签名称
     */
    private String labelName;

    /**
     * 标签描述
     */
    private String labelDescription;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}