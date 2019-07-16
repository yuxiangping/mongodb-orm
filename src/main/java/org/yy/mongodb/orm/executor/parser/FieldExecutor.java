package org.yy.mongodb.orm.executor.parser;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.yy.mongodb.exception.MongoORMException;
import org.yy.mongodb.orm.MqlMapConfiguration;
import org.yy.mongodb.orm.engine.config.MappingConfig;
import org.yy.mongodb.orm.engine.entry.Entry;
import org.yy.mongodb.orm.engine.entry.NodeEntry;
import org.yy.mongodb.orm.executor.MqlExecutor;

/**
 * Mongodb MQL field executor.
 * 
 * <select>
 *     <field>
 *         ......
 *     </field>
 * </select> 
 * 
 * @author yy
 */
public class FieldExecutor implements MqlExecutor<Map<String, Object>> {

  @Override
  public Map<String, Object> parser(MqlMapConfiguration configuration, NodeEntry entry, Object target) throws MongoORMException {
    List<Entry> entrys = entry.getNodeMappings();
    String mappingId = entry.getMappingId();
    if (mappingId != null) {
      MappingConfig mapping = (MappingConfig) configuration.getMapping(mappingId);
      entrys = mapping.getNodes();
    }
    
    Map<String, Object> field = new LinkedHashMap<String, Object>();
    for (Entry ety : entrys) {
// parent node include children fields
//      List<NodeEntry> nodes = ety.getNodes();
//      if(!ObjectUtils.isEmpty(nodes)) {
//        for(NodeEntry node : nodes) {
//          field.putAll(callback.callBack(configuration, node, target));
//        }
//      }
      field.put(ety.getColumn(), 1);
    }
    return field;
  }

  CallBack<Map<String, Object>> callback = new CallBack<Map<String, Object>>() {
    @Override
    public Map<String, Object> callBack(MqlMapConfiguration configuration, NodeEntry entry, Object target) throws MongoORMException {
      return parser(configuration, entry, target);
    }
  };
  
}
