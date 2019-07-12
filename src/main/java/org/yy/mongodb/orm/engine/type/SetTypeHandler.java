package org.yy.mongodb.orm.engine.type;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.yy.mongodb.exception.MongoORMException;

/**
 * Set implementation of TypeHandler
 * @author: xiangping_yu
 * @data : 2014-7-25
 * @since : 1.5
 */
public class SetTypeHandler implements TypeHandler<Set<?>> {

  private Class<?> clazz;
  
  public SetTypeHandler(Class<?> clazz) {
    this.clazz = clazz;
  }
  
  @Override
  public Object getParameter(String name, Set<?> instance) {
    return instance;
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  @Override
  public Set<?> getResult(String name, Object instance, Object value) {
    try {
      Set result = null;
      if(clazz.isInterface()) {
        result = new HashSet<Object>();  
      } else {
        result = (Set)clazz.newInstance();
      }
      
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
