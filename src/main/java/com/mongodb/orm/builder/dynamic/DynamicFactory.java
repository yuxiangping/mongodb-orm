package com.mongodb.orm.builder.dynamic;

import org.w3c.dom.Node;

/**
 * Factory for build dynamic function.
 * 
 * @author: xiangping_yu
 * @data : 2014-9-12
 * @since : 1.5
 */
public class DynamicFactory {

  private DynamicFactory() {}

  public static Dynamic builder(Node node) {
    String name = node.getNodeName();
    return Creator.valueOf(name).create(node);
  }

  private static enum Creator {
    list {
      @Override
      Dynamic create(Node node) {
        Dynamic dynamic = new Dynamic();
        ListFunction function = new ListFunction();
        function.init(node, dynamic);
        return dynamic;
      }
    },
    text {
      @Override
      Dynamic create(Node node) {
        Dynamic dynamic = new Dynamic();
        TextFunction function = new TextFunction();
        function.init(node, dynamic);
        return dynamic;
      }
    },
    script {
      @Override
      Dynamic create(Node node) {
        Dynamic dynamic = new Dynamic();
        ScriptFunction function = new ScriptFunction();
        function.init(node, dynamic);
        return dynamic;
      }
    };
    abstract Dynamic create(Node node);
  }

}
