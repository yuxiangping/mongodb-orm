package org.yy.mongodb.util;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.yy.mongodb.orm.engine.entry.Entry;

/**
 * Utils for object.
 * @author yy
 */
public class ObjectUtils {

  private ObjectUtils() {}

  public static boolean isEmpty(Object value) {
    if (value == null) {
      return true;
    }
    if (value instanceof String) {
      return StringUtils.isBlank((String) value);
    }
    if (value instanceof Collection) {
      return ((Collection<?>) value).isEmpty();
    }
    if (value instanceof Map) {
      return ((Map<?, ?>) value).isEmpty();
    }
    return false;
  }

  public static boolean checkValueNull(Object value, Entry entry) {
    if (!isEmpty(value)) {
      return false;
    }

    if (entry.isIgnoreNull()) {
      return false;
    }

    if (value != null && entry.isIgnoreEmpty()) {
      return false;
    }

    return true;
  }

}
