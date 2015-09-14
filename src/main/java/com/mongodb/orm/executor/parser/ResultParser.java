package com.mongodb.orm.executor.parser;

import com.mongodb.exception.MongoORMException;
import com.mongodb.orm.MqlMapConfiguration;
import com.mongodb.orm.engine.entry.NodeEntry;
import com.mongodb.orm.executor.MqlExecutor;

public class ResultParser<T> implements MqlExecutor<T> {

  @Override
  public T parser(MqlMapConfiguration configuration, NodeEntry entry, Object target) throws MongoORMException {
    // TODO Auto-generated method stub
    return null;
  }

  CallBack<T> callBack = new CallBack<T>() {
    @Override
    public T callBack(MqlMapConfiguration configuration, NodeEntry entry, Object target) throws MongoORMException {
      return parser(configuration, entry, target);
    }
  };
  
}
