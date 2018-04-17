package cn.lan.bookstore.util;


import cn.lan.bookstore.enums.CodeEnum;

/**
 * Author : Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 10/07/2017 08:32 PM
 * @author Ech0
 */
public class EnumUtil<T> {
    public static <T extends CodeEnum>T getByCode(Integer code , Class<T> enumClass){
        for (T each : enumClass.getEnumConstants()){
            if (code.equals(each.getCode())){
                return each;
            }
        }
        return null;
    }
}
