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
import org.springframework.util.StringUtils;

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
    public Result<UserVo> register(UserVo user) {
        // 校验用户信息
        Result checkRes = check(user);
        if (checkRes.getCode() == BizCodeEnum.FAIL.getCode()) {
            return Result.fail(checkRes.getMsg());
        }

        User u = new User();
        u.setUsername(user.getUsername());
        u.setPhoneNumber(user.getPhoneNumber());
        u.setEmail(user.getEmail());
        // md5加密存储密码
        u.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        u.setAvatar("");
        save(u);
        // todo 用户信息加入缓存
        return Result.success(user);
    }

    @Override
    public Result login(String account, String password, Boolean isAdmin) {
        // AES解密前端 password
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
        // todo 用户信息加入缓存
        return Result.success();
    }

    /**
     * 校验用户信息
     * @param user 用户信息
     * @return 校验结果
     */
    private Result check(UserVo user) {
        String regex = "\\d{11}";
        // 用户名校验
        if (user.getUsername().matches(regex)) {
            return Result.fail("用户名不能为11位数字！");
        }

        if (user.getUsername().contains("@")) {
            return Result.fail("用户名不能包含@！");
        }
        // 手机号、邮箱校验
        if (StringUtils.hasText(user.getPhoneNumber()) && StringUtils.hasText(user.getEmail())) {
            return Result.fail("手机号与邮箱只能选择其中一个！");
        }
        if (StringUtils.hasText(user.getPhoneNumber())) {
            // todo 手机号校验，验证码校验
        } else if (StringUtils.hasText(user.getEmail())) {
            // todo 邮箱校验，验证码校验
        } else {
            return Result.fail("请输入邮箱或手机号！");
        }
        // 加密后的密码返回到前端
        user.setPassword(new String(SecureUtil
                .aes(CommonConstant.CRYPOTJS_KEY.getBytes(StandardCharsets.UTF_8))
                .decrypt(user.getPassword())));
        Integer userCnt = lambdaQuery().eq(User::getUsername, user.getUsername()).count();
        if (userCnt > 0) {
            return Result.fail("用户名已存在！");
        }
        if (StringUtils.hasText(user.getPhoneNumber())) {
            Integer phoneNumberCount = lambdaQuery().eq(User::getPhoneNumber, user.getPhoneNumber()).count();
            if (phoneNumberCount != 0) {
                return Result.fail("手机号重复！");
            }
        } else if (StringUtils.hasText(user.getEmail())) {
            Integer emailCount = lambdaQuery().eq(User::getEmail, user.getEmail()).count();
            if (emailCount != 0) {
                return Result.fail("邮箱重复！");
            }
        }
        return Result.success();
    }
}




