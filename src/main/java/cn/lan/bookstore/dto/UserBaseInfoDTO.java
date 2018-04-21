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

    private String headImg = "p7f7rr200.bkt.clouddn.com/b6463e9a-e8e2-44a6-909b-332887e5d995";

    private Long phone;

    private String password;

    private Integer roleCode;

}
