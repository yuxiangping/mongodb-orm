package org.yy.mongodb.orm.engine.type;

/**
 * Long implementation of TypeHandler
 * @author: xiangping_yu
 * @data : 2014-7-25
 * @since : 1.5
 */
public class LongTypeHandler implements TypeHandler<Long>, ColumnHandler<Long> {

  @Override
  public Object getParameter(String name, Long instance) {
    return instance;
  }

  @Override
  public Long getResult(String name, Object instance, Object value) {
    if(value instanceof Number) {
      return ((Number) value).longValue();
    }
    return Long.parseLong(String.valueOf(value));
  }

  @Override
  public Long resovleColumn(Object value) {
    if(value instanceof Number) {
      return ((Number) value).longValue();
    }
    return Long.parseLong(String.valueOf(value));
  }

  @Override
  public Object resovleValue(Long target) {
    return target;
  }

}
