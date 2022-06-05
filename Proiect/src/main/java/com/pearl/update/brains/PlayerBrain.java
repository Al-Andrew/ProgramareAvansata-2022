package com.pearl.update.brains;

import com.pearl.records.EntityData;
import com.pearl.records.GameData;
import com.pearl.update.InputData;
import com.pearl.update.LogicalKeyboardKey;
import com.raylib.Jaylib;
import com.raylib.Raylib;

public class PlayerBrain extends Brain implements BrainMoving, BrainAttacking, BrainAttackable, BrainActioning {


    public PlayerBrain(EntityData body) {
        super(body);
    }

    @Override
    public void takeTurn(GameData data) {
        InputData idata = data.inputData;
        Raylib.Vector2 delta = new Jaylib.Vector2();
        delta.x(idata.getKeyJustPressedDelta(LogicalKeyboardKey.KEY_D, LogicalKeyboardKey.KEY_A));
        delta.y(idata.getKeyJustPressedDelta(LogicalKeyboardKey.KEY_S, LogicalKeyboardKey.KEY_W));

        if(moveBody(delta, data, body)) {
            return; //We moved. We're done
        }
        if(attack(Raylib.Vector2Add(body.tilePosition, delta), data, body)) {
            return; //We attacked. We're done
        }
        act(Raylib.Vector2Add(body.tilePosition, delta), data, body);
    }
}
