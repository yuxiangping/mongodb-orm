package com.mongodb.orm.builder.statement;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.mongodb.constant.ORM;
import com.mongodb.exception.StatementException;
import com.mongodb.orm.builder.dynamic.Dynamic;
import com.mongodb.orm.builder.dynamic.DynamicFactory;
import com.mongodb.orm.engine.Config;
import com.mongodb.orm.engine.entry.ColumnType;
import com.mongodb.orm.engine.entry.Entry;
import com.mongodb.orm.engine.entry.NodeEntry;
import com.mongodb.orm.engine.entry.Operator;
import com.mongodb.orm.engine.type.TypeHandlerFactory;
import com.mongodb.orm.executor.parser.FieldParser;
import com.mongodb.orm.executor.parser.QueryParser;
import com.mongodb.util.NodeletUtils;

/**
 * Base statement handler for convenience.
 * 
 * @author: xiangping_yu
 * @data : 2014-7-31
 * @since : 1.5
 */
public abstract class BaseStatement implements StatementHandler {

  protected List<Entry> getEntry(NodeList nodes, Class<?> clazz) {
    List<Entry> entrys = new ArrayList<Entry>(nodes.getLength());
    for (int i = 0; i < nodes.getLength(); i++) {
      Node node = nodes.item(i);

      Properties attributes = NodeletUtils.parseAttributes(node);

      String column = attributes.getProperty(ORM.TAG_CLUMN);
      String name = attributes.getProperty(ORM.TAG_NAME);
      String operate = attributes.getProperty(ORM.TAG_OPERATE);
      String value = attributes.getProperty(ORM.TAG_VALUE);
      String type = attributes.getProperty(ORM.TAG_TYPE);
      String isIgnoreNull = attributes.getProperty(ORM.TAG_IGNORE_NULL);

      Entry entry = new Entry();
      entry.setColumn(column);
      entry.setName(name);
      entry.setOperate(Operator.formName(operate));
      entry.setValue(value);
      entry.setIsIgnoreNull(isIgnoreNull == null ? null : Boolean.parseBoolean(isIgnoreNull));
      entry.setColumnHandler(TypeHandlerFactory.getColumnHandler(ColumnType.fromType(type)));
      entry.setTypeHandler(TypeHandlerFactory.getTypeHandler(clazz, name));

      NodeList childNodes = node.getChildNodes();
      for (int j = 0; j < childNodes.getLength(); j++) {
        Node cnode = childNodes.item(j);
        String nodeName = cnode.getNodeName();
        // Child
        if (ORM.NODE_CHILD_NODE.equals(nodeName)) {
          entry.addNode(resolveChildNode(cnode, name));
        // Dynamic Function
        } else {
          entry.setDynamic(resolveDynamic(cnode, name, clazz));
        }
      }
      entrys.add(entry);
    }
    return entrys;
  }

  protected NodeEntry getQuery(String id, Node node) {
    Properties attributes = NodeletUtils.parseAttributes(node);
    String clzz = attributes.getProperty(ORM.ORM_CLASS);
    String mapping = attributes.getProperty(ORM.ORM_MAPPING);

    NodeEntry entry = new NodeEntry();
    entry.setExecutor(new QueryParser());
    if (mapping != null) {
      entry.setMappingId(mapping);
      return entry;
    }

    Class<?> clazz = null;
    if (clzz != null) {
      try {
        clazz = Class.forName(clzz);
      } catch (ClassNotFoundException e) {
        throw new StatementException("Class not found by name '" + clzz + "' for node 'query' in '" + id + "'.", e);
      }
      entry.setClazz(clazz);
    }

    NodeList childNodes = node.getChildNodes();
    if (childNodes.getLength() == 0) {
      throw new StatementException("Error node 'query' in '" + id + "', because child nodes not found.");
    }

    List<Entry> nodes = getEntry(childNodes, clazz);

    entry.setNodeMappings(nodes);
    return entry;
  }

  protected NodeEntry getField(String id, Node node) {
    Properties attributes = NodeletUtils.parseAttributes(node);
    String clzz = attributes.getProperty(ORM.ORM_CLASS);
    String mapping = attributes.getProperty(ORM.ORM_MAPPING);

    NodeEntry entry = new NodeEntry();
    entry.setExecutor(new FieldParser());
    if (mapping != null) {
      entry.setMappingId(mapping);
      return entry;
    }

    Class<?> clazz = null;
    if (clzz != null) {
      try {
        clazz = Class.forName(clzz);
      } catch (ClassNotFoundException e) {
        throw new StatementException("Class not found by name '" + clzz + "' for node 'field' in '" + id + "'.", e);
      }
      entry.setClazz(clazz);
    }

    NodeList childNodes = node.getChildNodes();
    if (childNodes.getLength() == 0) {
      throw new StatementException("Error node 'field' in '" + id + "'.");
    }

    List<Entry> nodes = getEntry(node.getChildNodes(), clazz);

    entry.setNodeMappings(nodes);
    return entry;
  }

  protected interface NodeAnalyze<T extends Config> {
    void analyze(T config, Node node);
  }

  private NodeEntry resolveChildNode(Node node, String name) {
    Properties attribute = NodeletUtils.parseAttributes(node);
    String clazz = attribute.getProperty(ORM.ORM_CLASS);
    String mapping = attribute.getProperty(ORM.ORM_MAPPING);

    NodeEntry entry = new NodeEntry();
    if (mapping != null) {
      entry.setMappingId(mapping);
    } else {
      Class<?> clzz = null;
      if (clazz != null) {
        try {
          clzz = Class.forName(clazz);
        } catch (ClassNotFoundException e) {
          throw new StatementException("Class not found by name '" + clazz + "' for child node '" + name + "'.", e);
        }
        entry.setClazz(clzz);
      }

      NodeList childNodes = node.getChildNodes();
      if (childNodes.getLength() == 0) {
        throw new StatementException("Error child node '" + name + "', because child nodes not found.");
      }

      List<Entry> childEntrys = getEntry(childNodes, clzz);
      entry.setNodeMappings(childEntrys);
    }
    return entry;
  }

  private Dynamic resolveDynamic(Node node, String name, Class<?> clazz) {
    try {
      return DynamicFactory.builder(node, clazz);
    } catch (Exception ex) {
      logger.error("Error build dynamic content for name '" + name + "'. Cause:", ex);
      throw new StatementException("Error build dynamic content for name '" + name + "'. Cause:", ex);
    }
  }

}
