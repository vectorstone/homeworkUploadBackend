package com.atguigu.system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Description:
 * @Author: Gavin
 * @Date: 6/6/2023 3:48 PM
 */
@SpringBootApplication
// @EnableTransactionManagement 提取到service-util中分页的配置类上了
// @MapperScan(basePackages = "com.atguigu.system.mapper") 提取到service-util中分页的配置类上了
public class ServiceAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceAuthApplication.class,args);
    }
}
