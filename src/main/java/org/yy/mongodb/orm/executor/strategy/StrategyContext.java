package org.yy.mongodb.orm.executor.strategy;

import org.yy.mongodb.orm.engine.entry.Entry;
import org.yy.mongodb.orm.engine.type.TypeHandler;
import org.yy.mongodb.orm.executor.MqlExecutor.CallBack;

/**
 * Strategy context 
 * @author: xiangping_yu
 * @data : 2015-9-16
 * @since : 1.5
 */
public class StrategyContext {

  private Entry entry; 
  private Object target;
  private Object value;
  private CallBack<?> callback;
  private TypeHandler<?> typeHandler;
  
  public StrategyContext(Entry entry, Object target) {
    this.entry = entry;
    this.target = target;
    this.value = target;
  }

  public StrategyContext(Entry entry, Object target, CallBack<?> callback) {
    this.entry = entry;
    this.target = target;
    this.value = target;
    this.callback = callback;
  }

  public void setValue(Object value) {
    this.value = value;
  }
  
  public Entry getEntry() {
    return entry;
  }

  public Object getTarget() {
    return target;
  }

  public Object getValue() {
    return value;
  }

  public CallBack<?> getCallback() {
    return callback;
  }

  public TypeHandler<?> getTypeHandler() {
    return typeHandler;
  }

  public void setTypeHandler(TypeHandler<?> typeHandler) {
    this.typeHandler = typeHandler;
  }
  
}
