package org.yy.mongodb.util;

import org.apache.commons.beanutils.PropertyUtils;

/**
 * Utils for bean.
 * @author yy
 */
public class BeanUtils {

  private BeanUtils() {}
  
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
