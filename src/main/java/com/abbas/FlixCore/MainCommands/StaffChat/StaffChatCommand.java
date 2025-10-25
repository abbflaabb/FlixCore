package com.abbas.FlixCore.MainCommands.StaffChat;

import com.abbas.FlixCore.FlixCore;
import com.abbas.FlixCore.Utiles.ColorUtils;
import com.abbas.FlixCore.api.APICommands;
import com.abbas.FlixCore.api.StaffChat;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public record StaffChatCommand(FlixCore instance) implements APICommands, TabCompleter {
    @Override
    public boolean CMD(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (!StaffChat.hasAnyPermission(player)) {
                sender.sendMessage(ColorUtils.colorize(instance.getMessagesConfig().getString("Error-Commands")));
                return true;
            }
        } else {
            if (!sender.hasPermission("FlixCore.StaffChat")) {
                sender.sendMessage(ColorUtils.colorize(instance.getMessagesConfig().getString("Error-Commands")));
                return true;
            }
        }
        if (args.length > 0 && args[0].equalsIgnoreCase("toggle")) {
            if (sender instanceof Player player) {
                boolean inStaffChat = instance.getStaffChatManager().toggleStaffChat(player);
                if (inStaffChat) {
                    StaffChat type = instance.getStaffChatManager().getStaffChatType(player);
                    sender.sendMessage(ColorUtils.colorize("&a&lYou are now in a" + type.name() + "&d&lStaffChat"));
                } else {
                    sender.sendMessage(ColorUtils.colorize("&c&l[Error] You are left from staff chat mode"));
                }
            } else {
                sender.sendMessage(ColorUtils.colorize("&cConsole Can't toggle staffchat"));
            }
            return true;
        }
        StringBuilder messageBuilder = new StringBuilder();
        for (String arg : args) {
            messageBuilder.append(arg).append(" ");
        }
        String message = messageBuilder.toString().trim();

        String senderName;
        StaffChat chatType;

        if (sender instanceof Player player) {
            senderName = player.getName();
            chatType = StaffChat.getStaffChatType(player);
        } else {
            senderName = "Console";
            chatType = StaffChat.OWNER;
            chatType = StaffChat.MANAGER;
            chatType = StaffChat.ADMIN;
            chatType = StaffChat.ADMINJR;
            chatType = StaffChat.STAFF;
        }

        String formattedMessage = chatType.format(senderName, message);

        int recipientCount = 0;
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (StaffChat.hasAnyPermission(player)) {
                player.sendMessage(formattedMessage);
                recipientCount++;
            }
        }

        Bukkit.getConsoleSender().sendMessage(formattedMessage);

        if (sender instanceof Player && recipientCount == 1) {
            sender.sendMessage(ColorUtils.colorize("&eNo other staff members are online to see your message."));
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            if (sender instanceof Player) {
                completions.add("toggle");
            }
            completions.add("<message>");
        }

        return completions;
    }

}
