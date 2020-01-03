

<div align="center">
<div style="height:256px; width:256px; text-align: center;">
<img src="https://gitee.com/li_haodong/picture_management/raw/master/pic/WechatIMG9.png" height="256" width="256">
</div>
 <a href="http://spring.io/projects/spring-boot">
            <img src="https://img.shields.io/badge/spring--boot-2.2.2.RELEASE-green.svg" alt="spring-boot">
       </a>
    <a href="http://mp.baomidou.com">
            <img src="https://img.shields.io/badge/mybatis--plus-3.2.0-blue.svg" alt="mybatis-plus">
    </a>  
    <a href="https://spring.io/projects/spring-security">
            <img src="https://img.shields.io/badge/security-5.2.1-blue.svg" alt="spring security">
    </a>
</div>

### 系统介绍
Pre基于Spring Boot 、Spring Security 、Vue的前后端分离的的RBAC权限管理系统，项目支持数据权限管理，支持后端配置菜单动态路由, 第三方社交登录,努力做最简洁的后台管理系统。

- 基于 Spring Boot 2.2.2 、Spring Security 的RBAC权限管理系统
- 基于 Vue UI框架 Element-ui
- 作为 Mybatis Plus 学习与生产实践
- 作为 Lambda 、Stream Api 的学习与生产实践
- 作为 Spring Social 的学习与生产实践

### 扫码关注我的公众号和微信技术交流群
<table>
    <tr>
        <td>扫码添加作者</td>
        <td><img src="https://gitee.com/li_haodong/picture_management/raw/master/pic/WechatIMG2.jpeg" width="120"/></td>
        <td>我的公众号</td>
        <td><img src="https://gitee.com/li_haodong/picture_management/raw/master/pic/qrcode_for_gh_99ee464aac4f_258.jpg" width="120"/></td>
        <td>扫码邀请入群</td>
        <td><img src="https://gitee.com/li_haodong/picture_management/raw/master/pic/WechatIMG320.jpeg" width="120"/></td>
    </tr>
</table>

### 文档与教程
**使用文档** [https://www.kancloud.cn/xiaodong_it/pre_docs/1449633](https://www.kancloud.cn/xiaodong_it/pre_docs/1449633)

**从零搭建RBAC权限系统** [https://www.kancloud.cn/xiaodong_it/pre_docs/1449653](https://www.kancloud.cn/xiaodong_it/pre_docs/1449653)

### 系统体验

**体验地址**  [https://pre.52lhd.com/](https://pre.52lhd.com/)

**账号密码** ```admin/123456```

### 项目源码

|     |   后端源码  |   前端源码  |
|---  |--- | --- |
|  GitHub   |  https://github.com/LiHaodong888/pre   |  https://github.com/LiHaodong888/pre-ui   |
|  码云   |  https://gitee.com/li_haodong/pre   |  https://gitee.com/li_haodong/pre-ui   |

### 项目特点
- 前后端分离架构
- 代码注释丰富，极其简洁风格，上手快易理解
- 采用Restfull API 规范开发
- 统一异常拦截，友好的错误提示
- 基于注解 + Aop切面实现全方位日记记录系统
- 基于Mybatis拦截器 + 策略模式实现数据权限控制
- Jwt Token 鉴权机制 
- 提供解决前后分离第三方社交登录方案 
- Spring Social集成Security实现第三方社交登录
- 基于Mybatis-Plus实现SaaS多租户功能  
- 基于注解实现数据脱敏,防隐私


### 基本功能

- 用户管理：该功能主要完成系统用户配置，提供用户基础配置(用户名、手机号邮箱等)以及部门角色等
- 角色管理：权限菜单分配，以部门基础设置角色的数据权限范围
- 菜单管理：后端配置实现菜单动态路由，支持多级菜单，操作权限，按钮权限标识等
- 部门管理：配置系统组织架构，树形表格展示，可随意调整上下级
- 岗位管理：根据部门配置所属职位
- 字典管理：对系统中经常使用的一些较为固定的数据进行维护，如：状态(正常/异常)，性别(男/女)等
- 操作日志：记录用户操作的日志
- 异常日志：记录异常日志，方便开发人员定位错误 
- 代码生成：根据数据库快速生成entity、xml、mapper、sevice、serviceImpl基础代码,减少70%以上代码任务 
- 社交登录: 目前支持QQ登录、微信登录、码云登录、GitHub登录

#### 功能预告
任务调度 、免费图床 、七牛云存储 、持续集成

### 更新日志
2019年8月5日更新  
修复bug  
1.修复点击异常日志LOGO以及图片变大  
2.菜单修改URL不成功  
3.登录图片验证码不支持多人登录  
新增功能  
1.项目重构 分包合理  
2.提供前后分离第三方登录解决方案  
3.第三方登录  
4.手机号登录  
5.社交账号管理  
6.代码生成(初版)   

2019年8月14日更新  
1.新增多租户功能  
2.前端社交搜索功能完善  
3.修复后端记录日志并发问题  
4.后端内部包做整理  


### Pre X微服务架构  
Pre微服务版本Spring Cloud、Spring Cloud Alibaba、Oauth2  
项目地址: https://gitee.com/kaiyuantuandui/prex

### 系统预览
![pre系统预览图](https://gitee.com/li_haodong/picture_management/raw/master/pic/1565014094683.jpg)
![pre系统预览图](https://gitee.com/li_haodong/picture_management/raw/master/pic/1565014790417.jpg)
![pre系统预览图](https://gitee.com/li_haodong/picture_management/raw/master/pic/1561385170695.jpg)
![pre系统预览图](https://gitee.com/li_haodong/picture_management/raw/master/pic/1561385201508.jpg)
![pre系统预览图](https://gitee.com/li_haodong/picture_management/raw/master/pic/1561385224488.jpg)
![pre系统预览图](https://gitee.com/li_haodong/picture_management/raw/master/pic/1561385241925.jpg)
![pre系统预览图](https://gitee.com/li_haodong/picture_management/raw/master/pic/1561385273370.jpg)
![pre系统预览图](https://gitee.com/li_haodong/picture_management/raw/master/pic/1561385291674.jpg)
![pre系统预览图](https://gitee.com/li_haodong/picture_management/raw/master/pic/1565014818254.jpg)
### 关于作者
有问题可以加我(备注:pre)
<div style="height:300px; width:256px;">
<img src="https://gitee.com/li_haodong/picture_management/raw/master/pic/qrcode.png" height="300" width="256">
<img src="https://gitee.com/li_haodong/picture_management/raw/master/pic/WechatIMG2.jpeg" height="300" width="256">
</div>