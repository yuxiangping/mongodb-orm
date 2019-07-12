package org.yy.mongodb.orm.engine.type;

/**
 * Float implementation of TypeHandler
 * 
 * @author: xiangping_yu
 * @data : 2014-7-25
 * @since : 1.5
 */
public class FloatTypeHandler implements TypeHandler<Float> {

  @Override
  public Object getParameter(String name, Float instance) {
    return instance;
  }

  @Override
  public Float getResult(String name, Object instance, Object value) {
    if (value instanceof Number) {
      return ((Number) value).floatValue();
    }
    return Float.parseFloat(String.valueOf(value));
  }

}
