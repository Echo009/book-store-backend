package cn.lan.bookstore.enums.common;

import cn.lan.bookstore.enums.CodeEnum;

/**
 * @author: Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 04/24/2018 12:52 PM
 */
public enum UserSexEnum implements CodeEnum {
    FEMALE(2, "女"),
    MALE(1, "男"),
    UNKNOW(0, "未知");

    private Integer code;
    private String desc;

    UserSexEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
