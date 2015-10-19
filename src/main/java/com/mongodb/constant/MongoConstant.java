package com.mongodb.constant;

/**
 * Constant for mongo db client
 * @author: xiangping_yu
 * @data : 2014-7-4
 * @since : 1.5
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
  public static final int CONNECTION_TIME_OUT = 10000;
  
  /**
   * Max auto connect retry time.
   */
  public static final int MAX_RETRY_TIME = 10000;
  
  /**
   * Socket time out.
   */
  public static final int SOCKET_TIME_OUT = 60000;
  
  /**
   * Auto connect retry.
   */
  public static final boolean AUTO_CONNECT_RETRY = true;
  
  /**
   * Query order by 'asc'.
   */
  public static final int ORDER_ASC = -1;
  
  /**
   * Query order by 'desc'.
   */
  public static final int ORDER_DESC = 1;
  
}
