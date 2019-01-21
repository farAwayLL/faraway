package com.sboot.study.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * create by faraway on 2018/12/13
 * description: BigDecimal常用的加减乘除
 */
public class BigDecimalUtil {

    /**
     * 相加
     */
    public static BigDecimal add(BigDecimal b1, BigDecimal b2) {
        return b1.add(b2);
    }

    /**
     * 相减
     */
    public static BigDecimal subtract(BigDecimal b1, BigDecimal b2) {
        return b1.subtract(b2);
    }

    /**
     * 相乘
     */
    public static BigDecimal multiply(BigDecimal b1, BigDecimal b2) {
        return b1.multiply(b2);
    }

    /**
     * 相除 能整除直接返回，不能整除四舍五入保留两位小数
     */
    public static BigDecimal divide(BigDecimal b1, BigDecimal b2) {
        BigDecimal bigDecimal = remainder(b1, b2);
        //余数为0，说明能被整除，直接返回
        if (bigDecimal == BigDecimal.valueOf(0)) {
            return b1.divide(b2);
        } else {
            //如果不能被整除     保留两位小数           并四舍五入
            return b1.divide(b2, 2, BigDecimal.ROUND_HALF_UP);
        }
    }

    /**
     * 相除 返回余数
     */
    public static BigDecimal remainder(BigDecimal b1, BigDecimal b2) {
        return b1.remainder(b2);
    }

    /**
     * 相除 返回商和余数的数组 [0]是商，[1]是余数
     */
    public static BigDecimal[] divideAndRemainder(BigDecimal b1, BigDecimal b2) {
        return b1.divideAndRemainder(b2);
    }

    /**
     * 比较大小 返回值为-1 0 1   -1表示b1比b2小，0表示相等，1表示b1比b2大
     */
    public static int compareTo(BigDecimal b1, BigDecimal b2) {
        return b1.compareTo(b2);
    }

    /**
     * 求绝对值
     */
    public static BigDecimal abs(BigDecimal b) {
        return b.abs();
    }

    /**
     * 求相反数
     */
    public static BigDecimal negate(BigDecimal b) {
        return b.negate();
    }

    /**
     * 保留四位有效小数位 例：10.1000 -->10.1; 0.0030 -->0.003; 10.0000 -->10
     */
    public static BigDecimal format(BigDecimal b) {
        //return b.stripTrailingZeros();
        //或者(上面可能会不好使)
        DecimalFormat decimalFormat = new DecimalFormat("############.####");
        return new BigDecimal(decimalFormat.format(b));
    }

    /**
     * bigDecimal.setScale()方法用于格式化小数点
     * BigDecimal b = setScale(1, BigDecimal.ROUND_DOWN);       //直接删除多余的小数位，如2 .369 会变成2 .3
     * BigDecimal b = setScale(1, BigDecimal.ROUND_UP);         //进位处理，如2 .3*，都会变成2 .4
     * BigDecimal b = setScale(1, BigDecimal.ROUND_HALF_UP);    //四舍五入，如2.45，会变成2.5
     * BigDecimal b = setScale(1, BigDecimal.ROUND_HALF_DOWN);  //五舍六入，如2.45，会变成2.4
     */

    /**
     * BigDecimal b = bigDecimal.stripTrailingZeros()方法用于去除小数点末尾的0，保留有效小数位
     * <p>
     * String str = bigDecimal.toString()转成字符串，但不识别科学记数法     1E4---->1E4
     * String str = bigDecimal.toPlainString()识别科学计数法并转成字符串   1E4---->10000
     */

}
