package test.mongodborm;


import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.yy.mongodb.client.MongoClientTemplet;
import org.yy.mongodb.client.MongoDataSource;
import org.yy.mongodb.client.MongoFactoryBean;

public class Test {
  
  public static void main(String[] args) {
    try {
      InputStream resource =  Test.class.getResourceAsStream("sample-sql.xml");
      
      MongoDataSource dataSource = new MongoDataSource();
      dataSource.setNodeList("192.168.1.100:1000,192.168.1.101:1000");
      dataSource.setDatabase("test");
      dataSource.setUsername("username");
      dataSource.setPassword("password");
      dataSource.init();
      
      MongoFactoryBean factory = new MongoFactoryBean(dataSource);
      factory.setConfigLocations(new Resource[] {new InputStreamResource(resource)});
      factory.init();
      
      MongoClientTemplet templet = new MongoClientTemplet();
      templet.setFactory(factory);
      templet.init();
      
      List<Model> list = templet.find("test.queryModelList1");
      System.out.println(Arrays.toString(list.toArray()));
    } catch(Exception e) {
      e.printStackTrace();
    }
  }
  
}
