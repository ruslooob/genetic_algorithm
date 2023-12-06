package com.ruslooob.real_number.common;

public class Functions {

    public static double bransinsFunction(double x, double y) {
        return Math.pow(y - 5.1 / (4 * Math.pow(Math.PI, 2)) * Math.pow(x, 2) + (5 / Math.PI) * x - 6, 2) + 10 * (1 - 1 / (8 * Math.PI)) * Math.cos(x) + 10;
    }
}
