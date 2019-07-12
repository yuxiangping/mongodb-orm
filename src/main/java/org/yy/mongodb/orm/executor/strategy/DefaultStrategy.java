package org.yy.mongodb.orm.executor.strategy;

import org.yy.mongodb.orm.MqlMapConfiguration;
import org.yy.mongodb.orm.engine.entry.Entry;

/**
 * Default strategy  
 * @author: xiangping_yu
 * @data : 2015-9-16
 * @since : 1.5
 */
public class DefaultStrategy implements Strategy {

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
