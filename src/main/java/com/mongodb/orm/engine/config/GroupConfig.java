package com.mongodb.orm.engine.config;

import com.mongodb.orm.engine.Config;
import com.mongodb.orm.engine.entry.NodeEntry;

/**
 * XML Group config
 * 
 * @author: xiangping_yu
 * @data : 2014-3-6
 * @since : 1.5
 */
public class GroupConfig implements Config {

  private static final long serialVersionUID = 1289646863161628083L;

  /**
   * ORM group config id.
   */
  private String id;
  /**
   * Mongo db collection.
   */
  private String collection;
  /**
   * Group key.
   */
  private NodeEntry key;
  /**
   * Group condition.
   */
  private NodeEntry condition;
  /**
   * Group initial.
   */
  private NodeEntry initial;
  /**
   * Group reduce.
   */
  private String reduce;
  /**
   * Group finalize.
   */
  private String finalize;
  /**
   * Group return field.
   */
  private NodeEntry field;

  public GroupConfig(String id, String collection) {
    this.id = id;
    this.collection = collection;
  }

  public NodeEntry getKey() {
    return key;
  }

  public void setKey(NodeEntry key) {
    this.key = key;
  }

  public NodeEntry getCondition() {
    return condition;
  }

  public void setCondition(NodeEntry condition) {
    this.condition = condition;
  }

  public NodeEntry getInitial() {
    return initial;
  }

  public void setInitial(NodeEntry initial) {
    this.initial = initial;
  }

  public String getReduce() {
    return reduce;
  }

  public void setReduce(String reduce) {
    this.reduce = reduce;
  }

  public String getFinalize() {
    return finalize;
  }

  public void setFinalize(String finalize) {
    this.finalize = finalize;
  }

  public NodeEntry getField() {
    return field;
  }

  public void setField(NodeEntry field) {
    this.field = field;
  }

  public String getId() {
    return id;
  }

  public String getCollection() {
    return collection;
  }

}
