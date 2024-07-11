package com.iashin.poetry.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 分类
 * @TableName sort
 */
@TableName(value ="sort")
@Data
public class Sort implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 分类名称
     */
    private String sortName;

    /**
     * 分类描述
     */
    private String sortDescription;

    /**
     * 分类类型[0:导航栏分类,1:普通分类]
     */
    private Integer sortType;

    /**
     * 分类优先级：数字小的在前面
     */
    private Integer priority;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}