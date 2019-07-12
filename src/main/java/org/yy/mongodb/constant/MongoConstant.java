package org.yy.mongodb.constant;

/**
 * Constant for mongo db client.
 * 
 * @author yy
 */
public class MongoConstant {

  /**
   * Connection per host.
   */
  public static final int CONNECTION_PER_HOST = 10;

  /**
   * Threads allowed to block for connection multiplier.
   */
  public static final int THREADS_ALLOWED_TO_BLOCK = 5;

  /**
   * Connection time out.
   */
  public static final int CONNECTION_TIME_OUT = 5000;

  /**
   * Socket time out.
   */
  public static final int SOCKET_TIME_OUT = 10000;

  /**
   * Thread will block waiting for a connection.
   */
  public static final int MAX_WAIT_TIME = 10000;
  
  /**
   * Query order by 'asc'.
   */
  public static final int ORDER_ASC = -1;

  /**
   * Query order by 'desc'.
   */
  public static final int ORDER_DESC = 1;

}
