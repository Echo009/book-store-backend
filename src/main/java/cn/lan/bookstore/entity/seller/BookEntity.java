package cn.lan.bookstore.entity.seller;

import cn.lan.bookstore.enums.seller.ProductStatusEnum;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Author : Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 04/16/2018 04:08 PM
 *
 * @author Ech0
 */
@Entity
@Data
@Table(name = "book",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"bookName","storeId"})})
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long storeId;

    private String bookName;
    /**
     * 推荐语
     */
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

    private Integer sales = 0;

    private Integer stock;
    /**
     * 封面图片地址，多个图片之间以 ; 分隔
     */
    private String coverImg;
    /**
     * 分类，多个分类之间以 ; 分隔
     */
    private String category;


    private Integer status = ProductStatusEnum.ON_SALE.getCode();

}
