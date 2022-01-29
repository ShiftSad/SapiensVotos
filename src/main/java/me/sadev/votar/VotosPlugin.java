package me.sadev.votar;

import me.sadev.votar.cache.database.MySQLGetter;
import me.sadev.votar.cache.database.Mysql;
import me.sadev.votar.cache.models.VotePlayer;
import me.sadev.votar.commands.text.Candidatar;
import me.sadev.votar.commands.text.VoteToPlayer;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.plugin.RegisteredServiceProvider;
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
    public Vault vault;

    public HashMap<String, VotePlayer> players = new HashMap<>();

    public ArrayList<String> topNome    = new ArrayList<>();
    public ArrayList<Integer> topNumero = new ArrayList<>();

    public Economy econ;

    public static final Logger log = Logger.getLogger("SapiensVotos");
    public static VotosPlugin plugin;

    @Override
    public void onEnable() {
        if (!setupEconomy()) {
            log.severe(ChatColor.RED + "Este plugin precisa de Vault para funcionar!");
            getServer().getPluginManager().disablePlugin(this);
        }

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
        this.vault  = new Vault(this);

        saveDefaultConfig();
        asyncTopPlayers();

        getCommand("VoteToPlayer").setExecutor(new VoteToPlayer(this));
        getCommand("Candidatar").setExecutor(new Candidatar(this));

        getServer().getPluginManager().registerEvents(new Listeners(this), this);

    }

    @Override
    public void onDisable() {
        mysql.closeConnection();
        log.info(String.format(ChatColor.GREEN + "Plugin desativado! Versão %s", getDescription().getVersion()));
    }

    private void asyncTopPlayers() {
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
        }).runTaskTimerAsynchronously(this, 20, 20 * 60);
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null)
            return false;
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return true;
    }

    public static VotosPlugin instance() {
        return plugin;
    }
}
