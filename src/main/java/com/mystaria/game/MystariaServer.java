package com.mystaria.game;

import com.google.common.collect.Lists;
import com.mystaria.game.core.MystariaCore;
import com.mystaria.game.core.database.MystariaSQL;
import com.mystaria.game.core.instance.CachedMystariaInstanceContainer;
import com.mystaria.game.core.log.Logging;
import com.mystaria.game.core.player.MystariaPlayer;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerChatEvent;
import net.minestom.server.extras.MojangAuth;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;


/**
 * Created by Giovanni on 1/29/2023
 */
public class MystariaServer {

    public static final Logging SERVER_LOG = new Logging(MystariaServer.class);
    public static final File WORKING_DIR = new File(System.getProperty("user.dir"));
    public static final File DATABASE_DIR = new File(WORKING_DIR, "database");

    private static MinecraftServer minecraftServer;
    private static MystariaSQL mystariaDatabase;
    private static MystariaCore core;

    public static void main(String[] args) {
        SERVER_LOG.info("Starting..");

        if (!DATABASE_DIR.exists())
            DATABASE_DIR.mkdir();
        mystariaDatabase = new MystariaSQL(new File(DATABASE_DIR, "sql.properties"), new File(DATABASE_DIR, "defaults.sql"));

        CompletableFuture<Boolean> databaseStart = CompletableFuture.supplyAsync(() -> {
            try {
                return mystariaDatabase.open();
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
            return false;
        });

        try {
            if (!databaseStart.get()) {
                SERVER_LOG.severe("Failed to open the database connection.");
                return;
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return;
        }

        minecraftServer = MinecraftServer.init();
        MinecraftServer.setBrandName("Mystaria");
        MojangAuth.init();

        core = new MystariaCore(WORKING_DIR);
        minecraftServer.start(core.getServerProperties().bindIp, core.getServerProperties().bindPort);
        System.out.println(); // Empty line

        core.loadCore();

        MinecraftServer.getGlobalEventHandler().addListener(PlayerChatEvent.class, event -> {
            if (event.getMessage().equalsIgnoreCase("dev-test-stop")) stop();
        });

        SERVER_LOG.info("Mystaria is now running on " + core.getServerProperties().bindIp + ":" + core.getServerProperties().bindPort);
    }

    /**
     * Gracefully stops the server.
     */
    public static void stop() {
        SERVER_LOG.info("Stopping the server..");
        mystariaDatabase.savePlayerDataBulk(getOnlinePlayers());
        mystariaDatabase.close();

        for (Player onlinePlayer : MinecraftServer.getConnectionManager().getOnlinePlayers()) {
            onlinePlayer.kick("The server is stopping");
            onlinePlayer.remove();
        }

        for (CachedMystariaInstanceContainer cachedInstance : core.getInstanceHandler().getAllCachedInstances()) {
            cachedInstance.save();
            MinecraftServer.getInstanceManager().unregisterInstance(cachedInstance);
        }
        MinecraftServer.stopCleanly();
        SERVER_LOG.info("Done, goodbye!");
        System.exit(0);
    }

    public static Collection<MystariaPlayer> getOnlinePlayers() {
        return MinecraftServer.getConnectionManager().getOnlinePlayers().stream().map(onlinePlayer -> (MystariaPlayer) onlinePlayer).collect(Collectors.toCollection(Lists::newArrayList));
    }

    public static MystariaCore getCore() {
        return core;
    }

    public static MystariaSQL getDatabase() {
        return mystariaDatabase;
    }

    public MinecraftServer getMinecraftServer() {
        return minecraftServer;
    }
}
