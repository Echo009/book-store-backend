package cn.lan.bookstore.service.impl;

import cn.lan.bookstore.dao.UserBaseInfoDao;
import cn.lan.bookstore.dto.ResultDTO;
import cn.lan.bookstore.dto.UserBaseInfoDTO;
import cn.lan.bookstore.entity.common.UserBaseInfoEntity;
import cn.lan.bookstore.service.IUserBaseInfoService;
import cn.lan.bookstore.util.Encrypter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author : Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 03/16/2018 02:58 PM
 * @author Ech0
 */
@Service
public class UserBaseInfoServiceImpl implements IUserBaseInfoService {

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
    public ResultDTO<UserBaseInfoEntity> check(UserBaseInfoDTO userBaseInfoDTO) {

        userBaseInfoDTO.setPassword(Encrypter.md5(userBaseInfoDTO.getPassword()));

        UserBaseInfoEntity currentUser;
        if (userBaseInfoDTO.getPhone() != null) {
            currentUser = userBaseInfoDao.findByPhoneEqualsAndPassword(
                    userBaseInfoDTO.getPhone(),
                    userBaseInfoDTO.getPassword());
            if (currentUser == null) {
                return ResultDTO.BAD_RESULT;
            } else {
                return new ResultDTO<>(true, currentUser);
            }
        } else {
            currentUser = userBaseInfoDao.findByUserNameAndPassword(userBaseInfoDTO.getUserName(), userBaseInfoDTO.getPassword());
            if (currentUser == null) {
                return ResultDTO.BAD_RESULT;
            } else {
                return new ResultDTO<>(true, currentUser);
            }
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
