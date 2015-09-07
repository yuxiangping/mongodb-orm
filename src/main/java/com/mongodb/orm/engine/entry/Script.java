package com.mongodb.orm.engine.entry;

import com.mongodb.orm.engine.type.TypeHandler;

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

  public Script(String text) {
    this.text = text;
  }

  public String getText() {
    return text;
  }

  interface Analyer {
    String getValue(String target, Object value);
  }
  
  class CustomAnalyer implements Analyer {
    @Override
    public String getValue(String target, Object value) {
      return null;
    }
  }
  
  class MapAnalyer implements Analyer {
    @Override
    public String getValue(String target, Object value) {
      return null;
    }
  }
  
  class ValueAnalyer implements Analyer {
    @Override
    public String getValue(String target, Object value) {
      // TODO Auto-generated method stub
      return null;
    }
  }
  
}
