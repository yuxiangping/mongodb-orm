package com.mongodb.client.event;

/**
 * Result context for query callback.
 * 
 * @author: xiangping_yu
 * @data : 2014-6-27
 * @since : 1.5
 */
public interface ResultContext {
  
  /**
   * Return the result object.
   * @return
   */
  Object getResultObject();

  /**
   * Return the result count.
   * @return
   */
  int getResultCount();
}
