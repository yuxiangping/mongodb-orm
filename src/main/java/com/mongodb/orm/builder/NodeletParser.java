package com.mongodb.orm.builder;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.core.NestedIOException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * XML node parser 
 * @author: xiangping_yu
 * @data : 2014-3-6
 * @since : 1.5
 */
public class NodeletParser {
  
  private Map<String, Nodelet> letMap = new HashMap<String, Nodelet>();
  
  private boolean validation;
  private EntityResolver entityResolver;
  
  /**
   * Registers a nodelet for the specified XPath.  Current XPaths supported
   * are:
   * <ul>
   * <li> Attribute Path  - /rootElement/childElement/@theAttribute
   * <li> Element Path - /rootElement/childElement/theElement
   * <li> All Elements Named - //theElement
   * </ul>
   */
  public void addNodelet(String xpath, Nodelet nodelet) {
    letMap.put(xpath, nodelet);
  }
  
  /**
   * Begins parsing from the provided Reader.
   */
  public void parse(Reader reader) throws NestedIOException {
    try {
      Document doc = createDocument(reader);
      parse(doc.getLastChild());
    } catch (Exception e) {
      throw new NestedIOException("Error parsing XML.  Cause: " + e, e);
    }
  }
  
  public void parse(InputStream inputStream) throws NestedIOException {
    try {
      Document doc = createDocument(inputStream);
      parse(doc.getLastChild());
    } catch (Exception e) {
      throw new NestedIOException("Error parsing XML.  Cause: " + e, e);
    }
  }
  
  /**
   * Begins parsing from the provided Node.
   */
  public void parse(Node node) {
    Path path = new Path();
    process(node, path);
  }
  
  /**
   * A recursive method that walkes the DOM tree, registers XPaths and
   * calls Nodelets registered under those XPaths.
   */
  private void process(Node node, Path path) {
    if (node instanceof Element) {
      // Element
      String elementName = node.getNodeName();
      path.add(elementName);
      processNodelet(node, path.toString());
      processNodelet(node, new StringBuffer("//").append(elementName).toString());

      // Children
      NodeList children = node.getChildNodes();
      for (int i = 0; i < children.getLength(); i++) {
        process(children.item(i), path);
      }
      path.remove();
    }
  }

  private void processNodelet(Node node, String pathString) {
    Nodelet nodelet = (Nodelet) letMap.get(pathString);
    if (nodelet != null) {
      try {
        nodelet.process(node);
      } catch (Exception e) {
        throw new RuntimeException("Error parsing XPath '" + pathString + "'.  Cause: " + e, e);
      }
    }
  }

  /**
   * Creates a JAXP Document from a reader.
   */
  private Document createDocument(Reader reader) throws ParserConfigurationException, FactoryConfigurationError,
      SAXException, IOException {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    factory.setValidating(validation);
    factory.setIgnoringComments(true);
    factory.setIgnoringElementContentWhitespace(true);
    
    DocumentBuilder builder = factory.newDocumentBuilder();
    builder.setEntityResolver(entityResolver);
    builder.setErrorHandler(new ErrorHandler() {
      public void error(SAXParseException exception) throws SAXException {
        throw exception;
      }

      public void fatalError(SAXParseException exception) throws SAXException {
        throw exception;
      }

      public void warning(SAXParseException exception) throws SAXException {
      }
    });

    return builder.parse(new InputSource(reader));
  }
  
  /**
   * Creates a JAXP Document from an InoutStream.
   */
  private Document createDocument(InputStream inputStream) throws ParserConfigurationException, FactoryConfigurationError,
      SAXException, IOException {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    factory.setValidating(validation);
    factory.setIgnoringElementContentWhitespace(true);
    factory.setIgnoringComments(true);

    DocumentBuilder builder = factory.newDocumentBuilder();
    builder.setEntityResolver(entityResolver);
    builder.setErrorHandler(new ErrorHandler() {
      public void error(SAXParseException exception) throws SAXException {
        throw exception;
      }

      public void fatalError(SAXParseException exception) throws SAXException {
        throw exception;
      }

      public void warning(SAXParseException exception) throws SAXException {
      }
    });

    InputSource input = new InputSource(inputStream);
    input.setEncoding("UTF-8");
    return builder.parse(input);
  }
  
  public void setValidation(boolean validation) {
    this.validation = validation;
  }

  public void setEntityResolver(EntityResolver resolver) {
    this.entityResolver = resolver;
  }

  /**
   * Inner helper class that assists with building XPath paths.
   * <p/>
   * Note:  Currently this is a bit slow and could be optimized.
   */
  private static class Path {

    private List<String> nodeList = new ArrayList<String>();

    public void add(String node) {
      nodeList.add(node);
    }

    public void remove() {
      nodeList.remove(nodeList.size() - 1);
    }

    public String toString() {
      StringBuilder builder = new StringBuilder("/");
      for (int i = 0; i < nodeList.size(); i++) {
        builder.append(nodeList.get(i));
        if (i < nodeList.size() - 1) {
          builder.append("/");
        }
      }
      return builder.toString();
    }
  }
}
