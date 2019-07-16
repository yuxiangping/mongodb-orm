package org.yy.mongodb.orm.builder.statement;


import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.yy.mongodb.constant.ORM;
import org.yy.mongodb.exception.StatementException;
import org.yy.mongodb.orm.engine.Config;
import org.yy.mongodb.orm.engine.config.CommandConfig;
import org.yy.mongodb.orm.engine.entry.NodeEntry;

/**
 * Transform SQL file for ORM, "command" node statement.
 * @author yy
 */
public class CommandStatement extends BaseStatement implements StatementHandler {

  private String id;

  /**
   * Command node analyzes.
   */
  @SuppressWarnings("serial")
  private final Map<String, NodeAnalyze<CommandConfig>> analyzes = new HashMap<String, NodeAnalyze<CommandConfig>>() {
    {
      put(ORM.NODE_QUERY, new QueryNodeAnalyze());
      put(ORM.NODE_FIELD, new FieldNodeAnalyze());
    }
  };
  
  public CommandStatement(String id) {
    this.id = id;
  }
  
  @Override
  public Config handler(String namespace, Node node) {
    CommandConfig command = new CommandConfig(namespace, id);

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

  private class QueryNodeAnalyze implements NodeAnalyze<CommandConfig> {
    @Override
    public void analyze(CommandConfig config, Node node) {
      if (config.getQuery() != null) {
        throw new StatementException("Alias name conflict occurred.  The node 'query' is already exists in '" + config.getId() + "'.");
      }

      NodeEntry entry = getQuery(config.getId(), node);
      config.setQuery(entry);
    }
  }

  private class FieldNodeAnalyze implements NodeAnalyze<CommandConfig> {
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
