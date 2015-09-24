package com.mongodb.orm.executor.strategy;

import java.util.List;

import com.mongodb.orm.MqlMapConfiguration;
import com.mongodb.orm.engine.entry.Entry;
import com.mongodb.orm.engine.entry.NodeEntry;
import com.mongodb.orm.executor.MqlExecutor.CallBack;
import com.mongodb.util.ObjectUtils;

/**
 * Child node strategy 
 * @author: xiangping_yu
 * @data : 2015-9-16
 * @since : 1.5
 */
public class NodeStrategy implements Strategy {

  @Override
  public void doStrategy(MqlMapConfiguration configuration, StrategyContext context, StrategyChain chain) {
    Entry entry = context.getEntry();
    List<NodeEntry> nodes = entry.getNodes();
    if(!ObjectUtils.isEmpty(nodes)) {
      int count = nodes.size();
      Object[] array = new Object[count];
      CallBack<?> callback = context.getCallback();
      for(int i=0; i<count; i++) {
        array[i] = callback.callBack(configuration, nodes.get(i), context.getTarget());
      }
      
      if(count == 1) {
        context.setValue(array[0]);
      } else {
        context.setValue(array);
      }
    }
    chain.doStrategy(configuration, context);
  }

}
