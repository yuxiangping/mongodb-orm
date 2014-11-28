package com.mongodb.orm.engine.entry;

import java.io.Serializable;

import com.mongodb.orm.builder.dynamic.Dynamic;

/**
 * ORM mapping entry.
 * @author: xiangping_yu
 * @data  : 2013-6-8
 * @since : 1.5
 */
public class MappingEntry  implements Serializable{
	
	private static final long serialVersionUID = -8688546958061900484L;
	
	private String column;
	/**
	 * parameter name or entry field
	 */
	private String name;
	/**
	 * default value
	 */
	private String value;
	/**
	 * value type
	 */
	private Type type;
	/**
	 * operate, default "="
	 */
	private String operate;
	
	/**
	 * dynamic node
	 */
	private Dynamic dynamic;
	
	/**
	 * child node.
	 * Mapping子节点 (对象的属性为model 或 list 之类的结构时)
	 */
	private Mapping node;
	
	public MappingEntry(String column, String name, String value) {
		this.column = column;
		this.name = name;
		this.value = value;
	}

	public MappingEntry(String column, String name, String operate, String value){
		this.column = column;
		this.name = name;
		this.operate = operate;
		this.value = value;
	}
	
	public Mapping getNode() {
		return node;
	}
	public void setNode(Mapping node) {
		this.node = node;
	}
	public String getColumn() {
		return column;
	}
	public String getName() {
		return name;
	}
	public String getOperate() {
		return operate;
	}
	public String getValue() {
		return value;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public Dynamic getDynamic() {
		return dynamic;
	}
	public void setDynamic(Dynamic dynamic) {
		this.dynamic = dynamic;
	}

	/**
	 * value type enum
	 */
	public enum Type {
		STRING("string"),
		INT("int"),
		LONG("long"),
		FLOAT("float"),
		DOUBLE("double"),
		BOOLEAN("boolean"),
		REGEX("regex"),
		OBJECTID("objectid"),
		NULL("null");
		
		private String name;
		
		private Type(String name) {
			this.name = name;
		}
		
		public String getName() {
			return name;
		}
		
		public static Type fromName(String name) {
			for (Type type : values()) {
				if (type.getName().equals(name))
					return type;
			}
			return null;
		}
	}
}
