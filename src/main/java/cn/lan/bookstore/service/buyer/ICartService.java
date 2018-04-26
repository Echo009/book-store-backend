package cn.lan.bookstore.service.buyer;

import cn.lan.bookstore.dto.ResultDTO;
import cn.lan.bookstore.entity.buyer.CartEntity;

import java.util.List;

/**
 * @author: Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 04/26/2018 09:57 AM
 */
public interface ICartService {

    /**
     * 查找所有购物车
     * @param userId
     * @return
     */
    List<CartEntity> findCartsByUserId(Long userId);

    /**
     * 添加购物车
     * @param userId
     * @param bookId
     * @return
     */
    ResultDTO<CartEntity> addCart(Long userId , Long bookId,Integer amount);

    /**
     * 删除购物车
     * @param userId
     * @param cartId
     * @return
     */
    ResultDTO removeCart(Long userId,Long cartId);

    /**
     * 删除所有购物车
     * @param userId
     * @return
     */
    ResultDTO removeAllCart(Long userId);


}
