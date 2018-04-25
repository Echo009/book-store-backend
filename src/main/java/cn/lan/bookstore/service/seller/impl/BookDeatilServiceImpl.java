package cn.lan.bookstore.service.seller.impl;

import cn.lan.bookstore.dao.seller.BookDao;
import cn.lan.bookstore.dao.seller.BookDetailDao;
import cn.lan.bookstore.dao.seller.StoreDao;
import cn.lan.bookstore.dto.ResultDTO;
import cn.lan.bookstore.entity.seller.BookDetailEntity;
import cn.lan.bookstore.entity.seller.BookEntity;
import cn.lan.bookstore.entity.seller.StoreEntity;
import cn.lan.bookstore.enums.common.ResponseCodeEnum;
import cn.lan.bookstore.exception.BaseServerException;
import cn.lan.bookstore.service.seller.IBookDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 04/25/2018 09:22 AM
 */
@Service
@Slf4j
public class BookDeatilServiceImpl implements IBookDetailService{
    @Autowired
    private BookDetailDao bookDetailDao;
    @Autowired
    private StoreDao storeDao;
    @Autowired
    private BookDao bookDao;

    /**
     * 添加商品详情
     * @param bookDetailEntity
     * @return
     */
    @Override
    public ResultDTO<BookDetailEntity> addBookDetail(Long userId,BookDetailEntity bookDetailEntity) {

        // 校验当前用户是否具有店铺权限
        StoreEntity storeEntity = storeDao.findByUserId(userId);
        if (storeEntity == null) {
            throw new BaseServerException(ResponseCodeEnum.NO_PRIVILEGE);
        }
        // 校验当前书籍是否属于该店铺
        BookEntity bookEntity = bookDao.findOne(bookDetailEntity.getBookId());
        if (bookEntity == null) {
            throw new BaseServerException(ResponseCodeEnum.NO_PRIVILEGE);
        }
        bookDetailEntity = bookDetailDao.saveAndFlush(bookDetailEntity);
        return new ResultDTO<>(true, bookDetailEntity);
    }

    /**
     * 修改商品详情信息
     *
     * @param bookDetailEntity
     * @return
     */
    @Override
    public ResultDTO<BookDetailEntity> updateBookDetail(Long userId,BookDetailEntity bookDetailEntity) {
        // 校验当前用户是否具有店铺权限
        StoreEntity storeEntity = storeDao.findByUserId(userId);
        if (storeEntity == null) {
            throw new BaseServerException(ResponseCodeEnum.NO_PRIVILEGE);
        }
        BookDetailEntity orginal = bookDetailDao.findOne(bookDetailEntity.getId());
        if (orginal == null) {
            throw new BaseServerException(ResponseCodeEnum.VIOLATION_OPERATION);
        }
        BookEntity bookEntity = bookDao.findOne(orginal.getBookId());
        // 校驗该书籍是否在该用户的店铺下
        if (!bookEntity.getStoreId().equals(storeEntity.getId())) {
            throw new BaseServerException(ResponseCodeEnum.NO_PRIVILEGE);
        }
        bookDetailEntity.setBookId(orginal.getBookId());
        bookDetailEntity = bookDetailDao.saveAndFlush(bookDetailEntity);
        return new ResultDTO<>(true, bookDetailEntity);
    }

    /**
     * 查找商品详情
     *
     * @param bookId
     * @return
     */
    @Override
    public ResultDTO<BookDetailEntity> findBookDeatailByBookId(Long bookId) {
        BookDetailEntity bookDetailEntity = bookDetailDao.findByBookId(bookId);
        if (bookDetailEntity == null) {
            return new ResultDTO(false, null);
        }
        else {
            return new ResultDTO(true, bookDetailEntity);
        }
    }
}
