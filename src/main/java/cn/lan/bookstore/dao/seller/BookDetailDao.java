package cn.lan.bookstore.dao.seller;

import cn.lan.bookstore.entity.seller.BookDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author: Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 04/25/2018 09:07 AM
 */
public interface BookDetailDao extends JpaRepository<BookDetailEntity, Long> {

    BookDetailEntity findByBookId(Long bookId);

}
