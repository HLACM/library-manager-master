# 一、作品说明

![img](file:///C:\Users\86183\AppData\Local\Temp\ksohtml11592\wps1.jpg) 

这是一个简洁的东莞理工图书室管理系统，分为两个目标群体，分别是普通学生和管理员。学生可以使用的功能有借阅书籍、查看借书记录，归还书籍和查询书籍。管理员可以使用的功能有管理所有书籍、用户和查看借阅信息。

下面是可以使用的功能的演示：

 

登录页面：

![img](file:///C:\Users\86183\AppData\Local\Temp\ksohtml11592\wps2.jpg) 

分为学生和管理员登录

 

用户主页面：采用东莞理工学院的经典绿色，让系统更加有辨识度

![img](file:///C:\Users\86183\AppData\Local\Temp\ksohtml11592\wps3.jpg) 

 

功能演示：

![img](file:///C:\Users\86183\AppData\Local\Temp\ksohtml11592\wps4.jpg) 

![img](file:///C:\Users\86183\AppData\Local\Temp\ksohtml11592\wps5.jpg) 

![img](file:///C:\Users\86183\AppData\Local\Temp\ksohtml11592\wps6.jpg) 

![img](file:///C:\Users\86183\AppData\Local\Temp\ksohtml11592\wps7.jpg) 

 

 

 

管理员功能演示：

![img](file:///C:\Users\86183\AppData\Local\Temp\ksohtml11592\wps8.jpg) 

![img](file:///C:\Users\86183\AppData\Local\Temp\ksohtml11592\wps9.jpg) 

![img](file:///C:\Users\86183\AppData\Local\Temp\ksohtml11592\wps10.jpg) 

![img](file:///C:\Users\86183\AppData\Local\Temp\ksohtml11592\wps11.jpg) 

![img](file:///C:\Users\86183\AppData\Local\Temp\ksohtml11592\wps12.jpg) 

 

 

![img](file:///C:\Users\86183\AppData\Local\Temp\ksohtml11592\wps13.jpg) 

 

 

 

二、应用场景和解决的实际问题

帮助学校存储海量书籍，并分出各种类别来方便查找对应类型的书籍。学生可以在该系统上查询图书馆是否有自己喜欢的书籍，并在该系统上登记借阅书籍的信息。管理员也可以在该系统上录入新的图书，查看有多少书籍。

帮助学生解决无法查看图书馆是否有自己喜欢的书的问题，并提供了许多便捷的功能，帮助管理员更方便管理图书的数据。

 

 

 

三、技术开发方案

1. 前端： HTML5、CSS、JQuery、Thymeleaf、Layui、Ajax、
2. 后端 ：springboot、mybatis-plus、redis、mysql

模块包：

![img](file:///C:\Users\86183\AppData\Local\Temp\ksohtml11592\wps14.jpg) 

数据库表结构设计：

![img](file:///C:\Users\86183\AppData\Local\Temp\ksohtml11592\wps15.jpg) 

数据库一共有五张表，分别是admin，book_category，book，borrowwingbooks，user。表中记录着相对应的信息。

项目整体是一个springboot项目，模块包controller是项目的表现层，存放控制类，负责与前端接口进行交互，接收前端接口发出的请求，并返回相对应的数据给前端页面展示数据。模块包domain存放实体类，按照表结构进行一一映射。mapper是项目的数据层，使用mybatis-plus技术，对原有的mybatis进行了增强。模块包service是业务层，依托于数据层的方法实现各种业务需求。utils包中存放的是工具类，里面只有一个pages类，用于实现分页功能。

管理系统在拥有大量用户的情况下，在查看借阅记录查看书籍等对于数据库的频繁访问会造成速度变慢，数据库负载过大等一系列问题，所以本项目引用了Redis缓存技术，可以使得用户访问时先访问缓存中的数据，以提高访问速度。

在项目运行过程中用户的读和写操作的所有压力都由一台数据库承担，压力大。如果数据库服务器磁盘损坏则会造成数据丢失，单点故障等事故。所以本项目使用Mysql的主从复制技术配置两个数据库服务器，将数据库拆分为主库和从库，主库负责处理事务性的增删改操作，从库负责处理查询操作，能够有效的避免由数据更新导致的行锁，使得整个系统的查询性能得到极大的改善，实现读写分离。

 

3. 开发环境： IDEA 、SpringBoot 2.5.6、Maven、Git
4. 数据库：MySQL8

 

 

 

 

四、团队组成和分工

前端开发：曾繁晟、龙田华

前端开发使用Thymeleaf模板引擎，CSS框架：Layui，JavaScript库

：jQuery。数据传输：Ajax。

龙田华：负责开发初始化登录的页面，可以用户和管理员登录，还有登录后跳转的用户和管理员页面的基本布局(头部和底部的基本布局，左边导航栏),还有用户界面的功能接口（增删改查）开发。

曾繁晟：负责登录后跳转的用户和管理员页面的基本布局(头部和底部的基本布局，左边导航栏),管理员界面的功能接口（增删改查）开发。

 

后端开发：蔡华林

负责数据库表的设计以及后端接口的编写。能够根据系统需求合理设计数据库表结构，合理设计和实现各个接口。

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 