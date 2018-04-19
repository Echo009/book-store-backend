package cn.lan.bookstore.init;

import cn.lan.bookstore.entity.seller.CategoryEntity;
import cn.lan.bookstore.service.seller.ICategoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Author : Ech0
 *
 * @author: Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 04/19/2018 02:33 PM
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class InitDataTest {

    @Autowired
    private ICategoryService categoryService;

    @Test
    public void initCategorys(){
        for(CategoryEnum item : CategoryEnum.class.getEnumConstants()){
            CategoryEntity categoryEntity = new CategoryEntity();
            categoryEntity.setTag(item.getTag());
            categoryService.addCategory(categoryEntity);
        }
    }

}
