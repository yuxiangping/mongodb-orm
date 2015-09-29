# mongodb-orm简介

* Mongodb ORM是基于java的ORM框架，简化了SDK的使用，使代码变得更清晰、简单。 </br>
* 与Ibatis类似，将查询、执行语句封装在xml中，与代码隔离。简称MQL。 </br>


# 项目中使用

#### 加入mongodb orm的支持包

* 1. 添加jar包或maven支持

```
<dependency>
	<groupId>com.mongodborm</groupId>
  	<artifactId>mongodb-orm</artifactId>
  	<version>0.0.1-RELEASE</version>
</dependency>
```

* 2. 初始化mongodb templet

> spring中初始化

```xml
<bean id="mongoTemplet" class="com.mongodb.client.MongoClientTemplet">
    <property name="factory">
        <bean class="com.mongodb.client.MongoORMFactoryBean">
            <property name="dataSource">
                <bean class="com.mongodb.client.MongoDataSource">
                    <property name="nodeList" value="127.0.0.1:27017" />
					<property name="dbName" value="your db name" />
					<property name="userName" value="user name" />
					<property name="passWord" value="password" />
					<!-- 可使用默认值 -->
					<property name="connectionsPerHost" value="" />
					<property name="threadsAllowedToBlock" value="" />
					<property name="connectionTimeOut" value="" />
					<property name="maxRetryTime" value="" />
					<property name="socketTimeOut" value="" />
                </bean>
            </property>
            <property name="configLocations">
                <list>
					<value>classpath:mql/mongo-mql.xml</value>
				</list>
            </property>
        </bean>
    </property>
</bean>
```

> 代码初始化

```java
	try {
      Resource resource =  new ClassPathResource("sample-sql.xml");    
      MongoORMFactoryBean factory = new MongoORMFactoryBean();
      factory.setConfigLocations(new Resource[]{resource});
      factory.init();   
      MongoClientTemplet templet = new MongoClientTemplet();
      templet.setFactory(factory);
      templet.init();
    } catch(Exception e) {
      e.printStackTrace();
    }
```

#### 编写MQL

* Mapping

```xml
<mapping id="model" class="test.mongodborm.Model">
		<property column="_id" name="id" />
		<property column="name" name="name" />
		<property column="time" name="time" value="0" />
		<property column="status" name="status" />
</mapping>

<mapping id="extendModel" class="test.mongodborm.Model" extends="model">
	<property column="newProperty" name="newProperty" />
</mapping>
```

* select

```xml
<select id="queryModelList" collection="test_sample">
	<query class="java.lang.String">
		<property column="name" name="${value}" />
	</query>
	<field mapping="model" />
	<order>
		<property column="time" value="desc" />
	</order>
</select>
```

* update/findAndModify

```xml
<update id="updateModel" collection="test_sample">
	<query class="test.mongodborm.Model$Child">
		<property column="name" name="name" ignoreNull="true" />
		<property column="time" operate="gte" value="0" type="number" />
		<property column="status" operate="in">
			<list type="number">0,1</list>
		</property>
	</query>
	<action class="java.util.Map">
		<property column="name" name="name" operate="set" />
		<property column="status" operate="set" />
	</action>
</update>
```

* 有嵌套的查询	

```xml
<select id="queryModelList3" collection="test_sample">
	<query class="java.lang.String">
		<property column="_id" value="${value}" />
		<property column="time" value="0" type="number" />
	</query>
	<field class="java.util.Map">
		<property column="name" name="name" />
		<property column="parent" name="parent">
			<value class="test.mongodborm.Model$Parent">
				<property column="name" name="name" />
				<property column="child" name="child">
					<value class="test.mongodborm.Model$Child">
						<property column="name" name="name" />
						<property column="time" name="time" value="0" />
					</value>
				</property>
				<property column="data" name="data">
					<value class="java.util.Map">
						<property column="title" name="title" />
						<property column="content" name="content" />
					</value>
				</property>
			</value>
		</property>
		<property column="data" name="data">
			<value class="java.util.Map">
				<property column="title" name="title" />
				<property column="content" name="content" />
			</value>
		</property>
	</field>
	<order class="java.util.Map">
		<property column="time" name="time" value="desc" />
	</order>
</select>
```

#### Templet用法

```java
Model model = mongoTemplet.findOne("queryModelList", "yuxiangping");

List<Model> list = mongoTemplet.findOne("queryModelList", "");

Model model = new Model();
model.setTime(1L);
Map<String, String> action = new HashMap<String, String>();
action.put("name", "yuxiangping-update");
int update = mongoT emplet.update("updateModel", model, action);
```

> 更多的使用方法参见  [sample.xml](https://github.com/yuxiangping/mongodb-orm/blob/master/src/main/resources/sample-sql.xml)


# 资料

 [Wiki](https://github.com/yuxiangping/mongodb-orm/wiki)

 [Issues](https://github.com/yuxiangping/mongodb-orm/issues)
