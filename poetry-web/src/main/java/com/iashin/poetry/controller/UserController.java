package com.iashin.poetry.controller;

import com.alibaba.fastjson2.JSON;
import com.iashin.poetry.service.UserService;
import com.iashin.poetry.vo.req.UserVo;
import com.iashin.poetry.vo.resp.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 用户相关接口
 * @date: 2024/7/10
 * @project: poetry
 * @email: dingzhen7186@163.com
 * @author: dingzhen
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 用户注册
     * @param user 注册信息
     * @return 注册结果
     */
    @PostMapping("/register")
    public Result<UserVo> register(@Validated @RequestBody UserVo user) {
        log.info("UserController 用户注册请求:{}", JSON.toJSONString(user));
        try {
            return userService.register(user);
        } catch (Exception e) {
            log.error("UserController 用户注册失败:", e);
            return Result.fail(e.getMessage());
        }
    }

    /**
     * 用户登录
     * @param account 用户账号
     * @param password 密码
     * @param isAdmin 是否是管理员
     * @return 登录结果
     */
    @PostMapping("login")
    public Result login(@RequestParam("account") String account, @RequestParam("password") String password,
                        @RequestParam(value = "isAdmin", required = false) Boolean isAdmin) {
        log.info("UserController 用户登陆 account:{}, password:{}", account, password);
        try {
            return userService.login(account, password, isAdmin);
        } catch (Exception e) {
            log.error("UserController 用户登陆失败:", e);
            return Result.fail(e.getMessage());
        }
    }

    /**
     * 忘记密码 获取验证码
     * <p>
     * 1 手机号
     * 2 邮箱
     */
    @GetMapping("/getCodeForForgetPassword")
    public Result getCodeForForgetPassword(@RequestParam("place") String place, @RequestParam("flag") Integer flag) {
        return userService.getCodeForForgetPassword(place, flag);
    }

}
