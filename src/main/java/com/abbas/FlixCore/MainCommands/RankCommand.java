package com.abbas.FlixCore.MainCommands;

import com.abbas.FlixCore.FlixCore;
import com.abbas.FlixCore.Utiles.ColorUtils;
import com.abbas.FlixCore.Utiles.PAPIUTILS;
import com.abbas.FlixCore.api.APICommands;
import e.com.abbas.LuckPermsAPI.Perms;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RankCommand implements APICommands {
    private final Map<UUID, Long> cooldown;
    private final FlixCore instance;

    public RankCommand(FlixCore instance) {
        this.instance = instance;
        this.cooldown = new HashMap<>();
    }

    @Override
    public boolean CMD(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("FlixCore.rank")) {
            sender.sendMessage(instance.getMessagesConfig().getString("Error-Commands"));
            return true;
        }
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

        String rank = Perms.getPlayerRank(p);
        p.sendMessage(ColorUtils.colorize("&a&lYour Rank is:" + rank));
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
