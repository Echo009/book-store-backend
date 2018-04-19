package cn.lan.bookstore.service.seller.impl;

import cn.lan.bookstore.dao.seller.CategoryDao;
import cn.lan.bookstore.entity.seller.CategoryEntity;
import cn.lan.bookstore.service.seller.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author : Ech0
 *
 * @author: Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 04/19/2018 01:56 PM
 */
@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryDao categoryDao;


    @Override
    public List<CategoryEntity> findAllCategorys() {
        return categoryDao.findAllByTagNotNull();
    }

    @Override
    public CategoryEntity addCategory(CategoryEntity categoryEntity) {
        return categoryDao.save(categoryEntity);
    }

}
