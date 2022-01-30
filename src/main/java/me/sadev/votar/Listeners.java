package me.sadev.votar;

import me.sadev.votar.cache.models.VotePlayer;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Listeners implements Listener {

    private final VotosPlugin plugin;
    public Listeners(VotosPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        String name = player.getName();
        String uuid = player.getUniqueId().toString();

        if (plugin.getter.exists(player.getName())) {
            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> plugin.players.put(player.getName(),
                    new VotePlayer(name, uuid, plugin.getter.getVotos(player.getName()))));
        }
    }
}
