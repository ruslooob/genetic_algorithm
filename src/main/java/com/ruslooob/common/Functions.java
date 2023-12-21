package com.ruslooob.common;

public class Functions {

    private static final double CONST1 = 5.1 / (4 * Math.pow(Math.PI, 2));
    private static final double CONST2 = 5 / Math.PI;
    private static final double CONST3 = 1 - 1 / (8 * Math.PI);

    public static double bransinsFunction(double x, double y) {
        double xSquared = x * x;
        double firstTerm = y - CONST1 * xSquared + CONST2 * x - 6;
        return firstTerm * firstTerm + 10 * CONST3 * Math.cos(x) + 10;
    }

    public static double bransinsFunction2(double x, double y) {
        return Math.pow(y - 5.1 / (4 * Math.pow(Math.PI, 2)) * Math.pow(x, 2) + (5 / Math.PI) * x - 6, 2) + 10 * (1 - 1 / (8 * Math.PI)) * Math.cos(x) + 10;
    }
}
