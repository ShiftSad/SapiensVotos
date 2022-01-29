package me.sadev.votar;

import me.sadev.votar.cache.database.MySQLGetter;
import me.sadev.votar.cache.database.Mysql;
import me.sadev.votar.cache.models.VotePlayer;
import me.sadev.votar.commands.text.VoteToPlayer;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

public final class VotosPlugin extends JavaPlugin {

    public Mysql mysql;
    public MySQLGetter getter;

    public HashMap<String, VotePlayer> players = new HashMap<>();

    public ArrayList<String> topNome    = new ArrayList<>();
    public ArrayList<Integer> topNumero = new ArrayList<>();

    public static final Logger log = Logger.getLogger("SapiensVotos");

    @Override
    public void onEnable() {
        this.mysql = new Mysql(
                getConfig().getString("Mysql.host"),
                getConfig().getString("Mysql.database"),
                getConfig().getString("Mysql.user"),
                getConfig().getString("Mysql.password"),
                getConfig().getString("Mysql.table"),
                getConfig().getInt("Mysql.port"),
                this
        );
        this.getter = new MySQLGetter(this);

        saveDefaultConfig();
        asyncTopPlayers();
        
        getCommand("VoteToPlayer").setExecutor(new VoteToPlayer(this));
        getServer().getPluginManager().registerEvents(new Listeners(this), this);

    }

    @Override
    public void onDisable() {
        mysql.closeConnection();
    }

    public void asyncTopPlayers() {
        (new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    PreparedStatement ps = mysql.getConnection().prepareStatement("SELECT * FROM `presidente` ORDER BY `VOTOS` DESC LIMIT 70");

                    ResultSet rs = ps.executeQuery();

                    while (rs.next()) {
                        topNome.add(rs.getString(1));
                        topNumero.add(rs.getInt(3));
                    }
                } catch (Exception e) { e.printStackTrace(); }
            }
        }).runTaskTimerAsynchronously(this, 20, (20 * 60) * 5);
    }
}
