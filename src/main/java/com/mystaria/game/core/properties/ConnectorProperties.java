package com.mystaria.game.core.properties;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Giovanni on 1/29/2023
 */
public class ConnectorProperties {

    @SerializedName("player_slots")
    public final int maxPlayers = 100;
}
