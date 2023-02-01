package com.mystaria.game.core.player;

import com.mystaria.game.core.database.MystariaPlayerData;
import com.mystaria.game.core.database.MystariaSQL;
import net.minestom.server.entity.Player;
import net.minestom.server.network.PlayerProvider;
import net.minestom.server.network.player.PlayerConnection;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

/**
 * Created by Giovanni on 1/29/2023
 */
public class MystariaPlayerProvider implements PlayerProvider {

    private final MystariaSQL mystariaSQL;

    public MystariaPlayerProvider(MystariaSQL sql) {
        this.mystariaSQL = sql;
    }

    @Override
    public @NotNull Player createPlayer(@NotNull UUID uuid, @NotNull String username, @NotNull PlayerConnection connection) {
        MystariaPlayer mystariaPlayer = null;
        try {
            MystariaPlayerData playerData = mystariaSQL.loadPlayerData(uuid).get();
            if (playerData != null) mystariaPlayer = new MystariaPlayer(playerData, username, connection);
        } catch (InterruptedException | ExecutionException e) {
            return new Player(uuid, username, connection); // Invalid
        }
        return mystariaPlayer == null ? new Player(uuid, username, connection) : mystariaPlayer;
    }
}
