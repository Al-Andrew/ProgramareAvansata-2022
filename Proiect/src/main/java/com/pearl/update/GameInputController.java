package com.pearl.update;


import com.pearl.records.GameData;
import com.raylib.Jaylib;
import com.raylib.Raylib;

import static com.raylib.Raylib.*;

public class GameInputController {
    private final GameData data;

    public GameInputController(GameData data) {
        this.data = data;
    }

    public boolean consumeGameInput(InputData input) {
        Raylib.Vector2 delta = new Jaylib.Vector2();
        delta.x((IsKeyPressed(KEY_D)?1:0) - (IsKeyPressed(KEY_A)?1:0))
                .y((IsKeyPressed(KEY_S)?1:0) - (IsKeyPressed(KEY_W)?1:0));

        data.player.tilePosition = Jaylib.Vector2Add(data.player.tilePosition, delta);
        return false;
    }

}
