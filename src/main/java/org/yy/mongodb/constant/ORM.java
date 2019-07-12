package org.yy.mongodb.constant;

/**
 * ORM Constants.
 * 
 * @author yy
 */
public class ORM {

  private ORM() {}

  /**
   * XML file orm id
   */
  public static final String TAG_ORM_ID = "id";

  /**
   * XML file orm db collection
   */
  public static final String TAG_DB_COLLECTION = "collection";

  /**
   * XML file 'column' tag
   */
  public static final String TAG_CLUMN = "column";

  /**
   * XML file 'name' tag
   */
  public static final String TAG_NAME = "name";

  /**
   * XML file 'operate' tag
   */
  public static final String TAG_OPERATE = "operate";

  /**
   * XML file 'value' tag
   */
  public static final String TAG_VALUE = "value";

  /**
   * XML file 'type' tag
   */
  public static final String TAG_TYPE = "type";

  /**
   * XML file 'ignoreNull' tag
   */
  public static final String TAG_IGNORE_NULL = "ignoreNull";
  /**
   * XML file 'ignoreEmpty' tag
   */
  public static final String TAG_IGNORE_EMPTY = "ignoreEmpty";
  /**
   * XML file orm class tag
   */
  public static final String ORM_CLASS = "class";

  /**
   * XML file orm extends tag
   */
  public static final String ORM_EXTENDS = "extends";

  /**
   * XML file orm mapping tag
   */
  public static final String ORM_MAPPING = "mapping";

  /**
   * XML file node 'query'
   */
  public static final String NODE_QUERY = "query";

  /**
   * XML file node 'field'
   */
  public static final String NODE_FIELD = "field";

  /**
   * XML file child node 'value'
   */
  public static final String NODE_CHILD_NODE = "value";

  /**
   * XML file node 'order'
   */
  public static final String NODE_ORDER = "order";

  /**
   * XML file node 'action'
   */
  public static final String NODE_ACTION = "action";

  /**
   * XML file node 'document'
   */
  public static final String NODE_DOCUMENT = "document";

  /**
   * XML file node 'selectKey'
   */
  public static final String NODE_SELECTKEY = "selectKey";

  /**
   * XML file node 'key'
   */
  public static final String NODE_KEY = "key";

  /**
   * XML file node 'keyf'
   */
  public static final String NODE_KEY_FUNCTION = "keyf";

  /**
   * XML file node 'initial'
   */
  public static final String NODE_INITIAL = "initial";

  /**
   * XML file node 'condition'
   */
  public static final String NODE_CONDITION = "condition";

  /**
   * XML file node 'reduce'
   */
  public static final String NODE_REDUCE = "reduce";

  /**
   * XML file node 'finalize'
   */
  public static final String NODE_FINALIZE = "finalize";

  /**
   * XML file node 'function'
   */
  public static final String NODE_FUNCTION = "function";

  /**
   * XML file node 'pipeline'
   */
  public static final String NODE_PIPELINE = "pipeline";
  /**
   * XML file script params tag start
   */
  public static final String LABEL_SCRIPT_START = "${";
  /**
   * XML file script params tag end
   */
  public static final String LABEL_SCRIPT_END = "}";
  /**
   * XML file 'value' label. Example <property column="name" value="${value}" />
   */
  public static final String LABEL_VALUE = LABEL_SCRIPT_START + "value" + LABEL_SCRIPT_END;
}
