package com.mongodb.client;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.client.event.ResultHandler;

/**
 * The primary Java interface for working with NoSqlOrm. 
 * Through this interface you can execute commands, get mappers and manage transactions.
 * 
 * @info : Templet for MongoDB
 * @author: xiangping_yu
 * @data : 2014-6-22
 * @since : 1.5
 */
public interface MongoTemplet {

  Logger logger = LoggerFactory.getLogger(MongoTemplet.class);
  
  /**
   * Retrieve a single row mapped from the statement key.
   * @param <T> the returned object type.
   * @param statement Unique identifier matching the statement to use.
   * @return Mapped object.
   */
  <T> T findOne(String statement);
  
  /**
   * Retrieve a single row mapped from the statement key and parameter.
   * @param <T> the returned object type.
   * @param statement Unique identifier matching the statement to use.
   * @param parameter A parameter object to pass to the statement.
   * @return Mapped object.
   */
  <T> T findOne(String statement, Object parameter);
  
  /**
   * Retrieve a single row mapped from the statement key.
   * @param statement Unique identifier matching the statement to use.
   * @param handler ResultHandler that will handle each retrieved row  
   */
  void findOne(String statement, ResultHandler handler);
  
  /**
   * Retrieve a single row mapped from the statement key and parameter.
   * @param statement Unique identifier matching the statement to use.
   * @param parameter A parameter object to pass to the statement.
   * @param handler ResultHandler that will handle each retrieved row. 
   */
  void findOne(String statement, Object parameter, ResultHandler handler);
  
  /**
   * Retrieve a list of mapped objects from the statement key.
   * @param <T> the returned list element type.
   * @param statement Unique identifier matching the statement to use.
   * @return List of mapped object.
   */
  <T> List<T> find(String statement);
  
  /**
   * Retrieve a list of mapped objects from the statement key and parameter.
   * @param <T> the returned list element type.
   * @param statement Unique identifier matching the statement to use.
   * @param parameter A parameter object to pass to the statement.
   * @return List of mapped object.
   */
  <T> List<T> find(String statement, Object parameter);

  /**
   * Retrieve a list of mapped objects from the statement key and parameter.
   * @param <T> the returned list element type.
   * @param statement Unique identifier matching the statement to use.
   * @param limit Limit mapped objects.
   * @param skip Retrieve a list of mapped objects from skip index.
   * @return List of mapped object.
   */
  <T> List<T> find(String statement, Object parameter, Integer limit, Integer skip);

  /**
   * Retrieve a list of mapped objects from the statement key.
   * @param statement Unique identifier matching the statement to use.
   * @param handler ResultHandler that will handle each retrieved row. 
   */
  void find(String statement, ResultHandler handler);
  
  /**
   * Retrieve a list of mapped objects from the statement key and parameter.
   * @param statement Unique identifier matching the statement to use.
   * @param parameter A parameter object to pass to the statement.
   * @param handler ResultHandler that will handle each retrieved row. 
   */
  void find(String statement, Object parameter, ResultHandler handler);

  /**
   * Retrieve a list of mapped objects from the statement key and parameter.
   * @param statement Unique identifier matching the statement to use.
   * @param limit Limit mapped objects.
   * @param skip Retrieve a list of mapped objects from skip index.
   * @param handler ResultHandler that will handle each retrieved row. 
   */
  void find(String statement, Object parameter, Integer limit, Integer skip, ResultHandler handler);
  
  /**
   * Count a list of mapped objects from the statement key.
   * @param statement Unique identifier matching the statement to use.
   * @return Count of mapped object.
   */
  long count(String statement);
  
  /**
   * Count a list of mapped objects from the statement key and parameter.
   * @param statement Unique identifier matching the statement to use.
   * @param parameter A parameter object to pass to the statement.
   * @return Count of mapped object.
   */
  long count(String statement, Object parameter);

  /**
   * Retrieve a distinct list of mapped objects from the statement key.
   * @param <T> the returned list element type. 
   * @param statement Unique identifier matching the statement to use.
   * @param key The distinct key.
   * @return List of mapped object.
   */
  <T> List<T> distinct(String statement, String key);
  
  /**
   * Retrieve a distinct list of mapped objects from the statement key and parameter.
   * @param <T> the returned list element type. 
   * @param statement Unique identifier matching the statement to use.
   * @param key The distinct key.
   * @param parameter A parameter object to pass to the statement.
   * @return List of mapped object.
   */
  <T> List<T> distinct(String statement, String key, Object parameter);
  
  /**
   * Retrieve a distinct list of mapped objects from the statement key.
   * @param statement Unique identifier matching the statement to use.
   * @param key The distinct key.
   * @param handler ResultHandler that will handle each retrieved row.  
   */
  void distinct(String statement, String key, ResultHandler handler);
  
  /**
   * Retrieve a distinct list of mapped objects from the statement key and parameter.
   * @param statement Unique identifier matching the statement to use.
   * @param key The distinct key.
   * @param parameter A parameter object to pass to the statement.
   * @param handler ResultHandler that will handle each retrieved row.  
   */
  void distinct(String statement, String key, Object parameter, ResultHandler handler);
  
  /**
   * Execute an insert statement.
   * @param statement Unique identifier matching the statement to execute.
   * @return String The rows id affected by the insert.
   */
  String insert(String statement);

  /**
   * Execute an insert statement with the given parameter object. 
   * @param statement Unique identifier matching the statement to execute.
   * @param parameter A parameter object to pass to the statement.
   * @return String The rows id affected by the insert.
   */
  String insert(String statement, Object parameter);
  
  /**
   * Execute an insert statement with the given list of parameter object. 
   * @param statement Unique identifier matching the statement to execute.
   * @param list A list parameter object to pass to the statement.
   * @return List<String> The rows id affected by the insert.
   */
  <T> List<String> insertBatch(String statement, List<T> list);
  
  /**
   * Find a single row mapped from the statement key and update it. If no rows returned from this 
   * statement key, then execute this insert statement.
   * @param <T> the returned object type.
   * @param statement Unique identifier matching the statement to use.
   * @return Mapped object of modified.
   */
  <T> T findAndModify(String statement);
  
  /**
   * Find a single row mapped from the statement key and update it. If no rows returned from this 
   * statement key, then execute this insert statement.
   * @param <T> the returned object type.
   * @param statement Unique identifier matching the statement to use.
   * @param parameter A parameter object to pass to the statement.
   * @return Mapped object of modified.
   */
  <T> T findAndModify(String statement, Object parameter);
  
  /**
   * Find a single row mapped from the statement key and update it. If no rows returned from this 
   * statement key, then execute this insert statement.
   * @param statement Unique identifier matching the statement to use.
   * @param handler ResultHandler that will handle each retrieved row. 
   */
  void findAndModify(String statement, ResultHandler handler);

  /**
   * Find a single row mapped from the statement key and update it. If no rows returned from this 
   * statement key, then execute this insert statement.
   * @param statement Unique identifier matching the statement to use.
   * @param parameter A parameter object to pass to the statement.
   * @param handler ResultHandler that will handle each retrieved row. 
   */
  void findAndModify(String statement, Object parameter, ResultHandler handler);

  /**
   * Execute an update statement. The number of rows affected will be returned.
   * @param statement Unique identifier matching the statement to execute.
   * @return int The number of rows affected by the update.
   */
  int update(String statement);
  
  /**
   * Execute an update statement. The number of rows affected will be returned.
   * @param statement Unique identifier matching the statement to execute.
   * @param parameter A parameter object to pass to the statement.
   * @return int The number of rows affected by the update.
   */
  int update(String statement, Object parameter);

  /**
   * Execute a delete statement. The number of rows affected will be returned.
   * @param statement Unique identifier matching the statement to execute.
   * @return int The number of rows affected by the delete.
   */
  int delete(String statement);

  /**
   * Execute a delete statement. The number of rows affected will be returned.
   * @param statement Unique identifier matching the statement to execute.
   * @param parameter A parameter object to pass to the statement.
   * @return int The number of rows affected by the delete.
   */
  int delete(String statement, Object parameter);

  /**
   * Run a command statement from the statement key.
   * @param <T> the returned object type. 
   * @param statement Unique identifier matching the statement to execute.
   * @return Mapped objects affected by the command.
   */
  <T> T command(String statement);
  
  /**
   * Run a command statement from the statement key and parameter.
   * @param <T> the returned object type. 
   * @param statement Unique identifier matching the statement to execute.
   * @param parameter A parameter object to pass to the statement.
   * @return Mapped objects affected by the command.
   */
  <T> T command(String statement, Object parameter);
  
  /**
   * Run a command statement from the statement key.
   * @param statement Unique identifier matching the statement to execute.
   * @param handler ResultHandler that will handle each retrieved row. 
   */
  void command(String statement, ResultHandler handler);
  
  /**
   * Run a command statement from the statement key and parameter.
   * @param statement Unique identifier matching the statement to execute.
   * @param parameter A parameter object to pass to the statement.
   * @param handler ResultHandler that will handle each retrieved row. 
   */
  void command(String statement, Object parameter, ResultHandler handler);
  
  /**
   * Execute a group statement.
   * @param <T> the returned object type.  
   * @param statement Unique identifier matching the statement to execute.
   * @return Mapped objects affected by the command.
   */
  <T> T group(String statement);
  
  /**
   * Execute a group statement from the statement key and parameter.
   * @param <T> the returned object type.   
   * @param statement Unique identifier matching the statement to execute.
   * @param parameter A parameter object to pass to the statement.
   * @return Mapped objects affected by the command.
   */
  <T> T group(String statement, Object parameter);
  
  /**
   * Execute a group statement.
   * @param statement Unique identifier matching the statement to execute.
   * @param handler ResultHandler that will handle each retrieved row.
   */
  void group(String statement, ResultHandler handler);
  
  /**
   * Execute a group statement from the statement key and parameter.
   * @param statement Unique identifier matching the statement to execute.
   * @param parameter A parameter object to pass to the statement.
   * @param handler ResultHandler that will handle each retrieved row.
   */
  void group(String statement, Object parameter, ResultHandler handler);
  
  /**
   * Execute a aggregate statement.
   * @param <T> the returned object type.  
   * @param statement Unique identifier matching the statement to execute.
   * @return Mapped objects affected by the command.
   */
  <T> List<T> aggregate(String statement);
  
  /**
   * Execute a aggregate statement from the statement key and parameter.
   * @param <T> the returned object type.   
   * @param statement Unique identifier matching the statement to execute.
   * @param parameter A parameter object to pass to the statement.
   * @return Mapped objects affected by the command.
   */
  <T> List<T> aggregate(String statement, Object[] parameter);
  
  /**
   * Execute a aggregate statement.
   * @param statement Unique identifier matching the statement to execute.
   * @param handler ResultHandler that will handle each retrieved row.
   */
  void aggregate(String statement, ResultHandler handler);
  
  /**
   * Execute a aggregate statement from the statement key and parameter.
   * @param statement Unique identifier matching the statement to execute.
   * @param parameter A parameter object to pass to the statement.
   * @param handler ResultHandler that will handle each retrieved row.
   */
  void aggregate(String statement, Object[] parameter, ResultHandler handler);
  
  /**
   * Retrieves mongo database.
   * @return Database.
   */
  DB getDB();
  
  /**
   * Retrieves mongo database.
   * @param dbName Retrieves database name.
   * @return Database.
   */
  DB getDB(String dbName);
  
  /**
   * Retrieves T affected by the mappingId from source.
   * @param mappingId XML mapping id.
   * @param source Mongo source
   * @return Mapped objects affected by the mappingId.
   */
  <T> T parseObject(String mappingId, DBObject source);
  
}
