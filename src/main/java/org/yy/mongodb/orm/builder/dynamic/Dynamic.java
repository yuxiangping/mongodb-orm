package org.yy.mongodb.orm.builder.dynamic;

import org.yy.mongodb.exception.MongoORMException;
import org.yy.mongodb.orm.builder.dynamic.Function.FunctionData;

/**
 * Dynamic script query language.
 * @author yy
 */
public class Dynamic {

  private Function function;
  private FunctionData data;

  public void addFunction(Function function, FunctionData data) {
    this.function = function;
    this.data = data;
  }

  public Object parser(Object target) throws MongoORMException {
    return function.parser(data, target);
  }

  public Function getFunction() {
    return function;
  }

  public FunctionData getData() {
    return data;
  }

}
