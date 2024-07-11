package com.iashin.poetry.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 参数配置表
 * @TableName sys_config
 */
@TableName(value ="sys_config")
@Data
public class SysConfig implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 名称
     */
    private String configName;

    /**
     * 键名
     */
    private String configKey;

    /**
     * 键值
     */
    private String configValue;

    /**
     * 1 私用 2 公开
     */
    private String configType;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}