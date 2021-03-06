package org.yy.mongodb.orm.builder.statement;


import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.yy.mongodb.constant.ORM;
import org.yy.mongodb.exception.StatementException;
import org.yy.mongodb.orm.engine.Config;
import org.yy.mongodb.orm.engine.config.DeleteConfig;
import org.yy.mongodb.orm.engine.entry.NodeEntry;
import org.yy.mongodb.util.NodeletUtils;

/**
 * Transform SQL file for ORM, "delete" node statement.
 * @author yy
 */
public class DeleteStatement extends BaseStatement implements StatementHandler {

  private String id;

  /**
   * insert node analyzes.
   */
  @SuppressWarnings("serial")
  private final Map<String, NodeAnalyze<DeleteConfig>> analyzes = new HashMap<String, NodeAnalyze<DeleteConfig>>() {
    {
      put(ORM.NODE_QUERY, new QueryNodeAnalyze());
    }
  };

  public DeleteStatement(String id) {
    this.id = id;
  }

  @Override
  public Config handler(String namespace, Node node) {
    Properties attributes = NodeletUtils.parseAttributes(node);
    String collection = attributes.getProperty(ORM.TAG_DB_COLLECTION);

    DeleteConfig delete = new DeleteConfig(namespace, id, collection);

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

  private class QueryNodeAnalyze implements NodeAnalyze<DeleteConfig> {
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
