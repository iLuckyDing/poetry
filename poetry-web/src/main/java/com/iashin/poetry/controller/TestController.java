package com.iashin.poetry.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping
    public String ping() {
        log.info("pong!");
        return "pong";
    }
}
