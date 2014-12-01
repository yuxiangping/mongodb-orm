mongodb-orm
===========

Mongodb used in many java application. </br>
But the mongo java driver is complex to use when my coding. </br>

Like this:

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

Some times, my query or update is complex.

```java
BasicDBObject query = new BasicDBObject();
query.put(key1, value1);
query.put(key2, value2);

BasicDBObject subQuery = new BasicDBObject();
subQuery.put(subKey1, subValue1);
subQuery.put(subKey2, subValue2);

query.put("$or", subQuery);
```

It working like ibatis or hibernate. Reduce dependent with code. 

```java
List<Model> list = mongoTemplet.queryOne("queryModelList", "eason");
```

```xml
<mapping id="model" class="test.mongodborm.Model">
		<property column="_id" name="id" />
		<property column="name" name="name" />
		<property column="time" name="time" value="0" />
		<property column="status" name="status" />
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
		<property column="childrens" name="childrens">
			<value class="test.mongodborm.Model$Child">
				<property column="name" name="name" />
				<property column="time" name="time" />
			</value>
		</property>
		<property column="data" name="data">
			<value class="java.util.Map">
				<property column="title" name="title" />
				<property column="content" name="content" />
			</value>
		</property>
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
	
	<!-- Model List1 -->
	<select id="queryModelList1" collection="test_sample">
		<query class="java.util.Map">
			<property column="name" name="name" />
			<property column="status" operate="in">
				<list type="number">0,1</list>
			</property>
			<property column="time">
				<text>{"$gte":200,"$lte":300}</text>
			</property>
			<property column="childrens" operate="elemMatch" name="search_key">
				<value class="java.lang.String">
					<property column="name" value="${value}" />
					<property column="time" operate="lt" value="${systime}" />
				</value>
			</property>
		</query>
		<field mapping="model" />
		<order class="java.util.Map">
			<property column="time" value="desc" />
		</order>
	</select>
	
	<!-- Model List3 -->
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
```


More configuration see  [sample.xml]



###For more information, please visit:

* Wiki
* Download
