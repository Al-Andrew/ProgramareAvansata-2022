package com.pearl.update.brains;

import com.pearl.records.EntityData;
import com.pearl.records.GameData;

public interface BrainActionable {

    void takeAction(EntityData actor, EntityData body, GameData data);
}
