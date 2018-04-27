package cn.lan.bookstore.service.buyer;

import cn.lan.bookstore.entity.buyer.AddressEntity;

import java.util.List;

/**
 * @author: Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 04/27/2018 10:13 AM
 */
public interface IAddressService {

    List<AddressEntity> findAll(Long userId);

    AddressEntity update(AddressEntity addressEntity,Long userId);

    AddressEntity add(AddressEntity addressEntity, Long userId);

    void remove(Long id, Long userId);



}
