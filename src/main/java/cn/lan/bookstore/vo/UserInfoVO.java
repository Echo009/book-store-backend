package cn.lan.bookstore.vo;

import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * Author : Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 03/20/2018 12:35 PM
 */
@Data
public class UserInfoVO {

    private String userName;

    private String email;

    private Long phone;

    private String password;

    public boolean check() {
        if (StringUtils.isEmpty(userName) ||
                StringUtils.isEmpty(email) ||
                phone == null ||
                StringUtils.isEmpty(password)) {
            return false;
        }
        return true;
    }
}
