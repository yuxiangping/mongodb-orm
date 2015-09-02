package com.mongodb.orm.engine.config;

import java.util.List;

import com.mongodb.orm.engine.Config;
import com.mongodb.orm.engine.entry.Entry;

/**
 * XML Mapping config
 * 
 * @author: xiangping_yu
 * @data : 2014-3-6
 * @since : 1.5
 */
public class MappingConfig implements Config {

  private static final long serialVersionUID = -7995688169314848530L;

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
   * Node children nodes.
   */
  private List<Entry> nodes;

  public MappingConfig(String id, Class<?> clazz, String extend, List<Entry> nodes) {
    this.id = id;
    this.clazz = clazz;
    this.extend = extend;
    this.nodes = nodes;
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

  public List<Entry> getNodes() {
    return nodes;
  }

}
