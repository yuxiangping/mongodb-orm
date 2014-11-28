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
import com.mongodb.orm.engine.config.InsertConfig;
import com.mongodb.orm.engine.entry.Entry;
import com.mongodb.orm.engine.entry.NodeEntry;

/**
 * Transform SQL file for ORM, "insert" node statement.
 * 
 * @author: xiangping_yu
 * @data : 2014-1-22
 * @since : 1.5
 */
public class InsertStatement extends BaseStatement implements StatementHandler {

  private String id;

  /**
   * insert node analyzes.
   */
  @SuppressWarnings("serial")
  private static final Map<String, NodeAnalyze<InsertConfig>> analyzes = new HashMap<String, NodeAnalyze<InsertConfig>>() {
    {
      put(ORM.NODE_DOCUMENT, new DocumentNodeAnalyze());
      put(ORM.NODE_SELECTKEY, new SelectKeyNodeAnalyze());
    }
  };

  public InsertStatement(String id) {
    this.id = id;
  }

  @Override
  public Config handler(Node node) {
    Properties attributes = NodeletUtils.parseAttributes(node);
    String collection = attributes.getProperty(ORM.TAG_DB_COLLECTION);

    InsertConfig insert = new InsertConfig(id, collection);

    NodeList childNodes = node.getChildNodes();
    for (int i = 0; i < childNodes.getLength(); i++) {
      Node n = childNodes.item(i);
      String nodeName = n.getNodeName();

      NodeAnalyze<InsertConfig> analyze = analyzes.get(nodeName);
      if (analyze == null) {
        throw new StatementException("Error child node for 'insert', id '" + id + "'.");
      }
      analyze.analyze(insert, n);
    }
    logger.debug("Mongo config '" + id + "' has inited.");
    return insert;
  }

  private static class DocumentNodeAnalyze implements NodeAnalyze<InsertConfig> {
    @Override
    public void analyze(InsertConfig config, Node node) {
      if (config.getDocument() != null) {
        throw new StatementException("Alias name conflict occurred.  The node 'document' is already exists in '" + config.getId() + "'.");
      }

      NodeEntry entry = getQuery(config.getId(), node);
      config.setDocument(entry);
    }
  }

  private static class SelectKeyNodeAnalyze implements NodeAnalyze<InsertConfig> {
    @Override
    public void analyze(InsertConfig config, Node node) {
      if (config.getSelectKey() != null) {
        throw new StatementException("Alias name conflict occurred.  The node 'selectKey' is already exists in '" + config.getId() + "'.");
      }

      Properties attributes = NodeletUtils.parseAttributes(node);

      String column = attributes.getProperty(ORM.TAG_CLUMN);
      String name = attributes.getProperty(ORM.TAG_NAME);

      Entry selectKey = new Entry();
      selectKey.setColumn(column);
      selectKey.setName(name);
      config.setSelectKey(selectKey);
    }
  }
  
}
