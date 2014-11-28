package com.mongodb.orm.builder.statement;


import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.mongodb.constant.ORM;
import com.mongodb.exception.StatementException;
import com.mongodb.orm.builder.NodeletUtils;
import com.mongodb.orm.engine.Config;
import com.mongodb.orm.engine.config.AggregateConfig;
import com.mongodb.orm.engine.entry.NodeEntry;

/**
 * Transform SQL file for ORM, "aggregate" node statement.
 * 
 * @author: xiangping_yu
 * @data : 2014-1-22
 * @since : 1.5
 */
public class AggregateStatement extends BaseStatement implements StatementHandler {

  private String id;

  /**
   * Aggregate node analyzes.
   */
  @SuppressWarnings("serial")
  private static final Map<String, NodeAnalyze<AggregateConfig>> analyzes = new HashMap<String, NodeAnalyze<AggregateConfig>>() {
    {
      put(ORM.NODE_FUNCTION, new FunctionNodeAnalyze());
      put(ORM.NODE_FIELD, new FieldNodeAnalyze());
    }
  };

  public AggregateStatement(String id) {
    this.id = id;
  }

  @Override
  public Config handler(Node node) {
    Properties attributes = NodeletUtils.parseAttributes(node);
    String collection = attributes.getProperty(ORM.TAG_DB_COLLECTION);

    AggregateConfig aggregate = new AggregateConfig(id, collection);

    NodeList childNodes = node.getChildNodes();
    for (int i = 0; i < childNodes.getLength(); i++) {
      Node n = childNodes.item(i);
      String nodeName = n.getNodeName();

      NodeAnalyze<AggregateConfig> analyze = analyzes.get(nodeName);
      if (analyze == null) {
        throw new StatementException("Error child node for 'group', id '" + id + "'.");
      }
      analyze.analyze(aggregate, n);
    }

    logger.debug("Mongo config '" + id + "' has inited.");
    return aggregate;
  }

  private static class FunctionNodeAnalyze implements NodeAnalyze<AggregateConfig> {
    @Override
    public void analyze(AggregateConfig config, Node node) {
      if (config.getFunction() != null) {
        throw new StatementException("Alias name conflict occurred.  The node 'function' is already exists in '" + config.getId() + "'.");
      }

      Properties attributes = NodeletUtils.parseAttributes(node);
      String clzz = attributes.getProperty(ORM.ORM_CLASS);
      String mapping = attributes.getProperty(ORM.ORM_MAPPING);
      
      String operate = attributes.getProperty(ORM.TAG_OPERATE);

      NodeList childNodes = node.getChildNodes();
      if (childNodes.getLength() == 0) {
        throw new StatementException("Error aggregate 'function' node. Because the node 'pipeline' not exists in '" + config.getId() + "'.");
      }

      for (int i = 0; i < childNodes.getLength(); i++) {
        Node n = childNodes.item(i);

      }

    }
  }

  private static class FieldNodeAnalyze implements NodeAnalyze<AggregateConfig> {
    @Override
    public void analyze(AggregateConfig config, Node node) {
      if (config.getField() != null) {
        throw new StatementException("Alias name conflict occurred.  The node 'field' is already exists in '" + config.getId() + "'.");
      }

      NodeEntry entry = getField(config.getId(), node);
      config.setField(entry);
    }
  }

}
