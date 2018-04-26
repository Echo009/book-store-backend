package cn.lan.bookstore.vo;

import cn.lan.bookstore.entity.buyer.CartEntity;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.util.List;

/**
 * @author: Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 04/26/2018 09:27 PM
 */
@Data
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class CartWrapper {

    private Long storeId;

    private String storeName;

    private List<CartEntity> cartEntityList;
}
