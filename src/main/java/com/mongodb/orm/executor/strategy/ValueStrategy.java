package com.mongodb.orm.executor.strategy;

import com.mongodb.orm.MqlMapConfiguration;
import com.mongodb.orm.engine.entry.Entry;
import com.mongodb.orm.engine.type.TypeHandler;

/**
 * Value strategy
 * @author: xiangping_yu
 * @data : 2015-9-16
 * @since : 1.5
 */
@SuppressWarnings("unchecked")
public class ValueStrategy implements Strategy {

  @Override
  public void doStrategy(MqlMapConfiguration configuration, StrategyContext context, StrategyChain chain) {
    Entry entry = context.getEntry();
    TypeHandler<Object> typeHandler = (TypeHandler<Object>)entry.getTypeHandler();
    if(typeHandler != null) {
      Object parameter = typeHandler.getParameter(context.getTarget());
      context.setValue(parameter);
    }
    chain.doStrategy(configuration, context);
  }

}
