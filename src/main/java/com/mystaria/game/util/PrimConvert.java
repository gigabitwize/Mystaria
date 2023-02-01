package com.mystaria.game.util;

/**
 * Created by Giovanni on 2/1/2023
 */
public class PrimConvert {

    public static int intFrom(Object o) {
        if (o instanceof Double) {
            return ((Double) o).intValue();
        }
        if (o instanceof Float) {
            return Math.round((Float) o);
        }
        return -1;
    }
}
