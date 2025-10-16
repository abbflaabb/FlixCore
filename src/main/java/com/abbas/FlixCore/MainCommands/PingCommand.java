package com.abbas.FlixCore.MainCommands;

import com.abbas.FlixCore.FlixCore;
import com.abbas.FlixCore.Utiles.ColorUtils;
import com.abbas.FlixCore.api.APICommands;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PingCommand implements APICommands {
    private final FlixCore instance;

    public PingCommand(FlixCore instance) {
        this.instance = instance;
    }

    @Override
    public boolean CMD(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            if (sender.hasPermission("FlixCore.Ping")) {
                sender.sendMessage(ColorUtils.colorize("Pong! &bYour ping is: " + ((sender instanceof org.bukkit.entity.Player) ? getPing(((Player) sender).getPlayer()) : "N/A") + "ms"));
            } else {
                sender.sendMessage(ColorUtils.colorize(instance.getMessagesConfig().getString("Error-Commands")));
            }
        } else {
            sender.sendMessage("Usage: /ping");
        }

        return true;
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
