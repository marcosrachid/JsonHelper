package br.com.json.handler.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

public class JsonHelper {

	public static Object jsonDecode(Object json) throws JSONException {
		if (json.toString().startsWith("[") && json.toString().endsWith("]")) {
			List<Object> list = JsonHelper.toList(new JSONArray(json.toString()));
			for (Object obj : list) {
				if (obj instanceof Map<?, ?>) {
					Map<String, Object> map = (Map<String, Object>) obj;
					for (Map.Entry<String, Object> entry : map.entrySet()) {
						map.put(entry.getKey(), JsonHelper.jsonDecode(entry.getValue()));
					}
				}
			}
			return list;
		} else if (json.toString().startsWith("{") && json.toString().endsWith("}")) {
			Map<String, Object> map = JsonHelper.toMap(new JSONObject(json.toString()));
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				map.put(entry.getKey(), JsonHelper.jsonDecode(entry.getValue()));
			}
			return map;
		} else {
			return json.toString();
		}
	}

	public static String jsonEncode(Object obj) throws JSONException {
		
		String json = "";
		int count = 0;
		if (obj == null) {
			return "{}";
		} else if (obj instanceof List<?>) {
			json += "[";
			List<Object> list = (List<Object>)obj;
			for (Object ob : list) {
				if (list.size()-1 == count++) {
					json += JsonHelper.jsonEncode(ob);
				} else {
					json += JsonHelper.jsonEncode(ob)+",";
				}
			}			
			json += "]";
		} else if (obj instanceof Map<?,?>) {
			json += "{";
			Map<String, Object> map = (Map<String, Object>) obj;
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				if (map.size()-1 == count++) {
					json += "\""+entry.getKey()+"\": "+JsonHelper.jsonEncode(entry.getValue());
				} else {
					json += "\""+entry.getKey()+"\": "+JsonHelper.jsonEncode(entry.getValue())+",";
				}
			}
			json += "}";
		} else if (obj instanceof Integer || obj instanceof Boolean) {
			json += obj.toString();
		} else {
			json += "\""+obj.toString()+"\"";
		}

		return json;
		
	}

	public static boolean isEmptyObject(JSONObject object) {
		return object.names() == null;
	}

	public static Map<String, Object> getMap(JSONObject object, String key) throws JSONException {
		return toMap(object.getJSONObject(key));
	}

	public static Map<String, Object> toMap(JSONObject object) throws JSONException {
		Map<String, Object> map = new HashMap();
		Iterator keys = object.keys();
		while (keys.hasNext()) {
			String key = (String) keys.next();
			map.put(key, fromJson(object.get(key).toString()));
		}
		return map;
	}

	public static List<Object> toList(JSONArray array) throws JSONException {
		List<Object> list = new ArrayList<Object>();
		for (int i = 0; i < array.length(); i++) {
			if (array.get(i).toString().startsWith("{") && array.get(i).toString().endsWith("}"))
				list.add(toMap(new JSONObject(array.get(i).toString())));
			else
				list.add(array.get(i).toString());
		}
		return list;
	}

	private static Object fromJson(Object json) throws JSONException {
		if (json == JSONObject.NULL) {
			return null;
		} else if (json instanceof JSONObject) {
			return toMap((JSONObject) json);
		} else if (json instanceof JSONArray) {
			return toList((JSONArray) json);
		} else {
			return json;
		}
	}

}