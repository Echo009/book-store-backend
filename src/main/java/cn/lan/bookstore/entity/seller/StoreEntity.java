package cn.lan.bookstore.entity.seller;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * Author : Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 04/16/2018 04:05 PM
 */
@Entity(name = "store")
@Data
public class StoreEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    private Long userId ;

    private String storeName;

    private Date registDate;

    private Long registerPhone;

    private String bankNum ;

}
