package com.mystaria.game.core.database;

import com.mystaria.game.MystariaServer;
import com.mystaria.game.core.database.util.SQLScriptExecutor;
import com.mystaria.game.core.log.Logging;
import com.mystaria.game.core.player.MystariaPlayer;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Created by Giovanni on 1/31/2023
 */
public class MystariaSQL implements Database {

    private final Logging SQL_LOG;
    private final HikariConfig config;
    private final File defaultTableData;
    private HikariDataSource dataSource;

    public MystariaSQL(File properties, File tables) {
        this.SQL_LOG = new Logging(getClass());

        try {
            if (!properties.exists())
                Files.copy(Objects.requireNonNull(MystariaServer.class.getClassLoader().getResourceAsStream("sql.properties")), properties.toPath());
            if (!tables.exists())
                Files.copy(Objects.requireNonNull(MystariaServer.class.getClassLoader().getResourceAsStream("defaults.sql")), tables.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.config = new HikariConfig(properties.getPath());
        this.defaultTableData = tables;
    }

    @Override
    public void insertNewPlayer(MystariaPlayer player) {
        CompletableFuture.runAsync(() -> {
            MystariaPlayerData playerData = player.getPlayerData();
            if (!playerData.newData) return;

            try {
                Connection connection = dataSource.getConnection();

                String query = "INSERT INTO players (uuid,username) values (?,?)";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, playerData.uuid.toString());
                statement.setString(2, playerData.username);
                statement.executeUpdate();
                playerData.newData = false;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void savePlayerData(MystariaPlayer player) {
        CompletableFuture.runAsync(() -> {
            MystariaPlayerData playerData = player.getPlayerData();

            try {
                Connection connection = dataSource.getConnection();

                String query = "UPDATE players SET uuid=?, username=? WHERE uuid=?";

                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, playerData.uuid.toString());
                statement.setString(2, playerData.username);
                statement.setString(3, playerData.uuid.toString());
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void savePlayerDataBulk(Collection<MystariaPlayerData> dataCollection) {
        try {
            Connection connection = dataSource.getConnection();
            for (MystariaPlayerData playerData : dataCollection) {
                String query = "UPDATE players SET uuid=?, username=? WHERE uuid=?";

                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, playerData.uuid.toString());
                statement.setString(2, playerData.username);
                statement.setString(3, playerData.uuid.toString());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public CompletableFuture<MystariaPlayerData> loadPlayerData(UUID playerId) {
        return CompletableFuture.supplyAsync(() -> {
            MystariaPlayerData playerData = new MystariaPlayerData(playerId);
            try {
                Connection connection = dataSource.getConnection();

                String query = "SELECT * FROM players WHERE uuid = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, playerId.toString());

                ResultSet dataSet = statement.executeQuery();
                while (dataSet.next()) {
                    playerData.newData = false;
                    playerData.username = dataSet.getString("username");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
            return playerData;
        });
    }

    @Override
    public boolean open() throws IOException, SQLException {
        SQL_LOG.info("Opening connection..");
        if (this.dataSource != null) return false;
        if (!this.defaultTableData.exists()) {
            SQL_LOG.warn("Default table data does not exist, could not launch database.");
            return false;
        }

        try {
            this.dataSource = new HikariDataSource(config);
        } catch (Exception e) {
            return false;
        }
        SQLScriptExecutor sqlScriptExecutor = new SQLScriptExecutor(dataSource.getConnection(), false, true);
        sqlScriptExecutor.runScript(new BufferedReader(new FileReader(defaultTableData.getPath())));
        return true;
    }

    @Override
    public void close() {
        this.dataSource.close();
    }
}
