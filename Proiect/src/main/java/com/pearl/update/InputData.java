package com.pearl.update;


import java.util.HashMap;
import java.util.Map;

public class InputData {
    public Map<LogicalKeyboardKey, Boolean> keyJustPressedMap = new HashMap<>();
    public float mouseWheelDelta;
    public boolean inputChanged = false;

    public boolean isKeyJustPressed(LogicalKeyboardKey key) {
        return keyJustPressedMap.get(key);
    }

    public int getKeyJustPressedDelta(LogicalKeyboardKey key1, LogicalKeyboardKey key2) {
        return (isKeyJustPressed(key1)?1:0) - (isKeyJustPressed(key2)?1:0);
    }
}
