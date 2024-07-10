package com.iashin.poetry.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 家庭信息
 * @TableName family
 */
@TableName(value ="family")
@Data
public class Family implements Serializable {
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
     * 背景封面
     */
    private String bgCover;

    /**
     * 男生头像
     */
    private String manCover;

    /**
     * 女生头像
     */
    private String womanCover;

    /**
     * 男生昵称
     */
    private String manName;

    /**
     * 女生昵称
     */
    private String womanName;

    /**
     * 计时
     */
    private String timing;

    /**
     * 倒计时标题
     */
    private String countdownTitle;

    /**
     * 倒计时时间
     */
    private String countdownTime;

    /**
     * 是否启用[0:否,1:是]
     */
    private Integer status;

    /**
     * 额外信息
     */
    private String familyInfo;

    /**
     * 点赞数
     */
    private Integer likeCount;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最终修改时间
     */
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}