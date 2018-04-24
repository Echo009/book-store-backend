package cn.lan.bookstore.util;

import cn.lan.bookstore.entity.seller.StoreEntity;
import cn.lan.bookstore.form.seller.StoreForm;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.BeanUtils;

/**
 * @author: Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 04/20/2018 02:33 PM
 */
@Slf4j
public class BeanUtilTest {
    @Test
    public void copyPropertyTest(){
        StoreEntity storeEntity = new StoreEntity();
        storeEntity.setStoreName("Echo009");

        StoreForm storeForm = new StoreForm();
        BeanUtils.copyProperties(storeForm, storeEntity,"storeName");
        log.info(JsonUtil.toJson(storeEntity,true));
    }

}
