package com.mongodb.orm.engine.type;

/**
 * Enum implementation of TypeHandler
 * @author: xiangping_yu
 * @data : 2014-7-25
 * @since : 1.5
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class EnumTypeHandler implements TypeHandler<Enum<?>> {

  private Class clazz;
  
  public EnumTypeHandler(Class clazz) {
    this.clazz = clazz;
  }
  
  @Override
  public Object getParameter(Enum<?> instance) {
    return instance.toString();
  }

  @Override
  public Enum<?> getResult(Object instance, Object value) {
    return Enum.valueOf(clazz, String.valueOf(value));
  }

}
