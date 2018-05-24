package cn.lan.bookstore.dao;

import cn.lan.bookstore.entity.common.OrderDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author: Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 05/14/2018 03:26 PM
 */
public interface OrderDetailDao  extends JpaRepository<OrderDetailEntity, Long> ,JpaSpecificationExecutor<OrderDetailEntity> {
    List<OrderDetailEntity> findAllByOrderMasterId(String orderMasterId);
}
