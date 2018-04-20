package cn.lan.bookstore.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * Author : Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 10/07/2017 04:25 PM
 *
 * @author Ech0
 */
public class JsonUtil {
    /**
     * 返回对象的Json格式表示，with pretty printing
     *
     * @param object
     * @return
     */
    public static String toJson(Object object, boolean withPrettyPrint) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        if (withPrettyPrint) {
            gsonBuilder.setPrettyPrinting();
        }
        Gson gson = gsonBuilder.create();
        return gson.toJson(object);
    }


    public static <T> T toBean(String jsonString, Class<T> clazz) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, clazz);
    }


    public static String getValue(String jsonString, String propertyName) {
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(jsonString);
        return jsonElement.getAsJsonObject()
                .get(propertyName)
                .getAsString();
    }

}
