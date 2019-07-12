package test.mongodborm;


import java.util.Arrays;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.yy.mongodb.client.MongoClientTemplet;
import org.yy.mongodb.client.MongoDataSource;
import org.yy.mongodb.client.MongoFactoryBean;

public class Test {
  
  public static void main(String[] args) {
    try {
      Resource resource =  new ClassPathResource("sample-sql.xml");
      
      MongoDataSource dataSource = new MongoDataSource();
      dataSource.setNodeList("192.168.1.100:1000,192.168.1.101:1000");
      dataSource.setUsername("username");
      dataSource.setPassword("password");
      dataSource.init();
      
      MongoFactoryBean factory = new MongoFactoryBean(dataSource);
      factory.setConfigLocations(new Resource[]{resource});
      factory.init();
      
      MongoClientTemplet templet = new MongoClientTemplet();
      templet.setFactory(factory);
      templet.init();
      
      List<Model> list = templet.find("findModel");
      System.out.println(Arrays.toString(list.toArray()));
    } catch(Exception e) {
      e.printStackTrace();
    }
  }
  
}
