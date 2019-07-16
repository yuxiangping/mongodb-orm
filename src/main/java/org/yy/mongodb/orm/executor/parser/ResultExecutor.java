package org.yy.mongodb.orm.executor.parser;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.yy.mongodb.exception.MongoORMException;
import org.yy.mongodb.orm.MqlMapConfiguration;
import org.yy.mongodb.orm.engine.config.MappingConfig;
import org.yy.mongodb.orm.engine.entry.Entry;
import org.yy.mongodb.orm.engine.entry.NodeEntry;
import org.yy.mongodb.orm.engine.type.ColumnHandler;
import org.yy.mongodb.orm.engine.type.TypeHandler;
import org.yy.mongodb.orm.executor.MqlExecutor;
import org.yy.mongodb.util.ObjectUtils;

/**
 * Mongodb MQL field result executor.
 * 
 * <select>
 *     <field>
 *         ......
 *     </field>
 * </select> 
 * 
 * @author yy
 */
@SuppressWarnings("unchecked")
public class ResultExecutor implements MqlExecutor<Object> {

  @Override
  public Object parser(String namespace, MqlMapConfiguration configuration, NodeEntry entry, Object target) throws MongoORMException {
    List<Entry> entrys = entry.getNodeMappings();
    Class<?> clazz = entry.getClazz();
    TypeHandler<?> typeHandler = entry.getTypeHandler();
    String mappingId = entry.getMappingId();
    if (mappingId != null) {
      MappingConfig mapping = (MappingConfig) configuration.getMapping(namespace, mappingId);
      entrys = mapping.getNodes();
      clazz = mapping.getClazz();
      typeHandler = mapping.getTypeHandler();
    }
    
    Object instance = buildInstance(clazz, target);
    if(instance == null) {
      return null;
    }
    
    if(target instanceof List) {
      List<Object> list = new ArrayList<Object>();
      for(Object result : (List<Map<String, Object>>)target) {
        list.add(getResult(namespace, configuration, typeHandler, clazz, entrys, result));
      }
      return list;
    } else {
      return getResult(namespace, configuration, typeHandler, clazz, entrys, target);
    }
  }

  private Object buildInstance(Class<?> clazz, Object target) {
    if(ObjectUtils.isEmpty(target)) {
      return null;
    }
    
    if(clazz.equals(Map.class)) {
      return new LinkedHashMap<String, Object>();
    }
    
    try {
      return clazz.newInstance();
    } catch (Exception ex) {
      throw new MongoORMException("Build object instance error. Class:"+clazz,  ex);
    }
  }
  
  private Object getResult(String namespace, MqlMapConfiguration configuration, TypeHandler<?> handler, Class<?> clazz, List<Entry> entryNodes, Object target) {
    Object instance = buildInstance(clazz, target);
    Map<String, Object> resultSet = (Map<String, Object>)target;
    for (Entry ety : entryNodes) {
      String name = (ety.getName()==null) ? ety.getColumn() : ety.getName();
      Object value = resultSet.get(ety.getColumn());
      ColumnHandler<Object> columnHandler = (ColumnHandler<Object>)ety.getColumnHandler();
      
      if(value instanceof List) {
        List<Object> result = new ArrayList<Object>();
        for(Object _value : (List<Object>)value) {
          result.add(getValue(namespace, _value, columnHandler, ety, configuration));
        } 
        value = result;
      } else {
        value = getValue(namespace, value, columnHandler, ety, configuration);
      }
      instance = handler.getResult(name, instance, value);
    }
    return instance;
  }
  
  private Object getValue(String namespace, Object value, ColumnHandler<Object> columnHandler, Entry ety, MqlMapConfiguration configuration) {
    if(columnHandler != null) {
      value = columnHandler.resovleValue(value);
    }
    
    List<NodeEntry> nodes = ety.getNodes();
    if(!ObjectUtils.isEmpty(nodes)) {
      List<Object> array = new ArrayList<Object>();
      for(NodeEntry node : nodes) {
        array.add(callback.callback(namespace, configuration, node, value));
      }
      
      if(nodes.size() == 1) {
        value = array.get(0);
      } else {
        value = array;
      }
    }
    return value;
  }
  
  CallBack<Object> callback = new CallBack<Object>() {
    @Override
    public Object callback(String namespace, MqlMapConfiguration configuration, NodeEntry entry, Object target) throws MongoORMException {
      return parser(namespace, configuration, entry, target);
    }
  };
  
}
