package com.pearl.records;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pearl.util.ColorDeserializer;
import com.pearl.util.ColorSerializer;
import com.raylib.Jaylib;
import com.raylib.Raylib;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TexturePack {
    @JsonProperty("tilePaths")
    private Map<Tile, String> tilePaths;
    @JsonIgnore
    private Map<Tile, Raylib.Texture> tiles;
    @JsonProperty("entityPaths")
    private Map<Entity, String> entityPaths;
    @JsonIgnore
    private Map<Entity, Raylib.Texture> entities;
    @JsonDeserialize(using = ColorDeserializer.class)
    @JsonSerialize(using = ColorSerializer.class)
    @JsonProperty("backgroundColor")
    private Raylib.Color bgColor;
    @JsonDeserialize(using = ColorDeserializer.class)
    @JsonSerialize(using = ColorSerializer.class)
    @JsonProperty("textColorNormal")
    private Raylib.Color txColorNormal;
    @JsonDeserialize(using = ColorDeserializer.class)
    @JsonSerialize(using = ColorSerializer.class)
    @JsonProperty("textColorBad")
    private Raylib.Color txColorBad;
    @JsonDeserialize(using = ColorDeserializer.class)
    @JsonSerialize(using = ColorSerializer.class)
    @JsonProperty("textColorGood")
    private Raylib.Color txColorGood;
    @JsonDeserialize(using = ColorDeserializer.class)
    @JsonSerialize(using = ColorSerializer.class)
    @JsonProperty("uiColorBackground")
    private Raylib.Color uiColorBackground;
    @JsonDeserialize(using = ColorDeserializer.class)
    @JsonSerialize(using = ColorSerializer.class)
    @JsonProperty("uiColorBorder")
    private Raylib.Color uiColorBorder;

    @JsonIgnore
    private final Raylib.Vector2 tileSize = new Jaylib.Vector2(24, 24);

    public void loadTextures() {
        tiles = new HashMap<>();
        for(var entry : tilePaths.entrySet()) {
            Raylib.Image source;
            if(entry.getValue().isBlank()) {
                source = Raylib.GenImageChecked((int) tileSize.x(), (int) tileSize.y(),
                        (int) (tileSize.x()/2), (int) (tileSize.y()/2),
                        Jaylib.BLUE, Jaylib.MAGENTA);
            }
            else {
                source = Raylib.LoadImage(entry.getValue());
            }
            tiles.put(entry.getKey(), Raylib.LoadTextureFromImage(source));
        }
        entities = new HashMap<>();
        for(var entry : entityPaths.entrySet()) {
            Raylib.Image source;
            if(entry.getValue().isBlank()) {
                source = Raylib.GenImageChecked((int) tileSize.x(), (int) tileSize.y(),
                        (int) (tileSize.x()/2), (int) (tileSize.y()/2),
                        Jaylib.MAGENTA, Jaylib.GREEN);
            }
            else {
                source = Raylib.LoadImage(entry.getValue());
            }
            entities.put(entry.getKey(), Raylib.LoadTextureFromImage(source));
        }
    }

    static public TexturePack loadFromFile(String jsonPath) throws IOException {
        FileReader reader = new FileReader(jsonPath);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(reader, TexturePack.class);
    }

    static public TexturePack defaultEmpty() {
        TexturePack ret = new TexturePack();
        ret.tilePaths = new HashMap<>();
        for(var tile : Tile.values()) {
            ret.tilePaths.put(tile, "assets/textures/");
        }
        ret.entityPaths = new HashMap<>();
        for(var entt : Entity.values()) {
            ret.entityPaths.put(entt, "assets/textures/");
        }
        ret.bgColor = new Raylib.Color().r((byte) 120).g((byte) 120).b((byte) 120).a((byte) 255);
        ret.txColorNormal = new Raylib.Color().r((byte) 240).g((byte) 240).b((byte) 240).a((byte) 255);
        ret.txColorGood = Jaylib.GREEN;
        ret.txColorBad = Jaylib.RED;
        ret.uiColorBackground = new Raylib.Color().r((byte) 25).g((byte) 75).b((byte) 115).a((byte) 255);
        ret.uiColorBorder = new Raylib.Color().r((byte) 55).g((byte) 175).b((byte) 150).a((byte) 255);

        return ret;
    }

    public void saveToFile(String jsonPath) throws IOException {
        FileWriter writer = new FileWriter(jsonPath);
        ObjectMapper mapper = new ObjectMapper();

        mapper.writerWithDefaultPrettyPrinter().writeValue(writer, this);
    }

    @Override
    public String toString() {
        return "TexturePack{" +
                "tilePaths=" + tilePaths +
                ", tiles=" + tiles +
                ", entityPaths=" + entityPaths +
                ", entities=" + entities +
                ", bgColor=" + bgColor +
                ", txColorNormal=" + txColorNormal +
                ", txColorBad=" + txColorBad +
                ", txColorGood=" + txColorGood +
                ", uiColorBackground=" + uiColorBackground +
                ", uiColorBorder=" + uiColorBorder +
                '}';
    }

    public Map<Tile, String> getTilePaths() {
        return tilePaths;
    }

    public Map<Tile, Raylib.Texture> getTiles() {
        return tiles;
    }

    public Map<Entity, String> getEntityPaths() {
        return entityPaths;
    }

    public Map<Entity, Raylib.Texture> getEntities() {
        return entities;
    }

    public Raylib.Color getBgColor() {
        return bgColor;
    }

    public Raylib.Color getTxColorNormal() {
        return txColorNormal;
    }

    public Raylib.Color getTxColorBad() {
        return txColorBad;
    }

    public Raylib.Color getTxColorGood() {
        return txColorGood;
    }

    public Raylib.Color getUiColorBackground() {
        return uiColorBackground;
    }

    public Raylib.Color getUiColorBorder() {
        return uiColorBorder;
    }

    public Raylib.Vector2 getTileSize() {
        return tileSize;
    }
}
