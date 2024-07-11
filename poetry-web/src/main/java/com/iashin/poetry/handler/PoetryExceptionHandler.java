package com.iashin.poetry.handler;

import com.alibaba.fastjson.JSON;
import com.iashin.poetry.enums.BizCodeEnum;
import com.iashin.poetry.exception.PoetryLoginException;
import com.iashin.poetry.exception.PoetryRuntimeException;
import com.iashin.poetry.util.PoetryUtil;
import com.iashin.poetry.vo.resp.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * 全局异常处理
 * @date: 2024/7/11
 * @project: poetry
 * @email: dingzhen7186@163.com
 * @author: dingzhen
 */
@ControllerAdvice
@Slf4j
public class PoetryExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result handleException(Exception ex) {
        log.error("请求URL-----------------" + PoetryUtil.getRequest().getRequestURL());
        log.error("出错啦------------------", ex);
        if (ex instanceof PoetryRuntimeException) {
            PoetryRuntimeException e = (PoetryRuntimeException) ex;
            return Result.fail(e.getMessage());
        }

        if (ex instanceof PoetryLoginException) {
            PoetryLoginException e = (PoetryLoginException) ex;
            return Result.fail(300, e.getMessage());
        }

        if (ex instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException e = (MethodArgumentNotValidException) ex;
            Map<String, String> collect = e.getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
            return Result.fail(JSON.toJSONString(collect));
        }

        if (ex instanceof MissingServletRequestParameterException) {
            return Result.fail(BizCodeEnum.PARAMETER_ERROR.getCode(), BizCodeEnum.PARAMETER_ERROR.getMsg());
        }

        return Result.fail(BizCodeEnum.FAIL.getCode(), BizCodeEnum.FAIL.getMsg());
    }

}
