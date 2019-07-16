package org.yy.mongodb.orm;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.yy.mongodb.exception.StatementException;
import org.yy.mongodb.orm.engine.Config;

/**
 * Mongo QL configuration. Transfer xml config.
 * @author yy
 */
public class MqlMapConfiguration {

  /**
   * Mongodb mql configuration.
   */
  private Map<String, Config> configs;

  /**
   * Mongodb orm configuration
   */
  private Map<String, Config> mappings;

  public MqlMapConfiguration() {
    configs = new HashMap<String, Config>();
    mappings = new HashMap<String, Config>();
  }
  
  public Config getStatement(String statement) {
    return configs.get(statement);
  }

  public MqlMapConfiguration addConfig(String namespace, String key, Config value) {
    String statement = key;
    if (!StringUtils.isBlank(namespace) && key.indexOf(".") == -1) {
      statement = namespace + "." + key;
    }
    
    if (configs.containsKey(statement)) {
      throw new StatementException("Alias name conflict occurred.  This mql '"+key+"' is already exists."); 
    }
    configs.put(statement, value);
    return this;
  }

  public Config getMapping(String namespace, String key) {
    String statement = key;
    if (!StringUtils.isBlank(namespace) && key.indexOf(".") == -1) {
      statement = namespace + "." + key;
    }
    return mappings.get(statement);
  }

  public MqlMapConfiguration addMapping(String namespace, String key, Config value) {
    String statement = key;
    if (!StringUtils.isBlank(namespace) && key.indexOf(".") == -1) {
      statement = namespace + "." + key;
    }
    if (mappings.containsKey(statement)) {
      throw new StatementException("Alias name conflict occurred.  This mapping '"+key+"' is already exists.");
    }
    mappings.put(statement, value);
    return this;
  }

  public Map<String, Config> getConfigs() {
    return configs;
  }

  public Map<String, Config> getMappings() {
    return mappings;
  }
  
}
