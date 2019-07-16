package org.yy.mongodb.orm.engine.type;

/**
 * Enum implementation of TypeHandler
 * @author yy
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class EnumTypeHandler implements TypeHandler<Enum<?>> {

  private Class clazz;
  
  public EnumTypeHandler(Class clazz) {
    this.clazz = clazz;
  }
  
  @Override
  public Object getParameter(String name, Enum<?> instance) {
    return instance.toString();
  }

  @Override
  public Enum<?> getResult(String name, Object instance, Object value) {
    return Enum.valueOf(clazz, String.valueOf(value));
  }

}
