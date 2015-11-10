# db-helper
数据库辅助工具
==============
实现数据库的读写分离。

#使用方式：
##spring容器支持aop：
	在配置文件中beans节点下增加  xmlns:aop="http://www.springframework.org/schema/aop"
	xsi:schemaLocation中增加 http://www.springframework.org/schema/aop  		http://www.springframework.org/schema/aop/spring-aop-3.0.xsd
	增加 <aop:aspectj-autoproxy />

##导入cachehelper包
	已经在私库nexus上发布 com.meila.meigou.dbhelper 
	在spring中注入 <context:component-scan base-package="com.meila.meigou.dbhelper" />

##数据库配置
```	
请参考test/resources下文件
```
##注意事项
	默认的从库分配方式是顺序分发到每个库，实现放在SequenceDataSourceSelector。
	需要进行特殊的分配方式请实现DataSourceSelector接口。
	
