package com.mongodb.orm.engine.config;

import org.apache.commons.lang.StringUtils;

import com.mongodb.constant.MongoConstant;
import com.mongodb.exception.StatementException;
import com.mongodb.orm.engine.Config;
import com.mongodb.orm.engine.entry.NodeEntry;

/**
 * XML Select config
 * 
 * @author: xiangping_yu
 * @data : 2014-3-6
 * @since : 1.5
 */
public class SelectConfig implements Config {

  private static final long serialVersionUID = 8093241263645912723L;

  /**
   * ORM query config id.
   */
  private String id;

  /**
   * Mongo db collection.
   */
  private String collection;

  /**
   * Query parameter.
   */
  private NodeEntry query;
  /**
   * Query return field.
   */
  private NodeEntry field;
  /**
   * Query order.
   */
  private NodeEntry order;

  public SelectConfig(String id, String collection) {
    this.id = id;
    this.collection = collection;
  }

  /**
   * Add order parameter to config.
   * 
   * @param column Database column name.
   * @param orderType Order type, 'asc' or 'desc'
   */
  public int getOrder(String column, String orderType) {
    Order order = Order.fromName(orderType);
    if (order == null) {
      throw new StatementException("No order type found '" + orderType + "' by '" + column + "', please use 'asc' or 'desc'.");
    }
    return order.value;
  }

  private static enum Order {
    ASC("asc", MongoConstant.ORDER_ASC), DESC("desc", MongoConstant.ORDER_DESC);

    private String order;
    private int value;

    private Order(String order, int value) {
      this.order = order;
      this.value = value;
    }

    public static Order fromName(String name) {
      if (StringUtils.isBlank(name)) {
        return null;
      }

      for (Order order : values()) {
        if (order.order.equals(name)) {
          return order;
        }
      }
      return null;
    }
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

  public NodeEntry getOrder() {
    return order;
  }

  public void setOrder(NodeEntry order) {
    this.order = order;
  }

  public String getId() {
    return id;
  }

  public String getCollection() {
    return collection;
  }

}
