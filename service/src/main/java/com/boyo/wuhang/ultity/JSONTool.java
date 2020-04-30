package com.boyo.wuhang.ultity;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import java.util.List;

public class JSONTool {

	public static <T> List<T> jsonToList(String jsonString, Class<T> tClass) {
		JSONArray array = JSONArray.fromObject(jsonString);
		List<T> ts =(List<T>)JSONArray.toCollection(array, tClass);
		return ts;
	}

	public static <T> T getObject(String objStr, Class<T> tClass){
		try {
			JSONObject jsonObject = JSONObject.fromObject(objStr);
			JSONUtils.getMorpherRegistry()
					.registerMorpher(new DateMorpher(
							new String[] {"yyyy-MM-dd HH:mm:ss.SSS",
										"yyyy-MM-dd HH:mm:ss",
										"yyyy-MM-dd HH:mm",
										"yyyy-MM-dd HH",
										"yyyy-MM-dd",
										"yyyy-MM",
										"yyyy"}));
			return (T)JSONObject.toBean(jsonObject,tClass);
		}catch (Exception e) {
			System.out.println(tClass + "转 JSON 失败");
		}
		return null;
	}
}
