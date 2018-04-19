package cn.lan.bookstore.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Author : Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 10/07/2017 04:25 PM
 * @author Ech0
 */
public class JsonUtil {
    /**
     * 返回对象的Json格式表示，with pretty printing
     * @param object
     * @return
     */
    public static String toJson(Object object , boolean withPrettyPrint){
        GsonBuilder gsonBuilder = new GsonBuilder();
        if (withPrettyPrint) {
            gsonBuilder.setPrettyPrinting();
        }
        Gson gson = gsonBuilder.create();
        return gson.toJson(object);
    }
}
