package cn.lan.bookstore.dao;

import cn.lan.bookstore.entity.UserBaseInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

/**
 * Author : Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 03/16/2018 02:24 PM
 */
@Component
public interface UserBaseInfoDao extends JpaRepository<UserBaseInfoEntity, Integer> {

    UserBaseInfoEntity findByUserName(String userName);

    UserBaseInfoEntity findAllByPhone(Long phone);

    UserBaseInfoEntity findByUserNameAndPassword(String username, String password);

    UserBaseInfoEntity findByPhoneEqualsAndPassword(Long phone, String password);

}
