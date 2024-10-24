package com.iashin.poetry.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iashin.poetry.cache.PoetryCache;
import com.iashin.poetry.constants.CommonConstant;
import com.iashin.poetry.entity.User;
import com.iashin.poetry.enums.BizCodeEnum;
import com.iashin.poetry.enums.PoetryEnum;
import com.iashin.poetry.service.UserService;
import com.iashin.poetry.mapper.UserMapper;
import com.iashin.poetry.vo.req.UserVo;
import com.iashin.poetry.vo.resp.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
* @author dingzhen
* @description 针对表【user(用户信息表)】的数据库操作Service实现
* @createDate 2024-07-10 15:17:02
*/
@Service
@Slf4j
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
        // 用户信息加入缓存
        User one = lambdaQuery().eq(User::getUsername, u.getUsername()).one();
        String userToken = CommonConstant.USER_ACCESS_TOKEN + UUID.randomUUID().toString()
                            .replaceAll("-", "");
        PoetryCache.put(userToken, one, CommonConstant.TOKEN_EXPIRE);
        PoetryCache.put(CommonConstant.USER_TOKEN + one.getId(), userToken, CommonConstant.TOKEN_EXPIRE);
        UserVo userVO = new UserVo();
        BeanUtils.copyProperties(one, userVO);
        userVO.setPassword(null);
        userVO.setAccessToken(userToken);
        return Result.success(userVO);
    }

    @Override
    public Result login(String account, String password, Boolean isAdmin) {
        // AES解密前端 password
        password = SecureUtil.aes(CommonConstant.CRYPTOJS_KEY.getBytes(StandardCharsets.UTF_8))
                            .decryptStr(password);
        // 查询用户信息
        User user = lambdaQuery()
                    .eq(User::getPassword, DigestUtils.md5DigestAsHex(password.getBytes()))
                    .and(wrapper -> wrapper
                        .eq(User::getPhoneNumber, account)
                        .or()
                        .eq(User::getUsername, account)
                        .or()
                        .eq(User::getEmail, account))
                        .one();
        if (user == null) {
            return Result.fail(BizCodeEnum.ACCOUNT_OR_PASSWD_ERROR.getMsg());
        }
        if (user.getUserStatus() == 0) {
            return Result.fail(BizCodeEnum.ACCOUNT_LOCK.getMsg());
        }
        // 登录后将用户的token加入缓存
        String userToken = "", adminToken = "";
        // 管理员处理
        if (isAdmin != null && isAdmin) {
            if (user.getUserType() != PoetryEnum.USER_TYPE_ADMIN.getCode() && user.getUserType() != PoetryEnum.USER_TYPE_DEV.getCode()) {
                return Result.fail("请输入管理员账号！");
            }
            // 尝试从缓存中获取token
            if (PoetryCache.get(CommonConstant.ADMIN_TOKEN + user.getId()) != null) {
                adminToken = (String) PoetryCache.get(CommonConstant.ADMIN_TOKEN + user.getId());
            }
            // 如果缓存中没有token，则生成一个加入缓存
            if (!StringUtils.hasText(adminToken)) {
                String uuid = UUID.randomUUID().toString().replaceAll("-", "");
                adminToken = CommonConstant.ADMIN_ACCESS_TOKEN + uuid;
                PoetryCache.put(adminToken, user, CommonConstant.TOKEN_EXPIRE);
                PoetryCache.put(CommonConstant.ADMIN_TOKEN + user.getId(), adminToken, CommonConstant.TOKEN_EXPIRE);
            }
        } else { // 普通用户处理
            if (PoetryCache.get(CommonConstant.USER_TOKEN + user.getId()) != null) {
                userToken = (String) PoetryCache.get(CommonConstant.USER_TOKEN + user.getId());
            }
            // 如果userToken为空，则通过UUID生成一个并加入到缓存中
            if (!StringUtils.hasText(userToken)) {
                String uuid = UUID.randomUUID().toString().replaceAll("-", "");
                userToken = CommonConstant.USER_ACCESS_TOKEN + uuid;
                PoetryCache.put(CommonConstant.USER_TOKEN + user.getId(), userToken, CommonConstant.TOKEN_EXPIRE);
                PoetryCache.put(userToken, user, CommonConstant.TOKEN_EXPIRE);
            }
        }
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user, userVo);
        userVo.setPassword(null);
        if (Boolean.TRUE.equals(isAdmin) && user.getUserType() == PoetryEnum.USER_TYPE_ADMIN.getCode()) {
            userVo.setIsBoss(true);
        }
        if (Boolean.TRUE.equals(isAdmin)) {
            userVo.setAccessToken(adminToken);
        } else  {
            userVo.setAccessToken(userToken);
        }
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
            Integer codeCache = (Integer) PoetryCache.get(CommonConstant.FORGET_PASSWORD + user.getEmail() + "_2");
            if (codeCache == null || codeCache != Integer.parseInt(user.getCode())) {
                return Result.fail("验证码错误！");
            }
            PoetryCache.remove(CommonConstant.FORGET_PASSWORD + user.getEmail() + "_2");
        } else {
            return Result.fail("请输入邮箱或手机号！");
        }
        // 加密后的密码返回到前端
        /*user.setPassword(new String(SecureUtil
                .aes(CommonConstant.CRYPTOJS_KEY.getBytes(StandardCharsets.UTF_8))
                .decrypt(user.getPassword())));*/
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




