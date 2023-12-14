package com.ruslooob.common;

public record DoubleInterval(double start, double end) {
    public boolean contains(double value) {
        return value >= start && value <= end;
    }
}
