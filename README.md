# mongodb-orm简介
===========
Mongodb ORM是基于java的ORM框架，简化了SDK的使用，使代码变得更清晰、简单。 </br>

Mongodb提供的SDK相当于JDBC，而Mongodb ORM则扮演了ibatis的角色。 </br>

现在已经有一些Mongodb的ORM框架，如morphia，为什么我还要写这个框架来重复造轮子呢？

先看原生的写法
<查询>
```java
MongoClient  client = new MongoClient(host, port );
DB db = client.getDB(dbName);
DBCollection collection = db.getCollection(collectionName);

BasicDBObject query = new BasicDBObject();

query.put(key1, value1);
query.put(key2, value2);

DBCursor cursor = collection.find(query)
while(cursor.hasNext()) {
    // do something
}
```

```java
BasicDBObject query = new BasicDBObject();
query.put(key1, value1);
query.put(key2, value2);

BasicDBObject subQuery = new BasicDBObject();
subQuery.put(subKey1, subValue1);
subQuery.put(subKey2, subValue2);

query.put("$or", subQuery);
```

-------------------

Morphia的写法 类似hibernate，如果用过hibernate会比较容易上手
```java
Mongo mongo = new Mongo(); 
Morphia morphia = new Morphia(); 
EntryDAO dao = new EntryDAO(morphia, mongo); 
QueryResults<Entry> res=dao.find(); 
Query<Entry> q = dao.createQuery(); 
List<Entry> list=q.field("name").equal("name1").asList(); 
for (Entry entry : list) { 
    // do something
} 
```

--------------

Mongodb ORM的写法
```java
List<Model> list = mongoTemplet.queryOne("queryModelList", "eason");
for (Model model : list) {
    // do something
}
```

* BUT  这里很重要 ORM的精华所在
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
	
<select id="queryModelList" collection="test_sample">
	<query class="java.lang.String">
		<property column="name" name="${value}" />
	</query>
	<field mapping="model" />
	<order>
		<property column="time" value="desc" />
	</order>
</select>

<!-- Update model1 -->
<update id="updateModel1" collection="test_sample">
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

	
<!-- 当然 根据你的查询的复杂度  你也可以这样  Model List3 -->
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


* 更多的使用方法参见  [sample.xml]



### 更多的信息请访问这里

* Wiki
* Issues
 
* 欢迎成为commiter，共同完善该项目
