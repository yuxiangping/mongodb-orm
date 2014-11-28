package com.mongodb.orm.engine.entry;

import com.mongodb.exception.StatementException;

/**
 * Mongodb operator type.
 * @see {@link http://docs.mongodb.org/manual/reference/operator}
 * @author: xiangping_yu
 * @data : 2014-7-18
 * @since : 1.5
 */
public enum Operator {
  
  /**
   * Query and Projection Operators.
   */
  /**
   * Comparison
   */
  gt("$gt"),
  gte("$gte"),
  in("$in"),
  lt("$lt"),
  lte("$lte"),
  ne("$ne"),
  nin("$nin"),
  /**
   * Logical
   */
  or("$or"),  
  and("$and"),
  not("$not"),
  nor("$nor"),
  /**
   * Element
   */
  exists("$exists"),
  type("$type"),
  /**
   * Evaluation
   */
  mod("$mod"),
  regex("$regex"),
  text("$text"),
  where("$where"),
  /**
   * Geospatial
   */
  geoWithin("$geoWithin"),
  geoIntersects("$geoIntersects"),
  near("$near"),
  nearSphere("$nearSphere"),
  /**
   * Array
   */
  all("$all"),
  elemMatch("$elemMatch"),
  size("$size"),
  /**
   * Projection
   */
  $("$"),
  meta("$meta"),
  slice("$slice"),
  
  /**
   * Update Operators.
   */
  /**
   * Fields
   */
  inc("$inc"),
  mul("$mul"),
  rename("$rename"),
  setOnInsert("$setOnInsert"),
  set("$set"),
  unset("$unset"),
  min("$min"),
  max("$max"),
  currentDate("$currentDate"),
  
  /**
   * Array
   */
  addToSet("$addToSet"),
  pop("$pop"),
  pullAll("$pullAll"),
  pull("$pull"),
  pushAll("$pushAll"),
  push("$push"),
  
  each("$each"),
  sort("$sort"),
  position("$position"),
  /**
   * Bitwise
   */
  bit("$bit"),
  isolated("$isolated"),
  
  /**
   * Aggregation Framework Operators
   */
  /**
   * Pipeline Operators
   */
  project("$project"),
  match("$match"),
  redact("$redact"),
  limit("$limit"),
  skip("$skip"),
  unwind("$unwind"),
  group("$group"),
  geoNear("$geoNear"),
  out("$out"),
  
  /**
   * Expression Operators
   */
  // $group Operators
  first("$first"),
  last("$last"),
  avg("$avg"),
  sum("$sum"),
  // Set Operators
  setEquals("$setEquals"),
  setIntersection("$setIntersection"),
  setDifference("$setDifference"),
  setUnion("$setUnion"),
  setIsSubset("$setIsSubset"),
  anyElementTrue("$anyElementTrue"),
  allElementsTrue("$allElementsTrue"),
  // Comparison Operators
  cmp("$cmp"),
  eq("$eq"),
  // Arithmetic Operators
  divide("$divide"),
  multiply("$multiply"),
  subtract("$subtract"),
  // String Operators
  concat("$concat"),
  strcasecmp("$strcasecmp"),
  substr("$substr"),
  toLower("$toLower"),
  toUpper("$toUpper"),
  // Projection Expressions
  map("$map"),
  let("$let"),
  literal("$literal"),
  // Date Operators
  dayOfYear("$dayOfYear"),
  dayOfMonth("$dayOfMonth"),
  dayOfWeek("$dayOfWeek"),
  year("$year"),
  month("$month"),
  week("$week"),
  hour("$hour"),
  minute("$minute"),
  second("$second"),
  millisecond("$millisecond"),
  // Conditional Expressions
  cond("$cond"),
  ifNull("$ifNull");
  
  private String opt;
  
  public static Operator formName(String name) {
    if(name==null) {
      return null;
    }
    
    try {
      return Operator.valueOf(name);
    } catch (IllegalArgumentException ex) {
      throw new StatementException("Error operator const '"+name+"'.");
    }
  }
  
  private Operator(String opt) {
    this.opt = opt;
  }

  public String getOpt() {
    return opt;
  }
}
