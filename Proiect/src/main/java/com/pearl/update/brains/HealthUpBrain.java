package com.pearl.update.brains;

import com.pearl.records.EntityData;
import com.pearl.records.GameData;

public class HealthUpBrain extends Brain implements BrainActionable {
    public HealthUpBrain(EntityData body) {
        super(body);
    }

    @Override
    public void takeTurn(GameData data) {

    }

    @Override
    public void takeAction(EntityData actor, EntityData body, GameData data) {
        body.stats.currentHealth = -1;
        ++actor.inv.health;
        GameData.addLogEntryGood("PLAYER max health increases.");
    }
}
