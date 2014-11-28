package com.mongodb.orm.builder;

import org.apache.log4j.Logger;
import org.w3c.dom.Node;

/**
 * XML node parser interface 
 * @author: xiangping_yu
 * @data : 2014-3-6
 * @since : 1.5
 */
public interface Nodelet {

	Logger log = Logger.getLogger(Nodelet.class);
	
	void process (Node node) throws Exception;
}
