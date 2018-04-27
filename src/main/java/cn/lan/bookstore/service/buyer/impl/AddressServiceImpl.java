package cn.lan.bookstore.service.buyer.impl;

import cn.lan.bookstore.dao.buyer.AddressDao;
import cn.lan.bookstore.entity.buyer.AddressEntity;
import cn.lan.bookstore.enums.common.ResponseCodeEnum;
import cn.lan.bookstore.exception.BaseServerException;
import cn.lan.bookstore.service.buyer.IAddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author: Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 04/27/2018 10:15 AM
 */
@Service
@Slf4j
public class AddressServiceImpl implements IAddressService {

    @Autowired
    private AddressDao addressDao;
    @Override
    public List<AddressEntity> findAll(Long userId) {
        return addressDao.findAllByUserId(userId);
    }

    @Override
    public AddressEntity update(AddressEntity addressEntity, Long userId) {
        AddressEntity origin = addressDao.findOne(addressEntity.getId());
        if (origin == null) {
            throw new BaseServerException(ResponseCodeEnum.VIOLATION_OPERATION);
        } else if (!origin.getUserId().equals(userId)) {
            throw new BaseServerException(ResponseCodeEnum.NO_PRIVILEGE);
        } else {
            return addressDao.saveAndFlush(addressEntity);
        }
    }

    @Override
    public AddressEntity add(AddressEntity addressEntity, Long userId) {
        addressEntity.setUserId(userId);
        return addressDao.saveAndFlush(addressEntity);
    }

    @Override
    @Transactional(rollbackOn = {})
    public void remove(Long id, Long userId) {
        AddressEntity origin = addressDao.findOne(id);
        if (origin == null) {
            throw new BaseServerException(ResponseCodeEnum.VIOLATION_OPERATION);
        } else if (!origin.getUserId().equals(userId)) {
            throw new BaseServerException(ResponseCodeEnum.NO_PRIVILEGE);
        } else {
             addressDao.delete(origin);
        }
    }
}
