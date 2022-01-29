package me.sadev.votar.commands.text;

import me.sadev.votar.VotosPlugin;
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
        if (!(args.length == 1))
            return false;

        switch (args[0]) {
            case "presidente":
                break;
            case "senador":
                // TODO
                break;
            default:
                return false;
        }

        return false;


    }
}
