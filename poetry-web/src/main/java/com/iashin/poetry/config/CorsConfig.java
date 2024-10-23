package com.iashin.poetry.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @date: 2024/10/23
 * @project: poetry
 * @email: dingzhen7186@163.com
 * @author: dingzhen
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:1024")
                .allowedMethods("*");
    }

}
