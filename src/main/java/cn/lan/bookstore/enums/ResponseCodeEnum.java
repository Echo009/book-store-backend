package cn.lan.bookstore.enums;

import lombok.Getter;


/**
 * Author : Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 03/16/2018 03:39 PM
 * @author Ech0
 */
@Getter
public enum  ResponseCodeEnum {

    SUCCESS(0, "success"),
    ERROR(1, "error"),
    /**
     * register
     */
    INVALID_PHONE(101, "invalid phone number"),
    INVALID_USERNAME(102, "invalid username"),
    /**
     * login
     */
    INCOMPLETE_INFO(111,"incomplete_info"),
    INCORRECT_INFO(112,"incorrect info");


    private final Integer code;

    private final String desc;

    ResponseCodeEnum(Integer code, String desc){
        this.code = code;
        this.desc = desc;
    }



}
