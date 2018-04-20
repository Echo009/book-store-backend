package cn.lan.bookstore.controller.common;

import cn.lan.bookstore.entity.seller.CategoryEntity;
import cn.lan.bookstore.response.BaseResponse;
import cn.lan.bookstore.service.seller.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Author : Ech0
 *
 * @author: Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 04/19/2018 01:59 PM
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    @RequestMapping("/all")
    public BaseResponse getAllCategorys(){
        List categorys = categoryService.findAllCategorys();
        return new BaseResponse(true, categorys);
    }

    @RequestMapping("/add")
    public BaseResponse addCategory(String tag){
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setTag(tag);
        categoryEntity = categoryService.addCategory(categoryEntity);
        return new BaseResponse(true, categoryEntity);
    }
}
