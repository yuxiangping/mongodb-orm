package org.yy.mongodb.orm.builder.dynamic;

import org.w3c.dom.Node;

/**
 * Factory for build dynamic function.
 * @author yy
 */
public class DynamicFactory {

  private DynamicFactory() {}

  public static Dynamic builder(Node node, Class<?> clazz) {
    String name = node.getNodeName();
    return Creator.valueOf(name).create(node, clazz);
  }

  private static enum Creator {
    list {
      @Override
      Dynamic create(Node node, Class<?> clazz) {
        Dynamic dynamic = new Dynamic();
        new ListFunction().init(node, clazz, dynamic);
        return dynamic;
      }
    },
    text {
      @Override
      Dynamic create(Node node, Class<?> clazz) {
        Dynamic dynamic = new Dynamic();
        new TextFunction().init(node, clazz, dynamic);
        return dynamic;
      }
    },
    script {
      @Override
      Dynamic create(Node node, Class<?> clazz) {
        Dynamic dynamic = new Dynamic();
        new ScriptFunction().init(node, clazz, dynamic);
        return dynamic;
      }
    };
    abstract Dynamic create(Node node, Class<?> clazz);
  }

}
