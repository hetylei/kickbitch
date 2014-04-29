package com.winnie.pub.utils;

import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA.
 * User: chenlei
 * Date: 11-11-9
 * Time: ÉÏÎç1:05
 * To change this template use File | Settings | File Templates.
 */
public class Nums {
    public static Double toDouble(Object v) {
        try {
            return Double.parseDouble(v.toString());
        } catch (Exception ex) {
            return 0.0;
        }
    }

    public static BigDecimal toBD(Object v) {
        try {
            return new BigDecimal(v.toString());
        } catch (Exception ex) {
            return BigDecimal.ZERO;
        }
    }

    public static int toInt(Object v) {
        try {
            return new BigDecimal(v.toString()).intValue();
        } catch (Exception ex) {
            return 0;
        }
    }
}
