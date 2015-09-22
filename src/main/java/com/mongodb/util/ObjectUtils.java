package com.mongodb.util;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * Utils for object.
 * 
 * @author: xiangping_yu
 * @data : 2015-9-17
 * @since : 1.5
 */
public class ObjectUtils {

  private ObjectUtils() {}
  
  public static boolean isEmpty(Object value) {
    if(value == null) {
      return true;
    }
    if(value instanceof String) {
      return StringUtils.isBlank((String)value);
    }
    if(value instanceof Collection) {
      return ((Collection<?>)value).isEmpty();
    }
    if(value instanceof Map) {
      return ((Map<?,?>)value).isEmpty();
    }
    return false;
  }
  
}
