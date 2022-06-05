package com.pearl.update.brains;

import com.pearl.records.EntityData;
import com.pearl.records.GameData;
import com.raylib.Jaylib;
import com.raylib.Raylib;

import java.util.Random;

public class RandomMoveAttackBrain extends Brain implements BrainAttacking, BrainAttackable, BrainMoving {

    public RandomMoveAttackBrain(EntityData body) {
        super(body);
    }

    @Override
    public void takeTurn(GameData data) {
        Random prng = new Random();
        Raylib.Vector2 delta = new Jaylib.Vector2(prng.nextInt(-1, 2), prng.nextInt(-1, 2));

        if(moveBody(delta, data, body)) {
            return; //We moved. We're done
        }
        if(attack(Raylib.Vector2Add(body.tilePosition, delta), data, body)) {
            return; //We attacked. We're done
        }

    }
}
