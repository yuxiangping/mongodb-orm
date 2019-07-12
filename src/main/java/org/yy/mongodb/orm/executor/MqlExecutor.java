package org.yy.mongodb.orm.executor;

import org.yy.mongodb.exception.MongoORMException;
import org.yy.mongodb.orm.MqlMapConfiguration;
import org.yy.mongodb.orm.engine.entry.NodeEntry;

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