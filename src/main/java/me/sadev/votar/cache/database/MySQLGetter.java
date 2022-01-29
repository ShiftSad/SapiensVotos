package me.sadev.votar.cache.database;

import me.sadev.votar.VotosPlugin;
import me.sadev.votar.cache.models.VotePlayer;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class MySQLGetter {

    private final VotosPlugin plugin;
    private final String table;

    public MySQLGetter(VotosPlugin plugin) {
        this.plugin = plugin;
        this.table  = plugin.getConfig().getString("Mysql.table");
    }

    public boolean exists(String name) {
        try {
            PreparedStatement ps = plugin.mysql.getConnection().prepareStatement("SELECT * FROM " + table + " WHERE NAME=?");
            ps.setString(1, name);

            ResultSet results = ps.executeQuery();

            // Jogador Encontrado
            return results.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void createPlayer(Player player) {
        try {
            UUID uuid = player.getUniqueId();
            if (!exists(player.getName())) {
                PreparedStatement ps2 = plugin.mysql.getConnection().prepareStatement("INSERT IGNORE INTO " + table + " (NAME,UUID,VOTOS) VALUES (?,?,?)");
                ps2.setString(1, player.getName());
                ps2.setString(2, uuid.toString());
                ps2.setInt(3, 0);

                ps2.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Integer getVotos(String player) {
        try {
            PreparedStatement ps = plugin.mysql.getConnection().prepareStatement("SELECT VOTOS FROM " + table + " WHERE NAME=?");
            ps.setString(1, player);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("VOTOS");
            }
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void addVotos(String player, int votos) {
        try {
            VotePlayer votePlayer = plugin.players.get(player);
            if (votePlayer != null) {
                votePlayer.setVotos(votePlayer.getVotos() + votos);

                PreparedStatement ps = plugin.mysql.getConnection().prepareStatement("UPDATE " + table + " SET VOTOS=? WHERE NAME=?");
                ps.setInt(1, votos + votePlayer.getVotos());
                ps.setString(2, player);

                ps.executeUpdate();
            } else {
                PreparedStatement ps = plugin.mysql.getConnection().prepareStatement("UPDATE " + table + " SET VOTOS=? WHERE NAME=?");
                ps.setInt(1, votos + getVotos(player));
                ps.setString(2, player);

                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
