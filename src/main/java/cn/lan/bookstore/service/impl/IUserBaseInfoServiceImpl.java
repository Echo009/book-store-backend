package cn.lan.bookstore.service.impl;

import cn.lan.bookstore.dao.UserBaseInfoDao;
import cn.lan.bookstore.dto.UserBaseInfoDTO;
import cn.lan.bookstore.entity.UserBaseInfoEntity;
import cn.lan.bookstore.service.IUserBaseInfoService;
import cn.lan.bookstore.util.Encrypter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author : Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 03/16/2018 02:58 PM
 */
@Service
public class IUserBaseInfoServiceImpl implements IUserBaseInfoService {

    @Autowired
    private UserBaseInfoDao userBaseInfoDao;

    /**
     * 注册用户
     *
     * @param userBaseInfoEntity
     * @return
     */
    @Override
    public boolean register(UserBaseInfoEntity userBaseInfoEntity) {

        return !(userBaseInfoDao.save(userBaseInfoEntity) == null);

    }

    /**
     * 校验用户信息
     *
     * @param userBaseInfoDTO
     * @return
     */
    @Override
    public boolean check(UserBaseInfoDTO userBaseInfoDTO) {

        userBaseInfoDTO.setPassword(Encrypter.md5(userBaseInfoDTO.getPassword()));
        if (userBaseInfoDTO.getPhone() != null) {
            if ((userBaseInfoDao.findByPhoneEqualsAndPassword(userBaseInfoDTO.getPhone(), userBaseInfoDTO.getPassword()) == null)) {
                return false;
            }
            else return true;
        } else {
            if (userBaseInfoDao.findByUserNameAndPassword(userBaseInfoDTO.getUserName(), userBaseInfoDTO.getPassword()) == null) {
                return false;
            }
            else return true;
        }
    }

    /**
     * 校验用户名是否存在
     *
     * @param userName
     * @return true 如果存在
     */
    @Override
    public boolean checkUserName(String userName) {
        return userBaseInfoDao.findByUserName(userName) == null ? false : true;
    }

    /**
     * 校验手机号码是否已经被注册
     *
     * @param phone
     * @return true 如果存在
     */
    @Override
    public boolean checkPhone(Long phone) {
        return userBaseInfoDao.findAllByPhone(phone) == null ? false : true;
    }
}
