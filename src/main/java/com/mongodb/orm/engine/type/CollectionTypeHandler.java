package com.mongodb.orm.engine.type;

import java.util.Collection;

import com.mongodb.exception.MongoORMException;

/**
 * Collection implementation of TypeHandler
 * @author: xiangping_yu
 * @data : 2014-7-25
 * @since : 1.5
 */
public class CollectionTypeHandler implements TypeHandler<Collection<?>> {

  private Class<?> clazz;
  
  public CollectionTypeHandler(Class<?> clazz) {
    this.clazz = clazz;
  }
  
  @Override
  public Object getParameter(String name, Collection<?> instance) {
    return instance;
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  @Override
  public Collection<?> getResult(String name, Object instance, Object value) {
    try {
      Collection result = (Collection)clazz.newInstance();
      if (value instanceof Collection) {
        result.addAll((Collection)value);
      } else {
        result.add(value);
      }
      return result;
    } catch (Exception ex) {
      throw new MongoORMException("Get result from "+instance.getClass()+" has error. Target property is "+name, ex);
    }
  }

}
