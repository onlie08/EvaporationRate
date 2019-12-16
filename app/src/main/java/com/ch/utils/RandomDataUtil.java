package com.ch.utils;

import java.text.DecimalFormat;
import java.util.Random;

public class RandomDataUtil {

    public static float nextDouble(final float min, final float max) throws Exception {

//保留两位小数
        DecimalFormat df = new DecimalFormat("#.00");
        if (max < min) {
            throw new Exception("min < max");
        }
        if (min == max) {
            return min;
        }
        return Float.parseFloat(df.format(min + ((max - min) * new Random().nextFloat())));
    }
}
