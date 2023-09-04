package com.example.demo.util;

public class EnumUtil {
    public static <T extends Enum<T>> boolean equals(T a, T b) {
        return a.equals(b);
    }
}