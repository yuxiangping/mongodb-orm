package org.yy.mongodb.orm.engine.type;

import org.bson.types.ObjectId;

/**
 * ObjectId implementation of TypeHandler
 * @author yy
 */
public class ObjectIdTypeHandler implements ColumnHandler<ObjectId> {

  @Override
  public ObjectId resovleColumn(Object value) {
    return new ObjectId(value.toString());
  }

  @Override
  public Object resovleValue(ObjectId target) {
    return target.toString();
  }

}
