package org.yy.mongodb.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * MongoDB ORM Error
 * 
 * @author yy
 */
public class MongoORMException extends RuntimeException {

  private static final long serialVersionUID = 3988706952509457604L;

  private static final Logger logger = LoggerFactory.getLogger(MongoORMException.class);

  private String message;

  public MongoORMException(String message) {
    super(message);
    this.message = message;
    logger.error("Mongodb orm error. Cause:" + message);
  }

  public MongoORMException(String message, Throwable e) {
    super(message, e);
    this.message = message;
    logger.error("Mongodb orm error. Cause:" + message, e);
  }

  @Override
  public String getLocalizedMessage() {
    return message;
  }

}
