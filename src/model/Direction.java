package model;

import java.util.HashMap;
import java.util.Map;

public enum Direction {

    UP(0, -1), RIGHT(1, 0), DOWN(0, 1), LEFT(-1, 0);

    private final int y;
    private final int x;
    private static Direction[][] directions;

    Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    static {
        directions = new Direction[4][4];
        for (Direction direction : Direction.values()) {
            int x = direction.getX();
            int y = direction.getY();
            if(direction.getX() < 0) {
                x = 3 + x;
            }
            if(direction.getY() < 0) {
                y = 3 + y;
            }


            directions[x][y] = direction;
        }
    }

    public static Direction valueOf(int x, int y) {

        if(x < 0) {
            x = 3 + x;
        }
        if(y < 0) {
            y = 3 + x;
        }

        return (Direction) directions[x][y];
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
