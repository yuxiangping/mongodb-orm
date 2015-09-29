package com.mongodb.orm.engine.type;

/**
 * String implementation of TypeHandler
 * @author: xiangping_yu
 * @data : 2014-7-25
 * @since : 1.5
 */
public class StringTypeHandler implements TypeHandler<String>, ColumnHandler<String> {

  @Override
  public Object getParameter(String name, String instance) {
    return instance;
  }

  @Override
  public String getResult(String name, Object instance, Object value) {
    return value.toString();
  }

  @Override
  public String resovleColumn(Object value) {
    return value.toString();
  }

}
