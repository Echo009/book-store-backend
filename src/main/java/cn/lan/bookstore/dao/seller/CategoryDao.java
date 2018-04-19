package cn.lan.bookstore.dao.seller;

import cn.lan.bookstore.entity.seller.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Author : Ech0
 *
 * @author: Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 04/19/2018 01:53 PM
 */


public interface CategoryDao extends JpaRepository<CategoryEntity, Long> {

    List<CategoryEntity> findAllByTagNotNull();

}
