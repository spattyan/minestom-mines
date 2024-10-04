package com.yanspatt.util;

import net.minestom.server.instance.palette.Palette;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaletteUtils {

    public static List<Map<String, Integer>> getBlocksInPalette(Palette palette) {
        List<Map<String, Integer>> blocks = new ArrayList<>();
        palette.getAllPresent((x, y, z, stateId) -> {
            Map<String, Integer> block = new HashMap<>();
            block.put("x", x);
            block.put("y", y);
            block.put("z", z);
            block.put("stateId", stateId);
            blocks.add(block);
        });
        return blocks;
    }

}
