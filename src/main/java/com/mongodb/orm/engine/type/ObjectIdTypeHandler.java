package com.mongodb.orm.engine.type;

import org.bson.types.ObjectId;

/**
 * ObjectId implementation of TypeHandler
 * @author: xiangping_yu
 * @data : 2014-7-25
 * @since : 1.5
 */
public class ObjectIdTypeHandler implements ColumnHandler<ObjectId> {

  @Override
  public ObjectId resovleColumn(Object value) {
    return new ObjectId(value.toString());
  }

}
