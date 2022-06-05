package com.pearl.util;

public class MathUtil {
    public static boolean betweenInclusive(int val, int low, int high) {
        return (val >= low) && (val <= high);
    }
}
