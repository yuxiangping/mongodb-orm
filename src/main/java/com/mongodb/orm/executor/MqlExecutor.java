package com.mongodb.orm.executor;

import com.mongodb.exception.MongoORMException;
import com.mongodb.orm.engine.entry.NodeEntry;

/**
 * Sql Executor 
 * @author: xiangping_yu
 * @data : 2014-3-7
 * @since : 1.5
 */
public interface MqlExecutor<T> {
    
    public T parser(NodeEntry entry, Object target) throws MongoORMException;
    
    /**
     * SqlExecutor callback 
     * @author: xiangping_yu
     * @data : 2014-3-7
     * @since : 1.5
     */
    public static interface CallBack<T> {
        T callBack(NodeEntry entry, Object target) throws MongoORMException;
    }
    
}