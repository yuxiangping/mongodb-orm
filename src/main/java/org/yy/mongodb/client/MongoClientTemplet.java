package org.yy.mongodb.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.mongodb.MongoDatabaseUtils;
import org.springframework.data.mongodb.SessionSynchronization;
import org.springframework.util.Assert;
import org.yy.mongodb.client.event.ResultContext;
import org.yy.mongodb.client.event.ResultHandler;
import org.yy.mongodb.exception.MongoDaoException;
import org.yy.mongodb.exception.MongoORMException;
import org.yy.mongodb.orm.MqlMapConfiguration;
import org.yy.mongodb.orm.engine.config.AggregateConfig;
import org.yy.mongodb.orm.engine.config.CommandConfig;
import org.yy.mongodb.orm.engine.config.DeleteConfig;
import org.yy.mongodb.orm.engine.config.GroupConfig;
import org.yy.mongodb.orm.engine.config.InsertConfig;
import org.yy.mongodb.orm.engine.config.MapReduceConfig;
import org.yy.mongodb.orm.engine.config.MappingConfig;
import org.yy.mongodb.orm.engine.config.SelectConfig;
import org.yy.mongodb.orm.engine.config.UpdateConfig;
import org.yy.mongodb.orm.engine.entry.Entry;
import org.yy.mongodb.orm.engine.entry.NodeEntry;
import org.yy.mongodb.orm.engine.entry.Script;
import org.yy.mongodb.orm.executor.parser.ResultExecutor;
import org.yy.mongodb.util.BeanUtils;
import org.yy.mongodb.util.NodeletUtils;
import org.yy.mongodb.util.ScriptUtils;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.ReadPreference;
import com.mongodb.WriteConcern;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.DistinctIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MapReduceIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

/**
 * Templet for MongoDB. The primary Java interface implement for working with NoSql ORM.
 * 
 * @author yy
 * @since 1.8   mongodb  3.4
 */
@SuppressWarnings({"unchecked", "deprecation"})
public class MongoClientTemplet implements MongoTemplet, InitializingBean {

  private MongoFactoryBean factory;
  private MqlMapConfiguration configuration;
  private ResultHelper helper;

  /**
   * Mongo session synchronization.
   */
  private SessionSynchronization sessionSynchronization = SessionSynchronization.ON_ACTUAL_TRANSACTION;

  @Override
  public void afterPropertiesSet() throws Exception {
    init();
  }

  public void init() {
    Assert.notNull(factory, "Mongo orm factory not null.");
    configuration = factory.getConfiguration();
    helper = new ResultHelper();
  }

  @Override
  public <T> T findOne(String statement) {
    return findOne(statement, null, null, ReadPreference.secondaryPreferred());
  }

  @Override
  public <T> T findOne(String statement, Object parameter) {
    return findOne(statement, parameter, null, ReadPreference.secondaryPreferred());
  }

  @Override
  public void findOne(String statement, ResultHandler handler) {
    findOne(statement, null, handler, ReadPreference.secondaryPreferred());
  }

  @Override
  public void findOne(String statement, Object parameter, ResultHandler handler) {
    findOne(statement, parameter, handler, ReadPreference.secondaryPreferred());
  }

  private <T> T findOne(String statement, Object parameter, ResultHandler handler, ReadPreference readPreference) {
    if (logger.isDebugEnabled()) {
      logger.debug("Execute 'findOne' mongodb command. Statement '" + statement + "'.");
    }

    SelectConfig config = (SelectConfig) configuration.getStatement(statement);
    if (config == null) {
      throw new MongoDaoException(statement, "Query statement id '" + statement + "' not found.");
    }

    String collection = config.getCollection();
    NodeEntry query = config.getQuery();
    NodeEntry field = config.getField();
    NodeEntry order = config.getOrder();

    MongoDatabase db = getDatabase();
    MongoCollection<Document> coll = db.getCollection(collection).withReadPreference(readPreference);

    Map<String, Object> q = (Map<String, Object>) query.executorNode(config.getNamespace(), configuration, parameter);
    Map<String, Object> f = (Map<String, Object>) field.executorNode(config.getNamespace(), configuration, parameter);
    Map<String, Object> o = (Map<String, Object>) order.executorNode(config.getNamespace(), configuration, parameter);

    Document find = new Document(q);
    Document filter = (f == null) ? null : new Document(f);
    Document sort = (o == null) ? null : new Document(o);
    if (logger.isDebugEnabled()) {
      logger.debug("Execute 'findOne' mongodb command. Query '" + find + "'.");
      logger.debug("Execute 'findOne' mongodb command. Field '" + filter + "'.");
      logger.debug("Execute 'findOne' mongodb command. Order '" + sort + "'.");
    }

    Document doc = coll.find(find).filter(filter).sort(sort).first();
    if (logger.isDebugEnabled()) {
      logger.debug("Execute 'findOne' mongodb command. Result is '" + doc + "'.");
    }

    if (handler != null) {
      handler.handleResult(new ResultContext() {
        @Override
        public Object getResultObject() {
          return doc;
        }

        @Override
        public int getResultCount() {
          if (doc == null) {
            return 0;
          }
          return 1;
        }
      });
      return null;
    }
    return (T) helper.toResult(config.getNamespace(), field, doc);
  }

  @Override
  public <T> List<T> find(String statement) {
    return find(statement, null, null, null, null, ReadPreference.secondaryPreferred());
  }

  @Override
  public <T> List<T> find(String statement, Object parameter) {
    return find(statement, parameter, null, null, null, ReadPreference.secondaryPreferred());
  }

  @Override
  public <T> List<T> find(String statement, Object parameter, Integer limit, Integer skip) {
    return find(statement, parameter, limit, skip, null, ReadPreference.secondaryPreferred());
  }

  @Override
  public void find(String statement, ResultHandler handler) {
    find(statement, null, null, null, handler, ReadPreference.secondaryPreferred());
  }

  @Override
  public void find(String statement, Object parameter, ResultHandler handler) {
    find(statement, parameter, null, null, handler, ReadPreference.secondaryPreferred());
  }

  @Override
  public void find(String statement, Object parameter, Integer limit, Integer skip, ResultHandler handler) {
    find(statement, parameter, limit, skip, handler, ReadPreference.secondaryPreferred());
  }

  private <T> List<T> find(String statement, Object parameter, Integer limit, Integer skip, ResultHandler handler, ReadPreference readPreference) {
    if (logger.isDebugEnabled()) {
      logger.debug("Execute 'find' mongodb command. Statement '" + statement + "'.");
    }

    SelectConfig config = (SelectConfig) configuration.getStatement(statement);
    if (config == null) {
      throw new MongoDaoException(statement, "Query statement id '" + statement + "' not found.");
    }

    String collection = config.getCollection();
    NodeEntry query = config.getQuery();
    NodeEntry field = config.getField();
    NodeEntry order = config.getOrder();

    MongoDatabase db = getDatabase();
    MongoCollection<Document> coll = db.getCollection(collection).withReadPreference(readPreference);

    Map<String, Object> q = (Map<String, Object>) query.executorNode(config.getNamespace(), configuration, parameter);
    Map<String, Object> f = (Map<String, Object>) field.executorNode(config.getNamespace(), configuration, parameter);
    Map<String, Object> o = (Map<String, Object>) order.executorNode(config.getNamespace(), configuration, parameter);

    Document find = new Document(q);
    Document filter = (f == null) ? null : new Document(f);
    Document sort = (o == null) ? null : new Document(o);
    if (logger.isDebugEnabled()) {
      logger.debug("Execute 'find' mongodb command. Query '" + find + "'.");
      logger.debug("Execute 'find' mongodb command. Field '" + filter + "'.");
      logger.debug("Execute 'find' mongodb command. Order '" + sort + "'.");
    }

    FindIterable<Document> iterable = coll.find(find).filter(filter).sort(sort);
    if (skip != null) {
      iterable.skip(skip);
    }
    if (limit != null) {
      iterable.limit(limit);
    }

    List<Document> list = new ArrayList<Document>();
    MongoCursor<Document> iterator = iterable.iterator();
    while (iterator.hasNext()) {
      list.add(iterator.next());
    }

    if (logger.isDebugEnabled()) {
      logger.debug("Execute 'find' mongodb command. Result set is '" + list + "'.");
    }

    if (handler != null) {
      handler.handleResult(new ResultContext() {
        @Override
        public Object getResultObject() {
          return list;
        }

        @Override
        public int getResultCount() {
          return list.size();
        }
      });
      return null;
    }

    List<T> result = new ArrayList<T>(list.size());
    list.forEach(item -> {
      result.add((T) helper.toResult(config.getNamespace(), field, item));
    });
    return result;
  }

  @Override
  public long count(String statement) {
    return count(statement, null);
  }

  @Override
  public long count(String statement, Object parameter) {
    if (logger.isDebugEnabled()) {
      logger.debug("Execute 'count' mongodb command. Statement '" + statement + "'.");
    }

    SelectConfig config = (SelectConfig) configuration.getStatement(statement);
    if (config == null) {
      throw new MongoDaoException(statement, "Count statement id '" + statement + "' not found.");
    }

    String collection = config.getCollection();
    NodeEntry query = config.getQuery();

    MongoDatabase db = getDatabase();
    MongoCollection<Document> coll = db.getCollection(collection).withReadPreference(ReadPreference.secondaryPreferred());

    Map<String, Object> q = (Map<String, Object>) query.executorNode(config.getNamespace(), configuration, parameter);

    Document count = new Document(q);
    if (logger.isDebugEnabled()) {
      logger.debug("Execute 'count' mongodb command. Query '" + count + "'.");
    }
    return coll.countDocuments(count);
  }

  @Override
  public <T> List<T> distinct(String statement, String key) {
    return distinct(statement, key, null, null, ReadPreference.secondaryPreferred());
  }

  @Override
  public <T> List<T> distinct(String statement, String key, Object parameter) {
    return distinct(statement, key, parameter, null, ReadPreference.secondaryPreferred());
  }

  @Override
  public void distinct(String statement, String key, ResultHandler handler) {
    distinct(statement, key, null, handler, ReadPreference.secondaryPreferred());
  }

  @Override
  public void distinct(String statement, String key, Object parameter, ResultHandler handler) {
    distinct(statement, key, parameter, handler, ReadPreference.secondaryPreferred());
  }

  private <T> List<T> distinct(String statement, String key, Object parameter, ResultHandler handler, ReadPreference readPreference) {
    if (logger.isDebugEnabled()) {
      logger.debug("Execute 'distinct' mongodb command. Statement '" + statement + "'.");
    }

    SelectConfig config = (SelectConfig) configuration.getStatement(statement);
    if (config == null) {
      throw new MongoDaoException(statement, "Distinct statement id '" + statement + "' not found.");
    }

    if (StringUtils.isBlank(key)) {
      throw new MongoDaoException(statement, "Execute 'distinct' mongodb command. 'key' is blank.");
    }

    String collection = config.getCollection();
    NodeEntry query = config.getQuery();
    NodeEntry field = config.getField();

    MongoDatabase db = getDatabase();
    MongoCollection<Document> coll = db.getCollection(collection);

    Map<String, Object> q = (Map<String, Object>) query.executorNode(config.getNamespace(), configuration, parameter);
    Map<String, Object> f = (Map<String, Object>) field.executorNode(config.getNamespace(), configuration, parameter);

    Document distinct = new Document(q);
    Document filter = (f == null) ? null : new Document(f);
    if (logger.isDebugEnabled()) {
      logger.debug("Execute 'distinct' mongodb command. Query '" + distinct + "'.");
      logger.debug("Execute 'distinct' mongodb command. Field '" + filter + "'.");
    }

    DistinctIterable<Document> iterable = coll.distinct(key, distinct, Document.class);

    List<Document> list = new ArrayList<Document>();
    MongoCursor<Document> iterator = iterable.iterator();
    while (iterator.hasNext()) {
      list.add(iterator.next());
    }

    if (logger.isDebugEnabled()) {
      logger.debug("Execute 'distinct' mongodb command. Result set '" + list + "'.");
    }

    if (handler != null) {
      handler.handleResult(new ResultContext() {
        @Override
        public Object getResultObject() {
          return list;
        }

        @Override
        public int getResultCount() {
          return list.size();
        }
      });
      return null;
    }

    List<T> result = new ArrayList<T>(list.size());
    list.forEach(item -> {
      result.add((T) helper.toResult(config.getNamespace(), field, item));
    });
    return result;
  }

  @Override
  public String insert(String statement) {
    return insert(statement, null);
  }

  @Override
  public String insert(String statement, Object parameter) {
    if (logger.isDebugEnabled()) {
      logger.debug("Execute 'insert' mongodb command. Statement '" + statement + "'.");
    }

    InsertConfig config = (InsertConfig) configuration.getStatement(statement);
    if (config == null) {
      throw new MongoDaoException(statement, "Insert statement id '" + statement + "' not found.");
    }

    String collection = config.getCollection();
    NodeEntry document = config.getDocument();
    Entry selectKey = config.getSelectKey();

    MongoDatabase db = getDatabase();
    MongoCollection<Document> coll = db.getCollection(collection).withWriteConcern(WriteConcern.ACKNOWLEDGED);

    Map<String, Object> doc = (Map<String, Object>) document.executorNode(config.getNamespace(), configuration, parameter);

    Document insert = new Document(doc);
    if (logger.isDebugEnabled()) {
      logger.debug("Execute 'insert' mongodb command. Doc '" + insert + "'.");
    }

    try {
      coll.insertOne(insert);
    } catch (Exception e) {
      throw new MongoDaoException(statement, "Execute 'insert' mongodb command has exception. The write was unacknowledged.", e);
    }

    String newId = insert.getObjectId("_id").toString();
    if (logger.isDebugEnabled()) {
      logger.debug("Execute 'insert' mongodb command. ObjectId is '" + newId + "'.");
    }

    if (selectKey != null) {
      helper.setSelectKey(selectKey, newId, parameter);
    }
    return newId;
  }

  @Override
  public <T> List<String> insertBatch(String statement, List<T> list) {
    if (logger.isDebugEnabled()) {
      logger.debug("Execute 'insertBatch' mongodb command. Statement '" + statement + "'.");
    }

    InsertConfig config = (InsertConfig) configuration.getStatement(statement);
    if (config == null) {
      throw new MongoDaoException(statement, "Insert statement id '" + statement + "' not found.");
    }

    String collection = config.getCollection();
    NodeEntry document = config.getDocument();
    Entry selectKey = config.getSelectKey();

    MongoDatabase db = getDatabase();
    MongoCollection<Document> coll = db.getCollection(collection).withWriteConcern(WriteConcern.ACKNOWLEDGED);

    List<Document> documents = new ArrayList<Document>(list.size());
    for (T parameter : list) {
      Map<String, Object> doc = (Map<String, Object>) document.executorNode(config.getNamespace(), configuration, parameter);
      Document d = new Document(doc);
      documents.add(d);
      if (logger.isDebugEnabled()) {
        logger.debug("Execute 'insert' mongodb command. Doc '" + d + "'.");
      }
    }

    try {
      coll.insertMany(documents);
    } catch (Exception e) {
      throw new MongoDaoException(statement, "Execute 'insert' mongodb command has exception. The write was unacknowledged.", e);
    }

    List<String> newIds = new ArrayList<String>(list.size());
    for (Document doc : documents) {
      String newId = doc.getObjectId("_id").toString();
      newIds.add(newId);
      if (logger.isDebugEnabled()) {
        logger.debug("Execute 'insert' mongodb command. ObjectId is '" + newId + "'.");
      }
    }

    if (selectKey != null) {
      for (int i = 0; i < list.size(); i++) {
        T parameter = list.get(i);
        helper.setSelectKey(selectKey, newIds.get(i), parameter);
      }
    }
    return newIds;
  }

  @Override
  public <T> T findAndModify(String statement) {
    return findAndModify(statement, null, null, false);
  }

  @Override
  public <T> T findAndModify(String statement, Object parameter) {
    return findAndModify(statement, parameter, null, false);
  }

  @Override
  public void findAndModify(String statement, ResultHandler handler) {
    findAndModify(statement, null, handler, false);
  }

  @Override
  public void findAndModify(String statement, Object parameter, ResultHandler handler) {
    findAndModify(statement, null, handler, false);
  }

  private <T> T findAndModify(String statement, Object parameter, ResultHandler handler, boolean upsert) {
    if (logger.isDebugEnabled()) {
      logger.debug("Execute 'findAndModify' mongodb command. Statement '" + statement + "'.");
    }

    UpdateConfig config = (UpdateConfig) configuration.getStatement(statement);
    if (config == null) {
      throw new MongoDaoException(statement, "FindAndModify statement id '" + statement + "' not found.");
    }

    String collection = config.getCollection();
    NodeEntry query = config.getQuery();
    NodeEntry action = config.getAction();
    NodeEntry field = config.getField();

    MongoDatabase db = getDatabase();
    MongoCollection<Document> coll = db.getCollection(collection);

    Map<String, Object> q = (Map<String, Object>) query.executorNode(config.getNamespace(), configuration, parameter);
    Map<String, Object> a = (Map<String, Object>) action.executorNode(config.getNamespace(), configuration, parameter);
    Map<String, Object> f = (Map<String, Object>) field.executorNode(config.getNamespace(), configuration, parameter);

    Document filter = new Document(q);
    Document update = (a == null) ? null : new Document(a);
    Document fieldDbo = (f == null) ? null : new Document(f);

    if (logger.isDebugEnabled()) {
      logger.debug("Execute 'findAndModify' mongodb command. Query '" + filter + "'.");
      logger.debug("Execute 'findAndModify' mongodb command. Action '" + update + "'.");
      logger.debug("Execute 'findAndModify' mongodb command. Field '" + fieldDbo + "'.");
    }

    Document document = coll.findOneAndUpdate(filter, update, new FindOneAndUpdateOptions().upsert(upsert));
    if (logger.isDebugEnabled()) {
      logger.debug("Execute 'findAndModify' mongodb command. Result is '" + document + "'.");
    }

    if (handler != null) {
      handler.handleResult(new ResultContext() {
        @Override
        public Object getResultObject() {
          return document;
        }

        @Override
        public int getResultCount() {
          if (document == null) {
            return 0;
          }
          return 1;
        }
      });
      return null;
    }
    return (T) helper.toResult(config.getNamespace(), field, document);
  }

  @Override
  public long update(String statement) {
    return update(statement, null);
  }

  @Override
  public long update(String statement, Object parameter) {
    if (logger.isDebugEnabled()) {
      logger.debug("Execute 'update' mongodb command. Statement '" + statement + "'.");
    }

    UpdateConfig config = (UpdateConfig) configuration.getStatement(statement);
    if (config == null) {
      throw new MongoDaoException(statement, "Update statement id '" + statement + "' not found.");
    }

    String collection = config.getCollection();
    NodeEntry query = config.getQuery();
    NodeEntry action = config.getAction();

    MongoDatabase db = getDatabase();
    MongoCollection<Document> coll = db.getCollection(collection).withWriteConcern(WriteConcern.ACKNOWLEDGED);

    Map<String, Object> q = (Map<String, Object>) query.executorNode(config.getNamespace(), configuration, parameter);
    Map<String, Object> a = (Map<String, Object>) action.executorNode(config.getNamespace(), configuration, parameter);

    Document filter = new Document(q);
    Document update = (a == null) ? null : new Document(a);
    if (logger.isDebugEnabled()) {
      logger.debug("Execute 'update' mongodb command. Query '" + filter + "'.");
      logger.debug("Execute 'update' mongodb command. Action '" + update + "'.");
    }

    UpdateResult result = coll.updateMany(filter, update, new UpdateOptions().upsert(false));
    if (!result.wasAcknowledged()) {
      throw new MongoDaoException(statement, "Execute 'update' mongodb command has exception. The write was unacknowledged.");
    }
    return result.getModifiedCount();
  }

  @Override
  public long delete(String statement) {
    return delete(statement, null);
  }

  @Override
  public long delete(String statement, Object parameter) {
    if (logger.isDebugEnabled()) {
      logger.debug("Execute 'delete' mongodb command. Statement '" + statement + "'.");
    }

    DeleteConfig config = (DeleteConfig) configuration.getStatement(statement);
    if (config == null) {
      throw new MongoDaoException(statement, "Delete statement id '" + statement + "' not found.");
    }

    String collection = config.getCollection();
    NodeEntry query = config.getQuery();

    MongoDatabase db = getDatabase();
    MongoCollection<Document> coll = db.getCollection(collection).withWriteConcern(WriteConcern.ACKNOWLEDGED);

    Map<String, Object> q = (Map<String, Object>) query.executorNode(config.getNamespace(), configuration, parameter);

    Document filter = new Document(q);
    if (logger.isDebugEnabled()) {
      logger.debug("Execute 'delete' mongodb command. Query '" + filter + "'.");
    }

    DeleteResult result = coll.deleteMany(filter);
    if (!result.wasAcknowledged()) {
      throw new MongoDaoException(statement, "Execute 'delete' mongodb command has exception. The write was unacknowledged.");
    }
    return result.getDeletedCount();
  }

  @Override
  public <T> T command(String statement) {
    return command(statement, null, null, ReadPreference.secondaryPreferred());
  }

  @Override
  public <T> T command(String statement, Object parameter) {
    return command(statement, parameter, null, ReadPreference.secondaryPreferred());
  }

  @Override
  public void command(String statement, ResultHandler handler) {
    command(statement, null, handler, ReadPreference.secondaryPreferred());
  }

  @Override
  public void command(String statement, Object parameter, ResultHandler handler) {
    command(statement, parameter, handler, ReadPreference.secondaryPreferred());
  }

  private <T> T command(String statement, Object parameter, ResultHandler handler, ReadPreference readPreference) {
    if (logger.isDebugEnabled()) {
      logger.debug("Execute 'command' mongodb command. Statement '" + statement + "'.");
    }

    CommandConfig config = (CommandConfig) configuration.getStatement(statement);
    if (config == null) {
      throw new MongoDaoException(statement, "Command statement id '" + statement + "' not found.");
    }

    NodeEntry query = config.getQuery();
    NodeEntry field = config.getField();

    MongoDatabase db = getDatabase();

    Map<String, Object> q = (Map<String, Object>) query.executorNode(config.getNamespace(), configuration, parameter);

    Document command = new Document(q);
    if (logger.isDebugEnabled()) {
      logger.debug("Execute 'command' mongodb command. Query '" + command + "'.");
    }

    Document result = db.runCommand(command, readPreference);
    if (logger.isDebugEnabled()) {
      logger.debug("Execute 'command' mongodb command. Result set '" + result + "'.");
    }

    if (handler != null) {
      handler.handleResult(new ResultContext() {
        @Override
        public Object getResultObject() {
          return result;
        }

        @Override
        public int getResultCount() {
          if (result == null) {
            return 0;
          }
          return 1;
        }
      });
      return null;
    }
    return (T) helper.toResult(config.getNamespace(), field, result);
  }

  @Override
  public <T> T group(String statement) {
    return group(statement, null, null, ReadPreference.secondaryPreferred());
  }

  @Override
  public <T> T group(String statement, Object parameter) {
    return group(statement, parameter, null, ReadPreference.secondaryPreferred());
  }

  @Override
  public void group(String statement, ResultHandler handler) {
    group(statement, null, handler, ReadPreference.secondaryPreferred());
  }

  @Override
  public void group(String statement, Object parameter, ResultHandler handler) {
    group(statement, parameter, handler, ReadPreference.secondaryPreferred());
  }

  private <T> T group(String statement, Object parameter, ResultHandler handler, ReadPreference readPreference) {
    if (logger.isDebugEnabled()) {
      logger.debug("Execute 'group' mongodb command. Statement '" + statement + "'.");
    }

    GroupConfig config = (GroupConfig) configuration.getStatement(statement);
    if (config == null) {
      throw new MongoDaoException(statement, "Group statement id '" + statement + "' not found.");
    }

    String collection = config.getCollection();
    NodeEntry key = config.getKey();
    Script keyf = config.getKeyf();
    NodeEntry condition = config.getCondition();
    NodeEntry initial = config.getInitial();
    Script reduce = config.getReduce();
    Script finalize = config.getFinalize();
    NodeEntry field = config.getField();

    Map<String, Object> k = (Map<String, Object>) key.executorNode(config.getNamespace(), configuration, parameter);
    Map<String, Object> c = (Map<String, Object>) condition.executorNode(config.getNamespace(), configuration, parameter);
    Map<String, Object> i = (Map<String, Object>) initial.executorNode(config.getNamespace(), configuration, parameter);
    String kf = ScriptUtils.fillScriptParams(keyf, parameter);
    String r = ScriptUtils.fillScriptParams(reduce, parameter);
    String f = ScriptUtils.fillScriptParams(finalize, parameter);

    DBObject keys = new BasicDBObject(k);
    DBObject condi = new BasicDBObject(c);
    DBObject initi = new BasicDBObject(i);
    if (logger.isDebugEnabled()) {
      logger.debug("Execute 'group' mongodb command. Key '" + keys + "'.");
      logger.debug("Execute 'group' mongodb command. Condition '" + condi + "'.");
      logger.debug("Execute 'group' mongodb command. Initial '" + initi + "'.");
      logger.debug("Execute 'group' mongodb command. Keyf '" + kf + "'.");
      logger.debug("Execute 'group' mongodb command. Reduce '" + r + "'.");
      logger.debug("Execute 'group' mongodb command. Finalize '" + f + "'.");
    }

    DB legacyDb = factory.getLegacyDb();
    DBObject result = legacyDb.getCollection(collection).group(keys, condi, initi, r, f, readPreference);
    if (logger.isDebugEnabled()) {
      logger.debug("Execute 'group' mongodb command. Result set '" + result + "'.");
    }

    if (handler != null) {
      handler.handleResult(new ResultContext() {
        @Override
        public Object getResultObject() {
          return result;
        }

        @Override
        public int getResultCount() {
          if (result == null) {
            return 0;
          }
          return 1;
        }
      });
      return null;
    }
    return (T) helper.toResult(config.getNamespace(), field, result);
  }

  @Override
  public <T> List<T> aggregate(String statement) {
    return aggregate(statement, null, null, ReadPreference.secondaryPreferred());
  }

  @Override
  public <T> List<T> aggregate(String statement, Object parameter) {
    return aggregate(statement, parameter, null, ReadPreference.secondaryPreferred());
  }

  @Override
  public void aggregate(String statement, ResultHandler handler) {
    aggregate(statement, null, handler, ReadPreference.secondaryPreferred());
  }

  @Override
  public void aggregate(String statement, Object parameter, ResultHandler handler) {
    aggregate(statement, parameter, handler, ReadPreference.secondaryPreferred());
  }

  private <T> List<T> aggregate(String statement, Object parameter, ResultHandler handler, ReadPreference readPreference) {
    if (logger.isDebugEnabled()) {
      logger.debug("Execute 'aggregate' mongodb command. Statement '" + statement + "'.");
    }

    AggregateConfig config = (AggregateConfig) configuration.getStatement(statement);
    if (config == null) {
      throw new MongoDaoException(statement, "Aggregate statement id '" + statement + "' not found.");
    }

    String collection = config.getCollection();
    Map<String, NodeEntry> function = config.getFunction();
    NodeEntry field = config.getField();

    MongoDatabase db = getDatabase();
    MongoCollection<Document> coll = db.getCollection(collection).withReadPreference(readPreference);

    List<Document> operations = new ArrayList<Document>(function.size());
    for (Map.Entry<String, NodeEntry> entry : function.entrySet()) {
      NodeEntry ne = entry.getValue();
      Map<String, Object> op = (Map<String, Object>) ne.executorNode(config.getNamespace(), configuration, parameter);
      Document operation = new Document(op);
      operations.add(operation);
      if (logger.isDebugEnabled()) {
        logger.debug("Execute 'aggregate' mongodb command. Operation '" + operation + "'.");
      }
    }

    AggregateIterable<Document> iterable = coll.aggregate(operations);

    List<Document> list = new ArrayList<Document>();
    MongoCursor<Document> iterator = iterable.iterator();
    while (iterator.hasNext()) {
      list.add(iterator.next());
    }

    if (logger.isDebugEnabled()) {
      logger.debug("Execute 'aggregate' mongodb command. Result set '" + list + "'.");
    }

    if (handler != null) {
      handler.handleResult(new ResultContext() {
        @Override
        public Object getResultObject() {
          return list;
        }

        @Override
        public int getResultCount() {
          return list.size();
        }
      });
      return null;
    }

    List<T> result = new ArrayList<T>(list.size());
    for (Document doc : list) {
      T t = (T) helper.toResult(config.getNamespace(), field, doc);
      result.add(t);
    }
    return result;
  }

  @Override
  public <T> List<T> mapReduce(String statement) {
    return mapReduce(statement, null, null, ReadPreference.secondaryPreferred());
  }

  @Override
  public <T> List<T> mapReduce(String statement, Object parameter) {
    return mapReduce(statement, parameter, null, ReadPreference.secondaryPreferred());
  }

  @Override
  public void mapReduce(String statement, ResultHandler handler) {
    mapReduce(statement, null, handler, ReadPreference.secondaryPreferred());
  }

  @Override
  public void mapReduce(String statement, Object parameter, ResultHandler handler) {
    mapReduce(statement, parameter, handler, ReadPreference.secondaryPreferred());
  }
  
  private <T> List<T> mapReduce(String statement, Object parameter, ResultHandler handler, ReadPreference readPreference) {
    if (logger.isDebugEnabled()) {
      logger.debug("Execute 'mapReduce' mongodb command. Statement '" + statement + "'.");
    }

    MapReduceConfig config = (MapReduceConfig) configuration.getStatement(statement);
    if (config == null) {
      throw new MongoDaoException(statement, "MapReduce statement id '" + statement + "' not found.");
    }

    String collection = config.getCollection();
    Script map = config.getMap();
    Script reduce = config.getReduce();
    NodeEntry field = config.getField();
    
    String m = ScriptUtils.fillScriptParams(map, parameter);
    String r = ScriptUtils.fillScriptParams(reduce, parameter);
    
    MongoDatabase db = getDatabase();
    MongoCollection<Document> coll = db.getCollection(collection).withReadPreference(readPreference);

    MapReduceIterable<Document> iterable = coll.mapReduce(m, r);
    
    List<Document> list = new ArrayList<Document>();
    MongoCursor<Document> iterator = iterable.iterator();
    while (iterator.hasNext()) {
      list.add(iterator.next());
    }

    if (logger.isDebugEnabled()) {
      logger.debug("Execute 'mapReduce' mongodb command. Result set '" + list + "'.");
    }

    if (handler != null) {
      handler.handleResult(new ResultContext() {
        @Override
        public Object getResultObject() {
          return list;
        }

        @Override
        public int getResultCount() {
          return list.size();
        }
      });
      return null;
    }

    List<T> result = new ArrayList<T>(list.size());
    for (Document doc : list) {
      T t = (T) helper.toResult(config.getNamespace(), field, doc);
      result.add(t);
    }
    return result;
  }
  
  @Override
  public MongoDatabase getDatabase() {
    return MongoDatabaseUtils.getDatabase(factory, sessionSynchronization);
  }

  @Override
  public MongoDatabase getDatabase(String dbName) {
    if (StringUtils.isBlank(dbName)) {
      throw new MongoDaoException("getDatabase", "Execute get mongodb command. The db name is blank.");
    }
    return MongoDatabaseUtils.getDatabase(dbName, factory, sessionSynchronization);
  }

  @Override
  public <T> T parseObject(String mappingId, Document source) {
    MappingConfig mapping = (MappingConfig) configuration.getStatement(mappingId);

    NodeEntry nodeEntry = new NodeEntry();
    nodeEntry.setClazz(mapping.getClazz());
    nodeEntry.setNodeMappings(NodeletUtils.getMappingEntry(mapping, configuration));
    return (T) helper.toResult(mapping.getNamespace(), nodeEntry, source);
  }

  /**
   * Helper for mongodb result.
   */
  private class ResultHelper {

    private ResultExecutor resultExecutor;

    public ResultHelper() {
      resultExecutor = new ResultExecutor();
    }

    public Object toResult(String namespace, NodeEntry entry, Object object) {
      return resultExecutor.parser(namespace, configuration, entry, object);
    }

    public void setSelectKey(Entry entry, String key, Object target) {
      if (target instanceof Map) {
        ((Map<Object, Object>) target).put(entry.getName(), key);
      } else {
        try {
          BeanUtils.setProperty(target, entry.getColumn(), key);
        } catch (Exception e) {
          throw new MongoORMException("No selectKey property '" + entry.getColumn() + "'found. Class '" + target.getClass() + "'.", e);
        }
      }
    }
  }

  public void setFactory(MongoFactoryBean factory) {
    this.factory = factory;
  }

}
