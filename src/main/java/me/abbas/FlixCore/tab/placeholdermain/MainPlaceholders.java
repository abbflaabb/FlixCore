package me.abbas.FlixCore.tab.placeholdermain;

import e.com.abbas.LuckPermsAPI.Perms;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainPlaceholders extends PlaceholderExpansion {

    @Override
    public @NotNull String getIdentifier() {
        return "FlixCore";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Abbas";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.1.0";
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String params) {

        switch (params.toLowerCase()) {
            case "server_online":
                return String.valueOf(Bukkit.getOnlinePlayers().size());

            case "server_max":
                return String.valueOf(Bukkit.getMaxPlayers());

            case "server_tps":
                return getTPS();

            case "server_name":
                return Bukkit.getServerName();

            case "server_version":
                return Bukkit.getVersion();

            case "time":
                return new SimpleDateFormat("HH:mm:ss").format(new Date());

            case "date":
                return new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        }
        if (player != null && player.isOnline()) {
            switch (params.toLowerCase()) {
                case "player_name":
                    return player.getName();

                case "player_level":
                    return String.valueOf(player.getLevel());

                case "player_exp":
                    return Math.round(player.getExp() * 100) + "%";

                case "player_max_health":
                    return String.valueOf(Math.round(player.getMaxHealth()));
                case "player_health":
                    return String.valueOf(Math.round(player.getHealth()));
                case "player_food":
                    return String.valueOf(player.getFoodLevel());

                case "player_ping":
                    return String.valueOf(getPing(player));

                case "player_world":
                    return player.getWorld().getName();
                    case "player_rank":
                    return Perms.getPlayerRank(player);

                case "server_online_formatted":
                    return Bukkit.getOnlinePlayers().size() + " / " + Bukkit.getMaxPlayers();
            }
        }

        return null;
    }
    private String getTPS() {
        try {
            Object server = Bukkit.getServer().getClass().getMethod("getServer").invoke(Bukkit.getServer());
            double[] recentTps = (double[]) server.getClass().getField("recentTps").get(server);
            return String.format("%.2f", Math.min(recentTps[0], 20.0));
        } catch (Exception e) {
            return "20.00";
        }
    }
    private int getPing(Player player) {
        try {
            Object entityPlayer = player.getClass().getMethod("getHandle").invoke(player);
            return (int) entityPlayer.getClass().getField("ping").get(entityPlayer);
        } catch (Exception e) {
            return 0;
        }
    }
}
