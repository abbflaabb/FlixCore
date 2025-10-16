package com.abbas.FlixCore.MainCommands;

import com.abbas.FlixCore.FlixCore;
import com.abbas.FlixCore.Utiles.ColorUtils;
import com.abbas.FlixCore.Utiles.PAPIUTILS;
import com.abbas.FlixCore.api.APICommands;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public class FlyCommand implements APICommands {
    private final Map<UUID, Long> cooldown;
    private final Set<UUID> playerHasFly;
    private final FlixCore instance;

    public FlyCommand(FlixCore instance) {
        this.instance = instance;
        this.cooldown = new HashMap<>();
        this.playerHasFly = new HashSet<>();
    }

    @Override
    public boolean CMD(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) {
            String errorMessage = instance.getMessagesConfig().getString("Error-Commands");
            if (errorMessage != null) {
                sender.sendMessage(ColorUtils.colorize(errorMessage));
            }
            return true;
        }

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

        if (args.length == 0) {
            if (!p.hasPermission("Fly.command")) {
                p.sendMessage(ColorUtils.colorize("&c[Error] You don't have permission to use this command."));
                return true;
            }
            toggleFly(p, p);
        } else if (args.length == 1) {
            if (!p.hasPermission("Fly.command.others")) {
                p.sendMessage(ColorUtils.colorize("&c[Error] You can't toggle flight for other players."));
                return true;
            }

            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                p.sendMessage(ColorUtils.colorize("&c[Error] &5That player is not online or doesn't exist."));
                return true;
            }
            toggleFly(target, p);
        }
        return true;
    }

    private void toggleFly(Player target, CommandSender sender) {
        UUID uuid = target.getUniqueId();
        if (!playerHasFly.contains(uuid)) {
            playerHasFly.add(uuid);
            target.setAllowFlight(true);

            String flyEnableMsg = instance.getMessagesConfig().getString("Fly-Message-Enable");
            if (flyEnableMsg != null) {
                sender.sendMessage(formatMessage(target, flyEnableMsg));
            }
        } else {
            playerHasFly.remove(uuid);
            target.setAllowFlight(false);

            String flyDisableMsg = instance.getMessagesConfig().getString("Fly-Message-Disable");
            if (flyDisableMsg != null) {
                sender.sendMessage(formatMessage(target, flyDisableMsg));
            }
        }
    }
    private String formatMessage(Player player, String message) {
        if (message == null || message.isEmpty()) return "";
        message = message.replace("{player}", player.getName());
        message = PAPIUTILS.apply(player, message);
        message = ColorUtils.colorize(message);
        return message;
    }
}
