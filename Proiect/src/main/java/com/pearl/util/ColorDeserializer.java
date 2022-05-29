package com.pearl.util;

import com.raylib.Raylib;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public class ColorDeserializer extends JsonDeserializer<Raylib.Color> {

    @Override
    public Raylib.Color deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.readValueAsTree();
        byte r = node.get("r").getNumberValue().byteValue();
        byte g = node.get("g").getNumberValue().byteValue();
        byte b = node.get("b").getNumberValue().byteValue();
        byte a = node.get("a").getNumberValue().byteValue();

        return new Raylib.Color().r(r).g(g).b(b).a(a);
    }
}
