package test.mongodborm;


import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.mongodb.client.MongoClientTemplet;
import com.mongodb.client.MongoORMFactoryBean;

public class Test {
  
  public static void main(String[] args) {
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
  }
  
}
