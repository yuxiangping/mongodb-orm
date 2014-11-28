package com.mongodb.orm.builder;

import java.util.Properties;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * Nodelet utils for xml node parser.
 * @author: xiangping_yu
 * @data : 2014-7-14
 * @since : 1.5
 */
public class NodeletUtils {

  public static Properties parseAttributes(Node node) {
    Properties attributes = new Properties();
    NamedNodeMap attributeNodes = node.getAttributes();
    for (int i = 0; i < attributeNodes.getLength(); i++) {
      Node attribute = attributeNodes.item(i);
      attributes.put(attribute.getNodeName(), attribute.getNodeValue());
    }
    return attributes;
  }
  
}
