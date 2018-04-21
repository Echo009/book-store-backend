package cn.lan.bookstore.init;

import lombok.Getter;

/**
 * Author : Ech0
 *
 * @author: Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 04/19/2018 02:26 PM
 */
@Getter
public enum CategoryEnum {

    NOVEL("小说"),
    LITERATURE("文学"),
    ART("艺术"),
    CARTON("卡通"),
    EDUCATION("教育"),
    SCIENCE("科技"),
    HISTORY("历史"),
    LIFE("生活");

    private String tag;

    private CategoryEnum(String tag) {
        this.tag = tag;
    }
}
