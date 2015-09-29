package com.mongodb.orm.engine.type;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import com.mongodb.exception.StatementException;

/**
 * ByteArray implementation of TypeHandler
 * 
 * @author: xiangping_yu
 * @data : 2014-7-25
 * @since : 1.5
 */
public class ByteArrayTypeHandler implements TypeHandler<byte[]> {

  @Override
  public byte[] getParameter(String name, byte[] instance) {
    return instance;
  }

  @Override
  public byte[] getResult(String name, Object instance, Object value) {
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    ObjectOutput out = null;
    try {
      out = new ObjectOutputStream(bos);
      out.writeObject(value);
      byte[] bytes = bos.toByteArray();
      return bytes;
    } catch (IOException ex) {
      throw new StatementException("Parse result object to byte array error.", ex);
    } finally {
      try {
        if (out != null) {
          out.close();
        }
      } catch (IOException ex) {
        // ignore close exception
      }
      try {
        bos.close();
      } catch (IOException ex) {
        // ignore close exception
      }
    }
  }

}
