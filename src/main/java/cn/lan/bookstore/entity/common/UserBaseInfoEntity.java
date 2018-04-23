package cn.lan.bookstore.entity.common;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Author : Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 03/16/2018 02:17 PM
 *
 *  用户基础信息
 * @author Ech0
 */
@Entity(name = "user_base_info")
@Data
public class UserBaseInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String userName;

    private String headImg= "http://p7f7rr200.bkt.clouddn.com/b6463e9a-e8e2-44a6-909b-332887e5d995";

    private String email;

    private Long phone;

    private String password;

    private Integer roleCode ;

}
