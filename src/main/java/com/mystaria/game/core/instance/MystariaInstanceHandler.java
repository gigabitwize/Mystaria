package com.mystaria.game.core.instance;

import com.mystaria.game.api.file.JsonFile;
import com.mystaria.game.core.instance.exception.InstanceMissingException;
import com.mystaria.game.core.instance.generators.FlatTerrainGenerator;
import com.mystaria.game.core.log.Logging;
import com.mystaria.game.core.properties.InstanceProperties;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.block.Block;
import net.minestom.server.instance.generator.GenerationUnit;
import net.minestom.server.instance.generator.Generator;
import net.minestom.server.world.DimensionType;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

/**
 * Created by Giovanni on 1/30/2023
 */
public class MystariaInstanceHandler {

    private final File instancesDirectory;
    private final Logging LOG = new Logging(getClass());

    public MystariaInstanceHandler(File workingDirectory) {
        this.instancesDirectory = new File(workingDirectory, "instances");
        if (!this.instancesDirectory.exists())
            this.instancesDirectory.mkdir();
    }

    /**
     * Generates a default {@link CachedMystariaInstanceContainer}.
     */
    public CachedMystariaInstanceContainer generateDefault() {
        if (!MinecraftServer.getDimensionTypeManager().isRegistered(DimensionType.OVERWORLD))
            MinecraftServer.getDimensionTypeManager().addDimension(DimensionType.OVERWORLD);

        File defaultInstance = new File(instancesDirectory, "default_instance");

        CachedMystariaInstanceContainer cachedContainer = new CachedMystariaInstanceContainer(
                this,
                defaultInstance.getPath(),
                new Pos(0, 5, 0),
                DimensionType.OVERWORLD);
        cachedContainer.setGenerator(new FlatTerrainGenerator(Block.STONE));
        cachedContainer.register();
        return cachedContainer;
    }

    /**
     * Loads all {@link CachedMystariaInstanceContainer}s from disk.
     */
    public void loadAllInstances() {
        if (instancesDirectory.listFiles() == null || instancesDirectory.listFiles().length < 1) {
            LOG.info("Could not find any Cached Instances in /instances/");
            return;
        }

        try {
            Files.list(instancesDirectory.toPath()).forEach(instanceDirectory -> loadInstance(instanceDirectory.toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads a {@link CachedMystariaInstanceContainer} from disk.
     *
     * @param instancePath The instance's directory.
     */
    @Nullable
    public CachedMystariaInstanceContainer loadInstance(String instancePath) {
        if (isCachedInstanceLoaded(instancePath)) {
            LOG.warn("Instance '" + instancePath + "' is already loaded from disk!");
            return null;
        }

        LOG.info("Loading cached instance: " + instancePath);
        File instanceDirectory = new File(instancePath);
        if (!instanceDirectory.exists())
            throw new InstanceMissingException(instanceDirectory.getPath());

        JsonFile dataJson = new JsonFile(instanceDirectory.getPath(), "data.json", true);
        if (!dataJson.exists())
            throw new InstanceMissingException(instancePath + "::data.json");

        InstanceProperties instanceProperties;
        try {
            instanceProperties = dataJson.readObject(InstanceProperties.class);
        } catch (Exception e) {
            LOG.severe("Failed to load cached instance: " + instancePath);
            e.printStackTrace();
            return null;
        }
        // Done
        if (!MinecraftServer.getDimensionTypeManager().isRegistered(instanceProperties.dimension))
            MinecraftServer.getDimensionTypeManager().addDimension(instanceProperties.dimension);
        return new CachedMystariaInstanceContainer(this, instancePath, instanceProperties.spawn.convert(), instanceProperties.dimension);
    }

    /**
     * Returns whether a {@link CachedMystariaInstanceContainer} is currently loaded from disk.
     */
    public boolean isCachedInstanceLoaded(String instancePath) {
        for (Instance instance : MinecraftServer.getInstanceManager().getInstances()) {
            if (instance instanceof CachedMystariaInstanceContainer cachedInstance) {
                if (cachedInstance.getInstancePath().equalsIgnoreCase(instancePath)) return true;
            }
        }
        return false;
    }

    /**
     * Attempts to find a random {@link MystariaInstanceContainer}.
     */
    public Optional<MystariaInstanceContainer> getRandomInstance() {
        for (Instance instance : MinecraftServer.getInstanceManager().getInstances()) {
            if (instance instanceof MystariaInstanceContainer mystariaInstance) {
                return Optional.of(mystariaInstance);
            }
        }
        return Optional.empty();
    }
}
