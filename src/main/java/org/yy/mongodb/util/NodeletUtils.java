package org.yy.mongodb.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.yy.mongodb.orm.MqlMapConfiguration;
import org.yy.mongodb.orm.engine.config.MappingConfig;
import org.yy.mongodb.orm.engine.entry.Entry;

/**
 * Nodelet utils for xml node parser.
 * @author yy
 */
public class NodeletUtils {

  private NodeletUtils() {}
  
  public static Properties parseAttributes(Node node) {
    Properties attributes = new Properties();
    NamedNodeMap attributeNodes = node.getAttributes();
    for (int i = 0; i < attributeNodes.getLength(); i++) {
      Node attribute = attributeNodes.item(i);
      attributes.put(attribute.getNodeName(), attribute.getNodeValue());
    }
    return attributes;
  }
  
  public static List<Entry> getMappingEntry(MappingConfig mapping, MqlMapConfiguration configuration) {
    List<Entry> list = new ArrayList<Entry>();
    getEntrys(list, mapping, configuration);
    return list;
  }
  
  private static void getEntrys(List<Entry> list, MappingConfig mapping, MqlMapConfiguration configuration) {
    String extend = mapping.getExtend();
    if (extend != null) {
      getEntrys(list, (MappingConfig)configuration.getMapping(mapping.getNamespace(), mapping.getId()), configuration);
    }
    list.addAll(mapping.getNodes());
  }
}
