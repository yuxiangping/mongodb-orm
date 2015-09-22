package com.mongodb.orm.executor.strategy;

import com.mongodb.constant.ORM;
import com.mongodb.orm.MqlMapConfiguration;
import com.mongodb.orm.engine.entry.Entry;

/**
 * Label strategy  ${value} 
 * @author: xiangping_yu
 * @data : 2015-9-16
 * @since : 1.5
 */
public class LabelStrategy implements Strategy {

  @Override
  public void doStrategy(MqlMapConfiguration configuration, StrategyContext context, StrategyChain chain) {
    Entry entry = context.getEntry();
    Object value = entry.getValue();
    if(value != null && value instanceof String) {
       if(ORM.LABEL_VALUE.equals(value)) {
         context.setValue(context.getTarget());
       }
    }
    chain.doStrategy(configuration, context);
  }

}
