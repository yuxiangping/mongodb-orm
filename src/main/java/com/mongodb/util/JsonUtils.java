package com.mongodb.util;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

/**
 * Utils for json.
 * 
 * @author: xiangping_yu
 * @data : 2014-9-18
 * @since : 1.5
 */
public class JsonUtils {

  private JsonUtils() {}
  
  @SuppressWarnings("unchecked")
  public static <K,V> Map<K, V> toMap(String text) {
    JSONObject json = JSONObject.fromObject(text);
    return (Map<K, V>) JSONObject.toBean(json, HashMap.class);
  }
  
}
