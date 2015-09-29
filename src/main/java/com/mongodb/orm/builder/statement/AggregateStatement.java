package com.mongodb.orm.builder.statement;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.mongodb.constant.ORM;
import com.mongodb.exception.StatementException;
import com.mongodb.orm.engine.Config;
import com.mongodb.orm.engine.config.AggregateConfig;
import com.mongodb.orm.engine.entry.Entry;
import com.mongodb.orm.engine.entry.NodeEntry;
import com.mongodb.orm.engine.type.TypeHandlerFactory;
import com.mongodb.util.NodeletUtils;

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
  private final Map<String, NodeAnalyze<AggregateConfig>> analyzes = new HashMap<String, NodeAnalyze<AggregateConfig>>() {
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

  private class FunctionNodeAnalyze implements NodeAnalyze<AggregateConfig> {
    @Override
    public void analyze(AggregateConfig config, Node node) {
      String id = config.getId();
      if (config.getFunction() != null) {
        throw new StatementException("Alias name conflict occurred.  The node 'function' is already exists in '" + id + "'.");
      }
      Properties attributes = NodeletUtils.parseAttributes(node);
      String className = attributes.getProperty(ORM.ORM_CLASS);

      Class<?> clazz = null;
      try {
        clazz = Class.forName(className);
      } catch (ClassNotFoundException e) {
        throw new StatementException("Class not found by name '"+className+"' for function of aggregate id '" + id + "'.");
      }
      
      NodeList childNodes = node.getChildNodes();
      if (childNodes.getLength() == 0) {
        throw new StatementException("Error aggregate 'function' node. Because the node 'pipeline' not exists in '" + id + "'.");
      }

      for (int i = 0; i < childNodes.getLength(); i++) {
        Node n = childNodes.item(i);
        if(!ORM.NODE_PIPELINE.equals(n.getNodeName())) {
          throw new StatementException("Error aggregate 'function' node. Child node must be 'pipeline' in '" + id + "'.");
        }
        
        Properties attr = NodeletUtils.parseAttributes(n);
        String operate = attr.getProperty(ORM.TAG_OPERATE);
        
        List<Entry> nodes = getEntry(n.getChildNodes(), clazz);
        
        NodeEntry entry = new NodeEntry();
        entry.setClazz(clazz);
        entry.setNodeMappings(nodes);
        entry.setExecutor(queryExecutor);
        entry.setTypeHandler(TypeHandlerFactory.getTypeHandler(clazz));
        
        config.addFunction(operate, entry);
      }
    }
  }

  private class FieldNodeAnalyze implements NodeAnalyze<AggregateConfig> {
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
