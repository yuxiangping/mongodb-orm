package com.mongodb.orm.builder.dynamic;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Node;

import com.mongodb.exception.StatementException;
import com.mongodb.util.JsonUtils;

/**
 * Dynamic text function.
 * 
 * @author: xiangping_yu
 * @data : 2013-6-8
 * @since : 1.5
 */
public class TextFunction implements Function {

  @Override
  public void init(Node node, Class<?> clazz, Dynamic dynamic) {
    String text = node.getTextContent();
    if (StringUtils.isBlank(text)) {
      throw new StatementException("Error node configuration. The 'text' can't be null.");
    }

    Map<Object, Object> data = JsonUtils.toMap(text);
    dynamic.addFunction(this, new TextData(text, data));
  }

  @Override
  public Object parser(FunctionData data, Object target) {
    TextData textData = (TextData) data;
    return textData.data;
  }

  class TextData implements FunctionData {

    private String text;
    private Map<Object, Object> data;

    public TextData(String text, Map<Object, Object> data) {
      this.text = text;
      this.data = data;
    }

    public String getText() {
      return text;
    }

    public Map<Object, Object> getData() {
      return data;
    }

  }

}
