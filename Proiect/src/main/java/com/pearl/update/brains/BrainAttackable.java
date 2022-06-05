package com.pearl.update.brains;

import com.pearl.records.EntityData;

public interface BrainAttackable {

    default void takeAttack(EntityData attacker, EntityData body) {

    }

}
