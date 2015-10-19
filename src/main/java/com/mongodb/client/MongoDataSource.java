package com.mongodb.client;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.constant.MongoConstant;

/**
 * Mongodb data source
 * 
 * @author: xiangping_yu
 * @data : 2014-7-4
 * @since : 1.5
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
  private String dbName;

  /**
   * Mongo database user name.
   */
  private String userName;

  /**
   * Mongo database password.
   */
  private String passWord;

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
  private int connectionTimeOut = MongoConstant.CONNECTION_TIME_OUT;

  /**
   * Mongo database max retry time for connection failed.
   */
  private int maxRetryTime = MongoConstant.MAX_RETRY_TIME;

  /**
   * Mongo database socket time out.
   */
  private int socketTimeOut = MongoConstant.SOCKET_TIME_OUT;

  /**
   * Mongo database client.
   */
  private Mongo client;

  public Mongo getClient() {
    return client;
  }

  public DB getDB() {
    return client.getDB(dbName);
  }
  
  @Override
  public void afterPropertiesSet() throws Exception {
    init();
    logger.info("The mongo client is ready.");
  }
  
  private void init() {
    Assert.hasLength(nodeList, "Couldn't get available mongo server address.");
    Assert.hasLength(userName, "Property 'userName' is note null.");
    Assert.hasLength(dbName, "Property 'dbName' is note null.");
    Assert.hasLength(passWord, "Property 'passWord' is note null.");

    initClient();
  }

  /**
   * Init mongodb client.
   */
  private void initClient() {
    MongoClientOptions.Builder builder = MongoClientOptions.builder().connectionsPerHost(connectionsPerHost)
            .threadsAllowedToBlockForConnectionMultiplier(threadsAllowedToBlock).connectTimeout(connectionTimeOut)
            .socketTimeout(socketTimeOut).autoConnectRetry(MongoConstant.AUTO_CONNECT_RETRY).maxAutoConnectRetryTime(maxRetryTime);

    MongoCredential credential = MongoCredential.createMongoCRCredential(userName, dbName, passWord.toCharArray());

    String[] nodes = nodeList.split(",");
    List<ServerAddress> serverAddressList = new ArrayList<ServerAddress>(nodes.length);
    try {
      for (String node : nodes) {
        String[] nodeInfo = node.split(":", 2);
        String ip = nodeInfo[0];
        int port = Integer.parseInt(nodeInfo[1]);
        serverAddressList.add(new ServerAddress(ip, port));

        logger.info("The mongo server address ["+ip+":"+port+"] is inited.");
      }
    } catch (NumberFormatException e) {
      logger.error("The mongo server address is unavailable, because port is not a number. Cause:", e);
      throw new IllegalArgumentException("The mongo server address is unavailable, because port is not a number.");
    } catch (UnknownHostException e) {
      logger.error("The mongo server address is unavailable, because host is error. Cause:", e);
      throw new IllegalArgumentException("The mongo server address is unavailable, because host is error.");
    }

    client = new MongoClient(serverAddressList, Arrays.asList(credential), builder.build());

    DB dbpool = client.getDB(dbName);
    if (!dbpool.authenticate(userName, passWord.toCharArray())) {
      throw new IllegalArgumentException("Connection failed because of authentication failure, please check you username and password.");
    }
  }
  
  public void setNodeList(String nodeList) {
    this.nodeList = nodeList;
  }

  public void setDbName(String dbName) {
    this.dbName = dbName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public void setPassWord(String passWord) {
    this.passWord = passWord;
  }

  public void setConnectionsPerHost(Integer connectionsPerHost) {
    this.connectionsPerHost = connectionsPerHost;
  }

  public void setThreadsAllowedToBlock(Integer threadsAllowedToBlock) {
    this.threadsAllowedToBlock = threadsAllowedToBlock;
  }

  public void setConnectionTimeOut(int connectionTimeOut) {
    this.connectionTimeOut = connectionTimeOut;
  }

  public void setMaxRetryTime(int maxRetryTime) {
    this.maxRetryTime = maxRetryTime;
  }

  public void setSocketTimeOut(int socketTimeOut) {
    this.socketTimeOut = socketTimeOut;
  }

}
