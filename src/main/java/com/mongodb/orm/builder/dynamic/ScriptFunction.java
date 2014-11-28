package com.mongodb.orm.builder.dynamic;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Node;

import com.mongodb.exception.StatementException;

/**
 * Dynamic script function.
 * 
 * @author: xiangping_yu
 * @data : 2013-6-8
 * @since : 1.5
 */
public class ScriptFunction implements Function {

  @Override
  public void init(Node node, Dynamic dynamic) {
    String text = node.getTextContent();
    if (StringUtils.isBlank(text)) {
      throw new StatementException("Error node configuration. The 'script' can't be null.");
    }

    dynamic.addFunction(this, new ScriptData(text));
  }

  @Override
  public Object parser(FunctionData data, Object target) {
    ScriptData scriptData = (ScriptData) data;
    return scriptData.text;
  }

  class ScriptData implements FunctionData {

    private String text;

    public ScriptData(String text) {
      this.text = text;
    }

    public String getText() {
      return text;
    }

  }

}
