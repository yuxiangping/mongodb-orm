package com.mongodb.orm.executor.strategy;

import com.mongodb.orm.MqlMapConfiguration;
import com.mongodb.orm.engine.entry.Entry;
import com.mongodb.orm.engine.type.ColumnHandler;

/**
 * Column strategy  
 * @author: xiangping_yu
 * @data : 2015-9-16
 * @since : 1.5
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
