package com.eunut.http.json;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class IntegerTypeAdapter implements JsonDeserializer<Integer> {

	@Override
	public Integer deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
		try {
			return element.getAsInt();
		} catch (NumberFormatException e) {
			return 0;
		}
	}
}