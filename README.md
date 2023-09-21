# homeworkUploadBackend
---
layout: post
title: 作业上传及查看平台的使用
subtitle: 作业上传及查看平台的使用
date: 2023-09-07
author: Gavin
header-img: img/post-bg-cook.jpg
catalog: true
tags:
  - 博客
---
[前端项目](https://github.com/vectorstone/homeworkUploadFront.git)
[后端项目](https://github.com/vectorstone/homeworkUploadBackend.git)
# 使用说明
## 背景: 
传统作业的提交是按照各个小组的方式来提交,压缩包传递过程中容易出现问题,或者通过微信之类的传递存在文件大小限制等等诸多不便之处，因此简单的开发了一个作业上传以及查看的平台,使用方法如下:

## 学生端: 
1.访问如下地址: http://你的服务器的ip地址

2.点击注册
![](https://obsidiantuchuanggavin.oss-cn-beijing.aliyuncs.com/img/Pasted%20image%2020230920145329.png)

3.会弹出如下所示的注册页面
username英文及字母组成
name : 请务必填写真实的姓名(方便图片的查询展示,以及方便老师查看)
password: 密码 (MD5加盐加密了的,放心,我也看不到)
组别: 就是你在的第几组,例如 3
头像链接: 如果有自己觉得好看的图片,可以把链接粘贴进来,如果不想设置也没关系,我设置了个默认的

![](https://obsidiantuchuanggavin.oss-cn-beijing.aliyuncs.com/img/Pasted%20image%2020230920144514.png)
4.填写完成后点击提交

5.使用刚注册的账户进行登录
学生用户只有用户管理和作业上传两个菜单
![](https://obsidiantuchuanggavin.oss-cn-beijing.aliyuncs.com/img/Pasted%20image%2020230907225857.png)

6.上传作业
点击左侧作业管理菜单
![](https://obsidiantuchuanggavin.oss-cn-beijing.aliyuncs.com/img/Pasted%20image%2020230907225938.png)

7.点击upload
弹出图示的对话框,在第一个输入框里面填入作业的名称(必填,后端是以此作为文件名重命名后上传oss的)
点击下面的click to upload上传文件
![](https://obsidiantuchuanggavin.oss-cn-beijing.aliyuncs.com/img/Pasted%20image%2020230920144606.png)



8.图片的查看
回到用户管理页面,点击最右侧书籍一样的图表可以查看图片
![](https://obsidiantuchuanggavin.oss-cn-beijing.aliyuncs.com/img/Pasted%20image%2020230920144729.png)

如下图所示,如果觉得图太小的话,可以点击图片会放大显示
![](https://obsidiantuchuanggavin.oss-cn-beijing.aliyuncs.com/img/Pasted%20image%2020230920144752.png)


## 老师端
使用管理员账户登录,菜单比学生端多很多,也可以上传图片,查看图片和学生端的是一样的
注意: 老师的管理员账号用户删除用户的权限谨慎使用
![](https://obsidiantuchuanggavin.oss-cn-beijing.aliyuncs.com/img/Pasted%20image%2020230920144858.png)

在首页里面会默认的进行图片的轮播,方便老师查看
![](https://obsidiantuchuanggavin.oss-cn-beijing.aliyuncs.com/img/Pasted%20image%2020230920144947.png)

作业提交情况里面可以勾选要查看的作业提交情况的作业,点击搜索会显示出来所有的未提交作业的同学
![](https://obsidiantuchuanggavin.oss-cn-beijing.aliyuncs.com/img/Pasted%20image%2020230920145105.png)

# 项目部署
## 说明
初始状态只有admin一个管理员用户,默认密码: 123456
## 环境需求
JDK1.8 , Maven3.6.3 , MySQL8.0 , Redis , 阿里云oss
## 配置文件修改
将文件clone到本地后请修改如下的配置文件
/homeworkUploadBackend/blob/master/service-system/src/main/resources/application-dev.yaml
### 修改MySQL数据库连接信息
配置文件中我使用了jasypt来保护敏感信息,建议大家都这么做,具体的使用步骤参考我的这篇post
[jasypt加密工具的使用](http://wswxgpp.eu.org/2023/09/07/springboot%E9%A1%B9%E7%9B%AE%E4%B8%AD%E9%81%BF%E5%85%8D%E6%9A%B4%E9%9C%B2%E6%95%8F%E6%84%9F%E4%BF%A1%E6%81%AF%E7%9A%84%E6%96%B9%E6%B3%95/)
### 修改redis数据库的连接信息
也使用了加密的处理,参考上面加密工具的使用来进行设置
### aliyun oss
需要创建你自己的bucket,然后配置到配置文件中,注意最新的阿里云oss的API中推荐通过读取本地环境变量的方式来获取Access Token,具体的操作步骤不赘述了
## 前端项目部署
clone下来前端项目后,在项目的根目录下执行如下命令:
```sh
npm run build:prod # 打包的时候运行的命令
```
之后会在目标的targer文件夹里面生成对应的打包好的文件(后面这些文件需要上传到nginx的html目录里面)
## nginx
需要部署nginx反向代理服务器,通过docker的方式或者编译安装的方式都可以,使用的过程中没有什么区别
### nginx配置文件如下
```nginx
server {                                                                          
	listen 80;                                                        
	server_name 服务器的ip地址;
  
client_max_body_size 20m;
                       
	location / {                                                      
			root /var/www/html;                                            
			index index.html index.htm;                               
	}

 
  location /prod-api/ {                                                      
  		#root /www/vod;                                            
  		#index index.html index.htm;
      proxy_pass http://127.0.0.1:8800/;                               
  }
  
 
	error_page 500 502 503 504 /50x.html;                             
	location = /50x.html{                                             
			root html;                                                
	}                                                                 
}

```

## 数据库表
请将项目根目录下的SQL脚本导入到你的本地的数据库中
