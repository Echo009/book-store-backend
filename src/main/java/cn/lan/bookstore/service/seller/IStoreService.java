package cn.lan.bookstore.service.seller;

import cn.lan.bookstore.entity.seller.StoreEntity;

import java.util.List;

/**
 * @author: Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 04/20/2018 10:07 AM
 */
public interface IStoreService {

    StoreEntity findStoreByUserId(Long userId);

    StoreEntity updateStoreInfo(StoreEntity storeEntity);

    StoreEntity createStore(StoreEntity storeEntity);

    /**
     * 根据商铺名称 分页模糊查询 匹配的商铺信息
     *
     * @param storeName
     * @param pageSize
     * @param pageNum
     * @return
     */
    List<StoreEntity> findStoresByName(String storeName, Integer pageSize, Integer pageNum);

    StoreEntity findStoreByName(String storeName);
}
