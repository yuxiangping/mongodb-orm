package org.yy.mongodb.orm.executor;

import org.yy.mongodb.exception.MongoORMException;
import org.yy.mongodb.orm.MqlMapConfiguration;
import org.yy.mongodb.orm.engine.entry.NodeEntry;

/**
 * Mql executor 
 * @author yy
 */
public interface MqlExecutor<T> {
    
    public T parser(MqlMapConfiguration configuration, NodeEntry entry, Object target) throws MongoORMException;
    
    /**
     * SqlExecutor callback 
     * @author yy
     */
    public static interface CallBack<T> {
        T callBack(MqlMapConfiguration configuration, NodeEntry entry, Object target) throws MongoORMException;
    }
    
}