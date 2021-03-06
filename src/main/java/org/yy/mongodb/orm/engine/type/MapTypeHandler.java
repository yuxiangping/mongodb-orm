package org.yy.mongodb.orm.engine.type;

import java.util.Map;

import org.apache.commons.collections4.MapUtils;

/**
 * Map implementation of TypeHandler
 * @author yy
 */
@SuppressWarnings("unchecked")
public class MapTypeHandler implements TypeHandler<Map<Object, Object>> {
  
  @Override
  public Object getParameter(String name, Map<Object, Object> instance) {
    if (MapUtils.isEmpty(instance)) {
      return null;
    }    
    return instance.get(name);
  }

  @Override
  public Map<Object, Object> getResult(String name, Object instance, Object value) {
    Map<Object, Object> map = (Map<Object, Object>)instance;
    map.put(name, value);
    return map;
  }

}
