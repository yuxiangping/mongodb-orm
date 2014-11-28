package com.mongodb.orm.engine.type;

/**
 * Integer implementation of TypeHandler
 * @author: xiangping_yu
 * @data : 2014-7-25
 * @since : 1.5
 */
public class IntegerTypeHandler implements TypeHandler<Integer> {

  @Override
  public Object getParameter(Integer instance) {
    return instance;
  }

  @Override
  public Integer getResult(Object instance, Object value) {
    if(value instanceof Integer) {
      return (Integer)value;
    }
    return Integer.parseInt(value.toString());
  }

}
