package org.yy.mongodb.orm.builder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;

/**
 * XML node parser interface 
 * @author: xiangping_yu
 * @data : 2014-3-6
 * @since : 1.5
 */
public interface Nodelet {

	Logger logger = LoggerFactory.getLogger(Nodelet.class);
	
	void process (Node node) throws Exception;
}
