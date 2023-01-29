package com.mystaria.game.core;

import com.mystaria.game.MystariaServer;
import com.mystaria.game.api.file.JsonFile;
import com.mystaria.game.core.player.MystariaPlayerConnector;
import com.mystaria.game.core.properties.ConnectorProperties;
import com.mystaria.game.core.properties.ServerProperties;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

/**
 * Created by Giovanni on 1/29/2023
 */
public class MystariaCore {

    private final File workingDir;

    private ServerProperties serverProperties;
    private ConnectorProperties connectorProperties;

    private MystariaPlayerConnector playerConnector;

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
        long current = System.currentTimeMillis();

        System.out.println("Loading the MystariaCore..");
        this.playerConnector = new MystariaPlayerConnector(connectorProperties);
        System.out.println("MystariaCore done in " + (System.currentTimeMillis() - current) + "ms.");
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

    public ServerProperties getServerProperties() {
        return serverProperties;
    }

    public ConnectorProperties getConnectorProperties() {
        return connectorProperties;
    }
}
