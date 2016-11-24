package com.eunut.util;

import java.util.HashMap;
import java.util.Map;

public class ParameterUtil {

	public static Map<String, String> toMap(String urlString){
		if(urlString.indexOf("?")>-1){
			return transform(urlString.substring(urlString.indexOf("?")+1));
		}else if(urlString.indexOf("#")>-1){
			return transform(urlString.substring(urlString.indexOf("#")+1));
		}
		return null;
	}
	
	private static Map<String, String> transform(String args) {
		Map<String, String> reusltMap = new HashMap<String, String>();
		String keysAndValues [] = args.split("&");
		for (String string : keysAndValues) {
			String temp [] = string.split("=");
			reusltMap.put(temp[0], temp[1]);
		}
		return reusltMap;
	}
}
