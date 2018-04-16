package cn.lan.bookstore.entity.buyer;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Author : Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 04/16/2018 10:08 PM
 */
@Entity(name = "address")
@Data
public class AddressEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long user_id;

    private String name;

    private Long phone;

    private String country;

    private String province;

    private String city;

    private String area;

    private String detail;

}
