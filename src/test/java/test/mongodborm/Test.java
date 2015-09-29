package test.mongodborm;


import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.mongodb.client.MongoORMFactoryBean;

public class Test {
  
  public static void main(String[] args) {
    try {
      MongoORMFactoryBean factory = new MongoORMFactoryBean();
      
      Resource resource =  new ClassPathResource("sample-sql.xml");
      
      factory.setConfigLocations(new Resource[]{resource});
      
      factory.afterPropertiesSet();
      System.out.println("test is finished.");
    } catch(Exception e) {
      e.printStackTrace();
    }
  }
  
}
