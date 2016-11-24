package com.eunut.http.json;

import com.eunut.http.bean.ResultCode;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class ResultCodeTypeAdapter implements JsonSerializer<ResultCode>, JsonDeserializer<ResultCode> {

    // 对象转为Json时调用,实现JsonSerializer<ResultCode>接口
    @Override
    public JsonElement serialize(ResultCode code, Type arg1, JsonSerializationContext arg2) {
        return new JsonPrimitive(code.ordinal());
    }

    // json转为对象时调用,实现JsonDeserializer<ResultCode>接口
    @Override
    public ResultCode deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
        throws JsonParseException {
        try {
            return ResultCode.valueOf(json.getAsString());
        } catch (Exception e) {
            try {
                return ResultCode.values()[json.getAsInt()];
            } catch (Exception e1) {
            }
        }
        return ResultCode.FAIL;
    }
}