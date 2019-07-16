package org.yy.mongodb.orm.engine.type;

/**
 * Float implementation of TypeHandler
 * @author yy
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
