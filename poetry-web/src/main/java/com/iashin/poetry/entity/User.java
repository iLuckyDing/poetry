package com.iashin.poetry.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 用户信息表
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 手机号
     */
    private String phoneNumber;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 是否启用[0:否,1:是]
     */
    private Integer userStatus;

    /**
     * 性别[1:男,2:女,0:保密]
     */
    private Integer gender;

    /**
     * openId
     */
    private String openId;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 赞赏
     */
    private String admire;

    /**
     * 订阅
     */
    private String subscribe;

    /**
     * 简介
     */
    private String introduction;

    /**
     * 用户类型[0:admin,1:管理员,2:普通用户]
     */
    private Integer userType;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最终修改时间
     */
    private Date updateTime;

    /**
     * 最终修改人
     */
    private String updateBy;

    /**
     * 是否启用[0:未删除,1:已删除]
     */
    private Integer deleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}