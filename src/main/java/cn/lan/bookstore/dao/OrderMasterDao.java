package cn.lan.bookstore.dao;

import cn.lan.bookstore.entity.common.OrderMasterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author: Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 05/14/2018 03:28 PM
 */
public interface OrderMasterDao extends JpaRepository<OrderMasterEntity,Long>,JpaSpecificationExecutor<OrderMasterEntity> {

    OrderMasterEntity findByOrderMasterId(String orderMasterId);
}
