
## 项目介绍
黑马程序员 [哔哩哔哩](https://www.bilibili.com/video/BV13a411q753/) 的教程项目，点击 [此处](https://pan.baidu.com/share/init?surl=bxEy2bHiCYQtouifUppsTA&pwd=1234) 下载资料<br/>
因前端代码文件太大，本仓库没有对其进行版本控制，运行前需自行添加前端代码

本项目(瑞吉外卖)是专门为餐饮企业(餐厅、饭店)定制的一款软件产品，包括系统管理后台和移动端应用两部分<br/>
其中系统管理后台主要提供给餐饮企业内部员工使用，可以对餐厅的菜品、套餐、订单等进行管理维护。移动端应用<br/>
主要提供给消费者使用，可以在线浏览菜品、添加购物车、下单等。

## 技术选型
用户层：H5、vue.js、elementUI、微信小程序<br/>
网关层：nginx<br/>
应用层：springboot、springMVC、springSession、spring、swagger、Lombok<br/>
数据层：MySQL、mybatis、mybatisPlus、Redis<br/>
（工具：git、maven、junit）

## 功能架构
移动端前台(H5、微信小程序)：手机号登录、菜品规格、微信登录、购物车、地址管理、下单、历史订单、菜品浏览<br/>
系统管理后台：员工登录、员工退出、员工管理、分类管理、订单管理、菜品管理、套餐管理、菜品口味管理

## 角色
后台系统管理员：登录后台管理系统，拥有后台系统中的所有操作权限<br/>
后台系统普通员工：登录后台管理系统，对菜品、套餐、订单等进行管理<br/>
C端用户：登录移动端应用，可以浏览菜品、添加购物车、设置地址、在线下单等

## 项目目录结构
```yaml
src 代码目录
 - main 
   - java Java代码
   - resource 资源文件夹
     - backend 后台系统前端代码
     - front 前台前端代码
.gitattributes github代码统计配置
.gitignore 版本控制忽略配置
README 说明文件
```
