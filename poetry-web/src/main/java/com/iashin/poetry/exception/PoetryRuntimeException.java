package com.iashin.poetry.exception;

/**
 * @date: 2024/7/11
 * @project: poetry
 * @email: dingzhen7186@163.com
 * @author: dingzhen
 */
public class PoetryRuntimeException extends RuntimeException {

    private String msg;

    public PoetryRuntimeException() {
        super();
    }

    public PoetryRuntimeException(String msg) {
        super(msg);
        this.msg = msg;
    }


    public PoetryRuntimeException(Throwable cause) {
        super(cause);
        this.msg = cause.getMessage();
    }

    public PoetryRuntimeException(String msg, Throwable cause) {
        super(msg, cause);
        this.msg = msg;
    }


    public String getMsg() {
        return msg;
    }

}
