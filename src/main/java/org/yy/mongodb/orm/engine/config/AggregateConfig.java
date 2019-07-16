package org.yy.mongodb.orm.engine.config;

import java.util.LinkedHashMap;
import java.util.Map;

import org.yy.mongodb.orm.engine.Config;
import org.yy.mongodb.orm.engine.entry.NodeEntry;

/**
 * XML Aggregate config
 * @author yy
 */
public class AggregateConfig implements Config {

  private static final long serialVersionUID = 1289646863161628083L;

  /**
   * ORM config namespace.
   */
  private String namespace;
  
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
