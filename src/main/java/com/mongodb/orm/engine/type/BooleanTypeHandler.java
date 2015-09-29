package com.mongodb.orm.engine.type;

/**
 * Boolean implementation of TypeHandler
 * 
 * @author: xiangping_yu
 * @data : 2014-7-25
 * @since : 1.5
 */
public class BooleanTypeHandler implements TypeHandler<Boolean>, ColumnHandler<Boolean> {

  @Override
  public Boolean getParameter(String name, Boolean instance) {
    return instance;
  }

  @Override
  public Boolean getResult(String name, Object instance, Object value) {
    if (value instanceof Boolean) {
      return (Boolean) value;
    }
    return Boolean.parseBoolean(value.toString());
  }

  @Override
  public Boolean resovleColumn(Object value) {
    if (value instanceof Boolean) {
      return (Boolean) value;
    }
    return Boolean.parseBoolean(value.toString());
  }

}
