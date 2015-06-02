package com.sel2in.smsWebSend.utils;

import com.sel2in.smsWebSend.logger.Sel2inLogFactory;
import com.sel2in.smsWebSend.logger.Sel2inLogger;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class JsonUtils {

	private static final Sel2inLogger logger = Sel2inLogFactory.getLoggerInstance(JsonUtils.class.getName());

	public static String objectToJson(Object object) {
		String json = null;
		json = new Gson().toJson(object);
		return json;
	}

	public static <T> T jsonToObject(String json, Class<T> klass) {
		T object = null;
		try {
			object = new Gson().fromJson(json, klass);
		} catch (JsonSyntaxException e) {
			logger.log(Sel2inLogger.ERROR, "" + e);
		}
		return object;
	}
}
