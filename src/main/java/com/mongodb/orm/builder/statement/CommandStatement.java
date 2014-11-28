package com.mongodb.orm.builder.statement;


import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.mongodb.constant.ORM;
import com.mongodb.exception.StatementException;
import com.mongodb.orm.engine.Config;
import com.mongodb.orm.engine.config.CommandConfig;
import com.mongodb.orm.engine.entry.NodeEntry;

/**
 * Transform SQL file for ORM, "command" node statement.
 * @author: xiangping_yu
 * @data  : 2014-1-22
 * @since : 1.5
 */
public class CommandStatement extends BaseStatement implements StatementHandler {

  private String id;

  /**
   * Command node analyzes.
   */
  @SuppressWarnings("serial")
  private static final Map<String, NodeAnalyze<CommandConfig>> analyzes = new HashMap<String, NodeAnalyze<CommandConfig>>() {
    {
      put(ORM.NODE_QUERY, new QueryNodeAnalyze());
      put(ORM.NODE_FIELD, new FieldNodeAnalyze());
    }
  };
  
  public CommandStatement(String id) {
    this.id = id;
  }
  
  @Override
  public Config handler(Node node) {
    
    CommandConfig command = new CommandConfig(id);

    NodeList childNodes = node.getChildNodes();
    for (int i = 0; i < childNodes.getLength(); i++) {
      Node n = childNodes.item(i);
      String nodeName = n.getNodeName();

      NodeAnalyze<CommandConfig> analyze = analyzes.get(nodeName);
      if (analyze == null) {
        throw new StatementException("Error child node for 'command', id '" + id + "'.");
      }
      analyze.analyze(command, n);
    }

    logger.debug("Mongo config '" + id + "' has inited.");
    return command;
  }

  private static class QueryNodeAnalyze implements NodeAnalyze<CommandConfig> {
    @Override
    public void analyze(CommandConfig config, Node node) {
      if (config.getQuery() != null) {
        throw new StatementException("Alias name conflict occurred.  The node 'query' is already exists in '" + config.getId() + "'.");
      }

      NodeEntry entry = getQuery(config.getId(), node);
      config.setQuery(entry);
    }
  }

  private static class FieldNodeAnalyze implements NodeAnalyze<CommandConfig> {
    @Override
    public void analyze(CommandConfig config, Node node) {
      if (config.getField() != null) {
        throw new StatementException("Alias name conflict occurred.  The node 'field' is already exists in '" + config.getId() + "'.");
      }

      NodeEntry entry = getField(config.getId(), node);
      config.setField(entry);
    }
  }
	
}
