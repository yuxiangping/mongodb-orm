package com.mongodb.orm.engine.entry;

import com.mongodb.orm.engine.type.ColumnHandler;
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
  private TypeHandler<?> typeHandler;

  public Script(String text) {
    this.text = text;
  }
  
  public TypeHandler<?> getTypeHandler() {
    return typeHandler;
  }

  public void setTypeHandler(TypeHandler<?> typeHandler) {
    this.typeHandler = typeHandler;
  }

  public String getText() {
    return text;
  }

}
