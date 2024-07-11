package com.iashin.poetry.service;

import com.iashin.poetry.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.iashin.poetry.vo.req.UserVo;
import com.iashin.poetry.vo.resp.Result;

/**
* @author dingzhen
* @description 针对表【user(用户信息表)】的数据库操作Service
* @createDate 2024-07-10 15:17:02
*/
public interface UserService extends IService<User> {

    /**
     * 用户注册
     * @param user 注册信息
     * @return 返回结果
     */
    Result<UserVo> register(UserVo user);

    /**
     * 用户登录
     * @param account 账号
     * @param password 密码
     * @param isAdmin 是否是管理员
     * @return 登录结果
     */
    Result login(String account, String password, Boolean isAdmin);
}
