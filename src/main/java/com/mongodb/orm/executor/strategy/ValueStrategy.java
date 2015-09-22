package com.mongodb.orm.executor.strategy;

import com.mongodb.orm.MqlMapConfiguration;
import com.mongodb.orm.engine.entry.Entry;

/**
 * Value strategy
 * @author: xiangping_yu
 * @data : 2015-9-16
 * @since : 1.5
 */
public class ValueStrategy implements Strategy {

  @Override
  public void doStrategy(MqlMapConfiguration configuration, StrategyContext context, StrategyChain chain) {
    Entry entry = context.getEntry();
    Object value = entry.getValue();
    if(value != null) {
        context.setValue(value);
    }
    chain.doStrategy(configuration, context);
  }

}
