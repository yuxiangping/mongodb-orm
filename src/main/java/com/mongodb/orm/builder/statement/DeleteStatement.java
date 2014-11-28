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
import com.mongodb.orm.engine.config.DeleteConfig;
import com.mongodb.orm.engine.entry.NodeEntry;

/**
 * Transform SQL file for ORM, "delete" node statement.
 * 
 * @author: xiangping_yu
 * @data : 2014-1-22
 * @since : 1.5
 */
public class DeleteStatement extends BaseStatement implements StatementHandler {

  private String id;

  /**
   * insert node analyzes.
   */
  @SuppressWarnings("serial")
  private static final Map<String, NodeAnalyze<DeleteConfig>> analyzes = new HashMap<String, NodeAnalyze<DeleteConfig>>() {
    {
      put(ORM.NODE_QUERY, new QueryNodeAnalyze());
    }
  };

  public DeleteStatement(String id) {
    this.id = id;
  }

  @Override
  public Config handler(Node node) {
    Properties attributes = NodeletUtils.parseAttributes(node);
    String collection = attributes.getProperty(ORM.TAG_DB_COLLECTION);

    DeleteConfig delete = new DeleteConfig(id, collection);

    NodeList childNodes = node.getChildNodes();
    for (int i = 0; i < childNodes.getLength(); i++) {
      Node n = childNodes.item(i);
      String nodeName = n.getNodeName();

      NodeAnalyze<DeleteConfig> analyze = analyzes.get(nodeName);
      if (analyze == null) {
        throw new StatementException("Error child node for 'delete', id '" + id + "'.");
      }
      analyze.analyze(delete, n);
    }
    logger.debug("Mongo config '" + id + "' has inited.");
    return delete;
  }

  private static class QueryNodeAnalyze implements NodeAnalyze<DeleteConfig> {
    @Override
    public void analyze(DeleteConfig config, Node node) {
      if (config.getQuery() != null) {
        throw new StatementException("Alias name conflict occurred.  The node 'query' is already exists in '" + config.getId() + "'.");
      }

      NodeEntry entry = getQuery(config.getId(), node);
      config.setQuery(entry);
    }
  }

}
