package cn.lan.bookstore.enums.common;

import cn.lan.bookstore.enums.CodeEnum;
import lombok.Getter;

/**
 * Author : Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 04/16/2018 10:13 PM
 * @author Ech0
 */
@Getter
public enum OrderStatusEnum implements CodeEnum{


    NEW(0,"待处理"),
    FINISH(1,"已完成"),
    CANCEL(2,"已取消");

    private Integer code;
    private String description;

    private OrderStatusEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }
}
