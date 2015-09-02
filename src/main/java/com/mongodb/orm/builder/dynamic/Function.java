package com.mongodb.orm.builder.dynamic;

import org.w3c.dom.Node;

/**
 * Dynamic function.
 * 
 * @author: xiangping_yu
 * @data : 2014-1-22
 * @since : 1.5
 */
public interface Function {

  /**
   * Init the dynamic function.
   */
  void init(Node node, Class<?> clazz, Dynamic dynamic);

  /**
   * Parser the target object with dynamic function.
   */
  Object parser(FunctionData data, Object target);

  interface FunctionData {}

}
