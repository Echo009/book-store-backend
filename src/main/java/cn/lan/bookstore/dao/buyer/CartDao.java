package cn.lan.bookstore.dao.buyer;

import cn.lan.bookstore.entity.buyer.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author: Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 04/26/2018 09:55 AM
 */
public interface CartDao extends JpaRepository<CartEntity,Long> {

    /**
     * 根据用户Id查找其所有的购物车记录
     * @param userId
     * @return
     */
    List<CartEntity> findAllByUserId(Long userId);

    /**
     * 清空购物车
     * @param userId
     */
    void deleteAllByUserId(Long userId);

    /**
     * 根据用户id和图书id查找对应的购物车记录
     * @param userId
     * @param bookId
     * @return
     */
    CartEntity findByUserIdAndBookId(Long userId,Long bookId);

}
