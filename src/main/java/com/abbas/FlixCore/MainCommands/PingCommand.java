package com.abbas.FlixCore.MainCommands;

import com.abbas.FlixCore.FlixCore;
import com.abbas.FlixCore.Utiles.ColorUtils;
import com.abbas.FlixCore.api.APICommands;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class PingCommand implements APICommands {

    private final FlixCore instance;
    private static Method getHandleMethod;
    private static Field pingField;

    public PingCommand(FlixCore instance) {
        this.instance = instance;
        initializeReflection();
    }

    @Override
    public boolean CMD(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ColorUtils.colorize("&cOnly players can use this command!"));
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("FlixCore.Ping")) {
            player.sendMessage(ColorUtils.colorize(instance.getMessagesConfig().getString("Error-Commands")));
            return true;
        }

        if (args.length != 0) {
            player.sendMessage(ColorUtils.colorize("&cUsage: /ping"));
            return true;
        }

        int ping = getPing(player);
        String color = getPingColor(ping);

        player.sendMessage(ColorUtils.colorize("&aPong! &7Your ping is: " + color + ping + "ms"));
        return true;
    }

    /**
     * Initializes reflection methods and fields once for performance.
     */
    private void initializeReflection() {
        if (getHandleMethod != null && pingField != null) return;

        try {
            String version = org.bukkit.Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
            Class<?> craftPlayerClass = Class.forName("org.bukkit.craftbukkit." + version + ".entity.CraftPlayer");
            getHandleMethod = craftPlayerClass.getMethod("getHandle");
            pingField = getHandleMethod.getReturnType().getField("ping");
        } catch (Exception e) {
            instance.getLogger().warning("[FlixCore] Failed to initialize ping reflection!");
        }
    }

    /**
     * Retrieves the player's ping using reflection.
     */
    private int getPing(Player player) {
        try {
            Object entityPlayer = getHandleMethod.invoke(player);
            return pingField.getInt(entityPlayer);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Returns color based on ping value.
     */
    private String getPingColor(int ping) {
        if (ping < 50) return "&a";
        if (ping < 100) return "&2";
        if (ping < 200) return "&e";
        if (ping < 300) return "&c";
        return "&4";
    }
}
