package org.yy.mongodb.orm.builder.statement;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;
import org.yy.mongodb.orm.engine.Config;

/**
 * Xml element node builder handler.
 * 
 * @author: xiangping_yu
 * @data : 2014-3-7
 * @since : 1.5
 */
public interface StatementHandler {

  Logger logger = LoggerFactory.getLogger(StatementHandler.class);

  Config handler(Node node);

}
