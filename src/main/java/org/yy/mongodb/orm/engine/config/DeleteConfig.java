package org.yy.mongodb.orm.engine.config;

import org.yy.mongodb.orm.engine.Config;
import org.yy.mongodb.orm.engine.entry.NodeEntry;

/**
 * XML Delete config
 * @author yy
 */
public class DeleteConfig implements Config {

  private static final long serialVersionUID = 1289646863161628083L;

  /**
   * ORM config namespace.
   */
  private String namespace;
  
  /**
   * ORM delete config id.
   */
  private String id;

  /**
   * Mongo db collection.
   */
  private String collection;

  /**
   * Delete parameter
   */
  private NodeEntry query;

  public DeleteConfig(String namespace, String id, String collection) {
    this.namespace = namespace;
    this.id = id;
    this.collection = collection;
  }

  public NodeEntry getQuery() {
    return query;
  }

  public void setQuery(NodeEntry query) {
    this.query = query;
  }

  public String getId() {
    return id;
  }

  public String getCollection() {
    return collection;
  }

  public String getNamespace() {
    return namespace;
  }
}
