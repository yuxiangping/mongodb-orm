package com.mongodb.orm.engine.type;

/**
 * Float implementation of TypeHandler
 * 
 * @author: xiangping_yu
 * @data : 2014-7-25
 * @since : 1.5
 */
public class FloatTypeHandler implements TypeHandler<Float> {

  @Override
  public Object getParameter(Float instance) {
    return instance;
  }

  @Override
  public Float getResult(Object instance, Object value) {
    if (value instanceof Float) {
      return (Float) value;
    }
    return Float.parseFloat(value.toString());
  }

}
