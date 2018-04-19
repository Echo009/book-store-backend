package cn.lan.bookstore.util;

/**
 * Author : Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 10/07/2017 06:08 PM
 * @author Ech0
 */
public class MathUtil {
    private static final Double MONEY_RANGE = 0.01;

    /**
     * 判断两个金额是否相等 ， 精度为一分钱（基本单位为元）
     * @param d1
     * @param d2
     * @return
     */
    public static Boolean equals(Double d1 , Double d2){
        Double result = Math.abs(d1-d2);
        if (result<MONEY_RANGE){
            return true;
        }
        else {
            return false;
        }
    }
}
