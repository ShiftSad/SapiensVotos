package me.sadev.votar.cache.database;

import me.sadev.votar.VotosPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.*;
import java.util.logging.Level;

public class Mysql {

    private Connection connection;
    private VotosPlugin plugin;

    private String host, database, username, password, table;
    private int port;

    public Mysql(String host, String database, String username, String password, String table, int port, VotosPlugin plugin) {
        plugin.getLogger().info("Conectando ao database!");
        try {
            this.connection = null;
            this.plugin = plugin;

            this.host = host;
            this.database = database;
            this.username = username;
            this.password = password;
            this.port = port;
            this.table = table;

            openConnection();
            createTable();

            (new BukkitRunnable() {
                @Override
                public void run() {
                    try {
                        if (connection != null && !connection.isClosed()) {
                            connection.createStatement().execute("SELECT 1");
                        }
                    } catch (SQLException e) {
                        connection = getConnection();
                    }
                }
            }).runTaskTimerAsynchronously(plugin, 60 * 20, 60 * 20);
        } catch (Exception e) {
            VotosPlugin.log.severe("Erro ao conectar ao MYSQL, plugin sendo desativado.");
            plugin.getPluginLoader().disablePlugin(plugin);
        }
    }

    public void openConnection() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://"
                        + this.host + ":" + this.port + "/" + this.database,
                this.username, this.password);
    }

    public boolean checkConnection() {
        return connection != null;
    }

    public Connection getConnection() {
        try {
            openConnection();
            return connection;
        } catch (Exception e) {
            return null;
        }
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                plugin.getLogger().log(Level.SEVERE,
                        "Erro fechando o mysql!");
                e.printStackTrace();
                plugin.onDisable();
            }
        }
    }

    public void createTable() {
        PreparedStatement ps;
        try {
            ps = getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS " + table + " (NAME VARCHAR(48), UUID VARCHAR(48)," +
                    " VOTOS INT, PRIMARY KEY (NAME))");
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
}