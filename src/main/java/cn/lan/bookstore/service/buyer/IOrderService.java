package cn.lan.bookstore.service.buyer;

import cn.lan.bookstore.vo.OrderWrapper;

import java.util.List;

/**
 * @author: Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 05/14/2018 03:35 PM
 */
public interface IOrderService {

        /**
         * 生成订单
         * @param cartIdList
         * @param addressId
         * @param userId
         * @return
         */
        OrderWrapper generateOrder(List<Long> cartIdList,Long addressId , Long userId);

        /**
         * 取消订单
         * @param orderMasterId
         * @param userId
         */
        void cancelOrder(String orderMasterId, Long userId);

        /**
         * 支付订单
         * @param orderMasterId
         * @param userId
         */
        void payOrder(String orderMasterId, Long userId);

}
