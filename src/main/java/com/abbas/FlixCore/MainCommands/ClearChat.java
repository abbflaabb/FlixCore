package com.abbas.FlixCore.MainCommands;

import com.abbas.FlixCore.FlixCore;
import com.abbas.FlixCore.Utiles.ColorUtils;
import com.abbas.FlixCore.Utiles.PAPIUTILS;
import com.abbas.FlixCore.api.APICommands;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ClearChat implements APICommands {
    private final FlixCore instance;
    private final Map<UUID, Long> cooldown;

    public ClearChat(FlixCore instance) {
        this.instance = instance;
        this.cooldown = new HashMap<>();
    }
    @Override
    public boolean CMD(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("FlixCore.ClearChat")) {
            int lines = 100;

            Player p = (Player) sender;
            long cooldownTime = 10000; // 10 seconds
            if (cooldown.containsKey(p.getUniqueId())) {
                long timeElapsed = System.currentTimeMillis() - cooldown.get(p.getUniqueId());
                if (timeElapsed < cooldownTime) {
                    long timeLeft = (cooldownTime - timeElapsed) / 1000;
                    String cdMessage = instance.getMessagesConfig().getString("cooldown-message");
                    if (cdMessage != null) {
                        p.sendMessage(formatMessage(p, cdMessage.replace("{time}", String.valueOf(timeLeft))));
                    }
                    return true;
                }
            }

            cooldown.put(p.getUniqueId(), System.currentTimeMillis());

            if (args.length > 0) {
                try {
                    lines = Integer.parseInt(args[0]);
                } catch (NumberFormatException e) {
                    sender.sendMessage(ColorUtils.colorize(instance.getMessagesConfig().getString("Invalid-number")));
                    return true;
                }
            }
            String clearMessage = ColorUtils.colorize(instance.getMessagesConfig().getString("Chat-cleared-message"));
            for (int i = 0; i < lines; i++) {
                for (CommandSender s : instance.getServer().getOnlinePlayers()) {
                    s.sendMessage(" ");
                }
            }
            {
                String formattedMessage = formatMessage((Player) sender, clearMessage);
                instance.getServer().broadcastMessage(formattedMessage);
            }
            return true;
        } else {
            sender.sendMessage(ColorUtils.colorize(instance.getMessagesConfig().getString("Error-Commands")));
        }
        return true;
    }
    private String formatMessage(Player player, String message) {
        if (message == null || message.isEmpty()) return "";
        message = message.replace("{player}", player.getName());
        message = PAPIUTILS.apply(player, message);
        message = ColorUtils.colorize(message);
        return message;
    }
}
