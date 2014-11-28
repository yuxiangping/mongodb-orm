package com.mongodb.orm.engine.config;

import com.mongodb.orm.engine.Config;
import com.mongodb.orm.engine.entry.NodeEntry;

/**
 * XML Delete config
 * 
 * @author: xiangping_yu
 * @data : 2014-3-6
 * @since : 1.5
 */
public class DeleteConfig implements Config {

  private static final long serialVersionUID = 1289646863161628083L;

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

  public DeleteConfig(String id, String collection) {
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

}
