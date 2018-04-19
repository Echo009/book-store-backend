package cn.lan.bookstore.service.seller;

import cn.lan.bookstore.entity.seller.CategoryEntity;

import java.util.List;

/**
 * Author : Ech0
 *
 * @author: Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 04/19/2018 01:55 PM
 */
public interface ICategoryService {

    List<CategoryEntity> findAllCategorys();

    CategoryEntity addCategory( CategoryEntity categoryEntity);
}
