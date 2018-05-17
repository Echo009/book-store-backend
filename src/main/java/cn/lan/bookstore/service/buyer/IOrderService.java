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
         *
         * @param cartIdList
         * @param userId
         * @return
         */
        OrderWrapper generateOrder(List<Long> cartIdList,Long userId);


}
