package org.yy.mongodb.orm.engine.type;

/**
 * String implementation of TypeHandler
 * @author yy
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

  @Override
  public Object resovleValue(String target) {
    return target;
  }

}
