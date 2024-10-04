package com.yanspatt.adapter;

import com.google.gson.*;
import com.yanspatt.model.mine.Mine;
import com.yanspatt.model.mine.packetMine.MinedBlock;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.instance.block.Block;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MineAdapter implements JsonSerializer<Mine>, JsonDeserializer<Mine> {

    @Override
    public JsonElement serialize(Mine mine, Type type, JsonSerializationContext jsonSerializationContext) {

        JsonObject obj = new JsonObject();
        if (mine == null) return null;
        if (mine.getPosition1() == null) return null;
        if (mine.getPosition2() == null) return null;

        obj.addProperty("block",mine.getBlock().stateId());
        obj.addProperty("pos1",mine.getPosition1().blockX() +","+mine.getPosition1().blockY() + ","+ mine.getPosition1().blockZ());
        obj.addProperty("pos2",mine.getPosition2().blockX() +","+mine.getPosition2().blockY() + ","+ mine.getPosition2().blockZ());
        obj.addProperty("depth",mine.getDepth());
        obj.addProperty("size",mine.getSize());

        StringBuilder builder = new StringBuilder();
        for (MinedBlock minedBlock : mine.getMinedBlocks()) {
            builder.append(minedBlock.asEncoded() + ";");
        }
        obj.addProperty("minedBlocks",builder.toString());
        return obj;
    }

    @Override
    public Mine deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject obj = jsonElement.getAsJsonObject();
        String pos1Json = obj.get("pos1").getAsString();
        String pos2Json = obj.get("pos2").getAsString();

        int depth = obj.get("depth").getAsInt();
        int block = obj.get("block").getAsInt();
        int size = obj.get("size").getAsInt();

        Mine mine = new Mine();

        String[] pos1 = pos1Json.split(",");
        String[] pos2 = pos2Json.split(",");

        String blocks = obj.get("minedBlocks").getAsString();
        String[] minedBlocks = blocks.split(";");
        List<MinedBlock> blockList = new ArrayList<>();

        for (String minedBlock : minedBlocks) {
            if (minedBlock == null || minedBlock.isEmpty()) continue;
            blockList.add(MinedBlock.fromEncoded(minedBlock));
        }

        mine.setPosition1(new Pos(Double.parseDouble(pos1[0]),Double.parseDouble(pos1[1]),Double.parseDouble(pos1[2])));
        mine.setPosition2(new Pos(Double.parseDouble(pos2[0]),Double.parseDouble(pos2[1]),Double.parseDouble(pos2[2])));
        mine.setDepth(depth);
        mine.setSize(size);
        mine.setBlock(Block.fromStateId(block));
        mine.setMinedBlocks(blockList);
        return mine;
    }

}
