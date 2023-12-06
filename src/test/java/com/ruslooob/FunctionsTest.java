package com.ruslooob;

import org.junit.jupiter.api.Test;

import static com.ruslooob.real_number.common.Functions.bransinsFunction;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FunctionsTest {

    @Test
    void bransinsFunctionFirstMinimumTest() {
        double result = bransinsFunction(-Math.PI, 12.275);
        assertEquals(0.397887, result, 0.000_001);
    }

    @Test
    void bransinsFunctionSecondMinimumTest() {
        double result = bransinsFunction(Math.PI, 2.275);
        assertEquals(0.397887, result, 0.000_001);
    }
}