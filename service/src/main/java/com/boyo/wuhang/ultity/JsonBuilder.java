package com.boyo.wuhang.ultity;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.sf.json.JSONArray;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;
import org.apache.commons.lang.time.DateFormatUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.*;

public class JsonBuilder {

	public static JSONObject build(int code, String msg, Object result) {
		JSONObject jsonResult = new JSONObject();
		jsonResult.put("code", code);
		jsonResult.put("msg", msg);
		if (result != null) {
			jsonResult.put("result", BeanToMap(result));
		} else {
			jsonResult.put("result", "");
		}
		return jsonResult;
	}

	public static JSONObject build(int code, String msg, List<Map<String, Object>> result) {
		Date date = new Date();
		JSONObject jsonResult = new JSONObject();
		jsonResult.put("code", code);
		jsonResult.put("msg", msg);
		if (result != null) {
			jsonResult.put("result", BeanToMap(result));
			//jsonResult.put("result", JSONArray.fromObject(result)); //空指针 无法删除
		} else {
			jsonResult.put("result", "");
		}
//        System.out.println(new Date().getTime() - date.getTime());
		return jsonResult;
	}

	public static Object BeanToMap(Object source) {
		if (source == null || JSONNull.getInstance().equals(source)) return null;
		if (source instanceof String
				|| source instanceof Integer
				|| source instanceof Double
				|| source instanceof Float
				|| source instanceof Byte
				|| source instanceof BigDecimal
				|| source instanceof Long
				|| source instanceof Boolean
				|| source instanceof BigInteger
		) {
			return source;
		} else if (source instanceof Map) {
			Map temp = (Map) source;
			Iterator iterator = temp.keySet().iterator();
			Map result = new HashMap();
			while (iterator.hasNext()) {
				Object next = iterator.next();
				//为 null 不返回
				if (BeanToMap(temp.get(next)) != null) {
					result.put(next, BeanToMap(temp.get(next)));
				}
			}
			return result;
		} else if (source instanceof Date) {
			return DateFormatUtils.format((Date) source, "yyyy-MM-dd HH:mm:ss.SSS");
		} else if (source instanceof Calendar) {
			return DateFormatUtils.format((Calendar) source, "yyyy-MM-dd HH:mm:ss.SSS");
		} else if (source instanceof Timestamp) {
			return DateFormatUtils.format(((Timestamp) source).getTime(), "yyyy-MM-dd HH:mm:ss.SSS");
		} else if (source instanceof Collection) {
			List result = new ArrayList();
			for (Object val : (Collection) source) {
				if (BeanToMap(val) != null) {
					//为 null 不返回
					result.add(BeanToMap(val));
				}
			}
			return result;
		} else if (source.getClass().getSimpleName().equals("ObjectId")) {
			return source.toString();
		} else {
			Map result = new HashMap();
			Field[] fileds = source.getClass().getDeclaredFields();
			for (Field field : fileds) {
				field.setAccessible(true);
				try {
					if (BeanToMap(field.get(source)) != null) {
						//为 null 不返回
						result.put(field.getName(), BeanToMap(field.get(source)));
					}
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
			return result;
		}
	}

	/**
	 * JSONObject转为map
	 *
	 * @param object json对象
	 * @return 转化后的Map
	 */
	public static Map<String, Object> toMap(JSONObject object) {
		Map<String, Object> map = new HashMap<String, Object>();

		for (Iterator<?> it = object.keys(); it.hasNext(); ) {
			String key = (String) it.next();
			Object value;
			try {
				value = object.get(key);
				if (value instanceof JSONArray) {
					value = toList((JSONArray) value);
				} else if (value instanceof JSONObject) {
					value = toMap((JSONObject) value);
				}
				if (JSONNull.getInstance().equals(value)) {
					map.put(key, null);
				} else {
					map.put(key, value);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return map;
	}

	/**
	 * JSONArray转为List
	 *
	 * @param array json数组
	 * @return 转化后的List
	 */
	public static List<Object> toList(JSONArray array) {
		List<Object> list = new ArrayList<Object>();
		for (int i = 0; i < array.size(); i++) {
			Object value;
			try {
				value = array.get(i);
				if (value instanceof JSONArray) {
					value = toList((JSONArray) value);
				} else if (value instanceof JSONObject) {
					value = toMap((JSONObject) value);
				}
				list.add(value);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return list;
	}

	public static String objectToUrlEncodedString(Object object, Gson gson) {
		return jsonToUrlEncodedString((JsonObject) new JsonParser().parse(gson.toJson(object)));
	}

	private static String jsonToUrlEncodedString(JsonObject jsonObject) {
		return jsonToUrlEncodedString(jsonObject, "");
	}

	private static String jsonToUrlEncodedString(JsonObject jsonObject, String prefix) {
		String urlString = "";
		for (Map.Entry<String, JsonElement> item : jsonObject.entrySet()) {
			if (item.getValue() != null && item.getValue().isJsonObject()) {
				urlString += jsonToUrlEncodedString(
						item.getValue().getAsJsonObject(),
						prefix.isEmpty() ? item.getKey() : prefix + "[" + item.getKey() + "]"
				);
			} else {
				urlString += prefix.isEmpty() ?
						item.getKey() + "=" + item.getValue().getAsString() + "&" :
						prefix + "[" + item.getKey() + "]=" + item.getValue().getAsString() + "&";
			}
		}
		return urlString;
	}
}
