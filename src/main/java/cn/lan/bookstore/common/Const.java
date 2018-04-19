package cn.lan.bookstore.common;

import java.util.UUID;

/**
 * Author : Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 03/20/2018 01:29 PM
 */
public interface Const {

    String LOGIN_STATUS = "login_status";
    String CURRENT_USER = "current_user";

    public static String getUUID(){
        return String.valueOf(UUID.randomUUID());
    }
}
