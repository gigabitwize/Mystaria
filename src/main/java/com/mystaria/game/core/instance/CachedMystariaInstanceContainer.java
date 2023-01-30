package com.mystaria.game.core.instance;

import net.minestom.server.coordinate.Pos;
import net.minestom.server.instance.AnvilLoader;
import net.minestom.server.world.DimensionType;

/**
 * Created by Giovanni on 1/30/2023
 */
public class CachedMystariaInstanceContainer extends MystariaInstanceContainer {

    private final String instancePath;

    /**
     * A {@link MystariaInstanceContainer} that can be loaded from disk, behaves like a saved Minecraft world.
     *
     * @param instancePath The path to the instance's folder.
     */
    public CachedMystariaInstanceContainer(String instancePath, Pos spawnPosition, DimensionType dimensionType) {
        super(spawnPosition, dimensionType, new AnvilLoader(instancePath));
        this.instancePath = instancePath;
    }

    public String getInstancePath() {
        return instancePath;
    }
}
