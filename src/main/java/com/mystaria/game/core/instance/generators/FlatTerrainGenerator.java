package com.mystaria.game.core.instance.generators;

import net.minestom.server.instance.block.Block;
import net.minestom.server.instance.generator.GenerationUnit;
import net.minestom.server.instance.generator.Generator;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Giovanni on 1/30/2023
 */
public class FlatTerrainGenerator implements Generator {

    private final Block blockType;

    public FlatTerrainGenerator(Block blockType) {
        this.blockType = blockType;
    }


    @Override
    public void generate(@NotNull GenerationUnit unit) {
        unit.modifier().fillHeight(-40, 0, blockType);
    }
}
