package com.mongodb.orm.engine.type;

import java.util.Collection;

/**
 * Collection implementation of TypeHandler
 * @author: xiangping_yu
 * @data : 2014-7-25
 * @since : 1.5
 */
public class CollectionTypeHandler implements TypeHandler<Collection<?>> {

  @Override
  public Object getParameter(Collection<?> instance) {
    // TODO
    return null;
  }

  @Override
  public Collection<?> getResult(Object instance, Object value) {
    // TODO Auto-generated method stub
    return null;
  }

}
