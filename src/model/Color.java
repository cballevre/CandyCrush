package model;

import java.util.HashMap;
import java.util.Map;

public enum Color {

    RED(0), ORANGE(1), YELLOW(2), GREEN(3), BLUE(4), PURPLE(5);

    private int value;
    private static Map map = new HashMap<>();

    private Color(int value) {
        this.value = value;
    }

    static {
        for (Color color : Color.values()) {
            map.put(color.value, color);
        }
    }

    public static Color valueOf(int color) {
        return (Color) map.get(color);
    }

    public int getValue() {
        return value;
    }

}
