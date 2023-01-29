package com.mystaria.game.core.properties;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Giovanni on 1/29/2023
 */
public class ServerProperties {

    @SerializedName("bind_ip")
    public final String bindIp = "localhost";
    @SerializedName("bind_port")
    public final int bindPort = 25565;
}
