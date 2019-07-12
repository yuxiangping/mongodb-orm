package org.yy.mongodb.orm.engine.config;

import java.util.LinkedHashMap;
import java.util.Map;

import org.yy.mongodb.orm.engine.Config;
import org.yy.mongodb.orm.engine.entry.NodeEntry;

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
  private Map<String, NodeEntry> function;
  /**
   * Mongo db aggregate field.
   */
  private NodeEntry field;

  public AggregateConfig(String id, String collection) {
    this.id = id;
    this.collection = collection;
  }

  public Map<String, NodeEntry> getFunction() {
    return function;
  }

  public void addFunction(String operator, NodeEntry entry) {
    if(function == null) {
      function = new LinkedHashMap<String, NodeEntry>();
    }
    this.function.put(operator, entry);
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
