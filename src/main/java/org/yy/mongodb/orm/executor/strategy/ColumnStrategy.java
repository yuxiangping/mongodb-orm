package org.yy.mongodb.orm.executor.strategy;

import org.yy.mongodb.orm.MqlMapConfiguration;
import org.yy.mongodb.orm.engine.entry.Entry;
import org.yy.mongodb.orm.engine.type.ColumnHandler;

/**
 * Column strategy  
 * @author yy
 */
public class ColumnStrategy implements Strategy {

  @Override
  public void doStrategy(MqlMapConfiguration configuration, StrategyContext context, StrategyChain chain) {
    Entry entry = context.getEntry();
    ColumnHandler<?> columnHandler = entry.getColumnHandler();
    if(columnHandler != null && context.getValue() != null) {
      Object value = columnHandler.resovleColumn(context.getValue());
      context.setValue(value);
    }
    chain.doStrategy(configuration, context);
  }
  
}
