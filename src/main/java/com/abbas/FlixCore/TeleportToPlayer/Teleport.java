package com.abbas.FlixCore.TeleportToPlayer;

import com.abbas.FlixCore.Utiles.ColorUtils;
import com.abbas.FlixCore.api.APICommands;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Teleport implements APICommands {
    @Override
    public boolean CMD(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("Plugin.Admin")) {
            sender.sendMessage(ColorUtils.colorize("&cYou Not Allowed Use This Command"));
            return true;
        }
        if (sender instanceof Player p) {
            //like this tp M7wq ==  mahmoud <3
            if (args.length == 0) {
                p.sendMessage(ColorUtils.colorize("&c&lYou Need To Enter Some argument"));
                p.sendMessage(ColorUtils.colorize("&e&lTo Teleport To YourSelf! &d&l/tp<OtherPlayer>"));
                p.sendMessage(ColorUtils.colorize("&e&lTo Teleport Other /tp <Player> <OtherPlayers"));
            } else if (args.length == 1){
                Player targ = Bukkit.getPlayer(args[0]);
                try {
                    p.teleport(targ.getLocation());
                } catch (NullPointerException e) {
                    p.sendMessage(ColorUtils.colorize("&c&lPlayer Is Not Exist"));
                }
            } else if (args.length == 2) {
                Player playerSend = Bukkit.getPlayer(args[0]);
                Player target =  Bukkit.getPlayer(args[1]);
                try {
                    playerSend.teleport(target.getLocation());
                } catch (NullPointerException e) {
                    p.sendMessage(ColorUtils.colorize("&c&lPlayer Is Not Exist"));
                }
            }
        }
        return true;

    }
}
