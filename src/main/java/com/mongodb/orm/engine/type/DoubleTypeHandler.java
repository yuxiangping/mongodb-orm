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
    if (value instanceof Double) {
      return (Double) value;
    }
    return Double.parseDouble(value.toString());
  }

  @Override
  public Double resovleColumn(Object value) {
    if (value instanceof Double) {
      return (Double) value;
    }
    return Double.parseDouble(value.toString());
  }

}
