package cn.lan.bookstore.service;

import cn.lan.bookstore.service.impl.UserBaseInfoServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * Author : Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 03/16/2018 03:11 PM
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserBaseInfoServiceImplTest {

    @Autowired
    private UserBaseInfoServiceImpl userBaseInfoService;

    @Test
    public void checkUserNameTest(){
        String userName = "lan";
        System.out.println(userBaseInfoService.checkUserName(userName));
    }
}
