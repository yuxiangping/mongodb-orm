package com.mongodb.orm.engine.config;

import com.mongodb.orm.engine.Config;
import com.mongodb.orm.engine.entry.Entry;
import com.mongodb.orm.engine.entry.NodeEntry;

/**
 * XML Insert config
 * 
 * @author: xiangping_yu
 * @data : 2014-3-6
 * @since : 1.5
 */
public class InsertConfig implements Config {

  private static final long serialVersionUID = -5813468518730252909L;

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

  public InsertConfig(String id, String collection) {
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

}
