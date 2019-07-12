package org.yy.mongodb.orm.executor.parser;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.yy.mongodb.exception.MongoORMException;
import org.yy.mongodb.orm.MqlMapConfiguration;
import org.yy.mongodb.orm.engine.config.MappingConfig;
import org.yy.mongodb.orm.engine.entry.Entry;
import org.yy.mongodb.orm.engine.entry.NodeEntry;
import org.yy.mongodb.orm.engine.entry.Operator;
import org.yy.mongodb.orm.engine.type.TypeHandler;
import org.yy.mongodb.orm.executor.MqlExecutor;
import org.yy.mongodb.orm.executor.strategy.StrategyChain;
import org.yy.mongodb.orm.executor.strategy.StrategyContext;
import org.yy.mongodb.util.ObjectUtils;

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
      StrategyContext context = new StrategyContext(ety, target, callback);
      context.setTypeHandler(typeHandler);
      
      new StrategyChain().doStrategy(configuration, context);
      
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
