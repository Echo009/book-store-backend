package cn.lan.bookstore.entity.buyer;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Author : Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 04/16/2018 04:37 PM
 *
 * @author Ech0
 */
@Entity
@Data
@Table(name = "cart",uniqueConstraints = {@UniqueConstraint(name = "u_1",columnNames = {"userId","bookId"})})
public class CartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long userId;

    private Long bookId;

    private Long storeId;

    private String coverImg;

    private String category;

    private String authorName;

    private BigDecimal pricing;

    private String bookName;

    private String storeName;

    private String publisher;

    private String publishDate;

    private BigDecimal currentPrice;

    private Integer amount;

    private BigDecimal totalPrice;

}
