package com.mongodb.orm.executor;

import java.util.HashMap;
import java.util.Map;

import com.mongodb.orm.engine.entry.Entry;
import com.mongodb.orm.engine.entry.NodeEntry;
import com.mongodb.orm.executor.parser.ActionParser;
import com.mongodb.orm.executor.parser.FieldParser;
import com.mongodb.orm.executor.parser.OrderParser;
import com.mongodb.orm.executor.parser.QueryParser;
import com.mongodb.orm.executor.parser.ResultParser;
import com.mongodb.orm.executor.parser.ScriptParser;

/**
 * Parser engine for mql.
 * 
 * @author: xiangping_yu
 * @param <T>
 * @data : 2014-9-18
 * @since : 1.5
 */
@SuppressWarnings("unchecked")
public class ParserEngine {

  private ParserEngine() {}

  @SuppressWarnings("serial")
  private static final Map<Executor, MqlExecutor<?>> executor = new HashMap<Executor, MqlExecutor<?>>() {
    {
      put(Executor.query, Executor.query.instance());
      put(Executor.field, Executor.field.instance());
      put(Executor.order, Executor.order.instance());
      put(Executor.result, Executor.result.instance());
      put(Executor.action, Executor.action.instance());
      put(Executor.script, Executor.script.instance());
    }
  };

  public static Map<String, Object> toQuery(NodeEntry entry, Object target) {
    return (Map<String, Object>)executor.get(Executor.query).parser(entry, target);
  }
  
  public static Map<String, Object> toField(NodeEntry entry, Object target) {
    return (Map<String, Object>)executor.get(Executor.field).parser(entry, target);
  }
  
  public static Map<String, Object> toOrder(NodeEntry entry, Object target) {
    return (Map<String, Object>)executor.get(Executor.order).parser(entry, target);
  }
  
  public static Object toResult(NodeEntry entry, Object target) {
    return (Object)executor.get(Executor.result).parser(entry, target);
  }
  
  public static Map<String, Object> toAction(NodeEntry entry, Object target) {
    return (Map<String, Object>)executor.get(Executor.action).parser(entry, target);
  }
  
  public static String toScript(String text, Object target) {
    // TODO
    return null;
  }
  
  public static void setSelectKey(Entry entry, String key, Object target) {
    // TODO 
  }

  private enum Executor {
    query {
      @Override
      MqlExecutor<Map<String, Object>> instance() {
        return new QueryParser();
      }
    },
    field {
      @Override
      MqlExecutor<Map<String, Object>> instance() {
        return new FieldParser();
      }
    },
    order {
      @Override
      <T> MqlExecutor<T> instance() {
        return new OrderParser<T>();
      }
    },
    result {
      @Override
      <T> MqlExecutor<T> instance() {
        return new ResultParser<T>();
      }
    },
    action {
      @Override
      <T> MqlExecutor<T> instance() {
        return new ActionParser<T>();
      }
    },
    script {
      @Override
      <T> MqlExecutor<T> instance() {
        return new ScriptParser<T>();
      }
    };
    abstract <T> MqlExecutor<T> instance();
  }

}
