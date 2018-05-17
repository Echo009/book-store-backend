package cn.lan.bookstore.entity.common;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * Author : Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 04/16/2018 10:22 PM
 * @author Ech0
 */

@Entity(name = "order_detail")
@Data
public class OrderDetailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    private String orderDeatilId;

    private String orderMasterId;

    private Long buyerId;

    private Long sellerId;

    private Long bookId;

    private String bookName;

    private BigDecimal price;

    private Integer quantity;

    private String coverImg;



}
