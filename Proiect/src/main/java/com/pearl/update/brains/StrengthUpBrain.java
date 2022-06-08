package com.pearl.update.brains;

import com.pearl.records.EntityData;
import com.pearl.records.GameData;

public class StrengthUpBrain extends Brain implements BrainActionable{
    public StrengthUpBrain(EntityData body) {
        super(body);
    }

    @Override
    public void takeTurn(GameData data) {

    }

    @Override
    public void takeAction(EntityData actor, EntityData body, GameData data) {
        ++actor.inv.strength;
        body.stats.currentHealth = -1;
        GameData.addLogEntryGood("PLAYER strength increases.");
    }
}
