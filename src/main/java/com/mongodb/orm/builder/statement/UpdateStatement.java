package com.mongodb.orm.builder.statement;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.mongodb.constant.ORM;
import com.mongodb.exception.StatementException;
import com.mongodb.orm.engine.Config;
import com.mongodb.orm.engine.config.UpdateConfig;
import com.mongodb.orm.engine.entry.Entry;
import com.mongodb.orm.engine.entry.NodeEntry;
import com.mongodb.orm.engine.type.TypeHandlerFactory;
import com.mongodb.util.NodeletUtils;

/**
 * Transform SQL file for ORM, "update" node statement.
 * 
 * @author: xiangping_yu
 * @data : 2014-1-22
 * @since : 1.5
 */
public class UpdateStatement extends BaseStatement implements StatementHandler {

  private String id;

  /**
   * update node analyzes.
   */
  @SuppressWarnings("serial")
  private final Map<String, NodeAnalyze<UpdateConfig>> analyzes = new HashMap<String, NodeAnalyze<UpdateConfig>>() {
    {
      put(ORM.NODE_QUERY, new QueryNodeAnalyze());
      put(ORM.NODE_FIELD, new FieldNodeAnalyze());
      put(ORM.NODE_ACTION, new ActionNodeAnalyze());
    }
  };

  public UpdateStatement(String id) {
    this.id = id;
  }

  @Override
  public Config handler(Node node) {
    Properties attributes = NodeletUtils.parseAttributes(node);
    String collection = attributes.getProperty(ORM.TAG_DB_COLLECTION);

    UpdateConfig update = new UpdateConfig(id, collection);

    NodeList childNodes = node.getChildNodes();
    for (int i = 0; i < childNodes.getLength(); i++) {
      Node n = childNodes.item(i);
      String nodeName = n.getNodeName();

      NodeAnalyze<UpdateConfig> analyze = analyzes.get(nodeName);
      if (analyze == null) {
        throw new StatementException("Error child node for 'update', id '" + id + "'.");
      }
      analyze.analyze(update, n);
    }
    logger.debug("Mongo config '" + id + "' has inited.");
    return update;
  }

  private class QueryNodeAnalyze implements NodeAnalyze<UpdateConfig> {
    @Override
    public void analyze(UpdateConfig config, Node node) {
      if (config.getQuery() != null) {
        throw new StatementException("Alias name conflict occurred.  The node 'query' is already exists in '" + config.getId() + "'.");
      }

      NodeEntry entry = getQuery(config.getId(), node);
      config.setQuery(entry);
    }
  }

  private class FieldNodeAnalyze implements NodeAnalyze<UpdateConfig> {
    @Override
    public void analyze(UpdateConfig config, Node node) {
      if (config.getField() != null) {
        throw new StatementException("Alias name conflict occurred.  The node 'field' is already exists in '" + config.getId() + "'.");
      }

      NodeEntry entry = getField(config.getId(), node);
      config.setField(entry);
    }
  }

  private class ActionNodeAnalyze implements NodeAnalyze<UpdateConfig> {
    @Override
    public void analyze(UpdateConfig config, Node node) {
      if (config.getAction() != null) {
        throw new StatementException("Alias name conflict occurred.  The node 'action' is already exists in '" + config.getId() + "'.");
      }

      Properties attributes = NodeletUtils.parseAttributes(node);
      String clzz = attributes.getProperty(ORM.ORM_CLASS);

      Class<?> clazz = null;
      if (clzz != null) {
        try {
          clazz = Class.forName(clzz);
        } catch (ClassNotFoundException e) {
          throw new StatementException("Class not found by name '" + clzz + "' for node 'order' in '" + config.getId() + "'.", e);
        }
      }

      NodeList childNodes = node.getChildNodes();
      List<Entry> entrys = new ArrayList<Entry>(childNodes.getLength());
      for (int i = 0; i < childNodes.getLength(); i++) {
        Node n = childNodes.item(i);
        Properties childAttributes = NodeletUtils.parseAttributes(n);
        String column = childAttributes.getProperty(ORM.TAG_CLUMN);
        String value = childAttributes.getProperty(ORM.TAG_VALUE);
        String name = attributes.getProperty(ORM.TAG_NAME);

        Entry entry = new Entry();
        entry.setColumn(column);
        entry.setName(name);
        entry.setValue(value);

        entrys.add(entry);
      }

      NodeEntry actionEntry = new NodeEntry();
      actionEntry.setExecutor(actionExecutor);
      actionEntry.setTypeHandler(TypeHandlerFactory.getTypeHandler(clazz));
      actionEntry.setClazz(clazz);
      actionEntry.setNodeMappings(entrys);
      config.setAction(actionEntry);
    }
  }

}
