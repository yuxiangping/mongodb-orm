package org.yy.mongodb.orm.executor.strategy;

import org.yy.mongodb.orm.MqlMapConfiguration;
import org.yy.mongodb.orm.builder.dynamic.Dynamic;
import org.yy.mongodb.orm.engine.entry.Entry;

/**
 * Dynamic strategy  
 * <code>
 *     <dynimic>
 *          ...
 *     </dynimic>
 * </code> 
 * @author: xiangping_yu
 * @data : 2015-9-16
 * @since : 1.5
 */
public class DynamicStrategy implements Strategy {

  @Override
  public void doStrategy(MqlMapConfiguration configuration, StrategyContext context, StrategyChain chain) {
      Entry entry = context.getEntry();
      Dynamic dynamic = entry.getDynamic();
      if(dynamic != null) {
        Object value = dynamic.parser(context.getTarget());
        context.setValue(value);
      }
      chain.doStrategy(configuration, context);
  }

}
