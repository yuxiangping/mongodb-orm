package com.mongodb.client.event;

/**
 * Result handler for query callback.
 * 
 * @author: xiangping_yu
 * @data : 2014-6-27
 * @since : 1.5
 */
public interface ResultHandler {

  /**
   * Handle query result.
   * @param context Query result context.
   */
  void handleResult(ResultContext context);
}
