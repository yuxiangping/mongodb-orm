package org.yy.mongodb.orm.builder.dynamic;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Node;
import org.yy.mongodb.constant.ORM;
import org.yy.mongodb.exception.StatementException;
import org.yy.mongodb.orm.engine.entry.ColumnType;
import org.yy.mongodb.orm.engine.type.ColumnHandler;
import org.yy.mongodb.orm.engine.type.TypeHandlerFactory;
import org.yy.mongodb.util.NodeletUtils;

/**
 * Dynamic list function.
 * @author yy
 */
public class ListFunction implements Function {

  @Override
  public void init(Node node, Class<?> clazz, Dynamic dynamic) {
    Properties attribute = NodeletUtils.parseAttributes(node);

    String text = node.getTextContent();
    String type = attribute.getProperty(ORM.TAG_TYPE);

    if (StringUtils.isBlank(text)) {
      throw new StatementException("Error node configuration. The 'list' can't be null.");
    }

    ColumnType fromType = ColumnType.fromType(type);
    if (!TypeHandlerFactory.hasColumnType(fromType)) {
      throw new StatementException("Error node configuration. The 'list' function has not support this type '" + type + "'.");
    }

    ColumnHandler<?> columnHandler = TypeHandlerFactory.getColumnHandler(fromType);

    String[] values = text.split(",");
    List<Object> list = new ArrayList<Object>(values.length);
    for (String value : values) {
      list.add(columnHandler.resovleColumn(value));
    }

    dynamic.addFunction(this, new ListData(text, columnHandler, list));
  }

  @Override
  public Object parser(FunctionData data, Object target) {
    ListData listData = (ListData) data;
    return listData.data;
  }

  class ListData implements FunctionData {

    private String text;
    private ColumnHandler<?> handler;
    private List<Object> data;

    public ListData(String text, ColumnHandler<?> handler, List<Object> data) {
      this.text = text;
      this.handler = handler;
      this.data = data;
    }

    public String getText() {
      return text;
    }

    public ColumnHandler<?> getHandler() {
      return handler;
    }

    public List<Object> getData() {
      return data;
    }

  }

}
