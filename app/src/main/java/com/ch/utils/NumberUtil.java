package com.ch.utils;

import java.math.BigDecimal;

public class NumberUtil {

    public static float stayDigit(float value,int place)
    {
        /*
        BigDecimal b = new BigDecimal(value);
        value = b.setScale(place, BigDecimal.ROUND_HALF_UP).floatValue();
*/
        String str = String.format("%."+place+"f", value);
        System.out.println(str);//30.67

        return Float.parseFloat(str);
    }

}
