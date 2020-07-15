package com.atguigu.gulimall.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 整合mybatis plus
 *      1. 导入依赖
 *      <dependency>
 *           <groupId>com.baomidou</groupId>
 *           <artifactId>mybatis-plus-boot-starter</artifactId>
 *           <version>3.3.1</version>
 *      </dependency>
 *      2. 配置
 *          1. 配置数据源
 *              在application.yml中配置数据源
 *          2. 配置其他信息
 *              1. @MapperScan
 *              2. 告诉mybatis plus，sql映射文件的位置
 *              3. 设置主键自增
 *
 * 使用mybatis带的逻辑删除
 *      1. 配置全局逻辑删除规则 （可省略）
 *      2. 配置删除逻辑的组件bean（3.1版本呢后可省略）
 *      3. 给实体加上逻辑删除注解
 */
@MapperScan("com.atguigu.gulimall.product.dao")
@SpringBootApplication
@EnableDiscoveryClient
public class GulimallProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(GulimallProductApplication.class, args);
    }

}
