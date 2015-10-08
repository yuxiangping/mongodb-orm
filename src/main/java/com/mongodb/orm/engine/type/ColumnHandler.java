package com.mongodb.orm.engine.type;

/**
 * Interface for getting available column type.
 * @author: xiangping_yu
 * @data : 2014-7-30
 * @since : 1.5
 */
public interface ColumnHandler<T> {
  
  T resovleColumn(Object value);
  
  Object resovleValue(T target);
  
}