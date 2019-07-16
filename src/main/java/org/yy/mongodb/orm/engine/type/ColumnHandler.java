package org.yy.mongodb.orm.engine.type;

/**
 * Interface for getting available column type.
 * @author yy
 */
public interface ColumnHandler<T> {
  
  T resovleColumn(Object value);
  
  Object resovleValue(T target);
  
}