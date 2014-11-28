package com.mongodb.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @info : MongoDB DataBase Operate Error
 * @author: xiangping_yu
 * @data : 2014-1-22
 * @since : 1.5
 */
public class MongoDaoException extends RuntimeException {

  private static final long serialVersionUID = -8383893860114962233L;

  private static final Logger logger = LoggerFactory.getLogger(MongoDaoException.class);

  private String sqlId;
  private String msg;

  public MongoDaoException(String sqlId, String msg) {
    super(msg);
    this.sqlId = sqlId;
    this.msg = msg;
    logger.error("MongoDao exception, sqlId: " + sqlId + ", error message:" + msg);
  }

  public MongoDaoException(String sqlId, String msg, Exception e) {
    super(msg, e);
    this.sqlId = sqlId;
    this.msg = msg;
    logger.error("MongoDao exception, sqlId: " + sqlId + ", error message:" + msg, e);
  }

  @Override
  public String getLocalizedMessage() {
    return sqlId + ":" + msg;
  }
  
}
