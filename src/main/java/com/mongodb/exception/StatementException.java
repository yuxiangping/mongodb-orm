package com.mongodb.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @info : MongoDB Statement Error
 * @author: xiangping_yu
 * @data : 2014-1-22
 * @since : 1.5
 */
public class StatementException extends RuntimeException {

  private static final long serialVersionUID = 3988706952509457604L;

  private static final Logger logger = LoggerFactory.getLogger(StatementException.class);

  private String msg;

  public StatementException(String msg) {
    super(msg);
    this.msg = msg;
    logger.error("MongoDB statement error. Cause:  " + msg);
  }

  public StatementException(String msg, Throwable e) {
    super(msg, e);
    this.msg = msg;
    logger.error("MongoDB statement error, Cause: " + msg, e);
  }

  @Override
  public String getLocalizedMessage() {
    return msg;
  }

}
