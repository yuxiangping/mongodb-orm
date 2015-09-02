package com.mongodb.orm.executor;

import com.mongodb.orm.engine.entry.Entry;
import com.mongodb.orm.engine.entry.NodeEntry;
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
  
  public <T> T toResult(NodeEntry entry, Object object) {
    return (T) resultParser.parser(entry, object);
  }
  
  public String toScript(String text, Object target) {
    // TODO
    return null;
  }
  
  public void setSelectKey(Entry entry, String key, Object target) {
    // TODO 
  }

}
