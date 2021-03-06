package cn.lan.bookstore.dao.seller;

import cn.lan.bookstore.entity.seller.StoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Author : Ech0
 *
 * @author: Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 04/19/2018 04:10 PM
 */
public interface StoreDao extends JpaRepository<StoreEntity, Long>, JpaSpecificationExecutor<StoreEntity> {
    StoreEntity findByUserId(Long userId);

    StoreEntity findByStoreName(String storeName);
}
