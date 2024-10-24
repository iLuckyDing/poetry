package com.iashin.poetry.constants;

/**
 * 公共常量
 * @date: 2024/7/10
 * @project: poetry
 * @email: dingzhen7186@163.com
 * @author: dingzhen
 */
public class CommonConstant {

    /**
     * 超级管理员的用户Id
     */
    public static final int ADMIN_USER_ID = 1;

    /**
     * AES密钥
     * AES支持三种不同的密钥长度：128位（16字节）、192位（24字节）和256位（32字节）
     */
    public static final String CRYPTOJS_KEY = "iashin&&*1120520";

    public static final String TOKEN_HEADER = "Authorization";

    /**
     * 用户信息Token
     */
    public static final String USER_TOKEN = "user_token_";

    /**
     * 管理员信息Token
     */
    public static final String ADMIN_TOKEN = "admin_token_";

    /**
     * 用户权限Token
     */
    public static final String USER_ACCESS_TOKEN = "user_access_token_";

    /**
     * 管理员权限Token
     */
    public static final String ADMIN_ACCESS_TOKEN = "admin_access_token_";

    /**
     * Token过期时间：1天
     */
    public static final long TOKEN_EXPIRE = 86400;

    /**
     * 忘记密码时获取验证码用于找回密码
     */
    public static final String FORGET_PASSWORD = "forget_password_";

    /**
     * 评论和IM邮件
     */
    public static final String COMMENT_IM_MAIL = "comment_im_mail_";

    /**
     * 验证码邮件
     */
    public static final String CODE_MAIL = "code_mail_";

    /**
     * 评论和IM邮件发送次数
     */
    public static final int COMMENT_IM_MAIL_COUNT = 1;

    /**
     * 验证码邮件发送次数
     */
    public static final int CODE_MAIL_COUNT = 3;
}
