package com.mongodb.orm.engine.type;

import com.mongodb.exception.StatementException;
import com.mongodb.util.BeanUtils;

/**
 * Custom implementation of TypeHandler
 * 
 * @author: xiangping_yu
 * @data : 2014-7-25
 * @since : 1.5
 */
public class CustomHandler implements TypeHandler<Object> {

  private Class<?> type;

  private TypeHandler<?> typeHandler;

  private String property;

  public CustomHandler(Class<?> clazz, String name) {
    this.property = name;

    try {
      type = BeanUtils.getPropertyType(clazz, name);
    } catch (Exception e) {
      throw new StatementException("Resolve target class '" + clazz + "'. Unknow property '"+name+"' return type.", e);
    }
    
    if (TypeHandlerFactory.has(type)) {
      typeHandler = TypeHandlerFactory.getTypeHandler(type, property);
    }
  }

  @Override
  public Object getResult(Object target, Object value) {
    try {
      if (typeHandler != null) {
        value = typeHandler.getResult(null, value);
      }
      BeanUtils.setProperty(target, property, value);
    } catch (Exception e) {
      throw new StatementException("Invoke target method by '" + type + "', instance '" + target + "' error.", e);
    }
    return value;
  }

  @Override
  public Object getParameter(Object target) {
    try {
      return BeanUtils.getProperty(target, property);
    } catch (Exception e) {
      throw new StatementException("Invoke target method by '" + type + "', instance '" + target + "' error.", e);
    }
  }

}
