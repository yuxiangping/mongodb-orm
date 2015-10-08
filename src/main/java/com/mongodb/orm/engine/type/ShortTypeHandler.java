package com.mongodb.orm.engine.type;

/**
 * Short implementation of TypeHandler
 * 
 * @author: xiangping_yu
 * @data : 2014-7-25
 * @since : 1.5
 */
public class ShortTypeHandler implements TypeHandler<Short>, ColumnHandler<Short> {

  @Override
  public Object getParameter(String name, Short instance) {
    return instance;
  }

  @Override
  public Short getResult(String name, Object instance, Object value) {
    if (value instanceof Number) {
      return ((Number) value).shortValue();
    }
    return Short.parseShort(String.valueOf(value));
  }

  @Override
  public Short resovleColumn(Object value) {
    if (value instanceof Number) {
      return ((Number) value).shortValue();
    }
    return Short.parseShort(String.valueOf(value));
  }

  @Override
  public Object resovleValue(Short target) {
    return target;
  }

}
