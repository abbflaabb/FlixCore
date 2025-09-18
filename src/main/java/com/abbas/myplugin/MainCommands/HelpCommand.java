package com.abbas.myplugin.MainCommands;

import com.abbas.myplugin.MyPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class HelpCommand implements CommandExecutor {
    private final HashMap<UUID, Long> cooldown;

    public HelpCommand() {
        this.cooldown = new HashMap<>();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("help")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "This command can only be used by players!");
                return true;
            }

            Player player = (Player) sender;
            List<String> helpMessages = MyPlugin.getInstance().getMessagesConfig().getStringList("Help-Command");

            long cooldownTime = 10000;
            if (!this.cooldown.containsKey(player.getUniqueId())) {
                this.cooldown.put(player.getUniqueId(), System.currentTimeMillis());
            } else {
                long timeElapsed = System.currentTimeMillis() - cooldown.get(player.getUniqueId());
                if (timeElapsed >= cooldownTime) {
                    this.cooldown.put(player.getUniqueId(), System.currentTimeMillis());
                } else {
                    long timeLeft = (cooldownTime - timeElapsed) / 1000;
                    String cdMessage = MyPlugin.getInstance().getMessagesConfig().getString("cooldown-message");
                    if (cdMessage != null) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                cdMessage.replace("{time}", String.valueOf(timeLeft))));
                    }
                    return true;
                }
            }

            if (helpMessages != null && !helpMessages.isEmpty()) {
                for (String line : helpMessages) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            line.replace("{Player}", sender.getName())));
                }
            } else {
                sender.sendMessage(ChatColor.RED + "No help message found in config!");
            }
            return true;
        }
        return false;
    }
}