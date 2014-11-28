package com.mongodb.orm.engine.type;

/**
 * Boolean implementation of TypeHandler
 * @author: xiangping_yu
 * @data : 2014-7-25
 * @since : 1.5
 */
public class EnumTypeHandler implements TypeHandler<Enum<?>> {

  @Override
  public Object getParameter(Enum<?> instance) {
    return instance.toString();
  }

  @Override
  public Enum<?> getResult(Object instance, Object value) {
    // TODO Auto-generated method stub
    return null;
  }

}
