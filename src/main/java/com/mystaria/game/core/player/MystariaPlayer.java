package com.mystaria.game.core.player;

import com.mystaria.game.core.database.MystariaPlayerData;
import net.minestom.server.entity.Player;
import net.minestom.server.network.player.PlayerConnection;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Giovanni on 1/29/2023
 */
public class MystariaPlayer extends Player {

    private final MystariaPlayerData playerData;

    public MystariaPlayer(@NotNull MystariaPlayerData playerData, @NotNull String username, @NotNull PlayerConnection playerConnection) {
        super(playerData.uuid, username, playerConnection);
        this.playerData = playerData;
        this.playerData.username = username;
    }

    /**
     * Returns this player's {@link MystariaPlayerData}. This is the data that is
     * actually being stored in the {@link com.mystaria.game.core.database.MystariaSQL}.
     */
    public MystariaPlayerData getPlayerData() {
        return playerData;
    }
}
