package com.abbas.FlixCore.MainCommands;

import com.abbas.FlixCore.FlixCore;
import com.abbas.FlixCore.Utiles.ColorUtils;
import com.abbas.FlixCore.api.APICommands;
import com.abbas.FlixCore.manager.ChatLockManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public record ToggleChatLock(FlixCore instance) implements APICommands {

    @Override
    public boolean CMD(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (!player.hasPermission("FlixCore.LockChat")) {
                String errorMsg = instance.getMessagesConfig().getString("Error-Commands");
                if (errorMsg != null) {
                    player.sendMessage(ColorUtils.colorize(errorMsg));
                } else {
                    player.sendMessage(ColorUtils.colorize("&cYou don't have permission!"));
                }
                return true;
            }
        }
        ChatLockManager.togglechat();
        String status = ChatLockManager.isChatLocked() ? ColorUtils.colorize("&cChat Locked")
                : ColorUtils.colorize("&aChat Opened");
        Bukkit.broadcastMessage(ColorUtils.colorize("&e&lChange Status Chat to ") + status);

        return true;
    }
}
