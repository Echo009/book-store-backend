package cn.lan.bookstore.service.buyer.impl;

import cn.lan.bookstore.dao.buyer.CartDao;
import cn.lan.bookstore.dao.seller.BookDao;
import cn.lan.bookstore.dto.ResultDTO;
import cn.lan.bookstore.entity.buyer.CartEntity;
import cn.lan.bookstore.entity.seller.BookEntity;
import cn.lan.bookstore.enums.common.ResponseCodeEnum;
import cn.lan.bookstore.exception.BaseServerException;
import cn.lan.bookstore.service.buyer.ICartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 04/26/2018 10:01 AM
 */

@Service
@Slf4j
public class CartServiceImpl implements ICartService {

    @Autowired
    private CartDao cartDao;
    @Autowired
    private BookDao bookDao;

    /**
     * 查找所有购物车
     *
     * @param userId
     * @return
     */
    @Override
    public List<CartEntity> findCartsByUserId(Long userId) {
        return cartDao.findAllByUserId(userId);
    }

    /**
     * 添加购物车
     *
     * @param userId
     * @param bookId
     * @param amount
     * @return
     */
    @Override
    public ResultDTO<CartEntity> addCart(Long userId, Long bookId, Integer amount) {

        BookEntity bookEntity = bookDao.findOne(bookId);
        if (bookEntity == null) {
            throw new BaseServerException(ResponseCodeEnum.VIOLATION_OPERATION);
        }
        CartEntity cartEntity;
        amount = amount == null ? 1 : amount;
        cartEntity = cartDao.findByUserIdAndBookId(userId, bookId);
        if (cartEntity == null) {
            cartEntity = new CartEntity();
            cartEntity.setAmount(1);
            cartEntity.setUserId(userId);
            cartEntity.setBookId(bookId);
            cartEntity.setBookName(bookEntity.getBookName());
            cartEntity.setCoverImg(bookEntity.getCoverImg());
            cartEntity.setPrice(bookEntity.getCurrentPrice());
        } else {
            cartEntity.setAmount(cartEntity.getAmount() + amount);
        }
        cartDao.saveAndFlush(cartEntity);
        return new ResultDTO<>(true, cartEntity);
    }

    /**
     * 删除购物车
     *
     * @param cartId
     * @return
     */
    @Override
    public ResultDTO removeCart(Long userId, Long cartId) {
        CartEntity cartEntity = cartDao.findOne(cartId);
        if (cartEntity == null) {
            throw new BaseServerException(ResponseCodeEnum.VIOLATION_OPERATION);
        }
        if (!cartEntity.getUserId().equals(userId)) {
            throw new BaseServerException(ResponseCodeEnum.NO_PRIVILEGE);
        }
        cartDao.delete(cartId);
        return new ResultDTO(true, null);
    }

    /**
     * 删除所有购物车
     *
     * @param userId
     * @return
     */
    @Override
    public ResultDTO removeAllCart(Long userId) {
        cartDao.deleteAllByUserId(userId);
        return new ResultDTO(true, null);
    }


}
