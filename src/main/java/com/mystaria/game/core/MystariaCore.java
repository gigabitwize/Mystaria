package com.mystaria.game.core;

import com.mystaria.game.MystariaServer;
import com.mystaria.game.api.file.JsonFile;
import com.mystaria.game.core.instance.MystariaInstanceHandler;
import com.mystaria.game.core.log.Logging;
import com.mystaria.game.core.motd.MystariaMOTD;
import com.mystaria.game.core.player.MystariaPlayerConnector;
import com.mystaria.game.core.properties.ConnectorProperties;
import com.mystaria.game.core.properties.ServerProperties;
import net.minestom.server.MinecraftServer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

/**
 * Created by Giovanni on 1/29/2023
 * <p>
 * The Mystaria core provides base functionality for the server the server to function.
 */
public class MystariaCore {

    public static final String BUILD_VERSION = "v0";

    private final File workingDir;
    private final Logging LOG = new Logging(getClass());
    private boolean loaded;

    private ServerProperties serverProperties;
    private ConnectorProperties connectorProperties;

    private MystariaPlayerConnector playerConnector;
    private MystariaInstanceHandler instanceHandler;

    public MystariaCore(File workingDirectory) {
        this.workingDir = workingDirectory;

        JsonFile propertiesJson = new JsonFile(workingDirectory.toPath().toString(), "server.json", true);
        JsonFile connectorJson = new JsonFile(workingDirectory.toPath().toString(), "connector.json", true);
        copyDefaults(propertiesJson, connectorJson);

        try {
            serverProperties = propertiesJson.readObject(ServerProperties.class);
            connectorProperties = connectorJson.readObject(ConnectorProperties.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadCore() {
        if (loaded) {
            return;
        }

        long current = System.currentTimeMillis();
        LOG.info("Loading the MystariaCore..");
        this.instanceHandler = new MystariaInstanceHandler(workingDir);
        this.instanceHandler.loadAllInstances();
        if (serverProperties.generateDefaultInstance)
            instanceHandler.generateDefault();

        this.playerConnector = new MystariaPlayerConnector(connectorProperties);
        MinecraftServer.getGlobalEventHandler().addListener(new MystariaMOTD(connectorProperties));

        LOG.info("MystariaCore done in " + (System.currentTimeMillis() - current) + "ms.");
        this.loaded = true;
    }

    private void copyDefaults(JsonFile server, JsonFile connector) {
        try {
            if (!server.exists())
                Files.copy(Objects.requireNonNull(MystariaServer.class.getClassLoader().getResourceAsStream("server.json")), server.getPath());
            if (!connector.exists())
                Files.copy(Objects.requireNonNull(MystariaServer.class.getClassLoader().getResourceAsStream("connector.json")), connector.getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isLoaded() {
        return loaded;
    }

    public ServerProperties getServerProperties() {
        return serverProperties;
    }

    public ConnectorProperties getConnectorProperties() {
        return connectorProperties;
    }

    public MystariaInstanceHandler getInstanceHandler() {
        return instanceHandler;
    }
}
