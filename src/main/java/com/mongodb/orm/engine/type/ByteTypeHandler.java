package com.mongodb.orm.engine.type;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import com.mongodb.exception.MongoORMException;

/**
 * Byte implementation of TypeHandler
 * 
 * @author: xiangping_yu
 * @data : 2014-7-25
 * @since : 1.5
 */
public class ByteTypeHandler implements TypeHandler<byte[]>, ColumnHandler<byte[]> {

  @Override
  public Object getParameter(byte[] instance) {
    return instance;
  }

  @Override
  public byte[] getResult(Object instance, Object value) {
    if (value instanceof byte[]) {
      return (byte[]) value;
    }
    return null;
  }

  @Override
  public byte[] resovleColumn(Object value) {
    if (value instanceof byte[]) {
      return (byte[]) value;
    }

    ByteArrayOutputStream baos = null;
    ObjectOutputStream oos = null;

    try {
      baos = new ByteArrayOutputStream();
      oos = new ObjectOutputStream(baos);

      oos.writeObject(value);

      byte[] bytes = baos.toByteArray();

      return bytes;
    } catch (Exception ex) {
      logger.error("Resovle column value type has exception. Cause:", ex);
      throw new MongoORMException("Resovle column value type has exception. Cause:", ex);
    } finally {
      try {
        baos.close();
        oos.close();
      } catch (IOException ioex) {
        logger.error("Resovle column value type close io has exception. Cause:", ioex);
      }
    }
  }

}
