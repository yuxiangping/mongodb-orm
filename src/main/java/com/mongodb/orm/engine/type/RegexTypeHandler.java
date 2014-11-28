package com.mongodb.orm.engine.type;

import java.util.regex.Pattern;

/**
 * Regex implementation of TypeHandler
 * 
 * @author: xiangping_yu
 * @data : 2014-7-25
 * @since : 1.5
 */
public class RegexTypeHandler implements ColumnHandler<Pattern> {

  @Override
  public Pattern resovleColumn(Object value) {
    return Pattern.compile(value.toString());
  }

}
