package com.mystaria.game.core.database;

import java.util.UUID;

/**
 * Created by Giovanni on 1/31/2023
 */
public class MystariaPlayerData {

    public final UUID uuid;
    public String username;

    /**
     * This is used by MystariaSQL to determine the first save type.
     * <p>
     * newData true = INSERT
     * newData false = UPDATE
     */
    public boolean newData = true;

    public MystariaPlayerData(UUID uuid) {
        this.uuid = uuid;
    }
}
