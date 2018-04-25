package cn.lan.bookstore.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author: Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 04/25/2018 04:53 PM
 */
@Data
public class BookInfoDetailVO {

    private Long bookDetailId;

    private Long bookId;

    private Long storeId;

    private String bookName;

    private String recommendation;

    private String authorName;

    private String publisher;
    /**
     * yyyy-MM-dd
     */
    private String publishDate;

    private BigDecimal pricing;

    private BigDecimal discount;

    private BigDecimal currentPrice;

    private Integer sales;

    private Integer stock;
    /**
     * 封面图片地址，多个图片之间以 ; 分隔
     */
    private String coverImg;
    /**
     * 分类，多个分类之间以 ; 分隔
     */
    private String category;

    private Integer status;

    private Integer edition;

    private Integer pages = 0;

    private Integer words = 0;

    private String printDate;

    private String paperType;

    private String briefIntro;

    private String authorIntro;
    /**
     * 前言
     */
    private String foreword;
    /**
     * 详情图片地址，以;分隔
     */
    private String images;


}
