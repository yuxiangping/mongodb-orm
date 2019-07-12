package org.yy.mongodb.orm.engine.type;

import org.yy.mongodb.exception.StatementException;
import org.yy.mongodb.util.BeanUtils;

/**
 * Custom implementation of TypeHandler
 * 
 * @author: xiangping_yu
 * @data : 2014-7-25
 * @since : 1.5
 */
public class CustomHandler implements TypeHandler<Object> {

  private Class<?> clazz;

  public CustomHandler(Class<?> clazz) {
    this.clazz = clazz;
  }

  @Override
  public Object getResult(String name, Object target, Object value) {
    try {
      Class<?> typeClass = BeanUtils.getPropertyType(clazz, name);
      if (TypeHandlerFactory.has(typeClass)) {
        value = TypeHandlerFactory.getTypeHandler(typeClass).getResult(name, target, value);
      }
      BeanUtils.setProperty(target, name, value);
    } catch (Exception e) {
      throw new StatementException("Resolve target class '" + clazz + "'. Unknow property '" + name + "' return type.", e);
    }
    return target;
  }

  @Override
  public Object getParameter(String name, Object target) {
    try {
      return BeanUtils.getProperty(target, name);
    } catch (Exception e) {
      throw new StatementException("Invoke target method by '" + clazz + "', instance '" + target + "' error.", e);
    }
  }

}
