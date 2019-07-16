package org.yy.mongodb.orm.builder.statement;


import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.yy.mongodb.constant.ORM;
import org.yy.mongodb.exception.StatementException;
import org.yy.mongodb.orm.engine.Config;
import org.yy.mongodb.orm.engine.config.MapReduceConfig;
import org.yy.mongodb.orm.engine.entry.NodeEntry;
import org.yy.mongodb.orm.engine.entry.Script;
import org.yy.mongodb.util.NodeletUtils;

/**
 * Transform SQL file for ORM, "mapreduce" node statement.
 * @author yy
 */
public class MapReduceStatement extends BaseStatement implements StatementHandler {

  private String id;

  /**
   * MapReduce node analyzes.
   */
  @SuppressWarnings("serial")
  private final Map<String, NodeAnalyze<MapReduceConfig>> analyzes = new HashMap<String, NodeAnalyze<MapReduceConfig>>() {
    {
      put(ORM.NODE_MAP, new MapNodeAnalyze());
      put(ORM.NODE_REDUCE, new ReduceNodeAnalyze());
      put(ORM.NODE_FIELD, new FieldNodeAnalyze());
    }
  };
  
  public MapReduceStatement(String id) {
    this.id = id;
  }
  
  @Override
  public Config handler(String namespace, Node node) {
    Properties attributes = NodeletUtils.parseAttributes(node);
    String collection = attributes.getProperty(ORM.TAG_DB_COLLECTION);
    
    MapReduceConfig mapreduce = new MapReduceConfig(namespace, id, collection);
    NodeList childNodes = node.getChildNodes();
    for (int i = 0; i < childNodes.getLength(); i++) {
      Node n = childNodes.item(i);
      String nodeName = n.getNodeName();

      NodeAnalyze<MapReduceConfig> analyze = analyzes.get(nodeName);
      if (analyze == null) {
        throw new StatementException("Error child node for 'mapreduce', id '" + id + "'.");
      }
      analyze.analyze(mapreduce, n);
    }

    logger.debug("Mongo config '" + id + "' has inited.");
    return mapreduce;
  }

  private class MapNodeAnalyze implements NodeAnalyze<MapReduceConfig> {
    @Override
    public void analyze(MapReduceConfig config, Node node) {
      if (config.getMap() != null) {
        throw new StatementException("Alias name conflict occurred.  The node 'map' is already exists in '" + config.getId() + "'.");
      }
      config.setMap(new Script(node.getTextContent(), null));
    }
  }
  
  private class ReduceNodeAnalyze implements NodeAnalyze<MapReduceConfig> {
    @Override
    public void analyze(MapReduceConfig config, Node node) {
      if (config.getReduce() != null) {
        throw new StatementException("Alias name conflict occurred.  The node 'reduce' is already exists in '" + config.getId() + "'.");
      }
      config.setReduce(new Script(node.getTextContent(), null));
    }
  }
  
  private class FieldNodeAnalyze implements NodeAnalyze<MapReduceConfig> {
    @Override
    public void analyze(MapReduceConfig config, Node node) {
      if (config.getField() != null) {
        throw new StatementException("Alias name conflict occurred.  The node 'field' is already exists in '" + config.getId() + "'.");
      }

      NodeEntry entry = getField(config.getId(), node);
      config.setField(entry);
    }
  }
	
}
