package me.sadev.votar;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Vault {

    private final VotosPlugin plugin;
    public Vault(VotosPlugin plugin) {
        this.plugin = plugin;
    }

    public Economy getEconomy() {
        return plugin.econ;
    }

    public boolean withdrawPlayer(Player player, double money) {
        if (!getEconomy().has(player, money))
            return false;

        getEconomy().withdrawPlayer(player, money);
        return true;
    }

    public boolean depositPlayer(Player player, double money) {
        getEconomy().depositPlayer(player, money);
        return true;
    }

}
