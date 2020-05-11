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
	
	[mysqlId]
	init_connect='SET collation_connection = utf_unicode_ci'
	init_connect='SET NAMES utf8'
	character-set-server=utf8
	collation-server=utf_unicode_ci
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

## 6. 创建项目微服务

商品服务，仓储服务，订单服务，优惠卷服务，用户服务

共同：

	1.  web ，openfeign
 	2.  每个服务的包名统一：com.atguigu.gulimall.xxx(product/order/ware/coupon/member)
 	3.  模块名：