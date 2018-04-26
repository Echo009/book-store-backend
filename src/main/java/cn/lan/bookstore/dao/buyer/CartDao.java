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

    List<CartEntity> findAllByUserId(Long userId);

    void deleteAllByUserId(Long userId);

    CartEntity findByUserIdAndBookId(Long userId,Long bookId);

}
