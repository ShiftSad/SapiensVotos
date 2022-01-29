package me.sadev.votar.commands.text;

import me.sadev.votar.VotosPlugin;
import net.md_5.bungee.api.ChatMessageType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Candidatar implements CommandExecutor {

    private final VotosPlugin plugin;
    public Candidatar(VotosPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            VotosPlugin.log.warning("Bobinho, você não pode se candidatar!");
            return true;
        }
        Player player = (Player) sender;
        if (plugin.players.get(player.getName()) != null) {
            player.sendMessage(ChatColor.RED + "Você ja esta candidatado!");
            return true;
        }
        if (!(args.length == 1))
            return false;

        switch (args[0]) {
            case "presidente":
                if (!plugin.players.get(player.getName()).withdrawPlayer(50000)) {
                    player.sendMessage(ChatColor.RED + "Você não tem dinheiro para executar essa transacão!");
                    return true;
                }
                Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                    plugin.getter.createPlayer(player);
                    player.sendMessage(ChatColor.GREEN + "Candidatado com sucesso! Hello Mr. President");
                });

                player.sendMessage(ChatColor.GREEN + "Parabens, você esta sendo candidatado!");
                return true;
            case "senador":
                // TODO
                break;
            default:
                return false;
        }

        return false;
    }
}
