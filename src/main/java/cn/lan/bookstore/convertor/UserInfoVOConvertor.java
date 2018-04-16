package cn.lan.bookstore.convertor;

import cn.lan.bookstore.entity.common.UserBaseInfoEntity;
import cn.lan.bookstore.enums.common.RoleCodeEnum;
import cn.lan.bookstore.util.Encrypter;
import cn.lan.bookstore.vo.UserInfoVO;

/**
 * Author : Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 03/20/2018 12:53 PM
 */
public class UserInfoVOConvertor {

    public static UserBaseInfoEntity userInfoVO2UserBaseInfoEntity(UserInfoVO userInfoVO) {

        UserBaseInfoEntity userBaseInfoEntity = new UserBaseInfoEntity();
        if (userInfoVO == null) {
            return userBaseInfoEntity;
        }
        userBaseInfoEntity.setUserName(userInfoVO.getUserName());
        userBaseInfoEntity.setPhone(userInfoVO.getPhone());
        userBaseInfoEntity.setEmail(userInfoVO.getEmail());

        // default ： buyer
        userBaseInfoEntity.setRoleCode(RoleCodeEnum.BUYER.getCode());
        // 加密后
        userBaseInfoEntity.setPassword(Encrypter.md5(userInfoVO.getPassword()));
        return userBaseInfoEntity;
    }
}
