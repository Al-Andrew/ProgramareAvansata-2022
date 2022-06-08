package com.pearl.update.brains;

import com.pearl.records.EntityData;
import com.pearl.records.GameData;

public class DefenceUpBrain extends Brain implements BrainActionable{
    public DefenceUpBrain(EntityData body) {
        super(body);
    }

    @Override
    public void takeTurn(GameData data) {

    }

    @Override
    public void takeAction(EntityData actor, EntityData body, GameData data) {
        ++actor.inv.defence;
        body.stats.currentHealth = -1;
        GameData.addLogEntryGood("PLAYER defence increases.");
    }
}
