package com.abbas.FlixCore.MainCommands;

import com.abbas.FlixCore.FlixCore;
import com.abbas.FlixCore.Utiles.ColorUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class HelpCommand implements CommandExecutor {
    private final HashMap<UUID, Long> cooldown = new HashMap<>();
    private final FlixCore instance;
    public HelpCommand(FlixCore instance) {
        this.instance = instance;
    }
    @Override
    public boolean onCommand(CommandSender se, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("help")) return false;
        if (!(se instanceof Player player)) {
            se.sendMessage(ColorUtils.colorize( "&cThis command can only be used by players!"));
            return true;
        }
        UUID uuid = player.getUniqueId();
        long cooldownTime = 10_000;
        long now = System.currentTimeMillis();
        if (cooldown.containsKey(uuid)) {
            long timeElapsed = now - cooldown.get(uuid);
            if (timeElapsed < cooldownTime) {
                long timeLeft = (cooldownTime - timeElapsed) / 1000;
                String cdMessage = instance.getMessagesConfig().getString("cooldown-message");
                if (cdMessage != null) {se.sendMessage(ColorUtils.colorize(cdMessage.replace("{time}", String.valueOf(timeLeft))));
                }
                return true;
            }
        }
        cooldown.put(uuid, now);
        List<String> helpMessages = instance.getMessagesConfig().getStringList("Help-Command");
        if (helpMessages == null || helpMessages.isEmpty()) {
            String notLoaded = FlixCore.getInstance().getMessagesConfig().getString("Messages-not-loaded");
            se.sendMessage(ColorUtils.colorize(Objects.requireNonNullElse(notLoaded, "&cMessages not loaded!")));
            return true;
        }
        helpMessages.forEach(line -> se.sendMessage(ColorUtils.colorize(line.replace("{Player}", player.getName()))));
        return true;
    }
}