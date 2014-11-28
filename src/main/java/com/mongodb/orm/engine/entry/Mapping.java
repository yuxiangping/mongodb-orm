package com.mongodb.orm.engine.entry;

import java.io.Serializable;
import java.util.List;

import org.w3c.dom.NodeList;

/**
 * Mapping config
 * 
 * @author: xiangping_yu
 * @data : 2013-6-8
 * @since : 1.5
 */
public class Mapping implements Serializable {
  private static final long serialVersionUID = 8691886299505473312L;

  /**
   * Mapping id
   */
  private String id;
  /**
   * Mapping parameter class type
   */
  private Class<?> clazz;
  /**
   * Mapping extends partent.
   */
  private String extend;

  /**
   * mapping value entry list
   */
  private List<MappingEntry> om;

  public Mapping(String id, Class<?> clazz, String extend, NodeList nodeList) {
    this.id = id;
    this.extend = extend;
  }

  public Mapping(Class<?> clazz, List<MappingEntry> om) {
    this.clazz = clazz;
    this.om = om;
  }

  public Mapping(String id, Class<?> clazz, List<MappingEntry> om) {
    this.id = id;
    this.clazz = clazz;
    this.om = om;
  }

  public Mapping(List<MappingEntry> om) {
    this.om = om;
  }

  public String getId() {
    return id;
  }

  public Class<?> getClazz() {
    return clazz;
  }

  public String getExtend() {
    return extend;
  }

  public List<MappingEntry> getOm() {
    return om;
  }
}
