package org.yy.mongodb.orm.engine.config;

import org.yy.mongodb.orm.engine.Config;
import org.yy.mongodb.orm.engine.entry.NodeEntry;

/**
 * XML Command config
 * @author yy
 */
public class CommandConfig implements Config {

  private static final long serialVersionUID = 1289646863161628083L;
  
  /**
   * ORM config namespace.
   */
  private String namespace;
  
  /**
   * ORM command config id.
   */
  private String id;

  /**
   * Command parameter.
   */
  private NodeEntry query;

  /**
   * Command returned field.
   */
  private NodeEntry field;

  public CommandConfig(String id) {
    this.id = id;
  }

  public NodeEntry getQuery() {
    return query;
  }

  public void setQuery(NodeEntry query) {
    this.query = query;
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

}
