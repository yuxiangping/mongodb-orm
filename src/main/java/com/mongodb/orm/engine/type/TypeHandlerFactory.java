package com.mongodb.orm.engine.type;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.mongodb.exception.StatementException;
import com.mongodb.orm.engine.entry.ColumnType;

/**
 * Not much of a suprise, this is a factory class for TypeHandler objects.
 * 
 * @author: xiangping_yu
 * @data : 2014-7-28
 * @since : 1.5
 */
public class TypeHandlerFactory {

  private TypeHandlerFactory() {}

  /**
   * Type handler for database columns.
   */
  private static Map<Class<?>, Factory> typeAliases = new HashMap<Class<?>, Factory>();

  private static Map<ColumnType, ColumnHandler<?>> typeHandlers = new HashMap<ColumnType, ColumnHandler<?>>();

  private static Factory defaultHandlerFactory = new Factory() {
    @Override
    public TypeHandler<?> create(Class<?> clazz) {
      return new CustomHandler(clazz);
    }
  };

  static {
    Factory factory;

    factory = new Factory() {
      @Override
      public TypeHandler<Boolean> create(Class<?> clazz) {
        return new BooleanTypeHandler();
      }
    };
    register(Boolean.class, factory);
    register(boolean.class, factory);

    factory = new Factory() {
      @Override
      public TypeHandler<byte[]> create(Class<?> clazz) {
        return new ByteTypeHandler();
      }
    };
    register(Byte.class, factory);
    register(byte.class, factory);

    factory = new Factory() {
      @Override
      public TypeHandler<Short> create(Class<?> clazz) {
        return new ShortTypeHandler();
      }
    };
    register(Short.class, factory);
    register(short.class, factory);

    factory = new Factory() {
      @Override
      public TypeHandler<Integer> create(Class<?> clazz) {
        return new IntegerTypeHandler();
      }
    };
    register(Integer.class, factory);
    register(int.class, factory);

    factory = new Factory() {
      @Override
      public TypeHandler<Long> create(Class<?> clazz) {
        return new LongTypeHandler();
      }
    };
    register(Long.class, factory);
    register(long.class, factory);

    factory = new Factory() {
      @Override
      public TypeHandler<Float> create(Class<?> clazz) {
        return new FloatTypeHandler();
      }
    };
    register(Float.class, factory);
    register(float.class, factory);

    factory = new Factory() {
      @Override
      public TypeHandler<Double> create(Class<?> clazz) {
        return new DoubleTypeHandler();
      }
    };
    register(Double.class, factory);
    register(double.class, factory);

    register(String.class, factory = new Factory() {
      @Override
      public TypeHandler<String> create(Class<?> clazz) {
        return new StringTypeHandler();
      }
    });

    register(BigDecimal.class, factory = new Factory() {
      @Override
      public TypeHandler<BigDecimal> create(Class<?> clazz) {
        return new BigDecimalTypeHandler();
      }
    });

    register(byte[].class, factory = new Factory() {
      @Override
      public TypeHandler<byte[]> create(Class<?> clazz) {
        return new ByteArrayTypeHandler();
      }
    });

    register(Object.class, factory = new Factory() {
      @Override
      public TypeHandler<Object> create(Class<?> clazz) {
        return new ObjectTypeHandler();
      }
    });

    register(Date.class, factory = new Factory() {
      @Override
      public TypeHandler<Date> create(Class<?> clazz) {
        return new DateTypeHandler();
      }
    });

    register(Collection.class, factory = new Factory() {
      @Override
      public TypeHandler<Collection<?>> create(Class<?> clazz) {
        return new CollectionTypeHandler(clazz);
      }
    });

    register(Map.class, factory = new Factory() {
      @Override
      public TypeHandler<Map<Object, Object>> create(Class<?> clazz) {
        return new MapTypeHandler();
      }
    });

    register(Iterator.class, factory = new Factory() {
      @Override
      public TypeHandler<Iterator<?>> create(Class<?> clazz) {
        return new IteratorTypeHandler();
      }
    });

    register(Enum.class, factory = new Factory() {
      @Override
      public TypeHandler<Enum<?>> create(Class<?> clazz) {
        return new EnumTypeHandler(clazz);
      }
    });

    putTypeAlias(ColumnType.string, new StringTypeHandler());
    putTypeAlias(ColumnType._number, new LongTypeHandler());
    putTypeAlias(ColumnType._double, new DoubleTypeHandler());
    putTypeAlias(ColumnType._boolean, new BooleanTypeHandler());
    putTypeAlias(ColumnType.regex, new RegexTypeHandler());
    putTypeAlias(ColumnType.objectid, new ObjectIdTypeHandler());

    // Temporarily does not support
    putTypeAlias(ColumnType._byte, new ByteTypeHandler());
    putTypeAlias(ColumnType.date, new DateTypeHandler());
    
  }

  public static ColumnHandler<?> getColumnHandler(ColumnType type) {
    if (type == null) {
      return null;
    }

    ColumnHandler<?> handler = typeHandlers.get(type);
    if (handler == null) {
      throw new StatementException("Error type const for get hander '" + type + "'.");
    }
    return handler;
  }

  public static TypeHandler<?> getTypeHandler(Class<?> clazz) {
    if (clazz == null) {
      return null;
    }

    Factory factory;
    if (clazz.isEnum() || Enum.class.isAssignableFrom(clazz)) {
      factory = typeAliases.get(Enum.class);
    } else {
      factory = findFactory(clazz);
    }
    
    if (factory == null) {
      factory = defaultHandlerFactory;
    }
    return factory.create(clazz);
  }

  public static boolean has(Class<?> clazz) {
    if (clazz == null) {
      return false;
    }
    return typeAliases.containsKey(clazz);
  }

  public static boolean hasColumnType(ColumnType type) {
    if (type == null) {
      return false;
    }
    return typeHandlers.containsKey(type);
  }

  /**
   * Regist type handler.
   */
  private static void register(Class<?> clazz, Factory factory) {
    typeAliases.put(clazz, factory);
  }

  /**
   * Resolve type alias type handler.
   */
  private static void putTypeAlias(ColumnType columnType, ColumnHandler<?> handler) {
    typeHandlers.put(columnType, handler);
  }
  
  private static Factory findFactory(Class<?> clazz) {
    Factory factory = typeAliases.get(clazz);
    if(factory != null) {
      return factory;
    }
    
    for(Map.Entry<Class<?>, Factory> entry: typeAliases.entrySet()) {
      if(entry.getKey().isAssignableFrom(clazz)) {
        return entry.getValue();
      }
    }
    return null;
  }

  private interface Factory {
    public TypeHandler<?> create(Class<?> clazz);
  }

}
