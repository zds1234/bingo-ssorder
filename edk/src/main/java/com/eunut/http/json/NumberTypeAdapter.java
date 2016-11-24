package com.eunut.http.json;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class NumberTypeAdapter implements JsonDeserializer<Number> {

	@Override
	public Number deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
		try {
			return element.getAsNumber();
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return 0;
		}
	}
}