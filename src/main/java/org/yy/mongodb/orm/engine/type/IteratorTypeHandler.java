package org.yy.mongodb.orm.engine.type;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Iterator implementation of TypeHandler
 * @author: xiangping_yu
 * @data : 2014-7-25
 * @since : 1.5
 */
public class IteratorTypeHandler implements TypeHandler<Iterator<?>> {

  @Override
  public Object getParameter(String name, Iterator<?> instance) {
    return instance;
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  @Override
  public Iterator<?> getResult(String name, Object instance, Object value) {
    if(value instanceof Iterator) {
      return (Iterator<?>)value;
    }
    
    if(value instanceof Collection) {
      return ((Collection<?>)value).iterator();
    }
    
    List list = new ArrayList();
    list.add(value);
    return list.iterator();
  }

}
