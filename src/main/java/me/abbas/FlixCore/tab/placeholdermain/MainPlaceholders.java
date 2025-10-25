package me.abbas.FlixCore.tab.placeholdermain;

import com.abbas.FlixCore.Utiles.ColorUtils;
import com.abbas.FlixCore.manager.ChatLockManager;
import e.com.abbas.LuckPermsAPI.Perms;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainPlaceholders extends PlaceholderExpansion {

    private static Method getHandleMethod;
    private static Field pingField;
    private static boolean supportsDirectPing;
    public MainPlaceholders() {
        initializePingReflection();
    }

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
        return "1.2.0";
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String params) {

        String param = params.toLowerCase();

        // 🔹 Server placeholders
        switch (param) {
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

            case "server_online_formatted":
                return Bukkit.getOnlinePlayers().size() + " / " + Bukkit.getMaxPlayers();

            case "time":
                return new SimpleDateFormat("HH:mm:ss").format(new Date());

            case "date":
                return new SimpleDateFormat("dd/MM/yyyy").format(new Date());
            case "ChatLocked":
                return ChatLockManager.isChatLocked() ? ColorUtils.colorize("&a&lOpen Chat") : ColorUtils.colorize("&c&lChatClosed");
        }

        // 🔹 Player placeholders
        if (player == null || !player.isOnline()) return "";

        switch (param) {
            case "player_name":
                return player.getName();

            case "player_level":
                return String.valueOf(player.getLevel());

            case "player_exp":
                return Math.round(player.getExp() * 100) + "%";

            case "player_health":
                return String.valueOf(Math.round(player.getHealth()));

            case "player_max_health":
                return String.valueOf(Math.round(player.getMaxHealth()));

            case "player_food":
                return String.valueOf(player.getFoodLevel());

            case "player_ping":
                return String.valueOf(getPing(player));

            case "player_world":
                return player.getWorld().getName();

            case "player_rank":
                return Perms.getPlayerRank(player);

            default:
                return null;
        }
    }

    // =============================
    // ⚙️ Ping & TPS System
    // =============================

    private void initializePingReflection() {
        try {
            // Paper servers (1.17+)
            Player.class.getMethod("getPing");
            supportsDirectPing = true;
        } catch (NoSuchMethodException e) {
            try {
                String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
                Class<?> craftPlayer = Class.forName("org.bukkit.craftbukkit." + version + ".entity.CraftPlayer");
                getHandleMethod = craftPlayer.getMethod("getHandle");
                pingField = getHandleMethod.getReturnType().getField("ping");
            } catch (Exception ex) {
                Bukkit.getLogger().warning("[FlixCore] Failed to initialize ping reflection!");
            }
        }
    }

    private int getPing(Player player) {
        try {
            if (supportsDirectPing) {
                return (int) Player.class.getMethod("getPing").invoke(player);
            } else if (getHandleMethod != null && pingField != null) {
                Object handle = getHandleMethod.invoke(player);
                return pingField.getInt(handle);
            }
        } catch (Exception ignored) {
        }
        return 0;
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
}
