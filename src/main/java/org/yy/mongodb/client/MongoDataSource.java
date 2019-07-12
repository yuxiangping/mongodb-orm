package org.yy.mongodb.client;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;
import org.yy.mongodb.constant.MongoConstant;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

/**
 * The Mongodb pooled DataSource.
 * @author yy
 */
public class MongoDataSource implements InitializingBean {

  private static final Logger logger = LoggerFactory.getLogger(MongoDataSource.class);

  /**
   * Mongo database node list.
   * <p>
   * 192.168.1.0:80,192.168.1.1:8080,192.168.1.2:80
   */
  private String nodeList;

  /**
   * Mongo database name.
   */
  private String database;

  /**
   * Mongo database user name.
   */
  private String username;

  /**
   * Mongo database password.
   */
  private String password;

  /**
   * Mongo database connection per host.
   */
  private int connectionsPerHost = MongoConstant.CONNECTION_PER_HOST;

  /**
   * Mongo database threads allowed to block.
   */
  private int threadsAllowedToBlock = MongoConstant.THREADS_ALLOWED_TO_BLOCK;

  /**
   * Mongo database connection time out.
   */
  private int connectionTimeout = MongoConstant.CONNECTION_TIME_OUT;

  /**
   * Mongo database socket time out.
   */
  private int socketTimeout = MongoConstant.SOCKET_TIME_OUT;

  /**
   * Maximum time that a thread will block waiting for a connection.
   */
  private int maxWaitTime = MongoConstant.MAX_WAIT_TIME;
  /**
   * Mongo database client.
   */
  private MongoClient client;
  
  @Override
  public void afterPropertiesSet() throws Exception {
    init();
    logger.info("The mongo client is ready.");
  }
  
  public MongoClient getClient() {
    return client;
  }
  
  public String getDatabase() {
    return database;
  }

  public void init() {
    Assert.hasLength(nodeList, "Property 'nodeList' is note null. Couldn't get available mongo server address.");
    Assert.hasLength(username, "Property 'username' is note null.");
    Assert.hasLength(password, "Property 'password' is note null.");
    Assert.hasLength(database, "Property 'database' is note null.");

    initClient();
  }

  /**
   * Init mongodb client.
   */
  private void initClient() {
    MongoClientOptions.Builder build = new MongoClientOptions.Builder();  
    build.connectionsPerHost(Integer.valueOf(connectionsPerHost));  
    build.threadsAllowedToBlockForConnectionMultiplier(threadsAllowedToBlock);  
    build.connectTimeout(Integer.valueOf(connectionTimeout) * 1000);  
    build.maxWaitTime(Integer.valueOf(maxWaitTime) * 1000);
    build.socketTimeout(socketTimeout);
    MongoClientOptions options = build.build();  
    
    try {
      String[] nodes = nodeList.split(",");
      List<ServerAddress> serverAddressList = new ArrayList<ServerAddress>(nodes.length);
      for (String node : nodes) {
        if (!StringUtils.isBlank(node)) {
          String[] address = node.split(":", 2);
          String ip = address[0];
          int port = Integer.parseInt(address[1]);
          serverAddressList.add(new ServerAddress(ip, port));
          logger.info("The mongo server address [{}:{}] is inited.", ip, port);
        }
      }
          
      MongoCredential credential = MongoCredential.createScramSha1Credential(username, database, password.toCharArray());  
  
      client = new MongoClient(serverAddressList, credential, options);
    } catch (NumberFormatException e) {
      logger.error("The mongo server address is unavailable, because port is not a number. Cause:", e);
      throw new IllegalArgumentException("The mongo server address is unavailable, because port is not a number.");
    } catch (Exception e) {
      logger.error("The mongo client startup error. Cause:", e);
      throw new IllegalArgumentException("The mongo client startup error. ");
    }
  }

  public void setNodeList(String nodeList) {
    this.nodeList = nodeList;
  }

  public void setDatabase(String database) {
    this.database = database;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setConnectionsPerHost(int connectionsPerHost) {
    this.connectionsPerHost = connectionsPerHost;
  }

  public void setThreadsAllowedToBlock(int threadsAllowedToBlock) {
    this.threadsAllowedToBlock = threadsAllowedToBlock;
  }

  public void setConnectionTimeout(int connectionTimeout) {
    this.connectionTimeout = connectionTimeout;
  }

  public void setSocketTimeout(int socketTimeout) {
    this.socketTimeout = socketTimeout;
  }

  public void setMaxWaitTime(int maxWaitTime) {
    this.maxWaitTime = maxWaitTime;
  }

}
