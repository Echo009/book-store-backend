package cn.lan.bookstore.service.common;

import cn.lan.bookstore.dto.ResultDTO;
import cn.lan.bookstore.dto.UserBaseInfoDTO;
import cn.lan.bookstore.entity.common.UserBaseInfoEntity;

/**
 * Author : Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 03/16/2018 02:29 PM
 * @author Ech0
 */
public interface IUserBaseInfoService {

    /**
     * 注册用户
     * @param userBaseInfoEntity
     * @return
     */
    boolean register(UserBaseInfoEntity userBaseInfoEntity);

    /**
     * 校验用户信息
     * @param userBaseInfoDTO
     * @return
     */
    ResultDTO<UserBaseInfoEntity> check(UserBaseInfoDTO userBaseInfoDTO);

    /**
     * 校验用户名是否存在
     * @param userName
     * @return
     */
    boolean checkUserName(String userName);

    /**
     * 校验手机号码是否已经被注册
     * @param phone
     * @return
     */
    boolean checkPhone(Long phone);

    /**
     * 查询用户信息
     * @param id
     * @return
     */
    UserBaseInfoEntity findUserInfoById(Long id);

    UserBaseInfoEntity updateUserInfo(UserBaseInfoEntity userBaseInfoEntity);
}
