package org.yy.mongodb.orm.executor.strategy;

import org.yy.mongodb.constant.ORM;
import org.yy.mongodb.orm.MqlMapConfiguration;
import org.yy.mongodb.orm.engine.entry.Entry;

/**
 * Label strategy  ${value} 
 * @author yy
 */
public class LabelStrategy implements Strategy {

  @Override
  public void doStrategy(MqlMapConfiguration configuration, StrategyContext context, StrategyChain chain) {
    Entry entry = context.getEntry();
    Object value = entry.getValue();
    if(value != null && ORM.LABEL_VALUE.equals(value)) {
      context.setValue(context.getTarget());
    }
    chain.doStrategy(configuration, context);
  }

}
