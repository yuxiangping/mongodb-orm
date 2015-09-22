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
      CallBack<?> callback = context.getCallback();
      for(NodeEntry node : nodes) {
        callback.callBack(configuration, node, context.getTarget());
      }
    }
    chain.doStrategy(configuration, context);
  }

}
