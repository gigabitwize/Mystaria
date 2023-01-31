package com.mystaria.game.core.database;

import com.mystaria.game.core.player.MystariaPlayer;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.UUID;

/**
 * Created by Giovanni on 1/30/2023
 */
public interface Database {

    /**
     * Saves a player's data in the database.
     */
    void savePlayerData(MystariaPlayer player);

    /**
     * Used for saving player data in bulk.
     */
    void savePlayerDataBulk(Collection<MystariaPlayer> playerCollection);

    /**
     * Loads a player's data from the database.
     */
    void loadPlayerData(UUID playerId);

    /**
     * Opens a connection with the database.
     */
    boolean open() throws IOException, SQLException;

    /**
     * Closes the connection with the database.
     */
    void close();
}
