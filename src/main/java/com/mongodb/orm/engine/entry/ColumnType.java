package com.mongodb.orm.engine.entry;

import org.apache.commons.lang.StringUtils;

import com.mongodb.exception.StatementException;

/**
 * Mongodb column type.
 * 
 * exp:
 * <p>
 *  String, NumberLong, Double, ObjectId ...
 * </p>
 * 
 * @author: xiangping_yu
 * @data : 2014-7-18
 * @since : 1.5
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
      return string;
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
