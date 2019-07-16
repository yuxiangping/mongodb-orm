package org.yy.mongodb.orm.engine.entry;

import org.apache.commons.lang3.StringUtils;
import org.yy.mongodb.exception.StatementException;

/**
 * Mongodb column type.
 * 
 * exp:
 * <p>
 *  String, Number, Long, Double, ObjectId ...
 * </p>
 * @author yy
 */
public enum ColumnType {
  
  string("string"), 
  _number("number"), 
  _boolean("boolean"),
  _double("double"), 
  objectid("objectid"), 
  date("date"),
  regex("regex"),
  _byte("byte");
  
  private String type;
  
  public static ColumnType fromType(String type) {
    if(StringUtils.isBlank(type)) {
      return null;
    }
    
    for(ColumnType ct : values()) {
      if(ct.type.equals(type)) {
        return ct;
      }
    }
    throw new StatementException("Unknow column type '"+type+"'");
  }
  
  private ColumnType(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }
  
}
