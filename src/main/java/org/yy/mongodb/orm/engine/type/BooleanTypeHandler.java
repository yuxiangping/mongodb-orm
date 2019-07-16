package org.yy.mongodb.orm.engine.type;

/**
 * Boolean implementation of TypeHandler
 * @author yy
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

  @Override
  public Object resovleValue(Boolean target) {
    return target;
  }

}
