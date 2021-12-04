package com.arogyak.pg2es.utils;

import java.util.Map;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class Utils {

	public JSONObject toJsonObject(Map<String, String> addressMap) throws JSONException {
		JSONObject jo = new JSONObject();

		if (addressMap != null) {
			for (Map.Entry<String, String> entry : addressMap.entrySet()) {
				jo.put(entry.getKey(), entry.getValue());
			}
		}

		return jo;
	}

}
