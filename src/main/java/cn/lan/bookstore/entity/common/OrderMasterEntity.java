package cn.lan.bookstore.entity.common;

import cn.lan.bookstore.enums.common.OrderStatusEnum;
import cn.lan.bookstore.enums.common.PayStatusEnum;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Author : Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 04/16/2018 10:12 PM
 */
@Entity(name = "order_master")
@Data
public class OrderMasterEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long buyer_id;

    private Long seller_id;

    private Long addr_id;

    private BigDecimal orderAmount;
    /** 订单状态 ，0为新下单 */
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();
    /** 支付状态，0为待支付 */
    private Integer payStatus = PayStatusEnum.WAIT.getCode();
    /** 创建时间*/
    private Date createTime;
    /** 修改时间*/
    private Date updateTime;
}
