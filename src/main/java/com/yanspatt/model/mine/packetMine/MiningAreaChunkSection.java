package com.yanspatt.model.mine.packetMine;

import com.google.common.collect.Lists;
import lombok.Getter;


import java.util.List;
import java.util.Optional;

@Getter
public class MiningAreaChunkSection {

    private List<MiningChunkSection> chunkSection;

    public MiningAreaChunkSection() {
        chunkSection = Lists.newArrayList();
    }

    public MiningChunkSection add(int x, int z, int sectionY) {
            MiningChunkSection section = new MiningChunkSection(sectionY,x, z);
            chunkSection.add(section);

        return section;
    }

    public MiningChunkSection getChunk(int x, int z, int section) {
        Optional<MiningChunkSection> get = chunkSection.stream().filter((match -> x == match.getChunkX() && z == match.getChunkZ() && section == match.getId())).findFirst();
        return get.orElse(null);
    }


}
