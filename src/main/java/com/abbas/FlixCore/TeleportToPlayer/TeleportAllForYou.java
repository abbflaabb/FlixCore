package com.abbas.FlixCore.TeleportToPlayer;

import com.abbas.FlixCore.Utiles.ColorUtils;
import com.abbas.FlixCore.api.APICommands;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportAllForYou implements APICommands {
    @Override
    public boolean CMD(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("Plugin.Admin")) {
            sender.sendMessage(ColorUtils.colorize("&cYou Not Allowed Use This Command"));
            return true;
        }
///Packet Title
        if (sender instanceof Player player){
            if (Bukkit.getServer().getOnlinePlayers().size() ==1 ){
                player.sendMessage(ColorUtils.colorize("&c&lNo other Players are to Tp to You Right Now"));
            } else if (Bukkit.getServer().getOnlinePlayers().size() > 1) {
                int playersNumber = 0;
                for (Player p :Bukkit.getServer().getOnlinePlayers()) {
                    p.teleport(player.getLocation());
                    playersNumber++;
                }
                player.sendMessage(ColorUtils.colorize("&e&lTeleport All " + ColorUtils.colorize("&d&l" + (playersNumber -1)) + " Players To Your"));
            }
        }
        return true;
    }
}
