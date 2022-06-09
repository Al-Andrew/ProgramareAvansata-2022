package com.pearl.graphics;

import com.pearl.records.*;
import com.pearl.update.UpdateController;
import com.raylib.Jaylib;
import com.raylib.Raylib;

import java.io.IOException;

import static com.pearl.util.Mapping.mapToWorld;
import static com.raylib.Raylib.*;

public class Renderer {
    private GameData data;

    public Renderer(GameData data) {
        this.data = data;
        InitWindow(GameData.WINDOW_WIDTH, GameData.WINDOW_HEIGHT, GameData.WINDOW_TITLE);
        SetTargetFPS(60);
        data.texturePack.loadTextures();
        data.graphicsData.initialize();
    }

    public void drawDebugTexturePack() {
        TexturePack tp = data.texturePack;

        int cx = 0, cy = 0;
        for (var entry : tp.getTiles().entrySet()) {
                DrawTexture(entry.getValue(), cx, cy, Jaylib.WHITE);
            cx += GameData.TILE_SIZE.x();
            if (cx >= GameData.WINDOW_WIDTH) {
                cy += GameData.TILE_SIZE.y();
                cx = 0;
            }
        }
        cx = 0;
        cy = (GameData.WINDOW_HEIGHT / 2);
        for (var entry : tp.getEntities().entrySet()) {
            DrawTexture(entry.getValue(), cx, cy, Jaylib.WHITE);
            cx += GameData.TILE_SIZE.x();
            if (cx >= GameData.WINDOW_WIDTH) {
                cy += GameData.TILE_SIZE.y();
                cx = 0;
            }
        }
    }

    private void drawEntities() {
        for(EntityData ent : data.levelMaps[GameData.currentLevel].entityList) {
            if(!data.levelMaps[GameData.currentLevel].visibilityMap[ent.getTileY()][ent.getTileX()]) {
                continue;
            }
            Texture tx = data.texturePack.getEntities().get(ent.type);
            if(tx == null)
                continue;
            Vector2 ps =  mapToWorld(ent.tilePosition);

            DrawTextureV(tx, ps, Jaylib.WHITE);
        }
    }

    private void drawMap() {
        LevelData level = data.levelMaps[GameData.currentLevel];
        for (int y = 0; y < level.tileMap.length; ++y) {
            for (int x = 0; x < level.tileMap[y].length; ++x) {
                boolean memory = data.levelMaps[GameData.currentLevel].mapMemory[y][x];
                boolean visible = data.levelMaps[GameData.currentLevel].visibilityMap[y][x];

                if(!memory)
                    continue;

                var worldCoords = mapToWorld(x, y);
                Raylib.Texture tx = data.texturePack.getTiles().get(level.tileMap[y][x]);
                if (tx == null)
                    continue;

                if(!visible){
                    BeginShaderMode(data.graphicsData.grayscale);
                }
                DrawTextureV(tx, worldCoords, Jaylib.WHITE);
                if(!visible) {
                    EndShaderMode();
                }
            }
        }
    }

    private void drawUi() {
        final int borderSpacing = 5;
        final int borderWidth = 2;
        int logWidth  = 500;
        int logBaseOffX = GameData.GAME_WIDTH - logWidth;
        //Log back-panel
        Raylib.DrawRectangle(logBaseOffX, GameData.GAME_HEIGHT,
                logWidth, GameData.LOG_HEIGHT, data.texturePack.getUiColorBackground());
        //Log border
        Raylib.DrawRectangle(logBaseOffX + borderSpacing, GameData.GAME_HEIGHT + borderSpacing,
                borderWidth, GameData.LOG_HEIGHT, data.texturePack.getUiColorBorder());
        Raylib.DrawRectangle( logBaseOffX + logWidth - borderSpacing - borderWidth, GameData.GAME_HEIGHT + borderSpacing,
                borderWidth, GameData.LOG_HEIGHT, data.texturePack.getUiColorBorder());
        Raylib.DrawRectangle(logBaseOffX + borderSpacing, GameData.GAME_HEIGHT + borderSpacing,
                logWidth - 2 * borderSpacing - borderWidth, borderWidth, data.texturePack.getUiColorBorder());

        int logOffsetX = logBaseOffX + borderSpacing * 3 + borderWidth;
        int logOffsetY = GameData.GAME_HEIGHT + borderSpacing * 3 + borderWidth;

        final int logFontSize = 20;
        for(var entry : GameData.logBuffer) {
            LogEntry le = (LogEntry) entry;
            Raylib.DrawText(le.getMessage(), logOffsetX, logOffsetY, logFontSize, le.getColor());
            logOffsetY += logFontSize + borderSpacing;
        }




        int invetoryPannelY = GameData.WINDOW_HEIGHT - 200;
        //Inventory back-panel
        Raylib.DrawRectangle(GameData.GAME_WIDTH, invetoryPannelY,
                GameData.INVENTORY_WIDTH, GameData.WINDOW_HEIGHT, data.texturePack.getUiColorBackground());
        //Inventory border
        Raylib.DrawRectangle(GameData.GAME_WIDTH + borderSpacing, invetoryPannelY + borderSpacing,
                borderWidth, GameData.WINDOW_HEIGHT, data.texturePack.getUiColorBorder());
        Raylib.DrawRectangle(GameData.GAME_WIDTH + GameData.INVENTORY_WIDTH - 2 * borderSpacing, invetoryPannelY + borderSpacing,
                borderWidth, GameData.WINDOW_HEIGHT, data.texturePack.getUiColorBorder());
        Raylib.DrawRectangle(GameData.GAME_WIDTH + borderSpacing, invetoryPannelY + borderSpacing,
                GameData.INVENTORY_WIDTH - 2 * borderSpacing - 2 * borderWidth, borderWidth, data.texturePack.getUiColorBorder());

        int baseInvOffsetX = GameData.GAME_WIDTH + 4 * borderSpacing + borderWidth;
        int baseInvOffsetY = invetoryPannelY + 4 * borderSpacing + borderWidth;
        int invOffsetX = baseInvOffsetX;
        int invOffsetY = baseInvOffsetY;
        final int fontSize = 20;
        //Player avatar
        EntityData player = data.levelMaps[GameData.currentLevel].player;
        final Texture playerTx = data.texturePack.getEntities().get(player.type);
        Raylib.DrawTextureEx(playerTx, new Jaylib.Vector2(invOffsetX, invOffsetY), 0, 2.0f, Jaylib.WHITE);
        invOffsetX += playerTx.width() * 2 + borderSpacing;
        invOffsetY += playerTx.height() / 2.f * 2 - fontSize;
        final StatSheet statsAfterItems = player.getStatsAfterItems();
        String hpText = "HP(" + statsAfterItems.currentHealth + "/" + statsAfterItems.maxHealth + ")";
        Raylib.DrawText(hpText, invOffsetX, invOffsetY, fontSize, data.texturePack.getTxColorNormal());
        invOffsetY += fontSize + borderSpacing;
        int hpBarWidth = 150;
        int hpBarHeight = 13;
        int hpBarFilled = (int)((float) hpBarWidth * ((float) statsAfterItems.currentHealth / (float) statsAfterItems.maxHealth));
        Raylib.DrawRectangle(invOffsetX, invOffsetY, hpBarFilled, hpBarHeight, data.texturePack.getTxColorGood());
        Raylib.DrawRectangleLines(invOffsetX, invOffsetY, hpBarWidth, hpBarHeight, data.texturePack.getUiColorBorder());
        invOffsetX = baseInvOffsetX;
        invOffsetY = (int) (baseInvOffsetY + playerTx.height() * 2.f + borderSpacing * 2);

        String strString = "STR : " + statsAfterItems.strength;
        String defString = "DEF : " + statsAfterItems.defence;
        Raylib.DrawText(strString, invOffsetX, invOffsetY, fontSize, data.texturePack.getTxColorNormal());
        invOffsetY += fontSize + borderSpacing * 2;
        Raylib.DrawText(defString, invOffsetX, invOffsetY, fontSize, data.texturePack.getTxColorNormal());
    }

    private void drawAll() {
        EntityData player = data.levelMaps[GameData.currentLevel]
                .entityList.stream()
                .filter((e) -> e.type == Entity.PLAYER)
                .findFirst()
                .orElse(null);

        BeginDrawing();
        ClearBackground(data.texturePack.getBgColor());

        BeginMode2D(data.graphicsData.cam);
        //drawDebugTexturePack();
        drawMap();
        drawEntities(); //NOTE(Al-Andrew): The entities should be drawn on the top-most level
        EndMode2D();

        drawUi();
        if(GameData.gameOver != GameOver.NONE)
            drawGameOverScreen();

        EndDrawing();
    }

    private void drawGameOverScreen() {
        Raylib.DrawRectangle(0,0,
                GameData.WINDOW_WIDTH, GameData.WINDOW_HEIGHT, new Jaylib.Color(14, 17, 23, 127));
        int w = 300;
        int h = 200;
        int offX = GameData.WINDOW_WIDTH / 2 - w / 2;
        int offy = GameData.WINDOW_HEIGHT / 2 - h / 2;
        Raylib.DrawRectangle(offX, offy, w ,h , GameData.texturePack.getUiColorBackground());
        String text;
        Raylib.Color color;
        if(GameData.gameOver == GameOver.LOSE) {
            text = "You LOST";
            color = GameData.texturePack.getTxColorBad();
        } else {
            text = "You WON!";
            color = GameData.texturePack.getTxColorGood();
        }

        h = 30;
        w = Raylib.MeasureText(text, h);
        offX = GameData.WINDOW_WIDTH / 2  - w / 2;
        offy = GameData.WINDOW_HEIGHT / 2  - h;
        Raylib.DrawText(text, offX, offy, h, color);

        w = 80;
        offX = GameData.WINDOW_WIDTH / 2 - 10 - w;
        offy = GameData.WINDOW_HEIGHT / 2 + 10;

        if(Jaylib.GuiButton(new Jaylib.Rectangle(offX, offy, w, h), "Play again?")) {
            GameData.reset();
        }
        offX = GameData.WINDOW_WIDTH / 2 + 10;
        if(Jaylib.GuiButton(new Jaylib.Rectangle(offX, offy, w, h), "Exit")) {
            GameData.isRunning = false;
        }



    }

    public void run() {
        while (GameData.isRunning) {
            drawAll();
        }
    }

    public void close() {
        CloseWindow();
    }
}
