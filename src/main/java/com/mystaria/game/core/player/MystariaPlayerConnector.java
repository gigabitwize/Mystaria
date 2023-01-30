package com.mystaria.game.core.player;

import com.mystaria.game.MystariaServer;
import com.mystaria.game.core.properties.ConnectorProperties;
import net.kyori.adventure.text.Component;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.event.EventListener;
import net.minestom.server.event.player.PlayerLoginEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Giovanni on 1/29/2023
 */
public class MystariaPlayerConnector implements EventListener<PlayerLoginEvent> {

    private final ConnectorProperties properties;

    public MystariaPlayerConnector(ConnectorProperties properties) {
        this.properties = properties;

        MinecraftServer.getConnectionManager().setPlayerProvider(new MystariaPlayerProvider());
        MinecraftServer.getGlobalEventHandler().addListener(this);
    }

    @Override
    public @NotNull Result run(@NotNull PlayerLoginEvent event) {
        if (MinecraftServer.getConnectionManager().getOnlinePlayers().size() >= properties.maxPlayers) {
            event.getPlayer().kick(Component.text("The server is full"));
            return Result.INVALID;
        }
        Player player = event.getPlayer();
        player.setPermissionLevel(2);
        event.setSpawningInstance(MystariaServer.getCore().getInstanceContainer());
        player.setRespawnPoint(new Pos(0, 42, 0));
        return Result.SUCCESS;
    }

    @Override
    public @NotNull Class<PlayerLoginEvent> eventType() {
        return PlayerLoginEvent.class;
    }
}
