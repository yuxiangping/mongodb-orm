package com.mongodb.orm.executor.parser;

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
 * Mongodb MQL query executor.
 * 
 * <select>
 *     <query>
 *         ......
 *     </query>
 * </select> 
 * 
 * @author: xiangping_yu
 * @data : 2015-09-28
 * @since : 1.5
 */
public class QueryExecutor implements MqlExecutor<Map<String, Object>> {

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

    Map<String, Object> query = new LinkedHashMap<String, Object>();
    for (Entry ety : entrys) {
      StrategyChain chain = StrategyChain.getInstance();
      StrategyContext context = new StrategyContext(ety, target, callback);
      context.setTypeHandler(typeHandler);
      
      chain.doStrategy(configuration, context);

      Object value = context.getValue();
      if(!ObjectUtils.checkValueNull(value, ety)) {
        String column = ety.getColumn();
        Operator operate = ety.getOperate();
        
        if(operate == null) {
          query.put(column, value);
        } else {
          if(column == null) {
            query.put(operate.getOpt(), value);
          } else {
            Map<String, Object> qNode = new LinkedHashMap<String, Object>();
            qNode.put(operate.getOpt(), value); 
            query.put(column, qNode);
          }
        }
      }
    }
    return query;
  }

  CallBack<Map<String, Object>> callback = new CallBack<Map<String, Object>>() {
    @Override
    public Map<String, Object> callBack(MqlMapConfiguration configuration, NodeEntry entry, Object target) throws MongoORMException {
      return parser(configuration, entry, target);
    }
  };

}
