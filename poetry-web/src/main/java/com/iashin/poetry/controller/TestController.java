package com.iashin.poetry.controller;

import com.iashin.poetry.util.mail.MailUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @date: 2024/7/10
 * @project: poetry
 * @email: dingzhen7186@163.com
 * @author: dingzhen
 */
@RestController
@RequestMapping("ping")
@Slf4j
public class TestController {

    @Resource
    private MailUtil mailUtil;

    @GetMapping
    public String ping() {
        log.info("pong!");
        return "pong";
    }

    @GetMapping("testMail")
    public String testMail(){
        List<String> emailList = new ArrayList<>();
        emailList.add("iashin@aliyun.com");
        mailUtil.sendMailMessage(emailList, "测试邮件", "你好,这是Poetry的测试邮件");
        return "ok";
    }
}
