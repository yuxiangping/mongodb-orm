package org.yy.mongodb.orm.engine.config;

import java.util.ArrayList;
import java.util.List;

import org.yy.mongodb.orm.engine.Config;
import org.yy.mongodb.orm.engine.entry.Entry;
import org.yy.mongodb.orm.engine.type.TypeHandler;

/**
 * XML Mapping config
 * @author yy
 */
public class MappingConfig implements Config {

  private static final long serialVersionUID = -7995688169314848530L;

  /**
   * ORM config namespace.
   */
  private String namespace;
  
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
  private List<Entry> nodes = new ArrayList<Entry>();
  
  /**
   * Getting data into, and out of a mapped statement.
   */
  private TypeHandler<?> typeHandler;
  
  /**
   * Has merge extend mapping.
   */
  private boolean hasMerge;

  public MappingConfig(String id, Class<?> clazz, String extend, List<Entry> nodes) {
    this.id = id;
    this.clazz = clazz;
    this.extend = extend;
    this.nodes = nodes;
  }

  public boolean merge(List<Entry> nodes) {
    if(hasMerge) {
      return false;
    }
    this.nodes.addAll(nodes);
    this.hasMerge = true;
    return true;
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

  public TypeHandler<?> getTypeHandler() {
    return typeHandler;
  }

  public void setTypeHandler(TypeHandler<?> typeHandler) {
    this.typeHandler = typeHandler;
  }

}
