package cn.lan.bookstore.dao.buyer;

import cn.lan.bookstore.entity.buyer.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author: Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 04/27/2018 10:09 AM
 */
public interface AddressDao extends JpaRepository<AddressEntity,Long> {

    List<AddressEntity> findAllByUserId(Long userId);
}
