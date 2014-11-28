package com.mongodb.orm.execution.statement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

import com.mongodb.core.MongoSQLContext;
import com.mongodb.exception.MongoORMException;
import com.mongodb.orm.builder.dynamic.Dynamic;
import com.mongodb.orm.engine.entry.Mapping;
import com.mongodb.orm.engine.entry.MappingEntry;
import com.mongodb.orm.engine.entry.MappingEntry.Type;
import com.mongodb.orm.executor.ParserWapper;
import com.mongodb.orm.executor.MqlExecutor;

/**
 * @info  : 用于解析 update condition action 分析器 
 * @author: xiangping_yu
 * @data  : 2013-6-7
 * @since : 1.5
 */
@SuppressWarnings("unchecked")
public class UpdateActionNodeParser implements MqlExecutor<Map<String, Object>>{

	@Override
	public Map<String, Object> parser(Mapping orm, MongoSQLContext context, Object obj) throws MongoORMException {
		List<MappingEntry> om = orm.getOm();
		Map<String, Object> map = (Map<String, Object>)obj; 
		
		Map<String, Object> res = new HashMap<String, Object>();
		OptSet optSet = new OptSet();
		for (MappingEntry entry : om) {
			String column = entry.getColumn();
			String name = entry.getName();
			String operate = entry.getOperate();
			String value = entry.getValue();
			Type type = entry.getType(); // value 类型
			Dynamic dynamic = entry.getDynamic();

			// 子节点
			Mapping node = entry.getNode();
			
			Object v = null;
			if (dynamic != null) {
				v = dynamic.parser(obj);
			} else if (node != null) { // 回调 递归函数
				v = parser(node, context, obj);
			} else if (map.get(name)!=null) {
				v = map.get(name);
			} else if (!StringUtils.isBlank(value)) {
				v =value;
			}
			
			// 返回正确类型
			v =  ParserWapper.fitValue(type, v);
			
			if(v != null) {  // operate  表达式操作符  默认为= 
				if (StringUtils.isBlank(column) && StringUtils.isBlank(operate)) 
					throw new MongoORMException("请指定列的属性: column 或 operate");
					
				if (!StringUtils.isBlank(operate)) {
					if(StringUtils.isBlank(column)) {
						res.put(operate, v);
					} else {
						optSet.addOpt(operate, column , v);
					}
				} else {
					res.put(column, v);
				}
			}
		}
		// 合并操作集
		optSet.excute(res);
		return res;
	}
	
	class OptSet {
		Map<String, Map<String, Object>> operateSet = new HashMap<String, Map<String, Object>>(4);
		void addOpt(String opt, String column, Object v) {
			Map<String, Object> map = operateSet.get(opt);
			if (map == null) {
				map = new HashMap<String, Object>();
				map.put(column, v);
				operateSet.put(opt, map);
			} else {
				map.put(column, v);
			}
		}
		
		OptSet excute(Map<String, Object> res) {
			for (String opt : operateSet.keySet()) {
				Map<String, Object> map = operateSet.get(opt);
				Map<String, Object> m = new HashMap<String, Object>();
				for (Entry<String, Object> entry : map.entrySet()) {
					m.put(entry.getKey(), entry.getValue());
				}
				res.put(opt, m);
			}
			return this;
		}
	}
}
