package org.yy.mongodb.orm.engine.type;

import java.util.regex.Pattern;

/**
 * Regex implementation of TypeHandler
 * @author yy
 */
public class RegexTypeHandler implements ColumnHandler<Pattern> {

  @Override
  public Pattern resovleColumn(Object value) {
    return Pattern.compile(value.toString());
  }

  @Override
  public Object resovleValue(Pattern target) {
    return target.toString();
  }

}
