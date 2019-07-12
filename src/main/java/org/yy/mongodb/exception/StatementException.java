package org.yy.mongodb.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * MongoDB Statement Error
 * @author yy
 */
public class StatementException extends RuntimeException {

  private static final long serialVersionUID = 3988706952509457604L;

  private static final Logger logger = LoggerFactory.getLogger(StatementException.class);

  private String message;

  public StatementException(String message) {
    super(message);
    this.message = message;
    logger.error("Mongodb orm statement error. Cause:  " + message);
  }

  public StatementException(String message, Throwable e) {
    super(message, e);
    this.message = message;
    logger.error("Mongodb orm statement error. Cause: " + message, e);
  }

  @Override
  public String getLocalizedMessage() {
    return message;
  }

}
