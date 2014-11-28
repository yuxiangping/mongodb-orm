package com.mongodb.orm.engine.type;

import java.util.Iterator;

/**
 * Iterator implementation of TypeHandler
 * @author: xiangping_yu
 * @data : 2014-7-25
 * @since : 1.5
 */
public class IteratorTypeHandler implements TypeHandler<Iterator<?>> {

  @Override
  public Object getParameter(Iterator<?> instance) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Iterator<?> getResult(Object instance, Object value) {
    // TODO Auto-generated method stub
    return null;
  }

}
