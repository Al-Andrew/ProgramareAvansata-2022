package com.pearl.records;

import com.pearl.update.brains.Brain;
import com.raylib.Jaylib;
import com.raylib.Raylib;

import java.lang.reflect.InvocationTargetException;

public class EntityData {
    public Entity type;
    public Raylib.Vector2 tilePosition;
    public Brain brain;
    public StatSheet stats;
    public Inventory inv;


    private EntityData(Entity type, int tileX, int tileY) {
        this.type = type;
        this.tilePosition = new Jaylib.Vector2(tileX, tileY);
        this.stats = type.getBaseStats();
        this.inv = new Inventory();
    }

    public static EntityData withPosition(int x, int y, Entity type) {
        EntityData ret = new EntityData(type, x, y);
        try {
            var constructor = ret.type.getBrainClass().getConstructor(EntityData.class);
            ret.brain = (Brain) constructor.newInstance(ret);
        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return ret;
    }

    public StatSheet getStatsAfterItems() {
        StatSheet ret = new StatSheet(this.stats);
        ret.maxHealth += inv.health * 3;
        ret.strength += inv.strength * 2;
        ret.defence += inv.defence;
        return ret;
    }

    public String getAttackPhrase() {
        return this.type.attackPhrase;
    }

    public int getTileX(){
        return (int) this.tilePosition.x();
    }
    public int getTileY(){
        return (int) this.tilePosition.y();
    }
}
