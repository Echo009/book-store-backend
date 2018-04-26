package cn.lan.bookstore.service.seller.impl;

import cn.lan.bookstore.dao.seller.StoreDao;
import cn.lan.bookstore.entity.seller.StoreEntity;
import cn.lan.bookstore.enums.seller.StoreStatusEnum;
import cn.lan.bookstore.service.seller.IStoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.util.LinkedList;
import java.util.List;

/**
 * @author: Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 04/20/2018 10:29 AM
 */
@Service
@Slf4j
public class StoreServiceImpl implements IStoreService {

    @Autowired
    private StoreDao storeDao;

    @Override
    public StoreEntity findStoreByUserId(Long userId) {
        return storeDao.findByUserId(userId);
    }

    @Override
    public StoreEntity findStoreById(Long id) {
        return storeDao.findOne(id);
    }

    @Override
    public StoreEntity updateStoreInfo(StoreEntity storeEntity) {

        StoreEntity before = storeDao.findByUserId(storeEntity.getUserId());
        storeEntity.setId(before.getId());
        return storeDao.saveAndFlush(storeEntity);

    }

    @Override
    public StoreEntity createStore(StoreEntity storeEntity) {
        return storeDao.saveAndFlush(storeEntity);
    }

    /**
     * 根据商铺名称 分页模糊查询 匹配的商铺信息
     *
     * @param storeName
     * @param pageSize
     * @param pageNum   从1开始
     * @return
     */
    @Override
    public List<StoreEntity> findStoresByName(String storeName, Integer pageSize, Integer pageNum) {
        // 构造查询条件
        Specification<StoreEntity> specification =
                (root, query, cb) -> {
                    List<Predicate> predicates = new LinkedList<>();
                    // 商铺名称模糊查询条件
                    Path<String> $storeName = root.get("storeName");
                    Predicate _storeName = cb.like($storeName, "%" + storeName + "%");
                    predicates.add(_storeName);

                    // 商铺状态
                    Path<Integer> $storeStatus = root.get("status");
                    Predicate _storeStatus = cb.equal($storeName, StoreStatusEnum.VALID.getCode());
                    predicates.add(_storeStatus);
                    return cb.and(predicates.toArray(new Predicate[]{}));
                };


        Sort sort = new Sort(Sort.Direction.ASC, "id");
        Pageable pageable = new PageRequest(pageNum - 1, pageNum, sort);

        return storeDao.findAll(specification, pageable).getContent();
    }

    @Override
    public StoreEntity findStoreByName(String storeName) {
        return storeDao.findByStoreName(storeName);
    }
}
