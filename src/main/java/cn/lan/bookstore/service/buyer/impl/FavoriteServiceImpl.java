package cn.lan.bookstore.service.buyer.impl;

import cn.lan.bookstore.dao.buyer.FavoriteDao;
import cn.lan.bookstore.dao.seller.BookDao;
import cn.lan.bookstore.dao.seller.StoreDao;
import cn.lan.bookstore.entity.buyer.FavoriteEntity;
import cn.lan.bookstore.entity.seller.BookEntity;
import cn.lan.bookstore.entity.seller.StoreEntity;
import cn.lan.bookstore.enums.buyer.FavoriteTypeEnum;
import cn.lan.bookstore.enums.common.ResponseCodeEnum;
import cn.lan.bookstore.exception.BaseServerException;
import cn.lan.bookstore.service.buyer.IFavoriteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * @author: Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 04/27/2018 02:12 AM
 */
@Service
@Slf4j
public class FavoriteServiceImpl implements IFavoriteService {

    @Autowired
    private FavoriteDao favoriteDao;
    @Autowired
    private BookDao bookDao;
    @Autowired
    private StoreDao storeDao;

    @Override
    public FavoriteEntity add(boolean isBook, Long contentId, Long userId) {

        FavoriteEntity favoriteEntity = new FavoriteEntity();
        favoriteEntity.setContentId(contentId);
        favoriteEntity.setUserId(userId);
        if (isBook) {
            BookEntity bookEntity = bookDao.findOne(contentId);
            if (bookEntity == null) {
                throw new BaseServerException(ResponseCodeEnum.ILLEGAL_ARGUMENT);
            }
            favoriteEntity.setCoverImg(bookEntity.getCoverImg());
            favoriteEntity.setName(bookEntity.getBookName());
            favoriteEntity.setType((Short.valueOf(FavoriteTypeEnum.BOOK.getCode() + "")));
        } else {

            StoreEntity storeEntity = storeDao.findOne(contentId);
            if (storeEntity == null) {
                throw new BaseServerException(ResponseCodeEnum.ILLEGAL_ARGUMENT);
            }
            favoriteEntity.setName(storeEntity.getStoreName());
            favoriteEntity.setCoverImg(storeEntity.getStoreCoverImg());
            favoriteEntity.setType((Short.valueOf(FavoriteTypeEnum.STORE.getCode() + "")));
        }
        favoriteEntity.setCreateTime(new Date());
        return favoriteDao.saveAndFlush(favoriteEntity);
    }

    @Override
    @Transactional(rollbackOn = {Exception.class})
    public void remove(Long userId, Long id) {
        FavoriteEntity favoriteEntity = favoriteDao.findOne(id);
        if (favoriteEntity == null) {
            throw new BaseServerException(ResponseCodeEnum.ILLEGAL_ARGUMENT);
        }
        if (!favoriteEntity.getUserId().equals(userId)) {
            throw new BaseServerException(ResponseCodeEnum.VIOLATION_OPERATION);
        }
        favoriteDao.delete(id);
    }

    @Override
    public void remove(Long userId, Short type, Long contentId) {
        FavoriteEntity favoriteEntity = favoriteDao.findAllByUserIdAndTypeAndContentId(userId, type, contentId);
        if (favoriteEntity == null) {
            throw new BaseServerException(ResponseCodeEnum.VIOLATION_OPERATION);
        }
        favoriteDao.delete(favoriteEntity);
    }


    @Override
    public List<FavoriteEntity> findAll(boolean isBook, Long userId) {
        if (isBook) {
            return favoriteDao.findAllByUserIdAndType(userId, (Short.valueOf(FavoriteTypeEnum.BOOK.getCode() + "")));
        } else {
            return favoriteDao.findAllByUserIdAndType(userId, (Short.valueOf(FavoriteTypeEnum.STORE.getCode() + "")));
        }
    }

    @Override
    public boolean check(boolean isBook, Long contentId, Long userId) {
        short type;
        if (isBook) {
            type = Short.valueOf(FavoriteTypeEnum.BOOK.getCode() + "");
        } else {
            type = Short.valueOf(FavoriteTypeEnum.STORE.getCode() + "");

        }
        FavoriteEntity favoriteEntity = favoriteDao.findAllByUserIdAndTypeAndContentId(
                userId,
                type,
                contentId

        );
        return favoriteEntity != null;
    }
}
