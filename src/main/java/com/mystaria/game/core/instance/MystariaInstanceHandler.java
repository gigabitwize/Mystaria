package com.mystaria.game.core.instance;

import com.mystaria.game.api.file.JsonFile;
import com.mystaria.game.core.instance.exception.InstanceMissingException;
import com.mystaria.game.core.properties.InstanceProperties;
import net.minestom.server.MinecraftServer;
import net.minestom.server.instance.Instance;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Created by Giovanni on 1/30/2023
 */
public class MystariaInstanceHandler {

    private final File instancesDirectory;

    public MystariaInstanceHandler(File workingDirectory) {
        this.instancesDirectory = new File(workingDirectory, "instances");
        if (!this.instancesDirectory.exists())
            this.instancesDirectory.mkdir();
    }

    /**
     * Loads all {@link CachedMystariaInstanceContainer}s from disk.
     */
    public void loadAllInstances() {
        if (instancesDirectory.listFiles() == null) {
            System.out.println("Could not find any Cached Instances in /instances/!");
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
            System.err.println("Instance '" + instancePath + "' is already loaded from disk!");
            return null;
        }

        System.out.println("Loading cached instance: " + instancePath);
        File instanceDirectory = new File(instancesDirectory, instancePath);
        if (!instanceDirectory.exists())
            throw new InstanceMissingException(instancePath);
        JsonFile dataJson = new JsonFile(instanceDirectory.getPath(), "data.json", true);
        if (!dataJson.exists())
            throw new InstanceMissingException(instancePath + "::data.json");

        InstanceProperties instanceProperties;
        try {
            instanceProperties = dataJson.readObject(InstanceProperties.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        // Done
        return new CachedMystariaInstanceContainer(instanceDirectory.getPath(), instanceProperties.spawn, null);
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
     * - instances
     *   - instance01
     *      - world file (chunks)
     *      - data.json (spawners, spawnPos, etc)
     *   - instance02
     *      - world file (chunks)
     *      - data.json (spawners, spawnPos, etc)
     *   - etc..
     */
}
