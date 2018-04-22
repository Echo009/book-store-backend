package cn.lan.bookstore.service.seller;

import cn.lan.bookstore.dto.ResultDTO;
import cn.lan.bookstore.entity.seller.BookEntity;

import java.util.List;

/**
 * @author: Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 04/21/2018 03:09 PM
 */
public interface IBookService {
    /**
     * 新增书籍
     * @param userId
     * @param bookEntity
     * @return
     */
    ResultDTO<BookEntity> addBook(Long userId, BookEntity bookEntity);

    /**
     * 修改书籍信息
     * @param userId
     * @param bookEntity
     * @return
     */
    ResultDTO<BookEntity> updateBookInfo(Long userId ,BookEntity bookEntity);

    /**
     * 根据书籍名称分页查询书籍信息
     * @param bookName
     * @param pageSize
     * @param pageNum
     * @return
     */
    List<BookEntity> findBooksByBookName(String bookName, Integer pageSize, Integer pageNum);

    ResultDTO deleteBook(Long userId, Long bookId);

    List<BookEntity> findAllBooksByUserId(Long userId);
}