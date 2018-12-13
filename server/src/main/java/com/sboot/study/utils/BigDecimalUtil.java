package com.sboot.study.utils;

import java.math.BigDecimal;

/**
 * create by faraway on 2018/12/13
 * description:
 */
public class BigDecimalUtil {

    /**
     * 相加
     *
     * @param b1
     * @param b2
     * @return
     */
    public static BigDecimal add(BigDecimal b1, BigDecimal b2) {
        return b1.add(b2);
    }

    /**
     * 相减
     *
     * @param b1
     * @param b2
     * @return
     */
    public static BigDecimal subtract(BigDecimal b1, BigDecimal b2) {
        return b1.subtract(b2);
    }

    /**
     * 相乘
     *
     * @param b1
     * @param b2
     * @return
     */
    public static BigDecimal multiply(BigDecimal b1, BigDecimal b2) {
        return b1.multiply(b2);
    }

    /**
     * 相除 能整除返回商，不能整除保留两位小数
     *
     * @param b1
     * @param b2
     * @return
     */
    public static BigDecimal divide(BigDecimal b1, BigDecimal b2) {
        //求商和余数 bigDecimals[0]是商，bigDecimals[1]是余数
        BigDecimal[] bigDecimals = b1.divideAndRemainder(b2);
        //如果能被整除，直接返回
        if (bigDecimals[1] == BigDecimal.valueOf(0)) {
            return b1.divide(b2).stripTrailingZeros();
        } else {
            //保留两位小数,四舍五入
            return b1.divide(b2, 2, BigDecimal.ROUND_HALF_UP);
        }
    }

    /**
     * 相除 返回商和余数的数组 [0]是商，[1]是余数
     *
     * @param b1
     * @param b2
     * @return
     */
    public static BigDecimal[] divideAndRemainder(BigDecimal b1, BigDecimal b2) {
        //返回商和余数 bigDecimals[0]是商，bigDecimals[1]是余数
        return b1.divideAndRemainder(b2);
    }


    /**
     * setScale()方法用于格式化小数点
     * setScale(1, BigDecimal.ROUND_DOWN);       //直接删除多余的小数位，如2 .369 会变成2 .3
     * setScale(1, BigDecimal.ROUND_UP);         //进位处理，如2 .3*，都会变成2 .4
     * setScale(1, BigDecimal.ROUND_HALF_UP);    //四舍五入，如2.45，会变成2.5
     * setScale(1, BigDecimal.ROUND_HALF_DOWN);  //五舍六入，如2.45，会变成2.4
     */

//    public static void main(String[] args) {
//        BigDecimal b1 = new BigDecimal(9);
//        BigDecimal b2 = new BigDecimal(3);
//        System.out.println(add(b1, b2));
//        System.out.println(subtract(b1, b2));
//        System.out.println(multiply(b1, b2));
//        System.out.println(divide(b1, b2));
//        System.out.println(divide(b2, b1));
//        System.out.println(divideAndRemainder(b1, b2)[0] + "..." + divideAndRemainder(b1, b2)[1]);
//    }
}
