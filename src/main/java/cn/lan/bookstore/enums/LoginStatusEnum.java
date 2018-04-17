package cn.lan.bookstore.enums;

import lombok.Getter;

/**
 * Author : Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 03/20/2018 01:31 PM
 * @author Ech0
 */
@Getter
public enum LoginStatusEnum {

    LOGIN(0),
    LOGIN_OUT(1);

    private Integer code ;

    private LoginStatusEnum(int code) {
        this.code = code;
    }
}
