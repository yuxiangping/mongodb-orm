package com.mongodb.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @info : MongoDB ORM Error
 * @author: xiangping_yu
 * @data : 2014-1-22
 * @since : 1.5
 */
public class MongoORMException extends RuntimeException {

  private static final long serialVersionUID = 3988706952509457604L;

  private static final Logger logger = LoggerFactory.getLogger(MongoORMException.class);

  private String msg;

  public MongoORMException(String msg) {
    super(msg);
    this.msg = msg;
    logger.error("MongoDB ORM error, msg:" + msg);
  }

  public MongoORMException(String msg, Throwable e) {
    super(msg, e);
    this.msg = msg;
    logger.error("MongoDB ORM error, msg:" + msg, e);
  }

  @Override
  public String getLocalizedMessage() {
    return msg;
  }
  
}
