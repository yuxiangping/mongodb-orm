package org.yy.mongodb.orm.engine.config;

import org.yy.mongodb.orm.engine.Config;
import org.yy.mongodb.orm.engine.entry.NodeEntry;

/**
 * XML Update config
 * 
 * @author: xiangping_yu
 * @data : 2014-3-6
 * @since : 1.5
 */
public class UpdateConfig implements Config {

  private static final long serialVersionUID = -6774384486124325755L;

  /**
   * ORM query config id.
   */
  private String id;

  /**
   * mongo db collection.
   */
  private String collection;

  /**
   * Update parameter.
   */
  private NodeEntry query;
  /**
   * Update action.
   */
  private NodeEntry action;
  /**
   * Return field for <code>findAndModify</code>.
   */
  private NodeEntry field;

  public UpdateConfig(String id, String collection) {
    this.id = id;
    this.collection = collection;
  }

  public String getId() {
    return id;
  }

  public String getCollection() {
    return collection;
  }
  
  public NodeEntry getQuery() {
    return query;
  }

  public void setQuery(NodeEntry query) {
    this.query = query;
  }

  public NodeEntry getAction() {
    return action;
  }

  public void setAction(NodeEntry action) {
    this.action = action;
  }

  public NodeEntry getField() {
    return field;
  }

  public void setField(NodeEntry field) {
    this.field = field;
  }

}
