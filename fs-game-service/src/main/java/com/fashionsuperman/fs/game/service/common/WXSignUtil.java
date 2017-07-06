package com.fashionsuperman.fs.game.service.common;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fashionSuperman.fs.core.util.MD5Encrypt;
import com.fashionSuperman.fs.core.util.StringUtil;

/**
 * 
 * @description 微信接口调用签名工具
 * @author FashionSuperman
 * @date 2017年7月3日 下午1:43:45
 * @version 1.0
 */
public class WXSignUtil {
	@SuppressWarnings("unchecked")
	public static <T> String sign(T t , String key) throws IllegalArgumentException, IllegalAccessException{
		String result = "";
		
		if(t == null){
			return result;
		}
		
		//反射bean的所有属性名称和值  储存到map
		Class<T> clazz = (Class<T>) t.getClass();
		Map<String, String> field_Value_Map = new HashMap<String, String>();
		Field[] fields = clazz.getDeclaredFields();
		String fieldName;
		String fieldValue;
		List<String> sortFieldNameList = new ArrayList<String>();
		for(Field field : fields){
			field.setAccessible(true);
			fieldName = field.getName();
			if(field.get(t) != null){
				fieldValue = field.get(t).toString();
			}else{
				continue;
			}
			
			if(!"sign".equals(fieldName)){
				if(StringUtil.isNotEmpty(fieldValue)){
					if("backage".equals(fieldName)){
						field_Value_Map.put("package", fieldValue);
						sortFieldNameList.add("package");
					}else{
						field_Value_Map.put(fieldName, fieldValue);
						sortFieldNameList.add(fieldName);
					}
					
				}
			}
			
		}
		
		String[] sortedFieldNameArr = new String[sortFieldNameList.size()];
		sortFieldNameList.toArray(sortedFieldNameArr);
		Arrays.sort(sortedFieldNameArr);
		sortFieldNameList = Arrays.asList(sortedFieldNameArr);
		
		for(String fn : sortedFieldNameArr){
			fieldValue = field_Value_Map.get(fn);
			
			result += fn + "=" + fieldValue + "&";
		}
		
		//拼接key
		result = result + "key=" + key;
		
		result = MD5Encrypt.MD5Encode(result).toUpperCase();
		
		return result;
	}
}
