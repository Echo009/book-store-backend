package cn.lan.bookstore.enums.common;

import cn.lan.bookstore.enums.CodeEnum;
import lombok.Getter;

/**
 * Author : Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 04/16/2018 10:14 PM
 */
@Getter
public enum  PayStatusEnum implements CodeEnum {
    WAIT(0,"待支付"),
    SUCCESS(1,"支付成功");


    private Integer code;
    private String description;

    private PayStatusEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }
}
