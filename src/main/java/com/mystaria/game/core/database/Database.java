package com.mystaria.game.core.database;

import com.mystaria.game.core.player.MystariaPlayer;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

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
     * <p>
     * Bulk saving does not execute async by default since it's mainly used on server stop.
     */
    void savePlayerDataBulk(Collection<MystariaPlayerData> dataCollection);

    /**
     * Inserts a new player in the database.
     */
    void insertNewPlayer(MystariaPlayer player);

    /**
     * Loads a player's data from the database.
     */
    CompletableFuture<MystariaPlayerData> loadPlayerData(UUID playerId);

    /**
     * Opens a connection with the database.
     */
    boolean open() throws IOException, SQLException;

    /**
     * Closes the connection with the database.
     */
    void close();
}
