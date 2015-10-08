package com.mongodb.orm.engine.type;

/**
 * Double implementation of TypeHandler
 * 
 * @author: xiangping_yu
 * @data : 2014-7-25
 * @since : 1.5
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
