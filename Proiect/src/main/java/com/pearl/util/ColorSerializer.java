package com.pearl.util;

import com.raylib.Raylib;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

public class ColorSerializer extends JsonSerializer<Raylib.Color> {
    @Override
    public void serialize(Raylib.Color color, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("r", color.r());
        jsonGenerator.writeNumberField("g", color.g());
        jsonGenerator.writeNumberField("b", color.b());
        jsonGenerator.writeNumberField("a", color.a());
        jsonGenerator.writeEndObject();
    }
}
