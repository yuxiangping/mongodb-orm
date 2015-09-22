package com.mongodb.orm;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NestedIOException;
import org.w3c.dom.Node;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.mongodb.exception.StatementException;
import com.mongodb.orm.builder.Nodelet;
import com.mongodb.orm.builder.NodeletParser;
import com.mongodb.orm.builder.statement.AggregateStatement;
import com.mongodb.orm.builder.statement.CommandStatement;
import com.mongodb.orm.builder.statement.DeleteStatement;
import com.mongodb.orm.builder.statement.GroupStatement;
import com.mongodb.orm.builder.statement.InsertStatement;
import com.mongodb.orm.builder.statement.MappingStatement;
import com.mongodb.orm.builder.statement.SelectStatement;
import com.mongodb.orm.builder.statement.UpdateStatement;
import com.mongodb.orm.engine.Config;
import com.mongodb.orm.engine.config.MappingConfig;
import com.mongodb.util.NodeletUtils;

/**
 * MQL map config parser
 * 
 * @author: xiangping_yu
 * @data : 2014-7-11
 * @since : 1.5
 */
public class MqlMapConfigParser {

  private static final Logger logger = LoggerFactory.getLogger(MqlMapConfigParser.class);

  private NodeletParser parser = new NodeletParser();

  private MqlMapConfiguration configuration = new MqlMapConfiguration();

  public MqlMapConfigParser() {
    parser.setValidation(true);
    parser.setEntityResolver(new MqlMapEntityResolver());

    addMapNodelets();
    addSelectNodelets();
    addUpdateNodelets();
    addInsertNodelets();
    addDeleteNodelets();
    addCommandNodelets();
    addGroupNodelets();
    addAggregateNodelets();
  }

  /**
   * Parse resource.
   * 
   * @param inputStream
   * @throws NestedIOException
   */
  public void parse(InputStream inputStream) throws NestedIOException {
    parser.parse(inputStream);
  }

  /**
   * Validate mql mapping configuration.
   */
  public void validateMapping() {
    Map<String, Config> mappings = configuration.getMappings();
    for(Map.Entry<String, Config> entry : mappings.entrySet()) {
      MappingConfig value = (MappingConfig)entry.getValue();
      getMappingExtendsNode(value);
      if(value.getNodes().isEmpty()) {
        throw new StatementException("MQL mapping config can not empty property. Mapping id '"+value.getId()+"'");
      }
    }
  }
  
  /**
   * Return the mql configuration.
   */
  public MqlMapConfiguration getConfiguration() {
    return configuration;
  }
  
  private void addMapNodelets() {
    parser.addNodelet("/mql/mapping", new Nodelet() {
      @Override
      public void process(Node node) throws Exception {
        Properties attributes = NodeletUtils.parseAttributes(node);
        String id = attributes.getProperty("id");

        configuration.addMapping(id, new MappingStatement(id).handler(node));
        logger.debug("Mql configuration load [mapping] '" + id + "' has finished.");
      }
    });
  }

  private void addSelectNodelets() {
    parser.addNodelet("/mql/select", new Nodelet() {
      @Override
      public void process(Node node) throws Exception {
        Properties attributes = NodeletUtils.parseAttributes(node);
        String id = attributes.getProperty("id");

        configuration.addConfig(id, new SelectStatement(id).handler(node));
        logger.debug("Mql configuration load [select] '" + id + "' has finished.");
      }
    });
  }

  private void addUpdateNodelets() {
    parser.addNodelet("/mql/update", new Nodelet() {
      @Override
      public void process(Node node) throws Exception {
        Properties attributes = NodeletUtils.parseAttributes(node);
        String id = attributes.getProperty("id");

        configuration.addConfig(id, new UpdateStatement(id).handler(node));
        logger.debug("Mql configuration load [update] '" + id + "' has finished.");
      }
    });
  }

  private void addInsertNodelets() {
    parser.addNodelet("/mql/insert", new Nodelet() {
      @Override
      public void process(Node node) throws Exception {
        Properties attributes = NodeletUtils.parseAttributes(node);
        String id = attributes.getProperty("id");

        configuration.addConfig(id, new InsertStatement(id).handler(node));
        logger.debug("Mql configuration load [insert] '" + id + "' has finished.");
      }
    });
  }

  private void addDeleteNodelets() {
    parser.addNodelet("/mql/delete", new Nodelet() {
      @Override
      public void process(Node node) throws Exception {
        Properties attributes = NodeletUtils.parseAttributes(node);
        String id = attributes.getProperty("id");

        configuration.addConfig(id, new DeleteStatement(id).handler(node));
        logger.debug("Mql configuration load [delete] '" + id + "' has finished.");
      }
    });
  }

  private void addCommandNodelets() {
    parser.addNodelet("/mql/command", new Nodelet() {
      @Override
      public void process(Node node) throws Exception {
        Properties attributes = NodeletUtils.parseAttributes(node);
        String id = attributes.getProperty("id");

        configuration.addConfig(id, new CommandStatement(id).handler(node));
        logger.debug("Mql configuration load [command] '" + id + "' has finished.");
      }
    });
  }

  private void addGroupNodelets() {
    parser.addNodelet("/mql/group", new Nodelet() {
      @Override
      public void process(Node node) throws Exception {
        Properties attributes = NodeletUtils.parseAttributes(node);
        String id = attributes.getProperty("id");

        configuration.addConfig(id, new GroupStatement(id).handler(node));
        logger.debug("Mql configuration load [group] '" + id + "' has finished.");
      }
    });
  }

  private void addAggregateNodelets() {
    parser.addNodelet("/mql/aggregate", new Nodelet() {
      @Override
      public void process(Node node) throws Exception {
        Properties attributes = NodeletUtils.parseAttributes(node);
        String id = attributes.getProperty("id");

        configuration.addConfig(id, new AggregateStatement(id).handler(node));
        logger.debug("Mql configuration load [aggregate] '" + id + "' has finished.");
      }
    });
  }
  
  private void getMappingExtendsNode(MappingConfig target) {
    String extend = target.getExtend();
    if(extend != null && target.getNodes().size()>0) {
      MappingConfig mc = (MappingConfig)configuration.getMapping(extend);
      if(mc == null) {
        throw new StatementException("This extends mapping '"+extend+"' not found. Mapping id '"+target.getId()+"'");
      }
      
      if(mc.getId().equals(target.getId())) {
        throw new StatementException("This extends mapping can not inherit their own. Mapping id '"+target.getId()+"'");
      }
      getMappingExtendsNode(mc);
      target.getNodes().addAll(mc.getNodes());
    }
  }

  /**
   * Offline entity resolver for the mongodb DTDs
   */
  private static class MqlMapEntityResolver implements EntityResolver {

    private static final String MQL_MAP_DTD = "mongodb-mql-map.dtd";

    private static final Map<String, String> doctypeMap = new HashMap<String, String>(2);

    static {
      doctypeMap.put("mongodb-mql-map.dtd".toUpperCase(), MQL_MAP_DTD);
      doctypeMap.put("-//eason.xp.yu@gmail.com//DTD MONGODB QL Config 1.0//zh-CN".toUpperCase(), MQL_MAP_DTD);
    }

    /**
     * Converts a public DTD into a local one
     * 
     * @param publicId Unused but required by EntityResolver interface
     * @param systemId The DTD that is being requested
     * @return The InputSource for the DTD
     * @throws SAXException If anything goes wrong
     */
    public InputSource resolveEntity(String publicId, String systemId) throws SAXException {
      if (publicId != null) {
        publicId = publicId.toUpperCase();
      }

      if (systemId != null) {
        systemId = systemId.toUpperCase();
      }

      InputSource source = null;
      try {
        String path = (String) doctypeMap.get(publicId);
        source = getInputSource(path, source);
        if (source == null) {
          path = (String) doctypeMap.get(systemId);
          source = getInputSource(path, source);
        }
      } catch (Exception e) {
        throw new SAXException(e.toString());
      }
      return source;
    }

    private InputSource getInputSource(String path, InputSource source) {
      if (path != null) {
        InputStream in = ClassLoader.getSystemResourceAsStream(path);
        source = new InputSource(in);
      }
      return source;
    }
  }
  
}
