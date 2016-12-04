package jtemp.bingossorder.utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.LinkedList;

public class JsonImp implements Serializable{

	public String toJson()
	{
		return new Gson().toJson(this);
	}
	public static <T> T toObject(String json,Class<T> c)
	{
		T p = null;
		try {
			p = new Gson().fromJson(json, c);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
		return p;
	}
//    public static  <T> BaseListModel<T>  toBaseListModel(String json, Type type){
//        BaseListModel<T> p = null;
//        try {
//            p = new Gson().fromJson(json, type);
//        } catch (Exception e) {
//            // TODO: handle exception
//            e.printStackTrace();
//            return null;
//        }
//        return p;
//    }

	public static <T> T toList(String json, Type type){
		T p = null;
		try {
			//Type listType = new TypeToken<T>(){}.getType();
			p = new Gson().fromJson(json, type);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
		return p;
	}
	
	public static <T> LinkedList<T> toLinkList(String json){
		LinkedList<T> p = null;
		try {
			Type listType = new TypeToken<LinkedList<T>>(){}.getType();
			p = new Gson().fromJson(json, listType);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
		return p;
	}
	
	public static String toJson(Object src, Type type){
		String p = null;
		try {
			//Type listType = new TypeToken<T>(){}.getType();
			p = new Gson().toJson(src, type);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
		return p;
	}
}
