package cn.lan.bookstore.enums.buyer;

import lombok.Getter;

/**
 * Author : Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 04/16/2018 04:46 PM
 */

@Getter
public enum FavoriteTypeEnum {
    STORE(0,"store"),
    BOOK(1,"book");

    private Integer code;

    private String desc ;

    FavoriteTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
