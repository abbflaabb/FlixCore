package com.abbas.FlixCore.MainCommands;

import com.abbas.FlixCore.FlixCore;
import com.abbas.FlixCore.Utiles.ColorUtils;
import com.abbas.FlixCore.api.APICommands;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public record MsgCommand(FlixCore instance) implements APICommands, TabCompleter {

    @Override
    public boolean CMD(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("FlixCore.Msg")) {
            sender.sendMessage(ColorUtils.colorize(instance.getMessagesConfig().getString("Error-Commands")));
            return true;
        }
        if (args.length < 2) {
            sender.sendMessage(ColorUtils.colorize("/&b&lUsage:" + ColorUtils.colorize("&e&l<msg>" + ColorUtils.colorize("&a&l<Player>"))));
            return true;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null || !target.isOnline()) {
            sender.sendMessage(ColorUtils.colorize(instance.getMessagesConfig().getString("PlayerNotFound")));
            return true;
        }
        if (sender instanceof Player && target.equals(sender)) {
            sender.sendMessage(ColorUtils.colorize("&c[Error] You Can't Send Message to you're self"));
        }

        StringBuilder messagebuilder = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            messagebuilder.append(args[i]).append(" ");
        }
        String message = messagebuilder.toString().trim();

        String senderName = sender instanceof Player ? sender.getName() : ColorUtils.colorize(("&c&l[Error], Console"));

        target.sendMessage(ColorUtils.colorize("&7[&6" + senderName + " &7-> &6You&7] &f" + message));
        sender.sendMessage(ColorUtils.colorize("&7[&6You &7-> &6" + target.getName() + "&7] &f" + message));

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            String input = args[0].toLowerCase();
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (sender instanceof Player && player.equals(sender)) {
                    continue;
                }
                if (player.getName().toLowerCase().startsWith(input)) {
                    completions.add(player.getName());
                }
            }
        }
        return completions;
    }
}
