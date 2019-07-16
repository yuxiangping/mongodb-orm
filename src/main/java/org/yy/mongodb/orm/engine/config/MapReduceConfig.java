package org.yy.mongodb.orm.engine.config;

import org.yy.mongodb.orm.engine.Config;
import org.yy.mongodb.orm.engine.entry.NodeEntry;
import org.yy.mongodb.orm.engine.entry.Script;

/**
 * XML MapReduce config
 * @author yy
 */
public class MapReduceConfig implements Config {

  private static final long serialVersionUID = 1289646863161628083L;
  
  /**
   * ORM config namespace.
   */
  private String namespace;
  
  /**
   * ORM mapreduce config id.
   */
  private String id;
  
  /**
   * Mongo db collection.
   */
  private String collection;
  
  /**
   * MapReduce map.
   */
  private Script map;
  
  /**
   * MapReduce reduce.
   */
  private Script reduce;
  
  /**
   * MapReduce return field.
   */
  private NodeEntry field;
  
  public MapReduceConfig(String namespace, String id, String collection) {
    this.namespace = namespace;
    this.id = id;
    this.collection = collection;
  }

  public String getId() {
    return id;
  }

  public String getCollection() {
    return collection;
  }

  public Script getMap() {
    return map;
  }

  public void setMap(Script map) {
    this.map = map;
  }

  public Script getReduce() {
    return reduce;
  }

  public void setReduce(Script reduce) {
    this.reduce = reduce;
  }

  public NodeEntry getField() {
    return field;
  }

  public void setField(NodeEntry field) {
    this.field = field;
  }

  public String getNamespace() {
    return namespace;
  }
}
