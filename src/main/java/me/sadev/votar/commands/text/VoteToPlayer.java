package me.sadev.votar.commands.text;

import me.sadev.votar.VotosPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class VoteToPlayer implements CommandExecutor {

    private final VotosPlugin plugin;

    public VoteToPlayer(VotosPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            VotosPlugin.log.warning("O console não pode executar esse comando!");
            return true;
        }
        Player player = (Player) sender;
        if (!(args.length == 1))
            return false;

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            if (plugin.getter.exists(args[0])) {
                plugin.getter.addVotos(args[0], 1);
                player.sendMessage(ChatColor.GREEN + "Voto adicionado com sucesso!");
            } else {
                player.sendMessage(ChatColor.RED + "Esse jogador não existe!");
            }
        });

        player.sendMessage(ChatColor.GREEN + "Adicionando o voto...");
        return true;
    }
}
