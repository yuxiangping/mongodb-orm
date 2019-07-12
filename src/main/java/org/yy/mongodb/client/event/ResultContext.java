package org.yy.mongodb.client.event;

/**
 * Result context for query callback.
 * @author yy
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
