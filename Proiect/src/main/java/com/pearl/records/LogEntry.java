package com.pearl.records;

import com.raylib.Raylib;

public class LogEntry {
    String message;
    Raylib.Color color;

    public LogEntry(String msg, EntryType type) {
        this.message = msg;
        switch (type) {

            case NORMAL -> {
                color = GameData.texturePack.getTxColorNormal();
                break;
            }
            case GOOD -> {
                color = GameData.texturePack.getTxColorGood();
                break;
            }
            case BAD -> {
                color = GameData.texturePack.getTxColorBad();
            }
        }
    }
    public String getMessage() {
        return message;
    }

    public Raylib.Color getColor() {
        return color;
    }
}
