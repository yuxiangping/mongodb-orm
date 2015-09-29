package com.mongodb.orm.engine.type;

import java.util.Map;

/**
 * Map implementation of TypeHandler
 * @author: xiangping_yu
 * @data : 2014-7-25
 * @since : 1.5
 */
@SuppressWarnings("unchecked")
public class MapTypeHandler implements TypeHandler<Map<Object, Object>> {
  
  @Override
  public Object getParameter(String name, Map<Object, Object> instance) {
    return instance.get(name);
  }

  @Override
  public Map<Object, Object> getResult(String name, Object instance, Object value) {
    Map<Object, Object> map = (Map<Object, Object>)instance;
    map.put(name, value);
    return map;
  }

}
