package cn.lan.bookstore.enums.seller;

import cn.lan.bookstore.enums.CodeEnum;

/**
 * @author: Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 04/20/2018 11:01 AM
 */
public enum StoreStatusEnum implements CodeEnum {
    VALID(0, "有效"),
    INVALID(1, "已注销");

    private Integer code;
    private String desc;


    StoreStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
