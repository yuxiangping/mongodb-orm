package org.yy.mongodb.orm.engine.type;

/**
 * Double implementation of TypeHandler
 * @author yy
 */
public class DoubleTypeHandler implements TypeHandler<Double>, ColumnHandler<Double> {

  @Override
  public Object getParameter(String name, Double instance) {
    return instance;
  }

  @Override
  public Double getResult(String name, Object instance, Object value) {
    if (value instanceof Number) {
      return ((Number) value).doubleValue();
    }
    return Double.parseDouble(String.valueOf(value));
  }

  @Override
  public Double resovleColumn(Object value) {
    if (value instanceof Number) {
      return ((Number) value).doubleValue();
    }
    return Double.parseDouble(String.valueOf(value));
  }

  @Override
  public Object resovleValue(Double target) {
    return target;
  }

}
