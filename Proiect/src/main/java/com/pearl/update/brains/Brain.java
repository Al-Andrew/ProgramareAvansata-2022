package com.pearl.update.brains;

import com.pearl.records.EntityData;
import com.pearl.records.GameData;

public abstract class Brain {
    EntityData body;


    public Brain(EntityData body) {
        this.body = body;
    }

    public abstract void takeTurn(GameData data);
}
