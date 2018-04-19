package cn.lan.bookstore.util;

/**
 * Author : Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 10/04/2017 10:10 AM
 */

import java.util.Random;

/**
 * 生成随机字符串，用于订单id，和订单详情的id
 * @author Ech0
 */
public class KeyUtil {
    /**
     * 生成主键
     * 当前毫秒数+6位随机数
     */
    public static synchronized String   genUniqueKey(){
        Random random = new Random();
        Integer temp = random.nextInt(900_000)+100_000;
        return String.valueOf(System.currentTimeMillis())+temp;
    }
}
