package com.abbas.FlixCore.MainListeners;

import com.abbas.FlixCore.Utiles.ColorUtils;
import com.abbas.FlixCore.Utiles.PAPIUTILS;
import com.abbas.FlixCore.manager.ChatLockManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatLockListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (ChatLockManager.isChatLocked() && !player.hasPermission("ChatLock.bypass")) {
            String rawMessage = "&cChat now %chatlock_status%!";
            player.sendMessage(formatMessage(player, rawMessage));
            event.setCancelled(true);
        }
    }
    private String formatMessage(Player player, String message) {
        if (message == null || message.isEmpty()) return "";
        message = PAPIUTILS.apply(player, message);
        message = ColorUtils.colorize(message);

        return message;
    }
}
