package com.mongodb.orm.executor.parser;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.mongodb.exception.MongoORMException;
import com.mongodb.orm.MqlMapConfiguration;
import com.mongodb.orm.engine.config.MappingConfig;
import com.mongodb.orm.engine.entry.Entry;
import com.mongodb.orm.engine.entry.NodeEntry;
import com.mongodb.orm.engine.entry.Operator;
import com.mongodb.orm.engine.type.TypeHandler;
import com.mongodb.orm.executor.MqlExecutor;
import com.mongodb.orm.executor.strategy.StrategyChain;
import com.mongodb.orm.executor.strategy.StrategyContext;
import com.mongodb.util.ObjectUtils;

/**
 * Mongodb MQL action executor.
 * 
 * <update>
 *     <action>
 *         ......
 *     </action>
 * </update> 
 * 
 * @author: xiangping_yu
 * @data : 2015-09-28
 * @since : 1.5
 */
public class ActionExecutor implements MqlExecutor<Map<String, Object>> {

  @Override
  public Map<String, Object> parser(MqlMapConfiguration configuration, NodeEntry entry, Object target) throws MongoORMException {
    List<Entry> entrys = entry.getNodeMappings();
    TypeHandler<?> typeHandler = entry.getTypeHandler();
    String mappingId = entry.getMappingId();
    if (mappingId != null) {
      MappingConfig mapping = (MappingConfig) configuration.getMapping(mappingId);
      entrys = mapping.getNodes();
      typeHandler = mapping.getTypeHandler();
    }

    OptSet optSet = new OptSet();
    Map<String, Object> action = new LinkedHashMap<String, Object>();
    for (Entry ety : entrys) {
      StrategyChain chain = StrategyChain.getInstance();
      StrategyContext context = new StrategyContext(ety, target, callback);
      context.setTypeHandler(typeHandler);
      
      chain.doStrategy(configuration, context);

      Object value = context.getValue();
      if(!ObjectUtils.checkValueNull(value, ety)) {
        String column = ety.getColumn();
        String name = ety.getName();
        Operator operate = ety.getOperate();
        
        if(operate != null) {
          if(name != null) {
            action.put(operate.getOpt(), value);
          } else {
            optSet.addOpt(operate.getOpt(), column, value);
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
    public Map<String, Object> callBack(MqlMapConfiguration configuration, NodeEntry entry, Object target) throws MongoORMException {
      return parser(configuration, entry, target);
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
