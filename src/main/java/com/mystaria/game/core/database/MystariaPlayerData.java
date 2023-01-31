package com.mystaria.game.core.database;

import java.util.UUID;

/**
 * Created by Giovanni on 1/31/2023
 */
public class MystariaPlayerData {

    public final UUID uuid;
    public String username;

    public MystariaPlayerData(UUID uuid) {
        this.uuid = uuid;
    }
}
