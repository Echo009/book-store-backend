package cn.lan.bookstore.dao.seller;

import cn.lan.bookstore.entity.seller.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author: Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 04/21/2018 03:04 PM
 */
public interface BookDao extends JpaRepository<BookEntity,Long>,JpaSpecificationExecutor<BookEntity> {

    List<BookEntity> findAllByStoreId(Long storeId);

    BookEntity findAllByBookName(String name);

}
