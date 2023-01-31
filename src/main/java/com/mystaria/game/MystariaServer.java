package com.mystaria.game;

import com.google.common.collect.Sets;
import com.mystaria.game.core.MystariaCore;
import com.mystaria.game.core.database.MystariaPlayerData;
import com.mystaria.game.core.database.MystariaSQL;
import com.mystaria.game.core.instance.CachedMystariaInstanceContainer;
import com.mystaria.game.core.log.Logging;
import com.mystaria.game.core.player.MystariaPlayer;
import com.mystaria.game.item.TestListener;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.EventListener;
import net.minestom.server.event.player.PlayerChatEvent;
import net.minestom.server.event.server.ServerListPingEvent;
import net.minestom.server.extras.MojangAuth;
import net.minestom.server.ping.ResponseData;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


/**
 * Created by Giovanni on 1/29/2023
 */
public class MystariaServer {

    public static final Logging SERVER_LOG = new Logging(MystariaServer.class);
    public static final File WORKING_DIR = new File(System.getProperty("user.dir"));
    public static final File DATABASE_DIR = new File(WORKING_DIR, "database");
    public static boolean stopping = false;

    private static MinecraftServer minecraftServer;
    private static MystariaSQL mystariaDatabase;
    private static MystariaCore core;

    private static Collection<MystariaPlayerData> playerDataSnapshot;

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

        // A temporary MOTD listener to set the MOTD to = starting, it expires when the MystariaCore is done loading.
        MinecraftServer.getGlobalEventHandler().addListener(EventListener.builder(ServerListPingEvent.class)
                .expireWhen(serverListPingEvent -> core.isLoaded())
                .handler(serverListPing -> {
                    ResponseData tempResponseData = new ResponseData();
                    tempResponseData.setMaxPlayer(0);
                    tempResponseData.setDescription(Component.text("The server is starting..").color(NamedTextColor.RED));
                    serverListPing.setResponseData(tempResponseData);
                })
                .build());

        System.out.println(); // Empty line
        core.loadCore();

        MinecraftServer.getGlobalEventHandler().addListener(PlayerChatEvent.class, event -> {
            if (event.getMessage().equalsIgnoreCase("dev-test-stop")) stop();
        });


        // test listener
        MinecraftServer.getGlobalEventHandler().addListener(new TestListener());

        SERVER_LOG.info("Mystaria is now running on " + core.getServerProperties().bindIp + ":" + core.getServerProperties().bindPort);
    }

    /**
     * Gracefully stops the server.
     */
    public static void stop() {
        stopping = true;
        SERVER_LOG.info("Stopping the server..");

        createDataSnapshot();
        for (Player onlinePlayer : MinecraftServer.getConnectionManager().getOnlinePlayers()) {
            onlinePlayer.kick("The server is stopping");
            onlinePlayer.remove();
        }

        mystariaDatabase.savePlayerDataBulk(playerDataSnapshot);
        mystariaDatabase.close();

        for (CachedMystariaInstanceContainer cachedInstance : core.getInstanceHandler().getAllCachedInstances()) {
            cachedInstance.save();
            MinecraftServer.getInstanceManager().unregisterInstance(cachedInstance);
        }

        MinecraftServer.stopCleanly();
        SERVER_LOG.info("Done, goodbye!");
        System.exit(0);
    }

    private static void createDataSnapshot() {
        playerDataSnapshot = Sets.newHashSet();
        MinecraftServer.getConnectionManager().getOnlinePlayers().forEach(player -> playerDataSnapshot.add(((MystariaPlayer) player).getPlayerData()));
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
