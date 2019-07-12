package org.yy.mongodb.orm.executor.strategy;

import org.yy.mongodb.orm.MqlMapConfiguration;

/**
 * Mql value strategy 
 * @author: xiangping_yu
 * @data : 2015-9-16
 * @since : 1.5
 */
public interface Strategy {

  void doStrategy(MqlMapConfiguration configuration, StrategyContext context, StrategyChain chain);
  
}
