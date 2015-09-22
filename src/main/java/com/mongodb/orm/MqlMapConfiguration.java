package com.mongodb.orm;

import java.util.HashMap;
import java.util.Map;

import com.mongodb.exception.StatementException;
import com.mongodb.orm.engine.Config;

/**
 * Mongo QL configuration. Transfer xml config.
 * 
 * @author: xiangping_yu
 * @data : 2013-6-4
 * @since : 1.5
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
  
  public Config getConfig(String key) {
    return configs.get(key);
  }

  public MqlMapConfiguration addConfig(String key, Config value) {
    if (configs.containsKey(key)) {
      throw new StatementException("Alias name conflict occurred.  This mql '"+key+"' is already exists."); 
    }
    configs.put(key, value);
    return this;
  }

  public Config getMapping(String key) {
    return mappings.get(key);
  }

  public MqlMapConfiguration addMapping(String key, Config value) {
    if (mappings.containsKey(key)) {
      throw new StatementException("Alias name conflict occurred.  This mapping '"+key+"' is already exists.");
    }
    mappings.put(key, value);
    return this;
  }

  public Map<String, Config> getConfigs() {
    return configs;
  }

  public Map<String, Config> getMappings() {
    return mappings;
  }
  
}
