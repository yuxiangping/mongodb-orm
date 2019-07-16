package org.yy.mongodb.orm.builder.statement;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;
import org.yy.mongodb.orm.engine.Config;

/**
 * Xml element node builder handler.
 * @author yy
 */
public interface StatementHandler {

  Logger logger = LoggerFactory.getLogger(StatementHandler.class);

  /**
   * Handler for mql node. 
   * @param namespace Mql namespace
   * @param node Mql node
   * @return Return the mql config.
   */
  Config handler(String namespace, Node node);

}
