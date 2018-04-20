package cn.lan.bookstore.service.seller;

import cn.lan.bookstore.entity.seller.StoreEntity;
import cn.lan.bookstore.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

/**
 * @author: Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 04/20/2018 11:54 AM
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class StoreServiceTest {
    @Autowired
    private IStoreService storeService;


    @Test
    public void createStore() {
        StoreEntity storeEntity = new StoreEntity();
        storeEntity.setUserId(1L);
        storeEntity.setRegistDate(new Date());
        storeEntity.setRegisterPhone(17673207341L);
        storeEntity.setStoreName("Echo009 ");
        storeEntity.setBankNum("4029312378978923748923");
        storeService.createStore(storeEntity);
        log.info("storeEntity: {}",JsonUtil.toJson(storeEntity,true));
    }

    @Test
    public void findStore(){
        String storeName = "Echo";
        List<StoreEntity> storeEntityList = storeService.findStoresByName(storeName, 10, 1);
        log.info("result of search : {}",JsonUtil.toJson(storeEntityList.toArray(new StoreEntity[]{}),true));
    }
}
