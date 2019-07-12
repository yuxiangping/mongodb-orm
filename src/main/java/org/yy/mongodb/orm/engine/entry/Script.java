package org.yy.mongodb.orm.engine.entry;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.yy.mongodb.exception.MongoORMException;
import org.yy.mongodb.util.BeanUtils;


/**
 * Script text entry.
 * 
 * @author: xiangping_yu
 * @data : 2015-9-01
 * @since : 1.5
 */
public class Script {

  /**
   * Mql script text source.
   */
  private String text;

  /**
   * Getting data into, and out of a mapped statement.
   */
  private Analyer analyer;

  public Script(String text, Class<?> clazz) {
    this.text = text;
    if(clazz != null) {
      this.analyer = AnalyerHelper.getAnalyer(clazz);
    }
  }

  public String getText() {
    return text;
  }
  
  public String getValue(String target, Object value) {
    if(analyer == null) {
      analyer = AnalyerHelper.getAnalyer(value.getClass());
    }
    return String.valueOf(analyer.getValue(target, value));
  }

  static interface Analyer {
    Object getValue(String target, Object value);
  }
  
  static class CustomAnalyer implements Analyer {
    @Override
    public Object getValue(String target, Object value) {
      try {
        return BeanUtils.getProperty(value, target);
      } catch (Exception e) {
        throw new MongoORMException("No property '"+target+"'found. Class '"+value.getClass()+"'.", e);
      }
    }
  }
  
  @SuppressWarnings("rawtypes")
  static class MapAnalyer implements Analyer {
    @Override
    public Object getValue(String target, Object value) {
      return ((Map)value).get(target);
    }
  }
  
  static class ValueAnalyer implements Analyer {
    @Override
    public Object getValue(String target, Object value) {
      return value;
    }
  }
  
  private static class AnalyerHelper {
    
    static Analyer mapAnalyer = new MapAnalyer();
    static Analyer customAnalyer = new CustomAnalyer();
    static Analyer valueAnalyer = new ValueAnalyer();
    
    static List<Object> valueClass = Arrays.asList(String.class, Integer.class, int.class, Short.class, short.class, Long.class, long.class
        ,Boolean.class, boolean.class, Double.class, double.class, Float.class, float.class, Byte.class, byte.class, Character.class, char.class);
    
    public static Analyer getAnalyer(Class<?> clazz) {
      if(clazz.equals(Map.class)) {
        return mapAnalyer;
      }
      if(valueClass.contains(clazz)) {
        return valueAnalyer;
      }
      return customAnalyer;
    }
  }
}
