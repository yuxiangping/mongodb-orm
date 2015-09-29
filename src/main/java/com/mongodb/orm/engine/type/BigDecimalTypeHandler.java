package com.mongodb.orm.engine.type;

import java.math.BigDecimal;

/**
 * BigDecimal implementation of TypeHandler
 * 
 * @author: xiangping_yu
 * @data : 2014-7-25
 * @since : 1.5
 */
public class BigDecimalTypeHandler implements TypeHandler<BigDecimal> {

  @Override
  public Object getParameter(String name, BigDecimal instance) {
    return instance.longValue();
  }

  @Override
  public BigDecimal getResult(String name, Object instance, Object value) {
    if (value instanceof BigDecimal) {
      return (BigDecimal) value;
    }
    return new BigDecimal(value.toString());
  }
}
