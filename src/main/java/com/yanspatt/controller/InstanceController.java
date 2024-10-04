package com.yanspatt.controller;

import lombok.Getter;
import net.minestom.server.MinecraftServer;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.InstanceManager;
import net.minestom.server.instance.LightingChunk;
import net.minestom.server.instance.anvil.AnvilLoader;
import net.minestom.server.instance.block.Block;

import java.nio.file.Path;

public class InstanceController {

    @Getter
    private InstanceContainer instanceContainer;


    public InstanceController() {
        InstanceManager instanceManager = MinecraftServer.getInstanceManager();
        instanceContainer = instanceManager.createInstanceContainer();


        instanceContainer.setChunkLoader(new AnvilLoader(Path.of("mines/mine-01")));
        instanceContainer.enableAutoChunkLoad(true);
        //instanceContainer.setGenerator(unit -> unit.modifier().fillHeight(0, 40, Block.WHITE_STAINED_GLASS));

        instanceContainer.setChunkSupplier(LightingChunk::new);
    }


}
