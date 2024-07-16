package com.iashin.poetry;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @date: 2024/7/10
 * @project: poetry
 * @email: dingzhen7186@163.com
 * @author: dingzhen
 */
@SpringBootApplication
@Slf4j
public class PoetryApplication {
    public static void main(String[] args) {
        try {
            SpringApplication.run(PoetryApplication.class, args);
            log.info("PoetryApplication start successfully!");
        } catch (Exception e) {
            log.error("PoetryApplication start fail:", e);
        }
    }
}
