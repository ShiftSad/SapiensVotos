package me.sadev.votar.commands.text;

import me.sadev.votar.VotosPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TopPlayers implements CommandExecutor {

    private final VotosPlugin plugin;
    public TopPlayers(VotosPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(sender instanceof Player)) {
            VotosPlugin.log.info("Parabens, esse comando da para rodar pelo console :D");
            return true;
        }
        Player player = (Player) sender;
        if (plugin.topNome.isEmpty()) {
            player.sendMessage(ChatColor.RED + "O placar não foi carregado ainda, tente novamente mais tarde.");
            return true;
        }

        player.sendMessage(ChatColor.GREEN + "Candidatos(as) mais votados(as)...\n");
        for (int i = 0; i < plugin.topNumero.toArray().length; i++) {
            player.sendMessage(String.format(ChatColor.GREEN + "%s. %s%s: %s votos", (i + 1), ChatColor.GRAY, plugin.topNome.get(i), plugin.topNumero.get(i)));
        }
        return true;
    }
}
