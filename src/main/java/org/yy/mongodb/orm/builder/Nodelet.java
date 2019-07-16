package org.yy.mongodb.orm.builder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;

/**
 * XML node parser interface. 
 * @author yy
 */
public interface Nodelet {

	Logger logger = LoggerFactory.getLogger(Nodelet.class);
	
	void process (String namespace, Node node) throws Exception;
}
