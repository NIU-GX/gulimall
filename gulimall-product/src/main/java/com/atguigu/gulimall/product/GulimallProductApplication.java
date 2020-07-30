package com.atguigu.gulimall.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

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
 *
 * JSR303数据校验
 *      1. 给bean添加校验注解，并定义自己的message提示
 *      2. 给controller层添加@Valid注解，开启校验功能
 *      3. 在校验的bean后紧跟一个BindResult就可以获取校验的结果
 *          public R save(@Valid @RequestBody BrandEntity brand, BindResult result)
 *      4. 分组校验
 *          1. 给校验注解标注什么情况下需要进行校验@NotBlank(message = "品牌名称不可以为空",groups = {AddGroup.class,UpdateGroup.class})
 *          2. 在controller层添加注解 @Validated(value = AddGroup.class)
 *          3. 默认没有指定分组的校验注解在分组情况下不会生效
 *      5. 自定义校验
 *          1. 编写自定义的校验注解
 *          2. 编写自定义的校验器
 *          3. 关联校验注解和校验器
 *              @Constraint(
 *                  validatedBy = {ListValueConstraintValidator.class}
 *                  可以绑定多个不同的校验器
 *
 * 统一的异常处理
 * @ControllerAdvice
 * 1. 编写全局异常处理类，使用@ControllerAdvice
 * 2. 使用@ExceptionHandler标注方法
 *
 */
@MapperScan("com.atguigu.gulimall.product.dao")
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableTransactionManagement
public class GulimallProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(GulimallProductApplication.class, args);
    }

}
