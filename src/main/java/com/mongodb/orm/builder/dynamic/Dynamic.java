package com.mongodb.orm.builder.dynamic;

import com.mongodb.exception.MongoORMException;
import com.mongodb.orm.builder.dynamic.Function.FunctionData;

/**
 * Dynamic script query language.
 * 
 * @author: xiangping_yu
 * @data : 2014-9-12
 * @since : 1.5
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
