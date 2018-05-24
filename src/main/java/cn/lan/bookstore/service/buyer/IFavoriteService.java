package cn.lan.bookstore.service.buyer;

import cn.lan.bookstore.entity.buyer.FavoriteEntity;

import java.util.List;

/**
 * @author: Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 04/27/2018 02:07 AM
 */
public interface IFavoriteService {

    FavoriteEntity add(boolean isBook, Long contentId, Long userId);

    void remove(Long userId , Long id);

    void remove(Long userId, Short type, Long contentId);

    List findAll(boolean isBook, Long userId);

    boolean check(boolean isBook, Long contentId,Long userId);
}
