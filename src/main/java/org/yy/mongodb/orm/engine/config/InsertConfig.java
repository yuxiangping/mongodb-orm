package org.yy.mongodb.orm.engine.config;

import org.yy.mongodb.orm.engine.Config;
import org.yy.mongodb.orm.engine.entry.Entry;
import org.yy.mongodb.orm.engine.entry.NodeEntry;

/**
 * XML Insert config
 * @author yy
 */
public class InsertConfig implements Config {

  private static final long serialVersionUID = -5813468518730252909L;

  /**
   * ORM config namespace.
   */
  private String namespace;
  
  /**
   * ORM insert config id.
   */
  private String id;

  /**
   * Mongo db collection.
   */
  private String collection;

  /**
   * Insert document.
   */
  private NodeEntry document;

  /**
   * Insert select key mapping entry.
   */
  private Entry selectKey;

  public InsertConfig(String namespace, String id, String collection) {
    this.namespace = namespace;
    this.id = id;
    this.collection = collection;
  }

  public NodeEntry getDocument() {
    return document;
  }

  public void setDocument(NodeEntry document) {
    this.document = document;
  }

  public Entry getSelectKey() {
    return selectKey;
  }

  public void setSelectKey(Entry selectKey) {
    this.selectKey = selectKey;
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
