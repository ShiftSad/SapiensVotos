package me.sadev.votar;

import net.milkbowl.vault.economy.Economy;

public class Vault {

    private final VotosPlugin plugin;
    public Vault(VotosPlugin plugin) {
        this.plugin = plugin;
    }

    public Economy getEconomy() {
        return plugin.econ;
    }
}
