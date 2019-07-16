package org.yy.mongodb.orm.engine.type;

/**
 * Integer implementation of TypeHandler
 * @author yy
 */
public class IntegerTypeHandler implements TypeHandler<Integer> {

  @Override
  public Object getParameter(String name, Integer instance) {
    return instance;
  }

  @Override
  public Integer getResult(String name, Object instance, Object value) {
    if(value instanceof Number) {
      return ((Number) value).intValue();
    }
    return Integer.parseInt(String.valueOf(value));
  }

}
