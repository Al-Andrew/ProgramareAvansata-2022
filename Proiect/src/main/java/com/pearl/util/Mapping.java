package com.pearl.util;

import com.raylib.Raylib;

public class Mapping {
    public static Raylib.Vector2 mapToWorld(int x, int y) {
        return new Raylib.Vector2().x(x*24).y(y*24);
    }
}
