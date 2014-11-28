package com.mongodb.orm.executor.parser;

import java.util.Map;

import com.mongodb.exception.MongoORMException;
import com.mongodb.orm.engine.entry.NodeEntry;
import com.mongodb.orm.executor.MqlExecutor;

public class QueryParser implements MqlExecutor<Map<String, Object>> {

  @Override
  public Map<String, Object> parser(NodeEntry entry, Object target) throws MongoORMException {
    
    
    return null;
  }

  CallBack<Map<String, Object>> callBack = new CallBack<Map<String, Object>>() {
    @Override
    public Map<String, Object> callBack(NodeEntry entry, Object target) throws MongoORMException {
      return parser(entry, target);
    }
  };

}
