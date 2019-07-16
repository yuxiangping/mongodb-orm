package org.yy.mongodb.orm.engine.type;

import java.math.BigDecimal;

/**
 * BigDecimal implementation of TypeHandler
 * @author yy
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
