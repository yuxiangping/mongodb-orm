package org.yy.mongodb.client.event;

/**
 * Result handler for query callback.
 * @author yy
 */
public interface ResultHandler {

  /**
   * Handle query result.
   * @param context Query result context.
   */
  void handleResult(ResultContext context);
}
