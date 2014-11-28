package com.mongodb.orm.engine.entry;

import java.io.Serializable;

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
  private Boolean isIgnoreNull;
  /**
   * Dynamic node
   */
  private Dynamic dynamic;
  /**
   * Child node.
   */
  private NodeEntry node;

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

  public Boolean getIsIgnoreNull() {
    return isIgnoreNull;
  }

  public void setIsIgnoreNull(Boolean isIgnoreNull) {
    this.isIgnoreNull = isIgnoreNull;
  }

  public Dynamic getDynamic() {
    return dynamic;
  }

  public void setDynamic(Dynamic dynamic) {
    this.dynamic = dynamic;
  }

  public NodeEntry getNode() {
    return node;
  }

  public void setNode(NodeEntry node) {
    this.node = node;
  }

}
