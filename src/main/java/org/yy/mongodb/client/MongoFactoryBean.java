package org.yy.mongodb.client;

import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.NestedIOException;
import org.springframework.core.io.Resource;
import org.springframework.dao.support.PersistenceExceptionTranslator;
import org.springframework.data.mongodb.core.MongoDbFactorySupport;
import org.springframework.data.mongodb.core.MongoExceptionTranslator;
import org.yy.mongodb.orm.MqlMapConfigParser;
import org.yy.mongodb.orm.MqlMapConfiguration;

import com.mongodb.ClientSessionOptions;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoDatabase;

/**
 * Mongo database orm factory bean.
 * 
 * @author yy
 */
public class MongoFactoryBean extends MongoDbFactorySupport<MongoClient> implements InitializingBean, DisposableBean {

  private static final Logger logger = LoggerFactory.getLogger(MongoFactoryBean.class);

  /**
   * Xml config for orm.
   */
  private Resource[] configLocations;

  /**
   * Mongo data base data source.
   */
  private MongoDataSource dataSource;

  private MqlMapConfigParser configParser;

  public MongoFactoryBean(MongoDataSource dataSource) {
    this(dataSource.getClient(), dataSource.getDatabase(), false, new MongoExceptionTranslator());
    this.dataSource = dataSource;
  }
  
  private MongoFactoryBean(MongoClient client, String database, boolean created, PersistenceExceptionTranslator translator) {
    super(client, database, created, translator);
  }
  
  @Override
  public void afterPropertiesSet() throws Exception {
    init();
  }

  @SuppressWarnings("deprecation")
  @Override
  public DB getLegacyDb() {
    return dataSource.getClient().getDB(dataSource.getDatabase());
  }

  @Override
  public ClientSession getSession(ClientSessionOptions options) {
    return dataSource.getClient().startSession();
  }

  @Override
  protected MongoDatabase doGetMongoDatabase(String dbName) {
    return dataSource.getClient().getDatabase(dbName);
  }

  @Override
  protected void closeClient() {
    dataSource.getClient().close();
  }
  
  public void init() throws Exception {
    MqlMapConfigParser configParser = new MqlMapConfigParser();
    for (Resource configLocation : configLocations) {
      InputStream is = configLocation.getInputStream();
      try {
        configParser.parse(is);
        logger.info("Mql configuration parse resource file. File name is " + configLocation.getFilename());
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
