package com.mongodb.orm.engine.entry;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.mongodb.orm.builder.dynamic.Dynamic;
import com.mongodb.orm.engine.type.ColumnHandler;
import com.mongodb.orm.engine.type.TypeHandler;

/**
 * Element base entry.
 * 
 * @author: xiangping_yu
 * @data : 2014-7-17
 * @since : 1.5
 */
public class Entry implements Serializable {

  private static final long serialVersionUID = 2410570509734423022L;

  /**
   * Database column.
   */
  private String column;
  /**
   * Parameter name or entry field.
   */
  private String name;
  /**
   * Default value.
   */
  private Object value;
  /**
   * Getting data into, and out of a mapped statement.
   */
  private TypeHandler<?> typeHandler;
  /**
   * Resovler a available column type for mongodb.
   */
  private ColumnHandler<?> columnHandler;
  /**
   * Operate tag.
   * <p>
   * '$set', '$lt', '$elementMatch'
   * </p>
   */
  private Operator operate;
  /**
   * Whether to ignore null.
   */
  private Boolean ignoreNull;
  /**
   * Whether to ignore empty.
   */
  private Boolean ignoreEmpty;
  /**
   * Dynamic node
   */
  private Dynamic dynamic;
  /**
   * Child nodes.
   */
  private List<NodeEntry> nodes;

  public String getColumn() {
    return column;
  }

  public void setColumn(String column) {
    this.column = column;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Object getValue() {
    return value;
  }

  public void setValue(Object value) {
    this.value = value;
  }

  public TypeHandler<?> getTypeHandler() {
    return typeHandler;
  }

  public void setTypeHandler(TypeHandler<?> typeHandler) {
    this.typeHandler = typeHandler;
  }

  public ColumnHandler<?> getColumnHandler() {
    return columnHandler;
  }

  public void setColumnHandler(ColumnHandler<?> columnHandler) {
    this.columnHandler = columnHandler;
  }

  public Operator getOperate() {
    return operate;
  }

  public void setOperate(Operator operate) {
    this.operate = operate;
  }

  public boolean isIgnoreNull() {
    return (ignoreNull==null) ? false: ignoreNull;
  }
  
  public void setIgnoreNull(Boolean ignoreNull) {
    this.ignoreNull = ignoreNull;
  }

  public boolean isIgnoreEmpty() {
    return (ignoreEmpty==null) ? false: ignoreEmpty;
  }
  
  public void setIgnoreEmpty(Boolean ignoreEmpty) {
    this.ignoreEmpty = ignoreEmpty;
  }

  public Dynamic getDynamic() {
    return dynamic;
  }

  public void setDynamic(Dynamic dynamic) {
    this.dynamic = dynamic;
  }

  public List<NodeEntry> getNodes() {
    return nodes;
  }

  public void setNodes(List<NodeEntry> nodes) {
    this.nodes = nodes;
  }

  public Entry addNode(NodeEntry node) {
    if (nodes == null) {
      nodes = new ArrayList<NodeEntry>();
    }
    nodes.add(node);
    return this;
  }

}
