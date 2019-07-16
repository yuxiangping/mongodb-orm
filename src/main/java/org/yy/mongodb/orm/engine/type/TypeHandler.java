package org.yy.mongodb.orm.engine.type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Interface for getting data into, and out of a mapped statement.
 * @author yy
 */
public interface TypeHandler<T> {

  Logger logger = LoggerFactory.getLogger(TypeHandler.class);
  
  /**
   * Gets a column from a result set.
   * @param name Property name
   * @param instance Result instance.
   * @param value Database filed value
   * @return Result value.
   */
  T getResult(String name, Object instance, Object value);

  /**
   * Get a parameter on a prepared statement.
   * @param name Property name
   * @param instance Parameter instance.
   * @return Statement value.
   */
  Object getParameter(String name, T instance);
}