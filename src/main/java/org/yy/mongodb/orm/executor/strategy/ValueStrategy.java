package org.yy.mongodb.orm.executor.strategy;

import org.yy.mongodb.orm.MqlMapConfiguration;
import org.yy.mongodb.orm.engine.entry.Entry;
import org.yy.mongodb.orm.engine.type.TypeHandler;

/**
 * Value strategy
 * @author yy
 */
@SuppressWarnings("unchecked")
public class ValueStrategy implements Strategy {

  @Override
  public void doStrategy(MqlMapConfiguration configuration, StrategyContext context, StrategyChain chain) {
    Entry entry = context.getEntry();
    TypeHandler<Object> typeHandler = (TypeHandler<Object>)context.getTypeHandler();
    if(typeHandler != null) {
      Object parameter = typeHandler.getParameter(entry.getName(), context.getTarget());
      if(parameter != null) {
        context.setValue(parameter);
      }
    }
    chain.doStrategy(configuration, context);
  }

}
