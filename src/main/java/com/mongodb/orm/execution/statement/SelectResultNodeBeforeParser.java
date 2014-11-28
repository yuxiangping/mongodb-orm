package com.mongodb.orm.execution.statement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.mongodb.core.MongoSQLContext;
import com.mongodb.exception.MongoORMException;
import com.mongodb.orm.engine.entry.Mapping;
import com.mongodb.orm.engine.entry.MappingEntry;
import com.mongodb.orm.executor.MqlExecutor;

/**
 * @info  : 用于解析 select result 节点  查询前  的分析器 
 * @author: xiangping_yu
 * @data  : 2013-6-7
 * @since : 1.5
 */
public class SelectResultNodeBeforeParser implements MqlExecutor<Map<String, String>> {
	
	/**
	 * 默认值   1 为返回  -1为不需要返回
	 * 约定 需要返回的列必需在xml中配置  这里使用默认的1  如需要关闭默认规则  请注入
	 */
	private static final String DEFAULT = "1";
	
	private boolean def = true;
	
	@Override
	public Map<String, String> parser (Mapping orm, MongoSQLContext context, Object obj) throws MongoORMException {
		//if (obj == null) obj = MapUtils.EMPTY_MAP;
		Map<String, String> result = new HashMap<String, String>();
		if (!StringUtils.isBlank(orm.getId())) {
			Mapping map = context.getOM(orm.getId());   // 全局mapping映射
			List<MappingEntry> om = map.getOm();
			for(MappingEntry entry : om) {
				String column = entry.getColumn();
				String value = entry.getValue();
				result.put(column, getValue(value));
			}
		} else if (orm.getClazz() !=null){
			List<MappingEntry> entrys = orm.getOm();
			for(MappingEntry entry : entrys){
				String column = entry.getColumn();
				String value = entry.getValue();
				result.put(column, getValue(value));
			}
		} else {
			throw new RuntimeException("SQL配置规则异常, 请设置mapping或class属性");
		}
		return result;
	}
	
	/* 返回结果可直接使用预先配置的内容  没必要这么复杂的处理 
	@Override
	public Map<String, String> parser (Mapping orm, MongoSQLContext context, Object obj) throws Exception {
		if (obj == null) obj = MapUtils.EMPTY_MAP;
		Map<String, String> result = new HashMap<String, String>();
		if (!StringUtils.isBlank(orm.getId())) {
			Mapping map = context.getOM(orm.getId());   // 全局mapping映射
			List<MappingEntry> om = map.getOm();
			if (obj instanceof Map) {  // Map 转换
				Map<String, Object> para = (Map<String, Object>)obj;
				for(MappingEntry entry : om) {
					String column = entry.getColumn();
					String name = entry.getName();
					String value = entry.getValue();
					
					String v = DEFAULT;
					if(para.get(name)!=null){  // 优先使用 obj中的值 
						v = getValue(String.valueOf(para.get(name)));
					} else if (!StringUtils.isBlank(value)) {
						v = getValue(value);
					}
					result.put(column, v);
				}
			} else {  //  其他类型转换
				for(MappingEntry entry : om) {
					String value = entry.getValue();
					String v = DEFAULT;
					
					if (!StringUtils.isBlank(value)) {
						v = getValue(value);
					}
					 result.put( entry.getColumn(), v);
				}
			}
		} else if (orm.getClazz() !=null){
			List<MappingEntry> entrys = orm.getOm();
			if (obj instanceof Map) {
				Map<String, Object> para = (Map<String, Object>)obj;
				for(MappingEntry entry : entrys){
					String column = entry.getColumn();
					String name = entry.getName();
					String value = entry.getValue();
					String v = DEFAULT;
					if(para.get(name)!=null){
						v = getValue(String.valueOf(para.get(name)));
					} else if (value!=null) {
						v = getValue(String.valueOf(value));
					}
					result.put(column, v);
				}
			}  else {
				for(MappingEntry entry : entrys){
					String value = entry.getValue();
					String v = DEFAULT;
					if (value!=null) {
						v = getValue(value);
					}
					result.put(entry.getColumn(), v);
				}
			} 
		} else {
			throw new RuntimeException("SQL配置规则异常, 请设置mapping或class属性");
		}
		return result;
	}*/

	private String getValue(String value) {
		if (def) return DEFAULT;
		if(StringUtils.isBlank(value)) return DEFAULT;
		return value;
	}
	
	public void setDef(boolean def) {
		this.def = def;
	}
}
