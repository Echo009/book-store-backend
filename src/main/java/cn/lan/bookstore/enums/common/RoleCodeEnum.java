package cn.lan.bookstore.enums.common;

import lombok.Getter;

/**
 * Author : Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 04/16/2018 02:54 PM
 */
@Getter
public enum RoleCodeEnum {

    ADMINISTRATOR(0,"管理员"),
    BUYER(1,"买家"),
    SELLER(2,"卖家");

    private int code;
    private String role_name;

    private RoleCodeEnum(int code, String role_name) {
        this.code = code;
        this.role_name = role_name;
    }

}
