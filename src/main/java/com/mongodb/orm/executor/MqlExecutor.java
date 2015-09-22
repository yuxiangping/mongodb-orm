package com.mongodb.orm.executor;

import com.mongodb.exception.MongoORMException;
import com.mongodb.orm.MqlMapConfiguration;
import com.mongodb.orm.engine.entry.NodeEntry;

/**
 * Mql executor 
 * @author: xiangping_yu
 * @data : 2014-3-7
 * @since : 1.5
 */
public interface MqlExecutor<T> {
    
    public T parser(MqlMapConfiguration configuration, NodeEntry entry, Object target) throws MongoORMException;
    
    /**
     * SqlExecutor callback 
     * @author: xiangping_yu
     * @data : 2014-3-7
     * @since : 1.5
     */
    public static interface CallBack<T> {
        T callBack(MqlMapConfiguration configuration, NodeEntry entry, Object target) throws MongoORMException;
    }
    
}