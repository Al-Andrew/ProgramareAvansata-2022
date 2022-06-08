package com.pearl.records;

public class StatSheet {
    public int currentHealth;
    public int maxHealth;
    public int strength;
    public int defence;

    public static final StatSheet INDESTRUCTIBLE = new StatSheet(Integer.MAX_VALUE, Integer.MAX_VALUE, 0, 0);

    StatSheet(StatSheet other) {
        this.currentHealth = other.currentHealth;
        this.maxHealth= other.maxHealth;
        this.strength = other.strength;
        this.defence = other.defence;
    }

    StatSheet(int cH, int mH,int str, int def) {
        this.currentHealth = cH;
        this.maxHealth = mH;
        this.strength = str;
        this.defence = def;
    }
}
