package org.yy.mongodb.orm.executor.parser;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.yy.mongodb.exception.MongoORMException;
import org.yy.mongodb.orm.MqlMapConfiguration;
import org.yy.mongodb.orm.engine.config.MappingConfig;
import org.yy.mongodb.orm.engine.entry.Entry;
import org.yy.mongodb.orm.engine.entry.NodeEntry;
import org.yy.mongodb.orm.engine.type.TypeHandler;
import org.yy.mongodb.orm.executor.MqlExecutor;
import org.yy.mongodb.orm.executor.strategy.StrategyChain;
import org.yy.mongodb.orm.executor.strategy.StrategyContext;
import org.yy.mongodb.util.ObjectUtils;

/**
 * Mongodb MQL action executor.
 * 
 * <update>
 *     <action>
 *         ......
 *     </action>
 * </update> 
 * 
 * @author yy
 */
public class ActionExecutor implements MqlExecutor<Map<String, Object>> {

  @Override
  public Map<String, Object> parser(String namespace, MqlMapConfiguration configuration, NodeEntry entry, Object target) throws MongoORMException {
    List<Entry> entrys = entry.getNodeMappings();
    TypeHandler<?> typeHandler = entry.getTypeHandler();
    String mappingId = entry.getMappingId();
    if (mappingId != null) {
      MappingConfig mapping = (MappingConfig) configuration.getMapping(namespace, mappingId);
      entrys = mapping.getNodes();
      typeHandler = mapping.getTypeHandler();
    }

    OptSet optSet = new OptSet();
    Map<String, Object> action = new LinkedHashMap<String, Object>();
    for (Entry ety : entrys) {
      StrategyContext context = new StrategyContext(ety, target, callback);
      context.setTypeHandler(typeHandler);
      
      new StrategyChain().doStrategy(namespace, configuration, context);

      Object value = context.getValue();
      if(!ObjectUtils.checkValueNull(value, ety)) {
        String column = ety.getColumn();
        String name = ety.getName();
        String operate = ety.getOperate();
        
        if(operate != null) {
          if(name != null) {
            action.put(operate, value);
          } else {
            optSet.addOpt(operate, column, value);
          }
        } else {
          action.put(column, value);
        }
      }
    }
    optSet.excute(action);
    return action;
  }
  
  CallBack<Map<String, Object>> callback = new CallBack<Map<String, Object>>() {
    @Override
    public Map<String, Object> callback(String namespace, MqlMapConfiguration configuration, NodeEntry entry, Object target) throws MongoORMException {
      return parser(namespace, configuration, entry, target);
    }
  };

  class OptSet {
    Map<String, Map<String, Object>> operateSet = new LinkedHashMap<String, Map<String, Object>>();

    void addOpt(String opt, String column, Object value) {
      Map<String, Object> map = operateSet.get(opt);
      if (map == null) {
        map = new HashMap<String, Object>();
        map.put(column, value);
        operateSet.put(opt, map);
      } else {
        map.put(column, value);
      }
    }

    OptSet excute(Map<String, Object> action) {
      for (String opt : operateSet.keySet()) {
        Map<String, Object> map = operateSet.get(opt);
        Map<String, Object> value = new HashMap<String, Object>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
          value.put(entry.getKey(), entry.getValue());
        }
        action.put(opt, value);
      }
      return this;
    }
  }

}
