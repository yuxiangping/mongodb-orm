package com.mongodb.client;

import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.NestedIOException;
import org.springframework.core.io.Resource;

import com.mongodb.orm.MqlMapConfigParser;
import com.mongodb.orm.MqlMapConfiguration;

/**
 * Mongo database orm factory bean.
 * 
 * @author: xiangping_yu
 * @data : 2014-7-11
 * @since : 1.5
 */
public class MongoORMFactoryBean implements InitializingBean {

  private static final Logger logger = LoggerFactory.getLogger(MongoORMFactoryBean.class);
  
  /**
   * Xml config for orm.
   */
  private Resource[] configLocations;

  /**
   * Mongo data base data source.
   */
  private MongoDataSource dataSource;
  
  private MqlMapConfigParser configParser;

  @Override
  public void afterPropertiesSet() throws Exception {
    init();
  }

  public void init() throws Exception {
    MqlMapConfigParser configParser = new MqlMapConfigParser();
    for (Resource configLocation : configLocations) {
      InputStream is = configLocation.getInputStream();
      try {
        logger.debug("Mql configuration parse resource file. File name is "+configLocation.getFilename());
        configParser.parse(is);
        logger.debug("Mql configuration parse resource file finished. File name is "+configLocation.getFilename());
      } catch (RuntimeException ex) {
        throw new NestedIOException("Failed to parse config configuration. File name is " + configLocation.getFilename(), ex.getCause());
      }
    }
    configParser.validateMapping();
    this.configParser = configParser;
    logger.info("Mongodb orm framework has ready.");
  }
  
  public MqlMapConfiguration getConfiguration() {
    return configParser.getConfiguration();
  }
  
  public MongoDataSource getDataSource() {
    return dataSource;
  }

  public void setDataSource(MongoDataSource dataSource) {
    this.dataSource = dataSource;
  }

  public void setConfigLocations(Resource[] configLocations) {
    this.configLocations = configLocations;
  }
  
}
