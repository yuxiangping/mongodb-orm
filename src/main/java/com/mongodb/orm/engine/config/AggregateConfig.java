package com.mongodb.orm.engine.config;

import java.util.List;

import com.mongodb.orm.engine.Config;
import com.mongodb.orm.engine.entry.NodeEntry;

/**
 * XML Aggregate config
 * 
 * @author: xiangping_yu
 * @data : 2014-3-6
 * @since : 1.5
 */
public class AggregateConfig implements Config {

  private static final long serialVersionUID = 1289646863161628083L;

  /**
   * ORM aggregate config id.
   */
  private String id;

  /**
   * Mongo db collection.
   */
  private String collection;

  /**
   * Mongo db aggregate pipeline functions.
   */
  private List<NodeEntry> function;
  /**
   * Mongo db aggregate field.
   */
  private NodeEntry field;

  public AggregateConfig(String id, String collection) {
    this.id = id;
    this.collection = collection;
  }

  public List<NodeEntry> getFunction() {
    return function;
  }

  public void setFunction(List<NodeEntry> function) {
    this.function = function;
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
