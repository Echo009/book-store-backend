package cn.lan.bookstore.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

/**
 * Author : Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 03/16/2018 02:32 PM
 * @author Ech0
 */
@Data
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class UserBaseInfoDTO {

    private Long userId;

    private String userName;

    private String headImg ;

    private String email;

    private Long phone;

    private String password;

    private Integer roleCode;

}
