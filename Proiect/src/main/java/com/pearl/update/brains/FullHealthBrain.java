package com.pearl.update.brains;

import com.pearl.records.EntityData;
import com.pearl.records.GameData;

public class FullHealthBrain extends Brain implements BrainActionable{
    public FullHealthBrain(EntityData body) {
        super(body);
    }

    @Override
    public void takeTurn(GameData data) {

    }

    @Override
    public void takeAction(EntityData actor, EntityData body, GameData data) {
        body.stats.currentHealth = -1;
        actor.stats.currentHealth = actor.getStatsAfterItems().maxHealth;
        GameData.addLogEntryGood("PLAYER is now full health.");
    }
}
