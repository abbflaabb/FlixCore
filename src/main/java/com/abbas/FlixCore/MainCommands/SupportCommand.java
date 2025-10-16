package com.abbas.FlixCore.MainCommands;

import com.abbas.FlixCore.FlixCore;
import com.abbas.FlixCore.Utiles.ColorUtils;
import com.abbas.FlixCore.Utiles.PAPIUTILS;
import com.abbas.FlixCore.api.APICommands;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class SupportCommand implements APICommands {

    private final HashMap<UUID, Long> cooldown = new HashMap<>();
    private final FlixCore instance;

    public SupportCommand(FlixCore instance) {
        this.instance = instance;
    }

    @Override
    public boolean CMD(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ColorUtils.colorize("&cThis command can only be used by players!"));
            return true;
        }
        UUID uuid = player.getUniqueId();
        long cooldownTime = 10_000; // 10 seconds
        if (cooldown.containsKey(uuid)) {
            long timeElapsed = System.currentTimeMillis() - cooldown.get(uuid);
            if (timeElapsed < cooldownTime) {
                long timeLeft = (cooldownTime - timeElapsed) / 1000;
                String cdMessage = instance.getMessagesConfig().getString("cooldown-message");
                if (cdMessage != null) {
                    player.sendMessage(formatMessage(player, cdMessage.replace("{time}", String.valueOf(timeLeft))));
                }
                return true;
            }
        }
        cooldown.put(uuid, System.currentTimeMillis());
        List<String> supportMessages = instance.getMessagesConfig().getStringList("Support-links");
        if (supportMessages == null || supportMessages.isEmpty()) {
            String notLoaded = instance.getMessagesConfig().getString("Messages-not-loaded");
            player.sendMessage(ColorUtils.colorize(Objects.requireNonNullElse(notLoaded, "&cMessages not loaded!")));
            return true;
        }
        supportMessages.forEach(line -> player.sendMessage(formatMessage(player, line)));
        return true;
    }
    private String formatMessage(Player player, String message) {
        if (message == null || message.isEmpty()) return "";
        message = message.replace("{Player}", player.getName());
        message = PAPIUTILS.apply(player, message);
        message = ColorUtils.colorize(message);
        return message;
    }
}
