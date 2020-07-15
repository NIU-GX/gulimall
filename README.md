# 1. 环境搭建

## 1. 使用vagrant和virtual Box快速搭建虚拟机环境

首先安装两款软件。

在cmd窗口 输入 vagrant命令查看vagrant是否安装成功。



在vagrant的官网提供了镜像仓库，其中有各种版本的已经搭建好的虚拟机只需要下载镜像就可以使用。

```
// 执行此命令 会在当前文件夹生成vagrantfile配置文件 
vagrant init centos/7

// 下载镜像
vagrant up

// 下载完成之后，可以在cmd窗口直接连接虚拟机
vagrant ssh

// 退出虚拟机
exit
```

****

**以后直接使用vagrant up 命令，就可以在cmd启动虚拟机，然后使用vagrant ssh 连接虚拟机**。



## 2. 设置虚拟机的网路连接。

在本机的cmd窗口输入ipconfig 

```
以太网适配器 VirtualBox Host-Only Network:

   连接特定的 DNS 后缀 . . . . . . . :
   本地链接 IPv6 地址. . . . . . . . : fe80::dcf7:9d16:edde:b532%3
   IPv4 地址 . . . . . . . . . . . . : 192.168.56.1
   子网掩码  . . . . . . . . . . . . : 255.255.255.0
   默认网关. . . . . . . . . . . . . :
```

找到VirtualBox的ipv4  地址是  192.168.56.1。  

给虚拟机配置192.168.56.xxx的地址。

**打开vagrantfile找到配置私有网络的地方** ：config.vm.network "private_network", ip: "192.168.56.10"



然后使用**vagrant reload** 重启虚拟机

## 3. 安装docker

参照官方文档安装

1. **先卸载旧版本**

```cmd
$ sudo yum remove docker \
                  docker-client \
                  docker-client-latest \
                  docker-common \
                  docker-latest \
                  docker-latest-logrotate \
                  docker-logrotate \
                  docker-engine
```

2. **安装工具**

```cmd
$ sudo yum install -y yum-utils

$ sudo yum-config-manager \
    --add-repo \
    https://download.docker.com/linux/centos/docker-ce.repo
```

3. **安装docker**

```cmd
$ sudo yum install docker-ce docker-ce-cli containerd.io
```

4. **启动docker**

```cmd
$ sudo systemctl start docker
```

5. **设置linux开机自启动docker**

```cmd
$ sudo systemctl enable docker
```

6. **配置阿里云镜像**

``` cmd
sudo mkdir -p /etc/docker

sudo tee /etc/docker/daemon.json <<-'EOF'
{
  "registry-mirrors": ["https:xxxxx"]
}
EOF

sudo systemctl daemon-reload

sudo systemctl restart docker
```

## 4. 使用docker安装mysql

1. **拉取镜像**

```cmd
$ sudo docker pull mysql:5.7
```

2. **启动镜像**

```cmd
docker run -p 3306:3306 --name mysql \
-v /mydata/mysql/log:/var/log/mysql \
-v /mydata/mysql/data:/var/lib/mysql \
-v /mydata/mysql/conf:/etc/mysql \
-e MYSQL_ROOT_PASSWORD=root \
-d mysql:5.7
```

> （-p）端口映射 ：容器的3306端口，映射linux的3306端口，访问linux虚拟机的3306端口就可以访问容器的3306端口，**其实容器就是一个更小型的虚拟机**
>
> 
>
>  （-v）文件挂载：Linux的文件，/mydata/mysql/log    对应   容器中的/var/log/mysql 
>
>  访问Linux的文件就可以修改或者查看容器里的文件
>
>  
>
>  （-e）设置root用户的密码 
>
>  
>
>  （-d）后台启动MySQL



3. **mysql配置**

	```
	修改mysql的配置文件
	vi /mydata/mysql/conf/my.cnf
	
	[client]
	default-character-set=utf8
	
	[mysql]
	default-character-set=utf8
	
	[mysqld]
	init_connect='SET collation_connection = utf8_unicode_ci'
	init_connect='SET NAMES utf8'
	character-set-server=utf8
	collation-server=utf8_unicode_ci
	skip-character-set-client-handshake
	skip-name-resolve
	
	保存并退出
	```

4. **查看容器中的配置是否生效**

	```
	先重启容器
	docker restart mysql
	
	以交互式的模式进入容器
	docker exec -it mysql /bin/bash
	
	cd /etc/mysql
	cat my.cnf
	```

	

## 5. 使用docker安装redis

1. **拉取镜像**

``` 
docker pull redis
```

2. **启动镜像**

```
docker run -p 6379:6379 --name redis \
-v /mydata/redis/data:/data \
-v /mydata/redis/conf/redis.conf:/etc/redis/redis.conf \
-d redis redis-server /etc/redis/redis.conf

注意：
etc/redis/redis.conf    redis.conf 可能不存在

要现在linux中创建/mydata/redis/conf/redis.conf
mkdir -p /mydata/redis/conf
touch /mydata/redis/conf/redis.conf
```

3. **测试**

```
在linux中连接容器redis的客户端
docker exec -it redis redis-cli

set a b
get a
```

4. **配置redis**

在 /mydata/redis/conf/redis.conf 中添加

``` 
appendonly yes
```

开始redis数据的持久化存储，不然redis的数据存储在内存中，重启redis数据消失

## 6. 启动docker自动启动容器

sudo docker update mysql --restart=always

sudo docker update redis--restart=always

## 7. 创建项目微服务

商品服务，仓储服务，订单服务，优惠卷服务，用户服务

共同：

1.  web ，openfeign

 	2.  每个服务的包名统一：com.atguigu.gulimall.xxx(product/order/ware/coupon/member)
 	3.  模块名：

## 8. 数据库创建

每个微服务创建自己对应的数据库

## 9. 后台管理系统快速搭建

戳： [人人开源](https://gitee.com/renrenio)

开源项目的中的 [renren-fast](https://gitee.com/renrenio/renren-fast)  和 [renren-fast-vue](https://gitee.com/renrenio/renren-fast-vue)  快速搭建。

利用renren-generator代码生成器生成代码。

# 2. 分布式阶段

## 1. 注册中心，配置中心，网关

将微服务注册在注册中心，某个微服务可以通过注册中心去调用其他微服务。

将微服务的配置存放在配置中心，多台服务器上运行的相同的微服务，只需要修改配置中心的配置就可以修改多台服务器的配置。

前端的请求通过网关抵达微服务

## 2. 使用spring cloud alibaba实现分布式架构

```
SpringCloud Alibaba  -  Nacos：注册中心（服务发现/注册）
SpringCloud Alibaba  -  Nacos：配置中心（动态配置管理）
SpringCloud - Ribbon：负载均衡
SpringCloud - Feign：声明式HTTP客户端（调用远程服务）
SpringCloud Alibaba - Sentinel：服务容错（限流，降级，熔断）
SpringCloud - Gateway：API网关（webflux编程模式）
SpringCloud - Sleuth：调用链监控
SpringCloud Alibaba - Seata：原Fescar：分布式事务解决方案
```



## 3. 版本选择，2.x.x版本

在gulimall-common的pom.xml文件中添加

```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-alibaba-dependencies</artifactId>
            <version>2.2.0.RELEASE</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

## 4. nacos作为注册中心

**首先在common的pom.xml中导入依赖包**

```xml
<!--nacos注册中心-->
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
</dependency>
```

**下载nacos server**https://github.com/alibaba/nacos/releases



**打开nacos-server服务器，在application.yml配置文件中配置，服务器地址，为服务起自己的名字**

```yml
cloud:
  nacos:
    discovery:
      server-addr: 127.0.0.1:8848
    application:
      name: gulimall-coupon
```



**使用@EnableDiscoveryClient开启服务的注册和发现功能**

```java
@SpringBootApplication
@EnableDiscoveryClient
public class GulimallCouponApplication {

    public static void main(String[] args) {
        SpringApplication.run(GulimallCouponApplication.class, args);
    }

}
```



**访问127.0.0.1：8848/nacos   ，  账号密码都为nacos**



## 5. 使用feigen 声明式远程调用

Feign是一个声明式的HTTP客户端，他的目的就是让远程调用更加简单。

1. 导入Feign的依赖

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
```



2. 在一个服务controller层写好方法，然后使用注册在nacos-server上的服务远程调用。

```java
// 在gulimall-coupon中写一个方法
@RequestMapping("/member/list")
public R memberCoupon() {
    CouponEntity couponEntity = new CouponEntity();
    couponEntity.setCouponName("满100减10");
    return R.ok().put("coupon",Arrays.asList(couponEntity));
}
```

3. gulimall-member服务通过feign远程调用上面的方法。
4. 首先要编写一个接口，告诉springcloud这个接口需要调用远程服务

```java
// 填写服务名
@FeignClient("gulimall-coupon")
public interface CouponFeignService {

    @RequestMapping("/coupon/coupon/member/list")
    R memberCoupon();
}
```

5. 开启远程调用功能，@EnableFeignClients

```java
@EnableFeignClients(basePackages = "com.atguigu.gulimall.member.feign")
@EnableDiscoveryClient
@SpringBootApplication
public class GulimallMemberApplication {

    public static void main(String[] args) {
        SpringApplication.run(GulimallMemberApplication.class, args);
    }

}
```

6. 编写请求方法

```java
@Autowired
private CouponFeignService couponFeignService;

@RequestMapping("/coupon")
public R test() {
    MemberEntity memberEntity = new MemberEntity();
    memberEntity.setNickname("张三");
    R r = couponFeignService.memberCoupon();
    Object coupons = r.get("coupons");

    return R.ok().put("member",memberEntity).put("coupons",coupons);
}
```

## 6. nacos作为配置中心

1. 导入依赖

```xml
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
</dependency>
```

2. 创建配置文件，bootstrap.properties

```properties
spring.application.name=gulimall-coupon
spring.cloud.nacos.config.server-addr=127.0.0.1:8848
```

3. ```
	在nacos服务器的控制页面，添加名为当前项目名的配置文件（gulimall-coupon.properties）
	
	```

4. 动态获取配置需要两个注解

```java
@RefreshScope：动态刷新配置
@Value：获取配置的值
```

5. 如果配置中心和当前应用的配置文件中都配置的相同的项，优先使用配置中心的配置

**核心概念**

```java
命名空间：  配置隔离
    默认：public（保留空间），默认新增的配置都在public下
    例如：开发，测试，线上部署
    想要使用其他的命名空间，需要在bootstrap.properties中修改配置
    spring.cloud.nacos.config.namespace=e454150c-b0eb-471f-87f6-643ac1b9617a
    
    实际项目：为每一个微服务创建一个命名空间，每个服务使用自己命名空间里的配置
    
配置集： 所有的配置的集合
    
配置集ID：类似文件名
    Data ID：类似文件名
    
配置分组：
    默认所有的配置集都属于：DEFAULT_GROUP
    在一个命名空间里还可以定义配置的分组。
    通过在bootstrap.properties中修改配置，来修改配置所在的分组
    spring.cloud.nacos.config.group=xxxx
    
同时加载多个配置集：
    applicatoin.yml中的配置可以按照类别拆分成多个yml文件存放在配置中心，在项目的启动的时候去配置中心加载这些配置。
    
```



bootstrap.properties

```properties
spring.application.name=gulimall-coupon
spring.cloud.nacos.config.server-addr=127.0.0.1:8848
spring.cloud.nacos.config.namespace=d2e81459-f28c-4285-84cc-82bf717c3640
spring.cloud.nacos.config.group=dev

spring.cloud.nacos.config.extension-configs[0].data-id=datasource.yml
spring.cloud.nacos.config.extension-configs[0].group=dev
spring.cloud.nacos.config.extension-configs[0].refresh=true

spring.cloud.nacos.config.extension-configs[1].data-id=mybatis.yml
spring.cloud.nacos.config.extension-configs[1].group=dev
spring.cloud.nacos.config.extension-configs[1].refresh=true

spring.cloud.nacos.config.extension-configs[2].data-id=other.yml
spring.cloud.nacos.config.extension-configs[2].group=dev
spring.cloud.nacos.config.extension-configs[2].refresh=true
```



**微服务的任何配置文件和配置信息都可以配置在nacos的配置中心**

**只需要在bootstrap.properties中配置位置即可**



## 7. API网关

**spring -cloud GateWay**

网关作为流量的入口，常用功能包括路由转发，权限校验，限流控制等。而spring-cloud-gateway作为Spring-Cloud的第二代网关框架，取代了Zuul网关

### 1. 创建网关微服务

导入依赖包

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-gateway</artifactId>
</dependency>
```

1. 给网关开启服务的注册与发现

```java
@EnableDiscoveryClient
```

2. 配置nacos-server地址

```properties
spring.cloud.nacos.server-addr=127.0.0.1:8848
spring.application.name=gulimall-gateway

server.port=88
```

3. 配置配置中心的配置文件

```properties
spring.application.name=gulimall-gateway

spring.cloud.nacos.server-addr=127.0.0.1:8848
spring.cloud.nacos.config.namespace=3d2e2224-9b52-4412-9ece-db33635a914c
spring.cloud.nacos.config.group=dev
```

4. 因为网关微服务也依赖了common微服务，但是并没有配置mybatis的相关信息，启动时会报错，需要手动去除mybatis的自动配置。

```java
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
```

5. 配置网关相应的配置

```yml
spring:
  cloud:
    gateway:
      routes:
        - id: test_route
          uri: https://www.baidu.com
          predicates:
            - Query=url,baidu

        - id: qq_route
          uri: https://www.qq.com
          predicates:
            - Query=url,qq
```



6. 然后访问路径

localhost:88/hello?url=qq  

localhost:88/hello?url=baidu



# 3. 前端基础

## 1. VSCode

使用vsCode进行前端的开发。

## 2. ES6

相当于后端jdk的版本

ECMAScript6.0 **是浏览器脚本语言的规范**

js只是规范的具体实现

