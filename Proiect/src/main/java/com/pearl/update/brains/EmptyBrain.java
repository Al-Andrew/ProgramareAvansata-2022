package com.pearl.update.brains;

import com.pearl.records.EntityData;
import com.pearl.records.GameData;

public class EmptyBrain extends Brain{


    public EmptyBrain(EntityData body) {
        super(body);
    }

    @Override
    public void takeTurn(GameData data) {
        //Nothing, the brain is empty
    }


}
