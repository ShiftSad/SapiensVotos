package me.sadev.votar.cache.models;

import me.sadev.votar.VotosPlugin;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Objects;

public class VotePlayer {

    private String name;
    private String uuid;
    private int votos;

    public VotePlayer(String name, String uuid, int votos) {
        this.name = name;
        this.uuid = uuid;
        this.votos = votos;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getVotos() {
        return votos;
    }

    public void setVotos(int votos) {
        this.votos = votos;
    }

    public boolean withdrawPlayer(double money) {
        Player player = Bukkit.getPlayer(getName());
        Economy econ  = VotosPlugin.instance().econ;

        if (!econ.has(player, money))
            return false;

        econ.withdrawPlayer(player, money);
        return true;
    }

    public boolean depositPlayer(double money) {
        Player player = Bukkit.getPlayer(getName());
        Economy econ  = VotosPlugin.instance().econ;

        econ.depositPlayer(player, money);
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VotePlayer that = (VotePlayer) o;
        return votos == that.votos && Objects.equals(name, that.name) && Objects.equals(uuid, that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, uuid, votos);
    }

    @Override
    public String toString() {
        return "VotePlayer{" +
                "name='" + name + '\'' +
                ", uuid='" + uuid + '\'' +
                ", votos=" + votos +
                '}';
    }
}