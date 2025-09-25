package com.abbas.FlixCore.MainCommands;

import com.abbas.FlixCore.FlixCore;
import com.abbas.FlixCore.Utiles.ColorUtils;
import com.abbas.FlixCore.api.APICommands;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class SupportCommand implements APICommands {
    private final HashMap<UUID, Long> cooldown = new HashMap<>();
    @Override
    public boolean CMD(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) return true;
        UUID uuid = player.getUniqueId();
        if (cooldown.containsKey(uuid)) {
            long timeElapsed = System.currentTimeMillis() - cooldown.get(uuid);
            long cooldownTime = 10000;
            if (timeElapsed < cooldownTime) {
                long timeLeft = (cooldownTime - timeElapsed) / 1000;
                String cdMessage = FlixCore.getInstance().getMessagesConfig().getString("cooldown-message");
                if (cdMessage != null) {
                    sender.sendMessage(ColorUtils.colorize(cdMessage.replace("{time}", String.valueOf(timeLeft))));
                }
                return true;
            }
        }
        cooldown.put(uuid, System.currentTimeMillis());
        List<String> supportMessages = FlixCore.getInstance().getMessagesConfig().getStringList("Support-links");
        if (supportMessages != null && !supportMessages.isEmpty()) {
            for (String line : supportMessages) {
                sender.sendMessage(ColorUtils.colorize(line.replace("{Player}", sender.getName())));
            }
        } else {
            sender.sendMessage(FlixCore.getInstance().getMessagesConfig().getString("Messages-not-loaded"));
        }
        return true;
    }
}
