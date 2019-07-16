package org.yy.mongodb.orm.executor.strategy;

import org.yy.mongodb.orm.MqlMapConfiguration;

/**
 * Mql value strategy 
 * @author yy
 */
public interface Strategy {

  void doStrategy(String namespace, MqlMapConfiguration configuration, StrategyContext context, StrategyChain chain);
  
}
