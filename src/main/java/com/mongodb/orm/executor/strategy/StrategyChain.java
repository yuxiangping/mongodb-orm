package com.mongodb.orm.executor.strategy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.mongodb.orm.MqlMapConfiguration;

/**
 * Mql value strategy chain 
 * @author: xiangping_yu
 * @data : 2015-9-16
 * @since : 1.5
 */
public class StrategyChain {
  
  private static StrategyChain instance = new StrategyChain();
  
  private List<Strategy> strategys;
  private Iterator<Strategy> iterator;
  
  public static StrategyChain getInstance() {
    return instance;
  }
  
  private StrategyChain() {
    strategys = new ArrayList<Strategy>();
    strategys.add(new DefaultStrategy());
    strategys.add(new ValueStrategy());
    strategys.add(new LabelStrategy());
    strategys.add(new DynamicStrategy());
    strategys.add(new NodeStrategy());
    strategys.add(new ColumnStrategy());
    
    iterator = strategys.iterator();
  }
  
  public void doStrategy(MqlMapConfiguration configuration, StrategyContext context) {
    if(iterator.hasNext()) {
      Strategy nextStrategy = iterator.next();
      nextStrategy.doStrategy(configuration, context, this);
    }
  }
  
}