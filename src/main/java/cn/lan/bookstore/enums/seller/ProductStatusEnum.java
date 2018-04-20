package cn.lan.bookstore.enums.seller;

import cn.lan.bookstore.enums.CodeEnum;

/**
 * @author: Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 04/20/2018 11:01 AM
 */
public enum ProductStatusEnum implements CodeEnum {
    ON_SALE(0, "在售"),
    SOLD_OUT(1, "下架");

    private Integer code;
    private String desc;


    ProductStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
