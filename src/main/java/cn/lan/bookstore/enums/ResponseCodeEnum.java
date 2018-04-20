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
    /**
     * common
     */
    SUCCESS(0, "success !"),
    ERROR(1, "error !"),
    ILLEGAL_ARGUMENT(3,"illegal arguments !"),
    /**
     * register
     */
    INVALID_PHONE(101, "invalid phone number !"),
    INVALID_USERNAME(102, "invalid username !"),
    INVALID_STORE_NAME(103, "invalid store name !"),
    /**
     * login
     */
    INCOMPLETE_INFO(111,"incomplete info !"),
    INCORRECT_INFO(112,"incorrect info !"),
    REPEAT_LOGIN(113,"repeat login !"),

    /**
     *  Privilege
     */
    NO_LOGIN(121,"not logged in !"),
    NO_PRIVILEGE(122," permission denied !"),
    INVALID_TOKEN(123,"invalid token !"),
    VIOLATION_OPERATION(124,"violation operation !"),


    ;
    private final Integer code;

    private final String desc;

    ResponseCodeEnum(Integer code, String desc){
        this.code = code;
        this.desc = desc;
    }



}
