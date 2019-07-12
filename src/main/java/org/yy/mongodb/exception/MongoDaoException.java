package org.yy.mongodb.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * MongoDB DataBase Operate Error
 * 
 * @author yy
 */
public class MongoDaoException extends RuntimeException {

  private static final long serialVersionUID = -8383893860114962233L;

  private static final Logger logger = LoggerFactory.getLogger(MongoDaoException.class);

  private String mqlId;
  private String message;

  public MongoDaoException(String mqlId, String message) {
    super(message);
    this.mqlId = mqlId;
    this.message = message;
    logger.error("Mongodb orm dao [{}] error. Cause:{}.", mqlId, message);
  }

  public MongoDaoException(String mqlId, String msg, Exception e) {
    super(msg, e);
    this.mqlId = mqlId;
    this.message = msg;
    logger.error("Mongodb orm dao [{}] error. Cause:{}.", mqlId, message, e);
  }

  @Override
  public String getLocalizedMessage() {
    return mqlId + ":" + message;
  }

}
