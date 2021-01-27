package com.hl.fruitmall.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Hl
 * @create 2021/1/27 11:15
 */
@Configuration
@MapperScan(basePackages = "com.hl.fruitmall.mapper")
public class MybatisConfig {
}
