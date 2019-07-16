package org.yy.mongodb.orm.builder.statement;


import java.util.List;
import java.util.Properties;

import org.springframework.util.StringUtils;
import org.w3c.dom.Node;
import org.yy.mongodb.constant.ORM;
import org.yy.mongodb.exception.StatementException;
import org.yy.mongodb.orm.engine.Config;
import org.yy.mongodb.orm.engine.config.MappingConfig;
import org.yy.mongodb.orm.engine.entry.Entry;
import org.yy.mongodb.orm.engine.type.TypeHandlerFactory;
import org.yy.mongodb.util.NodeletUtils;

/**
 * Transform XML file for ORM, "mapping" node statement.
 * @author yy
 */
public class MappingStatement extends BaseStatement implements StatementHandler {

  private String id;
  
  public MappingStatement(String id) {
    this.id = id;
  }
  
  @Override
  public Config handler(Node node) {
    Properties attributes = NodeletUtils.parseAttributes(node);
    String className = attributes.getProperty(ORM.ORM_CLASS);
    String extend = attributes.getProperty(ORM.ORM_EXTENDS);
    
    if(StringUtils.isEmpty(className)) {
      throw new StatementException("Attribute 'class' not found.");
    }
    
    Class<?> clazz = null;
    try {
      clazz = Class.forName(className);
    } catch (ClassNotFoundException e) {
      throw new StatementException("Class not found by name '"+className+"' for mapping id '"+id+"'.");
    }
    
    List<Entry> nodes = getEntry(node.getChildNodes(), clazz);
    
    MappingConfig config = new MappingConfig(id, clazz, extend, nodes);
    config.setTypeHandler(TypeHandlerFactory.getTypeHandler(clazz));
    
    logger.info("Mongodb orm inited mapping config with mapping id '"+id+"'.");
    return config;
  }
  
}
