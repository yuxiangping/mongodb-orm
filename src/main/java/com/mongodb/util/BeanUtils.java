package com.mongodb.util;

import org.apache.commons.beanutils.PropertyUtils;

/**
 * Utils for bean.
 * 
 * @author: xiangping_yu
 * @data : 2014-9-18
 * @since : 1.5
 */
public class BeanUtils {

  public static Object getProperty(Object target, String name) throws Exception {
    return PropertyUtils.getProperty(target, name);
  }

  public static void setProperty(Object target, String name, Object value) throws Exception {
    PropertyUtils.setProperty(target, name, value);
  }

  public static Class<?> getPropertyType(Class<?> targetClass, String name) throws Exception {
    return org.springframework.beans.BeanUtils.findPropertyType(name, new Class<?>[] {targetClass});
  }

}
