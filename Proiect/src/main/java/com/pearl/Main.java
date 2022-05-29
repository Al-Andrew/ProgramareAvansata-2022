package com.pearl;

import com.pearl.graphics.Renderer;
import com.pearl.records.GameData;
import com.pearl.update.UpdateController;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
        GameData data = new GameData();
        Renderer renderer = new Renderer(data);
        UpdateController update = new UpdateController(data);

        //var tp = TexturePack.defaultEmpty();
        //tp.saveToFile("assets/test.json");

        Thread updateThread = new Thread(update);
        updateThread.start();

        renderer.run();
        renderer.close();
        updateThread.join();

    }
}
