package cn.lan.bookstore.entity.buyer;

import cn.lan.bookstore.constant.Constant;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * Author : Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 04/16/2018 10:08 PM
 * @author Ech0
 */
@Entity(name = "address")
@Data
public class AddressEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long userId;

    private String name;

    private Long phone;

    private String country = Constant.DEFAULT_COUNTRY;

    private String province;

    private String city;

    private String area;

    private String detail;

    private Date creatTime;


}
