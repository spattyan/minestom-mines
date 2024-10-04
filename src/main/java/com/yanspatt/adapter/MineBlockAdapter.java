package com.yanspatt.adapter;

import com.google.gson.*;
import com.yanspatt.model.mine.packetMine.MinedBlock;

import java.lang.reflect.Type;

public class MineBlockAdapter implements JsonSerializer<MinedBlock>, JsonDeserializer<MinedBlock> {


    @Override
    public JsonElement serialize(MinedBlock minedBlock, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject obj = new JsonObject();

        obj.addProperty("value", minedBlock.asEncoded());

        return obj;
    }

    @Override
    public MinedBlock deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return MinedBlock.fromEncoded(jsonElement.getAsString());
    }
}
