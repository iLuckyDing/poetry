package com.iashin.poetry.controller;

import com.iashin.poetry.service.UserService;
import com.iashin.poetry.vo.req.UserVo;
import com.iashin.poetry.vo.resp.Result;
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
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 用户注册
     * @param user 注册信息
     * @return 注册结果
     */
    @PostMapping("/register")
    public Result register(@Validated @RequestBody UserVo user) {
        return userService.register(user);
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
        return userService.login(account, password, isAdmin);
    }

}
