package com.iashin.poetry.vo.resp;

import com.iashin.poetry.enums.BizCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 统一返回结果
 * @date: 2024/7/10
 * @project: poetry
 * @email: dingzhen7186@163.com
 * @author: dingzhen
 */
@Data
@AllArgsConstructor
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 406754594494048189L;

    private int code;
    private String message;
    private T data;

    public static <T> Result<T> fail(int code, String msg, T data) {
        return new Result(code, msg, data);
    }

    public static <T> Result<T> fail(String msg, T data) {
        return fail(BizCodeEnum.FAIL.getCode(), msg, data);
    }

    public static <T> Result<T> fail(String msg) {
        return fail(msg, null);
    }

    public static <T> Result<T> fail() {
        return fail(BizCodeEnum.FAIL.getCode(), BizCodeEnum.FAIL.getMsg(), null);
    }

    public static <T> Result<T> success(int code, String msg, T data) {
        return new Result(code, msg, data);
    }

    public static <T> Result<T> success(String msg, T data) {
        return success(BizCodeEnum.SUCCESS.getCode(), msg, data);
    }

    public static <T> Result<T> success() {
        return success(BizCodeEnum.SUCCESS.getCode(), BizCodeEnum.SUCCESS.getMsg(), null);
    }
}
