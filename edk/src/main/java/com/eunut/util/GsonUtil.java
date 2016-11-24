package com.eunut.util;

import com.eunut.http.bean.ResultCode;
import com.eunut.http.json.DoubleTypeAdapter;
import com.eunut.http.json.IntegerTypeAdapter;
import com.eunut.http.json.NumberTypeAdapter;
import com.eunut.http.json.ResultCodeTypeAdapter;
import com.eunut.http.json.TimestampTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;
import java.sql.Timestamp;

public class GsonUtil {
    private static GsonBuilder gsonBuilder = new GsonBuilder().setDateFormat("yyyy-MM-dd")
        .registerTypeAdapter(Timestamp.class, new TimestampTypeAdapter())
        .registerTypeAdapter(ResultCode.class, new ResultCodeTypeAdapter())
        .registerTypeAdapter(Number.class, new NumberTypeAdapter())
        .registerTypeAdapter(Double.class, new DoubleTypeAdapter())
        .registerTypeAdapter(Integer.class, new IntegerTypeAdapter()).setPrettyPrinting();

    public static Gson get() {
        return gsonBuilder.create();
    }

    public static Gson get(String pattern) {
        return gsonBuilder.setDateFormat(pattern).create();
    }

    public static void registerTypeAdapter(Type type, Object typeAdapter) {
        gsonBuilder.registerTypeAdapter(type, typeAdapter);
    }
}
