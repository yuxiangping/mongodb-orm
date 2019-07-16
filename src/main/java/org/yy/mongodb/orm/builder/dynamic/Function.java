package org.yy.mongodb.orm.builder.dynamic;

import org.w3c.dom.Node;

/**
 * Dynamic function.
 * @author yy
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
