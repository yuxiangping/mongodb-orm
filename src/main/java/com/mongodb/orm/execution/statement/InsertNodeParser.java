//package com.mongodb.orm.execution.statement;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.commons.lang.StringUtils;
//
//import com.mongodb.core.MongoSQLContext;
//import com.mongodb.exception.MongoORMException;
//import com.mongodb.orm.engine.entry.Mapping;
//import com.mongodb.orm.engine.entry.MappingEntry;
//import com.mongodb.orm.executor.ParserWapper;
//import com.mongodb.orm.executor.MqlExecutor;
//
///**
// * @info  : 用于解析 delete 的分析器 
// * @author: xiangping_yu
// * @data  : 2013-6-7
// * @since : 1.5
// */
//@SuppressWarnings("unchecked")
//public class InsertNodeParser implements MqlExecutor<List<Map<String, Object>>> {
//
//	@Override
//	public List<Map<String, Object>> parser(Mapping orm, MongoSQLContext context, Object obj) throws MongoORMException {
//		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//		String id = orm.getId();
//		if (!StringUtils.isBlank(id)) {
//			Mapping mapping = context.getOM(id);  // 全局mapping
//			List<MappingEntry> om = mapping.getOm();
//			Class<?> clazz = mapping.getClazz();
//			if (obj instanceof List) {  
//				// 批量插入
//				batchInsertForMappingWarp(context, list, clazz, om, obj);
//			} else { 
//				// 单条插入
//				singleInsertForMappingWarp(context, list, clazz, om, obj);
//			}
//		} else if (orm.getClazz() != null) {
//			Class<?> clazz = orm.getClazz();
//			List<MappingEntry> om = orm.getOm();
//			if (obj instanceof List) {
//				// 批量插入
//				batchInserForClassWarp(context, list, clazz, om, obj);
//			} else {
//				// 单条插入
//				singleInsertForClassWarp(context,  list, clazz, om, obj);
//			}
//		}
//		return list;
//	}
//	
//	/**
//	 *  递归时回调
//	 */
//	ParserCallBack< List<Map<String, Object>>> callBack = new ParserCallBack< List<Map<String, Object>>>() {
//		@Override
//		public  List<Map<String, Object>> callBack(Mapping orm, MongoSQLContext context, Object obj) throws MongoORMException {
//			return parser(orm, context, obj);
//		}
//	};
//	
//	/**
//	 * 基于mapping 批量插入
//	 */
//	private void  batchInsertForMappingWarp(MongoSQLContext context, List<Map<String, Object>> list, Class<?> clazz, List<MappingEntry> om, Object obj) throws MongoORMException {
//		if (clazz.equals(Map.class)) {  // Map 转换
//			List<Map<String, Object>> objList = (List<Map<String, Object>>)obj;
//			for (Map<String, Object> para : objList) {
//				list.add(ParserWapper.mapToMap(context, om, para, callBack));
//			}
//		} else {  //  对象转换
//			List<Object> objList = (List<Object>)obj;
//			for (Object oj : objList) {
//				list.add(ParserWapper.objectToMap(context, om, oj, callBack));
//			}
//		}
//	}
//	
//	/**
//	 * 基于mapping单条插入
//	 */
//	private void singleInsertForMappingWarp(MongoSQLContext context, List<Map<String, Object>> list, Class<?> clazz, List<MappingEntry> om, Object obj) throws MongoORMException {
//		if (clazz.equals(Map.class)) {  // Map 转换
//			list.add(ParserWapper.mapToMap(context, om, (Map<String, Object>)obj, callBack));
//		} else {  //  对象转换
//			list.add(ParserWapper.objectToMap(context, om, obj, callBack));
//		}
//	}
//	
//	/**
//	 * 基于class的批量插入
//	 */
//	private void batchInserForClassWarp(MongoSQLContext context, List<Map<String, Object>> list, Class<?> clazz, List<MappingEntry> om, Object obj) throws MongoORMException {
//		if (clazz.equals(Map.class)) {  // Map 转换
//			List<Map<String, Object>> objList = (List<Map<String, Object>>)obj;
//			for (Map<String, Object> para : objList) {
//				list.add(ParserWapper.mapToMap(context, om,para, callBack));
//			}
//		} else {  //  其他类型转换 (String, Long ,Integer ...)
//			List<Object> objList = (List<Object>)obj;
//			for (Object oj : objList) {
//				if (ParserWapper.unitClass.contains(clazz))
//					list.add(ParserWapper.unitToMap(om, oj));
//				else
//					list.add(ParserWapper.objectToMap(context, om, oj, callBack));
//			}
//		}
//	}
//	
//	/**
//	 * 基于class的单条插入
//	 */
//	private void singleInsertForClassWarp(MongoSQLContext context, List<Map<String, Object>> list, Class<?> clazz, List<MappingEntry> om, Object obj) throws MongoORMException {
//		if (clazz.equals(Map.class)) {  // Map 转换
//			list.add(ParserWapper.mapToMap(context, om, (Map<String, Object>)obj, callBack));
//		} else {  //  其他类型转换 (String, Long ,Integer ...)
//			if (ParserWapper.unitClass.contains(clazz))
//				list.add(ParserWapper.unitToMap(om, obj));
//			else
//				list.add(ParserWapper.objectToMap(context, om, obj, callBack));
//		}
//	}
//}
