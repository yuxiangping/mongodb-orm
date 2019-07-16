package org.yy.mongodb.orm.executor.strategy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.yy.mongodb.orm.MqlMapConfiguration;

/**
 * Mql value strategy chain 
 * @author yy
 */
public class StrategyChain {
  
  private Iterator<Strategy> iterator;
  private static List<Strategy> strategys;
  
  static {
    strategys = new ArrayList<Strategy>();
    strategys.add(new DefaultStrategy());
    strategys.add(new ValueStrategy());
    strategys.add(new LabelStrategy());
    strategys.add(new DynamicStrategy());
    strategys.add(new NodeStrategy());
    strategys.add(new ColumnStrategy());
  }
  
  public StrategyChain() {
    iterator = strategys.iterator();
  }
  
  public void doStrategy(MqlMapConfiguration configuration, StrategyContext context) {
    if(iterator.hasNext()) {
      Strategy nextStrategy = iterator.next();
      nextStrategy.doStrategy(configuration, context, this);
    }
  }
  
}