package cn.lan.bookstore.vo;

import cn.lan.bookstore.enums.common.OrderStatusEnum;
import cn.lan.bookstore.enums.common.PayStatusEnum;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author: Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 05/14/2018 03:36 PM
 */
@Data
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class OrderWrapper {


    private String orderMasterId;

    private Long buyerId;

    private Long sellerId;

    private Long addrId;

    private BigDecimal orderAmount;
    /** 订单状态 ，0为新下单 */
    private Integer orderStatus;
    /** 支付状态，0为待支付 */
    private Integer payStatus ;
    /** 创建时间*/
    private Date createTime;
    /** 修改时间*/
    private Date updateTime;

    private List<OrderDetailVo> orderDetails;

}
