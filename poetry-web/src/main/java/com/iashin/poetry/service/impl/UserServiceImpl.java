package com.iashin.poetry.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iashin.poetry.constants.CommonConstant;
import com.iashin.poetry.entity.User;
import com.iashin.poetry.enums.BizCodeEnum;
import com.iashin.poetry.service.UserService;
import com.iashin.poetry.mapper.UserMapper;
import com.iashin.poetry.vo.req.UserVo;
import com.iashin.poetry.vo.resp.Result;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

/**
* @author dingzhen
* @description 针对表【user(用户信息表)】的数据库操作Service实现
* @createDate 2024-07-10 15:17:02
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

    @Override
    public Result register(UserVo user) {
        return null;
    }

    @Override
    public Result login(String account, String password, Boolean isAdmin) {
        // 解密password
        password = new String(SecureUtil
                        .aes(CommonConstant.CRYPOTJS_KEY.getBytes(StandardCharsets.UTF_8))
                        .decrypt(password));
        // 查询用户信息
        User user = lambdaQuery().and(wrapper -> wrapper
                    .eq(User::getPhoneNumber, account)
                    .or()
                    .eq(User::getUsername, account)
                    .or()
                    .eq(User::getEmail, account))
                .eq(User::getPassword, DigestUtils.md5DigestAsHex(password.getBytes()))
                .one();
        if (user == null) {
            return Result.fail("账号/密码输入错误，请重新输入!");
        }
        if (user.getUserStatus() == 0) {
            return Result.fail(BizCodeEnum.ACCOUNT_LOCK.getMsg());
        }
        return Result.success();
    }
}




