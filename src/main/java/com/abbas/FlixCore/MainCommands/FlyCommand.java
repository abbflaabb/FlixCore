package com.abbas.FlixCore.MainCommands;

import com.abbas.FlixCore.FlixCore;
import com.abbas.FlixCore.Utiles.ColorUtils;
import com.abbas.FlixCore.api.APICommands;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.*;
public class FlyCommand implements APICommands {
    private final HashMap<UUID, Long> cooldown;
    private final Set<UUID> PlayerHasFly;
    private final FlixCore instance;

    public FlyCommand(FlixCore instance) {
        this.instance = instance;
        this.cooldown = new HashMap<>();
        this.PlayerHasFly = new HashSet<>();
    }
    @Override
    public boolean CMD(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) {
            String errorMessage = instance.getMessagesConfig().getString("Error-Commands");
            if (errorMessage != null) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', errorMessage));
            }
            return true;
        }
        long cooldownTime = 10000;
        if (cooldown.containsKey(p.getUniqueId())) {
            long timeElapsed = System.currentTimeMillis() - cooldown.get(p.getUniqueId());
            if (timeElapsed < cooldownTime) {
                long timeLeft = (cooldownTime - timeElapsed) / 1000;
                String cdMessage = instance.getMessagesConfig().getString("cooldown-message");
                if (cdMessage != null) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', cdMessage.replace("{time}", String.valueOf(timeLeft))));
                }
                return true;
            }
        }
        cooldown.put(p.getUniqueId(), System.currentTimeMillis());
        if (args.length == 0) {
            if (!p.hasPermission("Fly.command")) {
                p.sendMessage(ColorUtils.colorize("&c[Error] You Can't Use Command This Please No Try Again."));
                return true;
            }
            toggleFly(p, p);
        } else if (args.length == 1) {
            if (!p.hasPermission("Fly.command.others")) {
                p.sendMessage(ColorUtils.colorize("&c[Error] You Cant use This Command for Toggle Fly For Other Players! Please no try again"));
                return true;
            }
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                p.sendMessage(ColorUtils.colorize( "&c[Error]" + ColorUtils.colorize( "&5This Player is Not Found.")));
                return true;
            }
            toggleFly(target, p);
        }
        return true;
    }
    private void toggleFly(Player player, CommandSender sender) {
        UUID uuid = player.getUniqueId();
        if (!PlayerHasFly.contains(uuid)) {
            PlayerHasFly.add(uuid);
            player.setAllowFlight(true);

            String flyMessages = instance.getMessagesConfig().getString("Fly-Message-Enable");
            if (flyMessages != null) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', flyMessages.replace("{player}", player.getName())));
            }
        } else {
            PlayerHasFly.remove(uuid);
            player.setAllowFlight(false);
            String flyMessages = instance.getMessagesConfig().getString("Fly-Message-Disable");
            if (flyMessages != null) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', flyMessages.replace("{player}", player.getName())));
            }
        }
    }
}
