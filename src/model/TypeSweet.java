package model;

import java.util.HashMap;
import java.util.Map;

public enum TypeSweet {

    NORMAL(0), ROW(1), COL(2);

    private int value;
    private static Map map = new HashMap<>();

    private TypeSweet(int value) {
        this.value = value;
    }

    static {
        for (TypeSweet typeSweet : TypeSweet.values()) {
            map.put(typeSweet.value, typeSweet);
        }
    }

    public static TypeSweet valueOf(int type) {
        return (TypeSweet) map.get(type);
    }

    public int getValue() {
        return value;
    }
}
