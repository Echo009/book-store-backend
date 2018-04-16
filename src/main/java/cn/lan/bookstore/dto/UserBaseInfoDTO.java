package cn.lan.bookstore.dto;

import lombok.Data;

/**
 * Author : Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 03/16/2018 02:32 PM
 */
@Data
public class UserBaseInfoDTO {


    private String userName;

    private Long phone;

    private String password;

    private Integer role_code;

}
