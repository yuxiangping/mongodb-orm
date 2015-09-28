package com.mongodb.orm.executor.parser;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.mongodb.exception.MongoORMException;
import com.mongodb.orm.MqlMapConfiguration;
import com.mongodb.orm.engine.config.MappingConfig;
import com.mongodb.orm.engine.entry.Entry;
import com.mongodb.orm.engine.entry.NodeEntry;
import com.mongodb.orm.engine.type.TypeHandler;
import com.mongodb.orm.executor.MqlExecutor;
import com.mongodb.util.ObjectUtils;

/**
 * Mongodb MQL field result executor.
 * 
 * <select>
 *     <field>
 *         ......
 *     </field>
 * </select> 
 * 
 * @author: xiangping_yu
 * @data : 2015-09-28
 * @since : 1.5
 */
@SuppressWarnings("unchecked")
public class ResultExecutor implements MqlExecutor<Object> {

  @Override
  public Object parser(MqlMapConfiguration configuration, NodeEntry entry, Object target) throws MongoORMException {
    List<Entry> entrys = entry.getNodeMappings();
    String mappingId = entry.getMappingId();
    if (mappingId != null) {
      MappingConfig mapping = (MappingConfig) configuration.getMapping(mappingId);
      entrys = mapping.getNodes();
    }
    
    Object instance = buildInstance(entry, target);
    if(instance == null) {
      return null;
    }
    
    if(target instanceof List) {
      List<Object> list = new ArrayList<Object>();
      for(Object result : (List<Map<String, Object>>)target) {
        list.add(getResult(configuration, entry, entrys, result));
      }
      return list;
    } else {
      return getResult(configuration, entry, entrys, target);
    }
  }

  private Object buildInstance(NodeEntry entry, Object target) {
    if(ObjectUtils.isEmpty(target)) {
      return null;
    }
    
    Class<?> clazz = entry.getClass();
    if(clazz.equals(Map.class)) {
      return new LinkedHashMap<String, Object>();
    }
    
    try {
      return clazz.newInstance();
    } catch (Exception ex) {
      throw new MongoORMException("Build object instance error. Class:"+clazz,  ex);
    }
  }
  
  private Object getResult(MqlMapConfiguration configuration, NodeEntry entry, List<Entry> entryNodes, Object target) {
    Object instance = buildInstance(entry, target);
    Map<String, Object> resultSet = (Map<String, Object>)target;
    for (Entry ety : entryNodes) {
      TypeHandler<?> handler = ety.getTypeHandler();
      List<NodeEntry> nodes = ety.getNodes();
      Object value = resultSet.get(ety.getColumn());
      if(!ObjectUtils.isEmpty(nodes)) {
        List<Object> array = new ArrayList<Object>();
        for(NodeEntry node : nodes) {
          array.add(callback.callBack(configuration, node, value));
        }
        
        if(array.size() == 1) {
          instance = handler.getResult(instance, array.get(0));
        } else {
          instance = handler.getResult(instance, array);
        }
      } else {
        instance = handler.getResult(instance, value);
      }
    }
    return instance;
  }
  
  CallBack<Object> callback = new CallBack<Object>() {
    @Override
    public Object callBack(MqlMapConfiguration configuration, NodeEntry entry, Object target) throws MongoORMException {
      return parser(configuration, entry, target);
    }
  };
  
}
