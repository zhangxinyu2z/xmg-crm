# xmg_crm

换名字了？以前叫xmg，现在是dmg，网站：https://www.520it.com/dmg

## 重点

1.aop是使用代理模式来生成接口的实现类，以service举例，假如使用了aop，就不可使用@autowired直接注入service的实现类，因代理类和实现类类型是不一致的，
反之，未使用aop来代理service，则可以直接注入到ServiceImpl中

## 调试问题

### 数据库连接问题

1. 日志：`create connection error, url: jdbc:mysql:///xmg_crm?createDatabaseIfNotExist=true&amp;useUnicode=true&amp;characterEncoding=UTF-8
   java.sql.SQLException: java.lang.ClassCastException: java.math.BigInteger cannot be cast to java.lang.Long`，原因：
   mysql驱动jar版本不适配
2. mysql地址报错,原链接：`jdbc:mysql://localhost:3306/mybatis?
   serverTimezone=UTC&amp;useSSL=false&amp;useUnicode=true&amp;characterEncoding=utf8`，去掉`&amp;`替换为`&`
3. 


## 扩展

1.使用tomcat7插件：idea可以在Configurations中新增一个maven配置，在run中填入tomcat7:run，这样就可以debug的方式启动项目。
2.每个request.getSession()都会创建一个session，若客户端请求携带了服务端传递的jSessionId，那么request.getSession()
就会获取该sessionId对应的session，否则创建一个新的session