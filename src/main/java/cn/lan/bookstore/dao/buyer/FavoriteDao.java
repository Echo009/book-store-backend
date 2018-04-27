package cn.lan.bookstore.dao.buyer;

import cn.lan.bookstore.entity.buyer.FavoriteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author: Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 04/27/2018 02:05 AM
 */
public interface FavoriteDao extends JpaRepository<FavoriteEntity, Long> {

    List<FavoriteEntity> findAllByUserIdAndType(Long userId, Short type);

    FavoriteEntity findAllByUserIdAndTypeAndContentId(Long userId, Short type,Long contentId);

}
