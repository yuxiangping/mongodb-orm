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
import com.mongodb.orm.engine.config.GroupConfig;
import com.mongodb.orm.engine.entry.NodeEntry;

/**
 * Transform SQL file for ORM, "group" node statement.
 * @author: xiangping_yu
 * @data  : 2014-1-22
 * @since : 1.5
 */
public class GroupStatement extends BaseStatement implements StatementHandler {

  private String id;

  /**
   * Group node analyzes.
   */
  @SuppressWarnings("serial")
  private static final Map<String, NodeAnalyze<GroupConfig>> analyzes = new HashMap<String, NodeAnalyze<GroupConfig>>() {
    {
      put(ORM.NODE_KEY, new KeyNodeAnalyze());
      put(ORM.NODE_INITIAL, new InitialNodeAnalyze());
      put(ORM.NODE_CONDITION, new ConditionNodeAnalyze());
      put(ORM.NODE_REDUCE, new ReduceNodeAnalyze());
      put(ORM.NODE_FINALIZE, new FinalizeNodeAnalyze());
      put(ORM.NODE_FIELD, new FieldNodeAnalyze());
    }
  };
  
  public GroupStatement(String id) {
    this.id = id;
  }
  
  @Override
  public Config handler(Node node) {
    Properties attributes = NodeletUtils.parseAttributes(node);
    String collection = attributes.getProperty(ORM.TAG_DB_COLLECTION);
    
    GroupConfig group = new GroupConfig(id, collection);
    
    NodeList childNodes = node.getChildNodes();
    for (int i = 0; i < childNodes.getLength(); i++) {
      Node n = childNodes.item(i);
      String nodeName = n.getNodeName();

      NodeAnalyze<GroupConfig> analyze = analyzes.get(nodeName);
      if (analyze == null) {
        throw new StatementException("Error child node for 'group', id '" + id + "'.");
      }
      analyze.analyze(group, n);
    }

    logger.debug("Mongo config '" + id + "' has inited.");
    return group;
  }

  private static class KeyNodeAnalyze implements NodeAnalyze<GroupConfig> {
    @Override
    public void analyze(GroupConfig config, Node node) {
      if (config.getKey() != null) {
        throw new StatementException("Alias name conflict occurred.  The node 'key' is already exists in '" + config.getId() + "'.");
      }

    }
  }

  private static class InitialNodeAnalyze implements NodeAnalyze<GroupConfig> {
    @Override
    public void analyze(GroupConfig config, Node node) {
      if (config.getInitial() != null) {
        throw new StatementException("Alias name conflict occurred.  The node 'initial' is already exists in '" + config.getId() + "'.");
      }

    }
  }
  
  private static class ConditionNodeAnalyze implements NodeAnalyze<GroupConfig> {
    @Override
    public void analyze(GroupConfig config, Node node) {
      if (config.getCondition() != null) {
        throw new StatementException("Alias name conflict occurred.  The node 'condition' is already exists in '" + config.getId() + "'.");
      }

    }
  }
  
  private static class ReduceNodeAnalyze implements NodeAnalyze<GroupConfig> {
    @Override
    public void analyze(GroupConfig config, Node node) {
      if (config.getReduce() != null) {
        throw new StatementException("Alias name conflict occurred.  The node 'reduce' is already exists in '" + config.getId() + "'.");
      }

    }
  }
  
  private static class FinalizeNodeAnalyze implements NodeAnalyze<GroupConfig> {
    @Override
    public void analyze(GroupConfig config, Node node) {
      if (config.getFinalize() != null) {
        throw new StatementException("Alias name conflict occurred.  The node 'finalize' is already exists in '" + config.getId() + "'.");
      }

    }
  }
  
  private static class FieldNodeAnalyze implements NodeAnalyze<GroupConfig> {
    @Override
    public void analyze(GroupConfig config, Node node) {
      if (config.getField() != null) {
        throw new StatementException("Alias name conflict occurred.  The node 'field' is already exists in '" + config.getId() + "'.");
      }

      NodeEntry entry = getField(config.getId(), node);
      config.setField(entry);
    }
  }
	
}
