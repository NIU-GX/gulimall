package com.atguigu.gulimall.coupon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 如何使用nacos作为配置中心，统一管理配置
 * 1. 导入依赖
 * 2. 创建一个配置文件bootstrap.properties，配置app-name，和nacos-server地址
 * 3. 在nacos服务器的控制页面，添加名为当前项目名的配置文件（gulimall-coupon.properties）
 * 4. 然后添加配置
 * 5. 动态获取配置需要两个注解，
 * @RefreshScope：动态刷新配置
 * @Value：获取配置的值
 * 6. 如果配置中心和当前应用的配置文件中都配置的相同的项，优先使用配置中心的配置
 */
@SpringBootApplication
@EnableDiscoveryClient
public class GulimallCouponApplication {

    public static void main(String[] args) {
        SpringApplication.run(GulimallCouponApplication.class, args);
    }

}
