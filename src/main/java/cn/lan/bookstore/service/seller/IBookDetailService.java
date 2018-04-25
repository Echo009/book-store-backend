package cn.lan.bookstore.service.seller;

import cn.lan.bookstore.dto.ResultDTO;
import cn.lan.bookstore.entity.seller.BookDetailEntity;

/**
 * @author: Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 04/25/2018 09:16 AM
 */
public interface IBookDetailService {
    /**
     * 添加商品详情
     * @param bookDetailEntity
     * @return
     */
    ResultDTO<BookDetailEntity> addBookDetail(Long userId, BookDetailEntity bookDetailEntity);

    /**
     * 修改商品详情信息
     * @param bookDetailEntity
     * @return
     */
    ResultDTO<BookDetailEntity> updateBookDetail(Long userId , BookDetailEntity bookDetailEntity);

    /**
     * 查找商品详情
     * @param bookId
     * @return
     */
    ResultDTO<BookDetailEntity> findBookDeatailByBookId(Long bookId);
}
