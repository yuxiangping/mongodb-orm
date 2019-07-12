package org.yy.mongodb.util;

import java.util.Map;

import org.boon.json.JsonFactory;
import org.boon.json.JsonParserFactory;
import org.boon.json.JsonSerializerFactory;
import org.boon.json.ObjectMapper;

/**
 * Json util. Use boon json.
 * @author yy
 */
public final class JsonUtils {

  private JsonUtils() {}
  
  private static ObjectMapper objectMapper = JsonFactory.create(new JsonParserFactory().lax(), new JsonSerializerFactory().setAsciiOnly(false));
  
  public static <T> T toBean(String json, Class<T> className) {
    return objectMapper.fromJson(json, className);
  }
  
  @SuppressWarnings("unchecked")
  public static Map<Object, Object> toMap(String json) {
    return (Map<Object, Object>)objectMapper.fromJson(json);
  }
  
  @SuppressWarnings("unchecked")
  public static <T> Map<Object, Object> toJson(T bean) {
    return (Map<Object, Object>)objectMapper.fromJson(toJsonString(bean));
  }

  public static <T> String toJsonString(T bean) {
    return objectMapper.toJson(bean);
  }

}
