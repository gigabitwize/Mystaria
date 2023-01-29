package com.mystaria.game;

import com.mystaria.game.core.MystariaCore;
import net.minestom.server.MinecraftServer;
import net.minestom.server.extras.MojangAuth;

import java.io.File;


/**
 * Created by Giovanni on 1/29/2023
 */
public class MystariaServer {

    private static MinecraftServer minecraftServer;
    private static MystariaCore core;

    public static void main(String[] args) {
        minecraftServer = MinecraftServer.init();
        MojangAuth.init();
        core = new MystariaCore(new File(System.getProperty("user.dir")));

        minecraftServer.start(core.getServerProperties().bindIp, core.getServerProperties().bindPort);
        core.loadCore();

        System.out.println("Mystaria is now running on " + core.getServerProperties().bindIp + ":" + core.getServerProperties().bindPort);
    }

    public static MystariaCore getCore() {
        return core;
    }

    public MinecraftServer getMinecraftServer() {
        return minecraftServer;
    }
}
