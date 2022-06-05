package com.pearl.update;

import static com.raylib.Raylib.IsKeyPressed;

public class InputCollector {


    static public void collectInput(InputData storage) {
        storage.inputChanged = false;
        for(LogicalKeyboardKey key : LogicalKeyboardKey.values()) {
            boolean value = IsKeyPressed(key.getcKeyValue());
            storage.keyJustPressedMap.put(key, value);
            if (value)
                storage.inputChanged = true;
        }
    }
}
