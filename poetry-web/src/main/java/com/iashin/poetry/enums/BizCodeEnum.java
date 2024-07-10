package com.iashin.poetry.enums;


/**
 * 业务代码枚举
 * @date: 2024/7/10
 * @project: poetry
 * @email: dingzhen7186@163.com
 * @author: dingzhen
 */
public enum BizCodeEnum {

    /**
     * 接口请求成功
     */
    SUCCESS(200, "成功！"),

    /**
     * 参数异常
     */
    PARAMETER_ERROR(400, "参数异常！"),

    /**
     * 账号/密码输入错误
     */
    ACCOUNT_OR_PASSWD_ERROR(401, "账号/密码输入错误！"),

    /**
     * 账号被冻结
     */
    ACCOUNT_LOCK(402, "账号被冻结！"),

    /**
     * 未登录
     */
    NOT_LOGIN(300, "未登陆，请登陆后再进行操作！"),

    /**
     * 登陆已过期
     */
    LOGIN_EXPIRED(300, "登录已过期，请重新登陆！"),

    /**
     * 系统维护中
     */
    SYSTEM_REPAIR(301, "系统维护中，敬请期待！"),

    /**
     * 接口请求失败
     */
    FAIL(500, "服务异常！");


    /**
     * 业务代码
     */
    private final int code;

    /**
     * 业务信息
     */
    private final String msg;

    BizCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
