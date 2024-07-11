package com.iashin.poetry.exception;

/**
 * @date: 2024/7/11
 * @project: poetry
 * @email: dingzhen7186@163.com
 * @author: dingzhen
 */
public class PoetryLoginException extends RuntimeException {
    private String msg;

    public PoetryLoginException() {
        super();
    }

    public PoetryLoginException(String msg) {
        super(msg);
        this.msg = msg;
    }


    public PoetryLoginException(Throwable cause) {
        super(cause);
        this.msg = cause.getMessage();
    }

    public PoetryLoginException(String msg, Throwable cause) {
        super(msg, cause);
        this.msg = msg;
    }


    public String getMsg() {
        return msg;
    }
}
