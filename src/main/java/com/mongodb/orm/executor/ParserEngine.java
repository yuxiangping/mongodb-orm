package com.mongodb.orm.executor;

import com.mongodb.orm.MqlMapConfiguration;
import com.mongodb.orm.engine.entry.Entry;
import com.mongodb.orm.engine.entry.NodeEntry;
import com.mongodb.orm.engine.entry.Script;
import com.mongodb.orm.executor.parser.ResultParser;

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

  private ResultParser resultParser = new ResultParser();
  
  public <T> T toResult(MqlMapConfiguration configuration, NodeEntry entry, Object object) {
    return (T) resultParser.parser(configuration, entry, object);
  }
  
  public String toScript(Script script, Object target) {
    // TODO
    return null;
  }
  
  public void setSelectKey(Entry entry, String key, Object target) {
    // TODO 
  }

}
