package com.iashin.poetry.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iashin.poetry.cache.PoetryCache;
import com.iashin.poetry.constants.CommonConstant;
import com.iashin.poetry.entity.User;
import com.iashin.poetry.entity.WebInfo;
import com.iashin.poetry.enums.BizCodeEnum;
import com.iashin.poetry.enums.PoetryEnum;
import com.iashin.poetry.service.UserService;
import com.iashin.poetry.mapper.UserMapper;
import com.iashin.poetry.util.mail.MailUtil;
import com.iashin.poetry.vo.req.UserVo;
import com.iashin.poetry.vo.resp.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
* @author dingzhen
* @description 针对表【user(用户信息表)】的数据库操作Service实现
* @createDate 2024-07-10 15:17:02
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

    @Resource
    private MailUtil mailUtil;

    @Value("${user.code.format}")
    private String codeFormat;

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

    @Override
    public Result getCodeForForgetPassword(String place, Integer flag) {
        int i = new Random().nextInt(900000) + 100000;
        if (flag == 1) {
            log.info(place + " 手机验证码为：{}", i);
        } else {
            log.info(place + " 邮箱验证码为：{}", i);
            // 收件人邮箱
            List<String> mailList = new ArrayList<>();
            mailList.add(place);
            String content = generateMailContent(i);
            WebInfo webInfo = (WebInfo) PoetryCache.get(CommonConstant.WEB_INFO);
            AtomicInteger count = (AtomicInteger) PoetryCache.get(CommonConstant.CODE_MAIL + mailList.get(0));
            if (count == null || count.get() < CommonConstant.CODE_MAIL_COUNT) {
                // 发送验证码邮件
                mailUtil.sendMailMessage(mailList, "您有一封来自" +  (webInfo == null ? "Poetize" : webInfo.getWebName()) + "的回执！", content);
                // 如果是第一次发送验证码, 记录缓存并更新验证码发送次数为1
                if (count == null) {
                   PoetryCache.put(CommonConstant.CODE_MAIL + mailList.get(0), new AtomicInteger(1), CommonConstant.CODE_EXPIRE);
                } else {
                    // 如果发送过验证码，更新该邮件发送验证码次数
                    count.incrementAndGet();
                }
            } else {
                return Result.fail("验证码发送过于频繁，请稍后再试！");
            }
        }
        // 把验证码加入缓存中
        PoetryCache.put(CommonConstant.FORGET_PASSWORD + place + "_" + flag, i, CommonConstant.CODE_EXPIRE);
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

    /**
     * 生成验证码邮件内容
     * @param code 验证码
     * @return 邮件内容
     */
    private String generateMailContent(int code) {
        WebInfo webInfo = (WebInfo) PoetryCache.get(CommonConstant.WEB_INFO);
        String webName = (webInfo == null ? "POETRY" : webInfo.getWebName());
        return String.format(mailUtil.getMailText(),
                webName,
                String.format(MailUtil.IM_MAIL, /*PoetryUtil.getAdminUser().getUsername()*/"admin"),
                /*PoetryUtil.getAdminUser().getUsername()*/"admin",
                String.format(codeFormat, code),
                "",
                webName);
    }

}




