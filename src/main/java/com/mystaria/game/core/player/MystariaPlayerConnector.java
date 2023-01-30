package com.mystaria.game.core.player;

import com.mystaria.game.MystariaServer;
import com.mystaria.game.core.instance.MystariaInstanceContainer;
import com.mystaria.game.core.properties.ConnectorProperties;
import net.kyori.adventure.text.Component;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.GameMode;
import net.minestom.server.event.EventFilter;
import net.minestom.server.event.EventListener;
import net.minestom.server.event.EventNode;
import net.minestom.server.event.player.AsyncPlayerPreLoginEvent;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.event.trait.PlayerEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * Created by Giovanni on 1/29/2023
 */
public class MystariaPlayerConnector implements EventListener<AsyncPlayerPreLoginEvent> {

    private final ConnectorProperties properties;
    private final EventNode<PlayerEvent> connectorNode;

    public MystariaPlayerConnector(ConnectorProperties properties) {
        this.properties = properties;
        this.connectorNode = EventNode.type("connector", EventFilter.PLAYER);

        MinecraftServer.getConnectionManager().setPlayerProvider(new MystariaPlayerProvider());
        connectorNode
                .addListener(this)
                .addListener(new EventListener<PlayerLoginEvent>() {
            @Override
            public @NotNull Class<PlayerLoginEvent> eventType() {
                return PlayerLoginEvent.class;
            }

            @Override
            public @NotNull Result run(@NotNull PlayerLoginEvent event) {
                Optional<MystariaInstanceContainer> instanceCheck = MystariaServer.getCore().getInstanceHandler().getRandomInstance();
                MystariaInstanceContainer instance = instanceCheck.get(); // Null = impossible

                MystariaPlayer player = (MystariaPlayer) event.getPlayer();
                event.setSpawningInstance(instance);
                player.setRespawnPoint(instance.getSpawn().getPosition());
                player.setPermissionLevel(2);
                player.setGameMode(GameMode.CREATIVE);
                return Result.SUCCESS;
            }
        });
        MinecraftServer.getGlobalEventHandler().addChild(connectorNode);
    }

    @Override
    public @NotNull Result run(@NotNull AsyncPlayerPreLoginEvent event) {
        if (MinecraftServer.getConnectionManager().getOnlinePlayers().size() >= properties.maxPlayers) {
            event.getPlayer().kick(Component.text("The server is full"));
            return Result.INVALID;
        }

        Optional<MystariaInstanceContainer> instanceCheck = MystariaServer.getCore().getInstanceHandler().getRandomInstance();
        if (instanceCheck.isEmpty()) {
            event.getPlayer().kick(Component.text("Could not find an instance for you"));
            return Result.INVALID;
        }
        return Result.SUCCESS;
    }

    @Override
    public @NotNull Class<AsyncPlayerPreLoginEvent> eventType() {
        return AsyncPlayerPreLoginEvent.class;
    }
}
