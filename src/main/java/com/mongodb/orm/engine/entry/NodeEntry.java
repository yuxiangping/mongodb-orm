package com.mongodb.orm.engine.entry;

import java.io.Serializable;
import java.util.List;

import com.mongodb.orm.executor.MqlExecutor;

/**
 * Xml node entry.
 * 
 * @author: xiangping_yu
 * @data : 2014-7-16
 * @since : 1.5
 */
public class NodeEntry implements Serializable {

  private static final long serialVersionUID = -6974784211299835940L;

  /**
   * Mapping id.
   */
  private String mappingId;

  /**
   * Node parameter class type.
   */
  private Class<?> clazz;

  /**
   * Node elements mapping entry.
   */
  private List<Entry> nodeMappings;

  /**
   * Mql node executor
   */
  private MqlExecutor<?> executor;
  
  public Object executorNode(Object target) {
    return executor.parser(this, target);
  }
  
  public String getMappingId() {
    return mappingId;
  }

  public void setMappingId(String mappingId) {
    this.mappingId = mappingId;
  }

  public Class<?> getClazz() {
    return clazz;
  }

  public void setClazz(Class<?> clazz) {
    this.clazz = clazz;
  }

  public List<Entry> getNodeMappings() {
    return nodeMappings;
  }

  public void setNodeMappings(List<Entry> nodeMappings) {
    this.nodeMappings = nodeMappings;
  }

  public void setExecutor(MqlExecutor<?> executor) {
    this.executor = executor;
  }

}
