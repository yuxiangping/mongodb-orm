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
    Class<?> clazz = entry.getClazz();
    TypeHandler<?> typeHandler = entry.getTypeHandler();
    String mappingId = entry.getMappingId();
    if (mappingId != null) {
      MappingConfig mapping = (MappingConfig) configuration.getMapping(mappingId);
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
        list.add(getResult(configuration, typeHandler, clazz, entrys, result));
      }
      return list;
    } else {
      return getResult(configuration, typeHandler, clazz, entrys, target);
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
  
  private Object getResult(MqlMapConfiguration configuration, TypeHandler<?> handler, Class<?> clazz, List<Entry> entryNodes, Object target) {
    Object instance = buildInstance(clazz, target);
    Map<String, Object> resultSet = (Map<String, Object>)target;
    for (Entry ety : entryNodes) {
      List<NodeEntry> nodes = ety.getNodes();
      Object value = resultSet.get(ety.getColumn());
      if(!ObjectUtils.isEmpty(nodes)) {
        List<Object> array = new ArrayList<Object>();
        for(NodeEntry node : nodes) {
          array.add(callback.callBack(configuration, node, value));
        }
        
        if(array.size() == 1) {
          instance = handler.getResult(ety.getName(), instance, array.get(0));
        } else {
          instance = handler.getResult(ety.getName(), instance, array);
        }
      } else {
        instance = handler.getResult(ety.getName(), instance, value);
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
