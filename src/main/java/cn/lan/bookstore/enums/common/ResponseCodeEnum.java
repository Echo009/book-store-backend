package cn.lan.bookstore.enums.common;

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
    SUCCESS(0, "成功 !"),
    ERROR(1, "错误 !"),
    ILLEGAL_ARGUMENT(2,"非法参数!"),
    /**
     * register
     */
    INVALID_PHONE(101, "无效的电话号码 !"),
    INVALID_USERNAME(102, "无效的用户名 !"),
    INVALID_STORE_NAME(103, "无效的店铺名 !"),
    /**
     * login
     */
    INCOMPLETE_INFO(111,"信息不全 !"),
    INCORRECT_INFO(112,"信息不对 !"),
    REPEAT_LOGIN(113,"重复登陆 !"),

    /**
     *  Privilege
     */
    NO_LOGIN(121,"没有登陆 !"),
    NO_PRIVILEGE(122," 没有权限 !"),
    INVALID_TOKEN(123,"无效的标志 !"),
    VIOLATION_OPERATION(124,"违规操作 !"),


    ;
    private final Integer code;

    private final String desc;

    ResponseCodeEnum(Integer code, String desc){
        this.code = code;
        this.desc = desc;
    }



}
