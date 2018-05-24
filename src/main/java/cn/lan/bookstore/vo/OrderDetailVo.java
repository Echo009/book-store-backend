package cn.lan.bookstore.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author: Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 05/14/2018 03:37 PM
 */
@Data
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class OrderDetailVo {

    private String orderDeatilId;

    private String orderMasterId;

    private Long bookId;

    private String bookName;

    private BigDecimal price;

    private Integer quantity;

    private String coverImg;

}
